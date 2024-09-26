[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="rdAchievementTypeSearchForm" action="!search" target="rdAchievementTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="rdAchievementType.code;代码"/]
      [@b.textfields names="rdAchievementType.name;名称"/]
      [@b.textfields names="rdAchievementType.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="rdAchievementType.code"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="rdAchievementTypelist" href="!search?orderBy=rdAchievementType.name"/]
    </div>
  </div>
[@b.foot/]
