[#ftl]
[@b.head/]
[@b.grid items=rdAwardGrades var="rdAwardGrade"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="code" title="代码"]${rdAwardGrade.code}[/@]
    [@b.col width="20%" property="name" title="名称"][@b.a href="!info?id=${rdAwardGrade.id}"]${rdAwardGrade.name}[/@][/@]
    [@b.col width="25%" property="enName" title="英文名称"]${rdAwardGrade.enName!}[/@]
    [@b.col width="20%" property="beginOn" title="生效时间"]${rdAwardGrade.beginOn!}[/@]
    [@b.col width="20%" property="endOn" title="失效时间"]${rdAwardGrade.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
