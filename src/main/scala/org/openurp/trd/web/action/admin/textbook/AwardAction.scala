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

package org.openurp.trd.web.action.admin.textbook

import org.beangle.commons.activation.MediaTypes
import org.beangle.doc.excel.schema.ExcelSchema
import org.beangle.doc.transfer.importer.ImportSetting
import org.beangle.doc.transfer.importer.listener.ForeignerListener
import org.beangle.webmvc.annotation.response
import org.beangle.webmvc.view.Stream
import org.beangle.webmvc.support.action.{ExportSupport, ImportSupport, RestfulAction}
import org.openurp.code.trd.model.{RdAwardGrade, RdLevel}
import org.openurp.trd.achievement.model.TextbookAward
import org.openurp.trd.web.helper.TextbookAwardImportListener

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

class AwardAction extends RestfulAction[TextbookAward], ExportSupport[TextbookAward], ImportSupport[TextbookAward] {

  override protected def editSetting(entity: TextbookAward): Unit = {
    put("grades", entityDao.getAll(classOf[RdAwardGrade]))
    put("levels", entityDao.getAll(classOf[RdLevel]))
    super.editSetting(entity)
  }

  protected override def configImport(setting: ImportSetting): Unit = {
    val fl = new ForeignerListener(entityDao)
    fl.addForeigerKey("name")
    setting.listeners = List(fl, new TextbookAwardImportListener(entityDao))
  }

  @response
  def downloadTemplate(): Any = {
    val grades = entityDao.getAll(classOf[RdAwardGrade]).map(_.name)
    val levels = entityDao.getAll(classOf[RdLevel]).map(_.name)

    val schema = new ExcelSchema()
    val sheet = schema.createScheet("数据模板")
    sheet.title(s"教材获奖信息模板")
    sheet.remark("特别说明：\n1、不可改变本表格的行列结构以及批注，否则将会导入失败！\n2、必须按照规格说明的格式填写。\n3、可以多次导入，重复的信息会被新数据更新覆盖。\n4、保存的excel文件名称可以自定。")
    sheet.add("ISBN", "textbook.isbn").length(15).required().remark("≤30位")
    sheet.add("获奖年月", "textbookAward.awardOn").length(4).remark("如有可以不填获奖年份")
    sheet.add("获奖名称", "textbookAward.name").length(300).required()
    sheet.add("获奖等级", "textbookAward.levels.name").ref(levels).required()
    sheet.add("获奖级别", "textbookAward.grade.name").ref(grades).required()
    val os = new ByteArrayOutputStream()
    schema.generate(os)
    Stream(new ByteArrayInputStream(os.toByteArray), MediaTypes.ApplicationXlsx, s"教材获奖模板.xlsx")
  }
}
