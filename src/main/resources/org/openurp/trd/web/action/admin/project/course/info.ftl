[#ftl]
[@b.head/]
[@b.toolbar title="课程建设项目信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">代码:</td>
    <td class="content">${rdProject.code}</td>
    <td class="title" width="20%">名称:</td>
    <td class="content">${rdProject.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">级别:</td>
    <td class="content">${rdProject.level.name}</td>
    <td class="title" width="20%">类别:</td>
    <td class="content">${rdProject.category.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">负责人:</td>
    <td class="content">
      [#list rdProject.leaders as leader]${leader.name}[#if leader_has_next],[/#if][/#list]
    </td>
    <td class="title" width="20%">建设院系:</td>
    <td class="content">${rdProject.department.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">参与人:</td>
    <td class="content" colspan="3">
      [#list rdProject.members as m]${m.name}[#if !(m.user??)]<sup>外</sup>[/#if][#if m_has_next],[/#if][/#list]
    </td>
  </tr>
  <tr>
    <td class="title" width="20%">立项年月:</td>
    <td class="content">${rdProject.beginIn}</td>
    <td class="title" width="20%">应结项年月:</td>
    <td class="content">${rdProject.endIn}</td>
  </tr>
   <tr>
    <td class="title" width="20%">实际结项年月:</td>
    <td class="content">${(rdProject.finishedIn)!}</td>
    <td class="title" width="20%">资金:</td>
    <td class="content">${rdProject.funds/10000.0}万</td>
  </tr>
  <tr>
    <td class="title" width="20%">项目状态:</td>
    <td class="content">${rdProject.status.name}</td>
   <td class="title" width="20%">备注:</td>
   <td class="content">${rdProject.remark!}</td>
  </tr>
</table>

[@b.foot/]
