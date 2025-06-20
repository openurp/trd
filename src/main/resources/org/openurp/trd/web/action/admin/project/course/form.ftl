[#ftl]
[@b.head/]
[@b.toolbar title="修改课程建设项目"]bar.addBack();[/@]
[@b.tabs]
  [@b.form action=b.rest.save(rdProject) theme="list"]
    [@b.textfield name="rdProject.code" label="编号" value="${rdProject.code!}" required="true" maxlength="20"/]
    [@b.textfield name="rdProject.name" label="名称" value="${rdProject.name!}" required="true" maxlength="100"/]
    [@b.select name="rdProject.level.id" label="项目级别" value="${(rdProject.level.id)!}" required="true"
               style="width:200px;" items=levels option="id,name" empty="..."/]
    [@b.select name="rdProject.category.id" label="项目分类" value="${(rdProject.category.id)!}" required="true"
               style="width:200px;" items=categories option="id,name" empty="..."/]

    [@b.select name="rdProject.department.id" label="院系" value=rdProject.department! required="true"
               style="width:200px;" items=departments option="id,name" empty="..."/]
    [@b.select name="leader.id" label="负责人"  required="true" multiple="true"
                 style="width:300px;" href=urp.api+"/base/users.json?q={term}&isTeacher=true" option="id,code+' '+data.name+' '+data.department.name"]
      [#list rdProject.leaders as m]
      <option value="${m.id}" selected="true">${m.description}</option>
      [/#list]
    [/@]
    [@b.select name="member.id" label="参与人" multiple="true"
                     style="width:300px;" href=urp.api+"/base/users.json?q={term}&isTeacher=true" option="id,code+' '+data.name+' '+data.department.name" empty="..."]
        [#list members as m]
        <option value="${m.id}" selected="true">${m.description}</option>
        [/#list]
    [/@]
    [@b.number name="rdProject.funds" label="资金" value="${rdProject.funds!}" required="true" maxlength="100" max="200000" comment="元（RMB）"/]
    [@b.date label="立项年月"  name="rdProject.beginIn" required="true"   value=rdProject.beginIn! format="yyyy-MM" style="width:100px"/]
    [@b.date label="应结项年月"  name="rdProject.endIn" required="true"   value=rdProject.endIn! format="yyyy-MM" style="width:100px"/]
    [@b.date label="实际结项年月"  name="rdProject.finishedIn" required="false"   value=rdProject.finishedIn! format="yyyy-MM" style="width:100px"/]
    [@b.select name="rdProject.status.id" label="项目状态" value="${(rdProject.status.id)!}" required="true"
               style="width:200px;" items=statuses option="id,name" empty="..."/]
    [@b.textarea name="rdProject.remark" label="备注" value="${rdProject.remark!}"  maxlength="100" cols="30"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[/@]
[@b.foot/]
