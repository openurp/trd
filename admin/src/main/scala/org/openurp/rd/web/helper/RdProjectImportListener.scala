/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openurp.rd.web.helper

import org.beangle.commons.collection.Collections
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.data.transfer.importer.{ImportListener, ImportResult}
import org.openurp.base.model.User
import org.openurp.rd.project.model.{RdProject, RdProjectMember}

import java.time.Instant

class RdProjectImportListener(entityDao: EntityDao, forCourse: Boolean) extends ImportListener {
  override def onStart(tr: ImportResult): Unit = {}

  override def onFinish(tr: ImportResult): Unit = {}

  override def onItemStart(tr: ImportResult): Unit = {
    transfer.curData.get("rdProject.code") foreach { code =>
      val cs = entityDao.findBy(classOf[RdProject], "code", List(code))
      if (cs.nonEmpty) {
        transfer.current = cs.head
      }
    }
  }

  private def getTeacherUserByName(name: Any): (Option[User], String) = {
    val query = OqlBuilder.from(classOf[User], "u")
    query.where("u.code=:code", name)
    val rs = entityDao.search(query)
    if (rs.size == 1) {
      (rs.headOption, null)
    } else {
      val query = OqlBuilder.from(classOf[User], "u")
      query.where("u.name=:name", name)
      query.where("u.category.name not like '%学生%'")
      val rs = entityDao.search(query)
      if (rs.size == 1) {
        (rs.headOption, null)
      } else if (rs.isEmpty) {
        (None, name.toString)
      } else {
        val users = rs.map(x => x.code).mkString(",")
        (None, s"$name($users)")
      }
    }
  }

  private def processNames(str: Any): Array[String] = {
    var names = Strings.replace(str.toString, "，", ",")
    Strings.split(Strings.replace(names, "、", ","))
  }

  override def onItemFinish(tr: ImportResult): Unit = {
    val project = transfer.current.asInstanceOf[RdProject]
    project.forCourse = forCourse

    project.updatedAt = Instant.now
    var leaders = Collections.newBuffer[User]
    var leaderNames: Set[String] = null
    transfer.curData.get("leaderName") foreach { leaderName =>
      leaderNames = processNames(leaderName).toSet
      leaderNames foreach { name =>
        val rs = getTeacherUserByName(name)
        if (Strings.isEmpty(rs._2)) {
          leaders += rs._1.get
        } else {
          tr.addFailure("找不到唯一的负责人", rs._2)
        }
      }
    }

    if (null != leaders && project.department != null && project.level != null && project.category != null && project.status != null) {
      project.members.clear()
      project.leaders.clear()
      project.leaders ++= leaders
      transfer.curData.get("memberNames") foreach { memberNames =>
        if (null != memberNames) {
          var names = Strings.replace(memberNames.toString, "，", ",")
          names = Strings.replace(names, "、", ",")
          var idx = 1
          Strings.split(names).foreach { name =>
            if (!leaderNames.contains(name)) {
              val rs = getTeacherUserByName(name)
              val member = new RdProjectMember()
              member.idx = idx
              if (Strings.isEmpty(rs._2)) {
                member.user = Some(rs._1.get)
                member.name= rs._1.get.name
              } else {
                member.name = name
                tr.addFailure("找不到唯一的参与人", rs._2)
              }
              member.project = project
              project.members += member
              idx += 1
            }
          }
        }
      }
      entityDao.saveOrUpdate(project)
    }
  }
}
