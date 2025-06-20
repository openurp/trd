[#ftl]
[@b.head/]
[@b.grid items=rdProjects var="rdProject"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    bar.addItem("导入",action.method('importForm'));
    bar.addItem("${b.text("action.export")}",action.exportData("code:编号,name:项目名称,leaderNames:负责人,department.name:建设院系,level.name:项目级别,category.name:项目类别,beginOn:立项年月,endOn:应结项年月,finishedOn:实际结项年月,memberNames:参与人,status.name:状态,funds:资金,remark:备注",null,'fileName=课程项目信息'));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="9%" property="code" title="编号"/]
    [@b.col property="name" title="名称"][@b.a href="!info?id=${rdProject.id}"]${rdProject.name}[/@][/@]
    [@b.col width="8%" property="leaderNames" title="负责人"/]
    [@b.col width="10%" property="department.name" title="建设院系"/]
    [@b.col width="8%" property="level.name" title="级别"/]
    [@b.col width="16%" property="category.name" title="类别"/]
    [@b.col width="8%" property="beginIn" title="立项年月"/]
    [@b.col width="8%" property="status.name" title="状态"/]
  [/@]
  [/@]
[@b.foot/]
