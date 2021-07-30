[#ftl]
[@b.head/]
[@b.toolbar title="教学成果维护"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="achievementSearchForm" action="!search" target="achievementlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="rdAchievement.code;编号"/]
      [@b.textfields names="rdAchievement.name;名称,rdAchievement.organization;完成单位"/]
      [@b.select name="rdAchievement.achievementType.id" items=achievementTypes label="成果类型"/]
      <input type="hidden" name="orderBy" value="rdAchievement.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="achievementlist" href="!search?active=1&orderBy=rdAchievement.code"/]
  </div>
</div>
[@b.foot/]
