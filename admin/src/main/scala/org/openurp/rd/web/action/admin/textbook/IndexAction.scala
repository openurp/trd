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
package org.openurp.rd.web.action.admin.textbook

import org.beangle.webmvc.api.view.View
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.edu.model.Textbook
import org.openurp.rd.achievement.model.TextbookAchievement

class IndexAction extends RestfulAction[TextbookAchievement] {

  override protected def saveAndRedirect(entity: TextbookAchievement): View = {
    get("textbook.isbn") foreach { isbn =>
      entity.textbook = entityDao.findBy(classOf[Textbook], "isbn", List(isbn)).headOption.orNull
    }
    super.saveAndRedirect(entity)
  }
}
