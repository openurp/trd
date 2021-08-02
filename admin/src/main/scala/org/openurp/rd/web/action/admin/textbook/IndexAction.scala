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
