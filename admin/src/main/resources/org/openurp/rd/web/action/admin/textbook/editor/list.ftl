[#ftl]
[@b.head/]
[@b.grid items=textbookEditors var="textbookEditor"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol width="10%"/]
    [@b.col width="15%" property="idx" title="次序" /]
    [@b.col width="15%" property="chief" title="是否主编"/]
    [@b.col width="25%" property="name" title="姓名"/]
    [@b.col width="35%" property="user.department.name" title="所在部门"/]
  [/@]
[/@]
[@b.foot/]
