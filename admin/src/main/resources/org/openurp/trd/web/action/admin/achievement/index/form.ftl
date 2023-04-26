[#ftl]
[@b.head/]
[@b.toolbar title="编辑教学成果"]bar.addBack();[/@]
<div style="margin:0px 20px">
 <div class="row">
  <div class="card card-info card-primary card-outline col-7">
    <div class="card-header">
      <h3 class="card-title">基本信息</h3>
    </div>
  [#assign sa][#if rdAchievement.persisted]!update?id=${rdAchievement.id!}[#else]!save[/#if][/#assign]
  [@b.form action=sa theme="list"]
    [@b.textfield name="rdAchievement.code" label="编号" value="${rdAchievement.code!}" required="true" maxlength="20"/]
    [@b.textfield name="rdAchievement.name" label="名称" value="${rdAchievement.name!}" required="true" maxlength="70"/]
    [@b.textfield name="rdAchievement.categoryCode" label="科类代码" value=rdAchievement.categoryCode! required="false" maxlength="4"/]
    [@b.select name="rdAchievement.achievementType.id" items=achievementTypes value=rdAchievement.achievementType! label="成果类型" required="true"/]
    [@b.textfield name="rdAchievement.orgName" label="完成单位" value=rdAchievement.organization! required="true" maxlength="300"/]
    [@b.textfield name="rdAchievement.website" label="成果网址" value=rdAchievement.website! required="false" maxlength="300" style="width:400px"/]
    [@b.date label="成果起始"  name="rdAchievement.beginOn" required="true" value=rdAchievement.beginOn! format="yyyy-MM" style="width:100px"/]
    [@b.date label="成果完成"  name="rdAchievement.endOn" required="true"  value=rdAchievement.endOn! format="yyyy-MM" style="width:100px"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
   [/@]
  </div>
  <div class="card card-info card-primary card-outline col-5">
     <div class="card-header">
       <h3 class="card-title">成果完成人</h3>
     </div>
     [@b.div href="member!search?rdAchievementMember.achievement.id=${rdAchievement.id}&orderBy=rdAchievementMember.idx"/]
  </div>
 </div>
  [#if rdAchievement.persisted]

   <div class="card card-info card-primary card-outline">
      <div class="card-header">
        <h3 class="card-title">获奖情况</h3>
      </div>
      [@b.div href="award!search?rdAchievementAward.achievement.id=${rdAchievement.id}&orderBy=rdAchievementAward.awardYear"/]
   </div>
  [/#if]
</div>
[@b.foot/]
