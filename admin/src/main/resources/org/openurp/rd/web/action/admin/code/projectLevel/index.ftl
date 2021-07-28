[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="rdProjectLevelSearchForm" action="!search" target="rdProjectLevellist" title="ui.searchForm" theme="search"]
      [@b.textfields names="rdProjectLevel.code;代码"/]
      [@b.textfields names="rdProjectLevel.name;名称"/]
      [@b.textfields names="rdProjectLevel.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="rdProjectLevel.code"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="rdProjectLevellist" href="!search?orderBy=rdProjectLevel.name"/]
    </div>
  </div>
[@b.foot/]
