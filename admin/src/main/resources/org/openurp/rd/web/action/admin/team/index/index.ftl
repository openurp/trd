[#ftl]
[@b.head/]
[@b.toolbar title="教学团队维护"/]
<div class="search-container">
    <div class="search-panel">
        [@b.form name="teachingTeamSearchForm" action="!search" target="teachingTeamlist" title="ui.searchForm" theme="search"]
            [@b.textfields names="teachingTeam.code;编号"/]
            [@b.textfields names="teachingTeam.name;名称"/]
            [@b.textfields names="beginYear;立项年度,leaderName;负责人,memberName;参与人"/]
            [#--
            [@b.select name="teachingTeam.level.id" label="项目级别" items=levels option="id,name" empty="..."/]
            [@b.select name="teachingTeam.category.id" label="项目分类"  items=categories option="id,name" empty="..."/]
            [@b.select name="teachingTeam.department.id" label="院系" items=departments option="id,name" empty="..."/]
            [@b.select name="teachingTeam.status.id" label="项目状态"  items=statuses option="id,name" empty="..."/]
            [@b.select name="hasExternalUser" label="校外用户" items={"1":"包含校外用户","0":"全是本校教师"}/]
            --]
            <input type="hidden" name="orderBy" value="teachingTeam.code"/>
        [/@]
    </div>
    <div class="search-list">[@b.div id="teachingTeamlist" href="!search?orderBy=teachingTeam.code asc"/]
  </div>
</div>
[@b.foot/]
