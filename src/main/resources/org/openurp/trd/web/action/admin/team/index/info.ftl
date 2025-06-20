[#ftl]
[@b.head/]
[@b.toolbar title="教学团队信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">编码:</td>
    <td class="content">${teachingTeam.code}</td>
    <td class="title" width="20%">名称:</td>
    <td class="content">${teachingTeam.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">级别:</td>
    <td class="content">${teachingTeam.level.name}</td>
    <td class="title" width="20%">带头人:</td>
    <td class="content">
      [#list teachingTeam.leaders as leader]${leader.name}[#if leader_has_next],[/#if][/#list]
    </td>
  </tr>
  <tr>
    <td class="title" width="20%">获奖名称:</td>
    <td class="content">${teachingTeam.awardTitle!}</td>
    <td class="title" width="20%">所在院系:</td>
    <td class="content">${teachingTeam.department.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">团队成员:</td>
    <td class="content" colspan="3">
      [#list teachingTeam.members as m]${m.user.name}[#if m_has_next],[/#if][/#list]
    </td>
  </tr>
  <tr>
    <td class="title" width="20%">立项年月:</td>
    <td class="content">${teachingTeam.beginIn}</td>
    <td class="title" width="20%">备注:</td>
    <td class="content">${teachingTeam.remark!}</td>
  </tr>
</table>

[@b.foot/]
