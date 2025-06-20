[#ftl]
[@b.head/]
[@b.toolbar title="教学成果维护"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="achievementSearchForm" action="!search" target="achievementlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="rdAchievement.code;编号"/]
      [@b.textfields names="rdAchievement.name;名称,memberName;参与人"/]
      [@b.select name="rdAchievement.achievementType.id" items=achievementTypes label="成果类型"/]
      [@b.textfields names="awardYear;获奖年度"/]
      [@b.select name="grade.id" label="获奖级别"  items=grades  empty="..."/]
      [@b.select name="level.id" label="获奖等级"  items=levels  empty="..."/]
      [@b.textfields names="rdAchievement.orgName;完成单位"/]
      [@b.select name="hasExternalUser" label="校外用户" items={"1":"包含校外用户","0":"全是本校教师"}/]
      <input type="hidden" name="orderBy" value="rdAchievement.endIn desc"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="achievementlist" href="!search?active=1&orderBy=rdAchievement.endIn desc"/]
  </div>
</div>
[@b.foot/]
