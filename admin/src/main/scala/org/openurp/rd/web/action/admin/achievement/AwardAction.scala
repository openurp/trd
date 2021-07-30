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

import org.beangle.webmvc.api.view.View
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.rd.achievement.model.RdAchievementAward
import org.openurp.rd.code.model.{RdAwardGrade, RdLevel}

class AwardAction extends RestfulAction[RdAchievementAward] {

  override protected def editSetting(entity: RdAchievementAward): Unit = {
    put("grades", entityDao.getAll(classOf[RdAwardGrade]))
    put("levels", entityDao.getAll(classOf[RdLevel]))
    super.editSetting(entity)
  }

  override protected def saveAndRedirect(entity: RdAchievementAward): View = {
    entity.awardOn match {
      case Some(awardOn) => entity.awardYear = awardOn.getYear
      case None =>
    }
    super.saveAndRedirect(entity)
  }
}