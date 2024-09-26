[#ftl]
[@b.head/]
[@b.toolbar title="教改建设项目维护"/]
<div class="search-container">
    <div class="search-panel">
        [@b.form name="rdProjectSearchForm" action="!search" target="rdProjectlist" title="ui.searchForm" theme="search"]
            [@b.textfields names="rdProject.code;编号"/]
            [@b.textfields names="rdProject.name;名称"/]
            [@b.textfields names="beginYear;立项年度,leaderName;负责人,memberName;参与人"/]
            [@b.select name="rdProject.level.id" label="项目级别" items=levels option="id,name" empty="..."/]
            [@b.select name="rdProject.category.id" label="项目分类"  items=categories option="id,name" empty="..."/]
            [@b.select name="rdProject.department.id" label="院系" items=departments option="id,name" empty="..."/]
            [@b.select name="rdProject.status.id" label="项目状态"  items=statuses option="id,name" empty="..."/]
            [@b.select name="hasExternalUser" label="校外用户" items={"1":"包含校外用户","0":"全是本校教师"}/]
            <input type="hidden" name="orderBy" value="rdProject.code"/>
        [/@]
    </div>
    <div class="search-list">[@b.div id="rdProjectlist" href="!search?orderBy=rdProject.code asc"/]
  </div>
</div>
[@b.foot/]
