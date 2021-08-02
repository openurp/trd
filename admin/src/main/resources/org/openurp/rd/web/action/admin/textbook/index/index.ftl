[#ftl]
[@b.head/]
[@b.toolbar title="教材成果"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="textbookSearchForm" action="!search" target="achievementlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="textbookAchievement.textbook.isbn;ISBN"/]
      [@b.textfields names="textbookAchievement.textbook.name;名称,textbookAchievement.textbook.press.name;出版社,textbookAchievement.textbook.category.name;分类,textbookAchievement.textbook.series;丛书"/]
      <input type="hidden" name="orderBy" value="textbookAchievement.textbook.name"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="achievementlist" href="!search?orderBy=textbookAchievement.textbook.name asc"/]
    </td>
  </tr>
</table>
[@b.foot/]
