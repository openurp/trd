[#ftl]
[@b.head/]
[@b.grid items=textbookAwards var="textbookAward"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="awardOn" title="获奖年月" /]
    [@b.col width="45%" property="name" title="奖项名称"/]
    [@b.col width="15%" property="level.name" title="获奖级别"/]
    [@b.col width="15%" property="grade.name" title="获奖等级"/]
  [/@]
[/@]
[@b.foot/]
