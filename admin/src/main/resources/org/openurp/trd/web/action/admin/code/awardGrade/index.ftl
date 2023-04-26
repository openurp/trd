[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="rdAwardGradeSearchForm" action="!search" target="rdAwardGradelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="rdAwardGrade.code;代码"/]
      [@b.textfields names="rdAwardGrade.name;名称"/]
      [@b.textfields names="rdAwardGrade.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="rdAwardGrade.code"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="rdAwardGradelist" href="!search?orderBy=rdAwardGrade.name"/]
    </div>
  </div>
[@b.foot/]
