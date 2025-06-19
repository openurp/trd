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

package org.openurp.trd.web.action.admin.project

import org.beangle.commons.activation.MediaTypes
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.doc.excel.schema.ExcelSchema
import org.beangle.doc.transfer.importer.ImportSetting
import org.beangle.doc.transfer.importer.listener.ForeignerListener
import org.beangle.ems.app.Ems
import org.beangle.webmvc.annotation.response
import org.beangle.webmvc.view.{Stream, View}
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport, RestfulAction}
import org.openurp.base.model.{Department, User}
import org.openurp.code.service.impl.CodeServiceImpl
import org.openurp.code.trd.model.RdLevel
import org.openurp.trd.project.code.{RdProjectCategory, RdProjectStatus}
import org.openurp.trd.project.model.*
import org.openurp.trd.web.helper.RdProjectImportListener

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.time.{Instant, YearMonth}

abstract class AbstractProjectAction extends RestfulAction[RdProject], ExportSupport[RdProject], ImportSupport[RdProject] {
  var codeService: CodeServiceImpl = _
  var forCourse: Boolean = _

  var projectName: String = _

  override def indexSetting(): Unit = {
    put("departments", entityDao.getAll(classOf[Department]))
    put("levels", entityDao.getAll(classOf[RdLevel]))
    put("statuses", entityDao.getAll(classOf[RdProjectStatus]))
    val query = OqlBuilder.from(classOf[RdProjectCategory], "c")
    query.where("c.forCourse=:forCourse", forCourse)
    put("categories", entityDao.search(query))

    super.indexSetting()
  }

  override protected def getQueryBuilder: OqlBuilder[RdProject] = {
    val query = super.getQueryBuilder
    query.where("rdProject.forCourse=:forCourse", forCourse)
    getInt("beginYear") foreach { beginYear =>
      query.where("year(rdProject.beginOn)=:year", beginYear)
    }
    get("leaderName") foreach { leaderName =>
      if (Strings.isNotBlank(leaderName)) {
        query.where("exists(from rdProject.leaders as l where l.name like :leaderName)", "%" + leaderName + "%")
      }
    }
    get("memberName") foreach { memberName =>
      if (Strings.isNotBlank(memberName)) {
        query.where("exists(from rdProject.members as m where m.name like :memberName)", "%" + memberName + "%")
      }
    }
    getBoolean("hasExternalUser") foreach { hasExternalUser =>
      query.where((if (hasExternalUser) "" else " not ") + "exists(from rdProject.members as m where m.user is null)")
    }
    query
  }

  override def editSetting(entity: RdProject): Unit = {
    put("departments", entityDao.getAll(classOf[Department]))
    put("levels", entityDao.getAll(classOf[RdLevel]))
    put("statuses", entityDao.getAll(classOf[RdProjectStatus]))
    val query = OqlBuilder.from(classOf[RdProjectCategory], "c")
    query.where("c.forCourse=:forCourse", forCourse)
    put("categories", entityDao.search(query))
    put("urp", Ems)
    val members = entity.members.filter(_.user.nonEmpty).map(_.user)
    put("members", members)
    super.editSetting(entity)
  }

  override protected def saveAndRedirect(entity: RdProject): View = {
    entity.forCourse = forCourse
    entity.leaders.clear()
    val leaderIds = getLongIds("leader").toSet
    entity.leaders ++= entityDao.find(classOf[User], leaderIds)

    val memberIds = getLongIds("member")
    entity.members --= entity.members.filter(x => x.user.nonEmpty)
    var idx = 1
    memberIds foreach { mId =>
      if (!leaderIds.contains(mId)) {
        val member = new RdProjectMember()
        member.idx = idx
        member.user = Some(entityDao.get(classOf[User], mId))
        member.name = member.user.get.name
        member.project = entity
        entity.members += member
        idx += 1
      }
    }
    entity.updatedAt = Instant.now
    super.saveAndRedirect(entity)
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    val fl = new ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    setting.listeners = List(fl, new RdProjectImportListener(entityDao, forCourse))
  }

  @response
  def downloadTemplate(): Any = {
    val statuses = entityDao.search(OqlBuilder.from(classOf[RdProjectStatus], "p").orderBy("p.name")).map(_.name)
    val levels = entityDao.search(OqlBuilder.from(classOf[RdLevel], "bc").orderBy("bc.name")).map(_.name)
    val departs = entityDao.search(OqlBuilder.from(classOf[Department], "bt").orderBy("bt.name")).map(_.name)
    val categories = entityDao.search(OqlBuilder.from(classOf[RdProjectCategory], "bc").orderBy("bc.name")).map(_.name)

    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title(s"${projectName}项目信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("项目编号", "rdProject.code").length(15).required().remark("≤10位")
    sheet.add("项目名称", "rdProject.name").length(150).required()
    sheet.add("负责人姓名(或工号)", "leaderName").length(30).required()
    sheet.add("建设院系", "rdProject.department.name").ref(departs).required()
    sheet.add("项目级别", "rdProject.level.name").ref(levels).required()
    sheet.add("项目类别", "rdProject.category.name").ref(categories).required()
    sheet.add("资金", "rdProject.funds").required().decimal()

    sheet.add("立项年月", "rdProject.beginOn").required().date("YYYY-MM").asType(classOf[YearMonth])
    sheet.add("应结项年月", "rdProject.endOn").required().date("YYYY-MM").asType(classOf[YearMonth])
    sheet.add("实际结项年月", "rdProject.finishedOn").date("YYYY-MM").asType(classOf[YearMonth])

    sheet.add("项目状态", "rdProject.status.name").ref(statuses).required()

    sheet.add("参与人姓名(或工号)列表（按次序）", "memberNames")
    sheet.add("备注", "rdProject.remark")

    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.ApplicationXlsx, s"${projectName}项目模板.xlsx")
  }
}
