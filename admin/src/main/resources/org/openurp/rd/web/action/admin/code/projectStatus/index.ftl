[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="rdProjectStatusSearchForm" action="!search" target="rdProjectStatuslist" title="ui.searchForm" theme="search"]
      [@b.textfields names="rdProjectStatus.code;代码"/]
      [@b.textfields names="rdProjectStatus.name;名称"/]
      [@b.textfields names="rdProjectStatus.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="rdProjectStatus.code"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="rdProjectStatuslist" href="!search?orderBy=rdProjectStatus.name"/]
    </div>
  </div>
[@b.foot/]
