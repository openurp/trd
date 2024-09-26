[#ftl]
[@b.head/]
[@b.grid items=rdLevels var="rdLevel"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="code" title="代码"]${rdLevel.code}[/@]
    [@b.col width="20%" property="name" title="名称"][@b.a href="!info?id=${rdLevel.id}"]${rdLevel.name}[/@][/@]
    [@b.col width="25%" property="enName" title="英文名称"]${rdLevel.enName!}[/@]
    [@b.col width="20%" property="beginOn" title="生效时间"]${rdLevel.beginOn!}[/@]
    [@b.col width="20%" property="endOn" title="失效时间"]${rdLevel.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
