package org.openurp.rd.web.helper

import org.beangle.commons.lang.Strings
import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.openurp.base.model.User

class TeacherUserFetcher(entityDao: EntityDao) {

  def getUserByName(name: Any): (Option[User], String) = {
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

  def processNames(str: Any): Array[String] = {
    val names = Strings.replace(str.toString, "，", ",")
    Strings.split(Strings.replace(names, "、", ","))
  }
}
