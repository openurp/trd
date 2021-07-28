package org.openurp.rd.web.helper

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

  override def onItemFinish(tr: ImportResult): Unit = {
    val project = transfer.current.asInstanceOf[RdProject]
    project.forCourse = forCourse

    project.updatedAt = Instant.now
    var leader: User = null
    transfer.curData.get("leaderName") foreach { leaderName =>
      val rs = getTeacherUserByName(leaderName)
      if (Strings.isEmpty(rs._2)) {
        leader = rs._1.get
      } else {
        tr.addFailure("找不到唯一的负责人", rs._2)
      }
    }

    if (null != leader && project.department != null && project.level != null && project.category != null && project.status != null) {
      project.members.clear()
      project.leader = leader
      val errors = tr.errors
      transfer.curData.get("memberNames") foreach { memberNames =>
        if (null != memberNames) {
          var names = Strings.replace(memberNames.toString, "，", ",")
          names = Strings.replace(names, "、", ",")
          var idx = 1
          Strings.split(names).foreach { name =>
            if (name != leader.name) {
              val rs = getTeacherUserByName(name)
              if (Strings.isEmpty(rs._2)) {
                val member = new RdProjectMember()
                member.idx = idx
                member.user = rs._1.get
                member.project = project
                project.members += member
                idx += 1
              } else {
                tr.addFailure("找不到唯一的参与人", rs._2)
              }
            }
          }
        }
      }
      if (tr.errors == errors) {
        entityDao.saveOrUpdate(project)
      }
    }
  }
}