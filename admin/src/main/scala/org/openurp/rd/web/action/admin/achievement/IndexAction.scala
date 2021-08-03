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
package org.openurp.rd.web.action.admin.achievement

import org.beangle.commons.collection.Collections
import org.beangle.commons.lang.Strings
import org.beangle.data.dao.OqlBuilder
import org.beangle.data.transfer.excel.ExcelSchema
import org.beangle.data.transfer.importer.ImportSetting
import org.beangle.data.transfer.importer.listener.ForeignerListener
import org.beangle.security.Securities
import org.beangle.webmvc.api.annotation.response
import org.beangle.webmvc.api.view.Stream
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.model.User
import org.openurp.rd.achievement.model.{RdAchievement, RdAchievementType}
import org.openurp.rd.code.model.{RdAwardGrade, RdLevel}
import org.openurp.rd.web.helper.RdAchievementImportListener

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

class IndexAction extends RestfulAction[RdAchievement] {
  override protected def indexSetting(): Unit = {
    put("achievementTypes", entityDao.getAll(classOf[RdAchievementType]))
    put("grades", entityDao.getAll(classOf[RdAwardGrade]))
    put("levels", entityDao.getAll(classOf[RdLevel]))
    super.indexSetting()
  }

  override protected def editSetting(entity: RdAchievement): Unit = {
    put("achievementTypes", entityDao.getAll(classOf[RdAchievementType]))
    if (!entity.persisted) {
      entity.orgName = entityDao.findBy(classOf[User], "code", List(Securities.user)).head.school.name
    }
    super.editSetting(entity)
  }

  override protected def getQueryBuilder: OqlBuilder[RdAchievement] = {
    val query = super.getQueryBuilder
    val awardYear = getInt("awardYear")
    val gradeId = getInt("grade.id")
    val levelId = getInt("level.id")

    if (awardYear.nonEmpty || gradeId.nonEmpty || levelId.nonEmpty) {
      val params = Collections.newMap[String, Any]
      val conditions = Collections.newBuffer[String]
      awardYear foreach { awardYear =>
        conditions += " award.awardYear=:year "
        params.put("year", awardYear)
      }
      gradeId foreach { gid =>
        conditions += " award.grade.id=:gradeId "
        params.put("gradeId", gid)
      }
      levelId foreach { lid =>
        conditions += " award.level.id=:levelId "
        params.put("levelId", lid)
      }
      val condition = "exists(from rdAchievement.awards award where " + conditions.mkString(" and ") + ")"
      query.where(condition)
      query.params(params)
    }
    get("memberName") foreach { memberName =>
      if (Strings.isNotBlank(memberName)) {
        query.where("exists(from rdAchievement.members as m where m.name like :memberName)", "%" + memberName + "%")
      }
    }
    getBoolean("hasExternalUser") foreach { hasExternalUser =>
      query.where((if (hasExternalUser) "" else " not ") + "exists(from rdAchievement.members as m where m.user is null)")
    }
    query
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    val fl = new ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    setting.listeners = List(fl, new RdAchievementImportListener(entityDao))
  }

  @response
  def downloadTemplate(): Any = {
    val achievementTypes = entityDao.search(OqlBuilder.from(classOf[RdAchievementType], "p").orderBy("p.name")).map(_.name)
    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title(s"教学成果项目信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("成果编号", "rdAchievement.code").length(15).required().remark("≤20位")
    sheet.add("成果名称", "rdAchievement.name").length(150).required()
    sheet.add("完成人姓名(或工号)", "memberNames").length(100).required()
    sheet.add("成果类型", "rdAchievement.achievementType.name").ref(achievementTypes).required()
    sheet.add("完成单位", "rdAchievement.orgName").length(300).required()
    sheet.add("科类代码", "rdProject.categoryCode").length(4)

    val code = schema.createScheet("数据字典")
    code.add("成果类型").data(achievementTypes)
    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", s"教学成果模板.xlsx")
  }
}
