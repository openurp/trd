package org.openurp.rd.web.action.admin.project

import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.rd.project.model.RdProject

class TeachingAction extends RestfulAction[RdProject] {

  override protected def getQueryBuilder: OqlBuilder[RdProject] = {
    val query = super.getQueryBuilder
    query.where("rdProject.forCourse=false")
  }
}
