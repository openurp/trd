[#ftl]
[@b.head/]
[@b.toolbar title="修改项目类别"]bar.addBack();[/@]
[@b.tabs]
  [@b.form action=b.rest.save(rdProjectStatus) theme="list"]
    [@b.textfield name="rdProjectStatus.code" label="代码" value="${rdProjectStatus.name!}" required="true" maxlength="20"/]
    [@b.textfield name="rdProjectStatus.name" label="名称" value="${rdProjectStatus.name!}" required="true" maxlength="20"/]
    [@b.textfield name="rdProjectStatus.enName" label="英文名称" value="${rdProjectStatus.enName!}" maxlength="100"/]
    [@b.startend label="有效期限"
      name="rdProjectStatus.beginOn,rdProjectStatus.endOn" required="true,false"
      start=rdProjectStatus.beginOn end=rdProjectStatus.endOn format="date"/]
    [@b.textfield name="rdProjectStatus.remark" label="备注" value="${rdProjectStatus.remark!}" maxlength="3"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[/@]
[@b.foot/]
