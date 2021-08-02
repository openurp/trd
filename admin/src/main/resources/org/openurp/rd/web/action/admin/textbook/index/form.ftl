[#ftl]
[@b.head/]
[@b.toolbar title="编辑教材成果"]bar.addBack();[/@]
<div style="margin:0px 20px">
 <div class="row">
  <div class="card card-info card-primary card-outline [#if textbookAchievement.persisted]col-7[/#if]">
    <div class="card-header">
      <h3 class="card-title">基本信息</h3>
    </div>
  [@b.form action=b.rest.save(textbookAchievement) theme="list"]
    [@b.textfield name="textbook.isbn" label="教材ISBN" value=(textbookAchievement.textbook.isbn)! required="true" maxlength="20"/]
    [#if textbookAchievement.persisted]
    [@b.field label="名称"] ${textbookAchievement.textbook.name}[/@]
    [@b.field label="出版社"] ${textbookAchievement.textbook.press.name}[/@]
    [@b.field label="作者"] ${textbookAchievement.textbook.author}[/@]
    [@b.field label="版次"] ${textbookAchievement.textbook.edition}[/@]
    [/#if]
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
   <div class="card card-info card-primary card-outline">
      <div class="card-header">
        <h3 class="card-title">获奖情况</h3>
      </div>
      [@b.div href="award!search?textbookAward.achievement.id=${textbookAchievement.id}&orderBy=textbookAward.awardOn"/]
   </div>
  [/#if]
</div>
[@b.foot/]
