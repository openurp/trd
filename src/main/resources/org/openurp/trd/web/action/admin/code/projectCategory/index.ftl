[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="rdProjectCategorySearchForm" action="!search" target="rdProjectCategorylist" title="ui.searchForm" theme="search"]
      [@b.textfields names="rdProjectCategory.code;代码"/]
      [@b.textfields names="rdProjectCategory.name;名称"/]
      [@b.textfields names="rdProjectCategory.enName;英文名称"/]
      [@b.select name="rdProjectCategory.forCourse" label="使用范围" items={"1":"课程项目","0":"教改项目"}/]
      <input type="hidden" name="orderBy" value="rdProjectCategory.code"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="rdProjectCategorylist" href="!search?orderBy=rdProjectCategory.code"/]
    </div>
  </div>
[@b.foot/]
