[#ftl]
[@b.head/]
[@b.grid items=textbookAchievements var="textbookAchievement"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    //bar.addItem("教材成果导入",action.method('importForm'));
    bar.addItem("获奖导入","awardImport()");
    function awardImport(){
       bg.form.submit(document.awardImportForm);
    }
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="12%" property="isbn" title="ISBN"]
      <span style="font-size:0.8em"> ${(textbookAchievement.isbn)!}</span>
    [/@]
    [@b.col property="name" title="名称"]
      <span [#if textbookAchievement.name?length > 25]style="font-size:0.8em"[/#if]>
      [@b.a href="!info?id=${textbookAchievement.id}"]${textbookAchievement.name}[/@]
      </span>
    [/@]
    [@b.col width="12%" property="press.name" title="出版社"]
     <span style="font-size:0.8em">${(textbookAchievement.press.name)!}</span>
    [/@]
    [@b.col width="13%" title="编辑"]
      [#list textbookAchievement.editors as u]${(u.name)}[#if !u.chief]<sup>从</sup>[/#if][#if u_has_next],[/#if][/#list]
    [/@]
    [@b.col width="16%" title="获奖"]
      [#list textbookAchievement.awards as a]${a.name}[#if a_has_next],[/#if][/#list]
    [/@]
    [@b.col width="8%" property="publishedIn" title="出版年月"]${(textbookAchievement.publishedIn?string('yyyy-MM'))!}[/@]
  [/@]
 [/@]
 [@b.form name="awardImportForm" action="award!importForm" target="_blank" /]
[@b.foot/]
