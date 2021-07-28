[#ftl]
[@b.head/]
[@b.toolbar title="课程建设项目维护"/]
<div class="search-container">
    <div class="search-panel">
        [@b.form name="rdProjectSearchForm" action="!search" target="rdProjectlist" title="ui.searchForm" theme="search"]
            [@b.textfields names="rdProject.code;代码"/]
            [@b.textfields names="rdProject.name;名称"/]
            <input type="hidden" name="orderBy" value="rdProject.code"/>
        [/@]
    </div>
    <div class="search-list">[@b.div id="rdProjectlist" href="!search?orderBy=rdProject.code asc"/]
  </div>
</div>
[@b.foot/]
