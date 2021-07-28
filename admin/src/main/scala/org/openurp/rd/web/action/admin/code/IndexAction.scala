package org.openurp.rd.web.action.admin.code

import org.beangle.webmvc.api.action.ActionSupport
import org.beangle.webmvc.api.view.View

class IndexAction extends ActionSupport {

  def index: View = {
    forward()
  }
}