[#ftl]
[@b.head/]
[@b.toolbar title="编辑教材成果"]bar.addBack();[/@]
<div style="margin:0px 20px">
 <div class="row">
  <div class="card card-info card-primary card-outline [#if textbookAchievement.persisted]col-7[#else]col-12[/#if]">
    <div class="card-header">
      <h3 class="card-title">基本信息</h3>
    </div>
  [@b.form action=b.rest.save(textbookAchievement) theme="list"]
    [@b.textfield name="textbookAchievement.isbn" label="教材ISBN" value=(textbookAchievement.isbn)! required="true" maxlength="20"/]
    [@b.textfield name="textbookAchievement.name" label="教材名称" value=textbookAchievement.name! required="true" maxlength="200"/]
    [@b.select name="textbookAchievement.press.id" id="textbook_press_id" label="出版社" value=(textbookAchievement.press.id)!
               style="width:200px;" items=presses?sort_by('name') empty="..." required="true"/]
    [@b.date name="textbookAchievement.publishedIn" label="出版年月" value=(textbook.publishedIn)!  required="true"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
   [/@]
  </div>
  [#if textbookAchievement.persisted]
  <div class="card card-info card-primary card-outline col-5">
     <div class="card-header">
       <h3 class="card-title">教材编辑信息</h3>
     </div>
     [@b.div href="editor!search?textbookEditor.achievement.id=${textbookAchievement.id}&orderBy=textbookEditor.idx"/]
  </div>
  [/#if]
 </div>
  [#if textbookAchievement.persisted]
  <div class="row">
   <div class="card card-info card-primary card-outline col-12">
      <div class="card-header">
        <h3 class="card-title">获奖情况</h3>
      </div>
      [@b.div href="award!search?textbookAward.achievement.id=${textbookAchievement.id}&orderBy=textbookAward.awardOn"/]
   </div>
  </div>
  [/#if]
</div>
[@b.foot/]
