/*
 * Copyright (C) 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.openurp.trd.web.helper

import org.beangle.commons.collection.Collections
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.data.transfer.importer.{ImportListener, ImportResult}
import org.openurp.base.model.User
import org.openurp.trd.project.model.{RdProject, RdProjectMember}

import java.time.Instant

class RdProjectImportListener(entityDao: EntityDao, forCourse: Boolean) extends ImportListener {

  private val teacherUserFetcher = new TeacherUserFetcher(entityDao)

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


  override def onItemFinish(tr: ImportResult): Unit = {
    val project = transfer.current.asInstanceOf[RdProject]
    project.forCourse = forCourse

    project.updatedAt = Instant.now
    val leaders = Collections.newBuffer[User]
    var leaderNames: Set[String] = null
    transfer.curData.get("leaderName") foreach { leaderName =>
      leaderNames = teacherUserFetcher.processNames(leaderName).toSet
      leaderNames foreach { name =>
        val rs = teacherUserFetcher.getUserByName(name)
        if (Strings.isEmpty(rs._2)) {
          leaders += rs._1.get
        } else {
          tr.addFailure("找不到唯一的负责人", rs._2)
        }
      }
    }

    if (leaders.nonEmpty && project.department != null && project.level != null && project.category != null && project.status != null) {
      project.leaders.clear()
      project.leaders ++= leaders
      transfer.curData.get("memberNames") foreach { memberNames =>
        if (null != memberNames) {
          var idx = 1
          teacherUserFetcher.processNames(memberNames).foreach { name =>
            if (!leaderNames.contains(name)) {
              val rs = teacherUserFetcher.getUserByName(name)
              val member = project.getMember(idx) match {
                case Some(m) => m
                case None =>
                  val m = new RdProjectMember()
                  m.idx = idx
                  m.project = project
                  project.members += m
                  m
              }
              if (Strings.isEmpty(rs._2)) {
                member.user = Some(rs._1.get)
                member.name = rs._1.get.name
              } else {
                member.name = name
                tr.addFailure("找不到唯一的参与人", rs._2)
              }
              idx += 1
            }
          }
        }
      }
      entityDao.saveOrUpdate(project)
    }
  }
}
