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

package org.openurp.trd.web.action.admin.code

import org.beangle.commons.cdi.BindModule

class DefaultModule extends BindModule {
  override protected def binding(): Unit = {
    bind(classOf[IndexAction], classOf[ProjectStatusAction])
    bind(classOf[RdLevelAction], classOf[ProjectCategoryAction])
    bind(classOf[AwardGradeAction],classOf[AchievementTypeAction])
  }
}
