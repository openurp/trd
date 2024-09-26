[#ftl]
[@b.head/]
[@b.toolbar title="教学成果信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
[#assign achievement= rdAchievement/]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">编号:</td>
    <td class="content">${achievement.code}</td>
    <td class="title" width="20%">名称:</td>
    <td class="content">${achievement.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">成果类型:</td>
    <td class="content">${achievement.achievementType.name}</td>
    <td class="title" width="20%">完成单位:</td>
    <td class="content">${achievement.orgName}</td>
  </tr>
  <tr>
    <td class="title" width="20%">完成人:</td>
    <td class="content" colspan="3">
      [#list achievement.members as m]${m.name}[#if !(m.user??)]<sup>外</sup>[/#if][#if m_has_next],[/#if][/#list]
    </td>
  </tr>
  <tr>
    <td class="title" width="20%">科类代码:</td>
    <td class="content">${achievement.categoryCode!}</td>
    <td class="title" width="20%">成果网址:</td>
    <td class="content">${achievement.website!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">获奖情况:</td>
    <td class="content" colspan="3">
      [#list achievement.awards as a]${a.awardYear} ${a.name} ${a.level.name} ${a.grade.name} [#if a_has_next]<br>[/#if][/#list]
    </td>
  </tr>
</table>

[@b.foot/]
