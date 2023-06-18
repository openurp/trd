[#ftl]
[@b.head/]
[@b.grid items=teachingTeams var="teachingTeam"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    bar.addItem("导入",action.method('importForm'));
    bar.addItem("${b.text("action.export")}",action.exportData("code:编号,name:项目名称,leaderNames:负责人,department.name:所在院系,level.name:级别,beginOn:立项年月,memberNames:团队成员,remark:备注",null,'fileName=团队信息'));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="7%" property="code" title="编号"/]
    [@b.col width="20%" property="name" title="名称"][@b.a href="!info?id=${teachingTeam.id}"]${teachingTeam.name}[/@][/@]
    [@b.col width="8%" property="leaderNames" title="带头人"/]
    [@b.col width="10%" property="department.name" title="所在院系"/]
    [@b.col property="memberNames" title="团队成员"/]
    [@b.col width="8%" property="level.name" title="级别"/]
    [@b.col width="12%" property="awardTitle" title="获奖名称"]
      <span style="font-size:0.8em">${teachingTeam.awardTitle!}</span>
    [/@]
    [@b.col width="8%" property="beginOn" title="立项年月"/]
  [/@]
  [/@]
[@b.foot/]
