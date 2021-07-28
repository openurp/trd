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
package org.openurp.rd.web.action.admin.project

import org.beangle.data.dao.OqlBuilder
import org.beangle.ems.app.Ems
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.model.Department
import org.openurp.code.service.impl.CodeServiceImpl
import org.openurp.rd.project.model.{RdProject, RdProjectCategory, RdProjectLevel, RdProjectStatus}

class CourseAction extends RestfulAction[RdProject] {
  var codeService: CodeServiceImpl = _

  override protected def getQueryBuilder: OqlBuilder[RdProject] = {
    val query = super.getQueryBuilder
    query.where("rdProject.forCourse=true")
  }

  override def editSetting(entity: RdProject): Unit = {
    put("departments", entityDao.getAll(classOf[Department]))
    put("levels", entityDao.getAll(classOf[RdProjectLevel]))
    put("statuses",entityDao.getAll(classOf[RdProjectStatus]))
    val query = OqlBuilder.from(classOf[RdProjectCategory], "c")
    query.where("c.forCourse=true")
    put("categories", entityDao.search(query))
    put("urp",Ems)
    val members=entity.members map(_.user)
    put("members",members)
    super.editSetting(entity)
  }
}
