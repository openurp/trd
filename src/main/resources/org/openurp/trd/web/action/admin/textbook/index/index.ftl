[#ftl]
[@b.head/]
[@b.toolbar title="教材成果"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="textbookSearchForm" action="!search" target="achievementlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="textbookAchievement.isbn;ISBN"/]
      [@b.textfields names="textbookAchievement.name;名称,textbookAchievement.press.name;出版社"/]
      <input type="hidden" name="orderBy" value="textbookAchievement.name"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="achievementlist" href="!search?orderBy=textbookAchievement.name asc"/]
    </td>
  </tr>
</table>
[@b.foot/]
