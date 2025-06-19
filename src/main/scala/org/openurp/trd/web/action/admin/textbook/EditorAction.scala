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

import org.beangle.commons.lang.Strings
import org.beangle.ems.app.Ems
import org.beangle.webmvc.view.View
import org.beangle.webmvc.support.action.RestfulAction
import org.openurp.base.model.User
import org.openurp.trd.achievement.model.TextbookEditor

class EditorAction extends RestfulAction[TextbookEditor] {

  override protected def editSetting(entity: TextbookEditor): Unit = {
    put("urp", Ems)
    super.editSetting(entity)
  }

  override protected def saveAndRedirect(entity: TextbookEditor): View = {
    entity.user match {
      case Some(u) => entity.name = entityDao.get(classOf[User], u.id).name
      case None => if (Strings.isEmpty(entity.name)) entity.name = "??"
    }
    super.saveAndRedirect(entity)
  }
}
