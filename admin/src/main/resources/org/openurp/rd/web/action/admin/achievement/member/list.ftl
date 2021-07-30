[#ftl]
[@b.head/]
[@b.grid items=rdAchievementMembers var="rdAchievementMember"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="20%" property="idx" title="次序" /]
    [@b.col width="40%" property="name" title="姓名"/]
    [@b.col width="35%" property="user.department.name" title="所在部门"/]
  [/@]
[/@]
[@b.foot/]
