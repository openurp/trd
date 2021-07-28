package org.openurp.rd.web.action.admin.code

import org.beangle.cdi.bind.BindModule

class DefaultModule extends BindModule {
  override protected def binding(): Unit = {
    bind(classOf[IndexAction], classOf[ProjectStatusAction])
    bind(classOf[ProjectLevelAction], classOf[ProjectCategoryAction])
  }
}
