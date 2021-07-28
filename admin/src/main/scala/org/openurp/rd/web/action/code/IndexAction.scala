package org.openurp.rd.web.action.code

import org.beangle.webmvc.api.action.ActionSupport
import org.beangle.webmvc.api.view.View

class IndexAction extends ActionSupport {

  def index: View = {
    forward()
  }
}