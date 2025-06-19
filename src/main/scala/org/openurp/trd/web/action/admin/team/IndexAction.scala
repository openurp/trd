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

package org.openurp.trd.web.action.admin.team

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
import org.openurp.trd.team.model.{TeachingTeam, TeachingTeamMember}
import org.openurp.trd.web.helper.TeachingTeamImportListener

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import java.time.{Instant, YearMonth}

class IndexAction extends RestfulAction[TeachingTeam], ExportSupport[TeachingTeam], ImportSupport[TeachingTeam] {
  var codeService: CodeServiceImpl = _

  override def indexSetting(): Unit = {
    put("departments", entityDao.getAll(classOf[Department]))
    put("levels", entityDao.getAll(classOf[RdLevel]))
    super.indexSetting()
  }

  override protected def getQueryBuilder: OqlBuilder[TeachingTeam] = {
    val query = super.getQueryBuilder
    get("leaderName") foreach { leaderName =>
      if (Strings.isNotBlank(leaderName)) {
        query.where("exists(from teachingTeam.leaders as l where l.name like :leaderName)", "%" + leaderName + "%")
      }
    }
    get("memberName") foreach { memberName =>
      if (Strings.isNotBlank(memberName)) {
        query.where("exists(from teachingTeam.members as m where m.user.name like :memberName)", "%" + memberName + "%")
      }
    }
    getInt("beginYear") foreach { beginYear =>
      query.where("year(teachingTeam.beginOn)=:year", beginYear)
    }
    query
  }

  override def editSetting(entity: TeachingTeam): Unit = {
    put("departments", entityDao.getAll(classOf[Department]))
    put("levels", entityDao.getAll(classOf[RdLevel]))
    put("urp", Ems)
    val members = entity.members map (_.user)
    put("members", members)
    super.editSetting(entity)
  }

  override protected def saveAndRedirect(entity: TeachingTeam): View = {
    entity.leaders.clear()
    val leaderIds = getLongIds("leader").toSet
    entity.leaders ++= entityDao.find(classOf[User], leaderIds)

    val memberIds = getLongIds("member")
    entity.members.clear()
    var idx = 1
    memberIds foreach { mId =>
      if (!leaderIds.contains(mId)) {
        val member = new TeachingTeamMember()
        member.idx = idx
        member.user = entityDao.get(classOf[User], mId)
        member.team = entity
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
    setting.listeners = List(fl, new TeachingTeamImportListener(entityDao))
  }

  @response
  def downloadTemplate(): Any = {
    val levels = entityDao.search(OqlBuilder.from(classOf[RdLevel], "bc").orderBy("bc.name")).map(_.name)
    val departs = entityDao.search(OqlBuilder.from(classOf[Department], "bt").orderBy("bt.name")).map(_.name)

    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title(s"教学团队信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("团队编号", "teachingTeam.code").length(15).required().remark("≤10位")
    sheet.add("项团队目名称", "teachingTeam.name").length(150).required()
    sheet.add("带头人姓名(或工号)", "leaderName").length(30).required()
    sheet.add("所在院系", "teachingTeam.department.name").ref(departs).required()
    sheet.add("团队级别", "teachingTeam.level.name").ref(levels).required()
    sheet.add("成员姓名(或工号)列表（按次序）", "memberNames")
    sheet.add("获奖信息", "teachingTeam.awardTitle").length(50)
    sheet.add("立项年月", "teachingTeam.beginOn").date("YYYY-MM").asType(classOf[YearMonth])
    sheet.add("备注", "teachingTeam.remark")
    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.ApplicationXlsx, "教学团队信息模板.xlsx")
  }
}
