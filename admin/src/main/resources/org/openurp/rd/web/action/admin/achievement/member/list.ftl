[#ftl]
[@b.head/]
[@b.grid items=rdAchievementMembers var="rdAchievementMember"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol width="10%"/]
    [@b.col width="15%" property="idx" title="次序" /]
    [@b.col width="30%" property="name" title="姓名"/]
    [@b.col width="45%" property="user.department.name" title="所在部门"/]
  [/@]
[/@]
[@b.foot/]
