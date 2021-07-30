[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="rdLevelSearchForm" action="!search" target="rdLevellist" title="ui.searchForm" theme="search"]
      [@b.textfields names="rdLevel.code;代码"/]
      [@b.textfields names="rdLevel.name;名称"/]
      [@b.textfields names="rdLevel.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="rdLevel.code"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="rdLevellist" href="!search?orderBy=rdLevel.name"/]
    </div>
  </div>
[@b.foot/]
