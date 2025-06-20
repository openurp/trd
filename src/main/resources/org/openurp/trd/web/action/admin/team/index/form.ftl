[#ftl]
[@b.head/]
[@b.toolbar title="编辑教学团队"]bar.addBack();[/@]
[@b.tabs]
  [@b.form action=b.rest.save(teachingTeam) theme="list"]
    [@b.textfield name="teachingTeam.code" label="编号" value="${teachingTeam.code!}" required="true" maxlength="20"/]
    [@b.textfield name="teachingTeam.name" label="名称" value="${teachingTeam.name!}" required="true" maxlength="100"/]
    [@b.select name="teachingTeam.level.id" label="项目级别" value="${(teachingTeam.level.id)!}" required="true"
               style="width:200px;" items=levels option="id,name" empty="..."/]
    [@b.select name="teachingTeam.department.id" label="院系" value=teachingTeam.department! required="true"
               style="width:200px;" items=departments option="id,name" empty="..."/]
    [@b.select name="leader.id" label="带头人"  required="true" multiple="true"
                 style="width:300px;" href=urp.api+"/base/users.json?q={term}&isTeacher=true" option="id,code+' '+data.name+' '+data.department.name"]
      [#list teachingTeam.leaders as m]
      <option value="${m.id}" selected="true">${m.description}</option>
      [/#list]
    [/@]
    [@b.select name="member.id" label="参与人" multiple="true"
                     style="width:300px;" href=urp.api+"/base/users.json?q={term}&isTeacher=true" option="id,code+' '+data.name+' '+data.department.name" empty="..."]
        [#list members as m]
        <option value="${m.id}" selected="true">${m.description}</option>
        [/#list]
    [/@]
    [@b.textfield name="teachingTeam.awardTitle" label="获奖名称" value=teachingTeam.awardTitle! required="true" maxlength="100" /]
    [@b.date label="立项年月"  name="teachingTeam.beginIn" required="true" value=teachingTeam.beginIn! format="yyyy-MM" style="width:100px"/]
    [@b.textarea name="teachingTeam.remark" label="备注" value="${teachingTeam.remark!}"  maxlength="100" cols="30"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[/@]
[@b.foot/]
