[#ftl]
[@b.head/]
[@b.grid items=rdAchievements var="rdAchievement"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    bar.addItem("成果导入",action.method('importForm'));
    bar.addItem("获奖导入","awardImport()");
    function awardImport(){
       bg.form.submit(document.awardImportForm);
    }
  [/@]
  [@b.row]
      [@b.boxcol /]
      [@b.col width="6%" property="code" title="编号"]${rdAchievement.code}[/@]
      [@b.col width="6%" property="endOn" title="完成年度"]${rdAchievement.endOn?string("yyyy")}[/@]
      [@b.col property="name" title="名称"][@b.a href="!info?id=${rdAchievement.id}"]${rdAchievement.name}[/@][/@]
      [@b.col width="10%" property="achievementType.name" title="成果类型"/]
      [@b.col width="25%" property="memberNames" title="完成人"]
        [#list rdAchievement.members?sort_by('idx') as m]${m.name}[#sep],[/#list]
      [/@]
      [@b.col width="10%" title="获奖情况"]
         [#list rdAchievement.awards as a]${a.awardYear}  ${a.level.name} ${a.grade.name}[#if a_has_next]<br>[/#if][/#list]
      [/@]
  [/@]
[/@]
[@b.form name="awardImportForm" action="award!importForm" target="_blank" /]
[@b.foot/]
