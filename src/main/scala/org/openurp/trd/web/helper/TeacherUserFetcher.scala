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
