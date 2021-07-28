[#ftl]
[@b.head/]
[@b.grid items=rdProjects var="rdProject"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    bar.addItem("导入",action.method('importForm'));
    //bar.addItem("${b.text("action.export")}",action.exportData("code:代码,name:课程名称,enName:英文名,credits:学分,creditHours:学时,weekHours:周课时,department.name:所属院系,rdProjectType.name:课程类型,category.name:课程分类,examMode.name:考核方式,hasMakeup:是否有补考,calgp:是否计算绩点,beginOn:生效日期,endOn:失效日期",null,'fileName=课程信息'));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="code" title="编号"/]
    [@b.col width="20%" property="name" title="名称"][@b.a href="!info?id=${rdProject.id}"]${rdProject.name}[/@][/@]
    [@b.col width="10%" property="leader.name" title="负责人"/]
    [@b.col width="10%" property="department.name" title="建设院系"/]
    [@b.col width="10%" property="level.name" title="级别"/]
    [@b.col width="10%" property="category.name" title="类别"/]
    [@b.col width="10%" property="beginOn" title="立项年月"/]
    [@b.col width="10%" property="status.name" title="状态"/]
  [/@]
  [/@]
[@b.foot/]
