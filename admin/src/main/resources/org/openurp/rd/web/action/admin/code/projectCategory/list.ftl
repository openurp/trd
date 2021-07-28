[#ftl]
[@b.head/]
[@b.grid items=rdProjectCategories var="rdProjectCategory"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="code" title="代码"]${rdProjectCategory.code}[/@]
    [@b.col width="20%" property="name" title="名称"][@b.a href="!info?id=${rdProjectCategory.id}"]${rdProjectCategory.name}[/@][/@]
    [@b.col width="10%" property="forCourse" title="适用范围"][#if rdProjectCategory.forCourse]课程建设项目[#else]教改项目[/#if][/@]
    [@b.col width="15%" property="enName" title="英文名称"]${rdProjectCategory.enName!}[/@]
    [@b.col width="20%" property="beginOn" title="生效时间"]${rdProjectCategory.beginOn!}[/@]
    [@b.col width="20%" property="endOn" title="失效时间"]${rdProjectCategory.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
