[#ftl]
[@b.head/]
[@b.grid items=rdAchievements var="rdAchievement"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    bar.addItem("导入",action.method('importForm'));
  [/@]
  [@b.row]
      [@b.boxcol /]
      [@b.col width="6%" property="code" title="编号"]${rdAchievement.code}[/@]
      [@b.col width="39%" property="name" title="名称"][@b.a href="!info?id=${rdAchievement.id}"]${rdAchievement.name}[/@][/@]
      [@b.col width="10%" property="achievementType.name" title="成果类型"/]
      [@b.col width="25%" property="memberNames" title="完成人"/]
      [@b.col width="15%" property="organization" title="完成单位"/]
  [/@]
[/@]
[@b.foot/]
