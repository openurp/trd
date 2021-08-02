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

import org.beangle.data.dao.{EntityDao, OqlBuilder}
import org.beangle.data.transfer.importer.{ImportListener, ImportResult}
import org.openurp.rd.achievement.model.{RdAchievement, RdAchievementAward, TextbookAchievement, TextbookAward}

class TextbookAwardImportListener(entityDao: EntityDao) extends ImportListener {
  override def onStart(tr: ImportResult): Unit = {}

  override def onFinish(tr: ImportResult): Unit = {}

  override def onItemStart(tr: ImportResult): Unit = {
    transfer.curData.get("textbook.isbn") foreach { code =>
      val cs = entityDao.findBy(classOf[TextbookAchievement], "textbook.isbn", List(code))
      if (cs.nonEmpty) {
        val query = OqlBuilder.from(classOf[TextbookAward], "award")
        query.where("award.achievement=:ac", cs.head)
        query.where("award.awardYear=:year", transfer.curData.get("rdAchievementAward.awardYear").getOrElse("0").toString.toInt)
        query.where("award.name=:name", transfer.curData.get("rdAchievementAward.name").getOrElse("null"))
        val awards = entityDao.search(query)
        transfer.current = awards.headOption match {
          case None => val award = new TextbookAward
            award.achievement = cs.head
            award
          case Some(award) => award
        }
      }
    }
  }

  override def onItemFinish(tr: ImportResult): Unit = {
    val award = transfer.current.asInstanceOf[RdAchievementAward]
    if (null == award.achievement || !award.achievement.persisted) {
      tr.addFailure("找不到唯一的教学成果", transfer.curData.get("achievement.code").getOrElse(""))
    } else {
      if(null!=award.awardOn && award.awardOn.nonEmpty){
        award.awardYear = award.awardOn.get.getYear
      }
      if (award.grade != null && award.level != null) entityDao.saveOrUpdate(award)
    }
  }
}
