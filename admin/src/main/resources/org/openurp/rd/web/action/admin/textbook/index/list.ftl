[#ftl]
[@b.head/]
[@b.grid items=textbookAchievements var="textbookAchievement"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    //bar.addItem("导入",action.method('importForm'));
    //bar.addItem("${b.text("action.export")}",action.exportData("isbn:ISBN,name:名称,press.name:出版社,author:作者,translator:译者,publishedOn:出版年月,edition:版次,category.name:图书分类,bookType.name:教材类型,awardType.name:获奖类型,awardOrg:奖项授予单位,series:丛书,madeInSchool:是否自编,beginOn:创建日期",null,'fileName=教材信息'));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="12%" property="textbook.isbn" title="ISBN"]
      <span style="font-size:0.8em"> ${(textbookAchievement.textbook.isbn)!}</span>
    [/@]
    [@b.col width="35%" property="textbook.name" title="名称"]
      <span [#if textbookAchievement.textbook.name?length > 25]style="font-size:0.8em"[/#if]>
      [@b.a href="!info?id=${textbookAchievement.id}"]${textbookAchievement.textbook.name}[/@]
      [#if textbookAchievement.textbook.madeInSchool]<sup>自编</sup>[/#if]
      [#if textbookAchievement.textbook.awardType??]<sup>${textbookAchievement.textbook.awardType.name}</sup>[/#if]
      </span>
    [/@]
    [@b.col width="12%" property="textbook.press.name" title="出版社"]
     <span style="font-size:0.8em">${(textbookAchievement.textbook.press.name)!}</span>
    [/@]
    [@b.col width="13%" property="textbook.author" title="编辑"]
      [#list textbookAchievement.editors as u]${u.user.name}[#if !u.chief]<sup>从</sup>[/#if][#if u_has_next],[/#if][/#list]
    [/@]
    [@b.col width="16%" title="获奖"]
      [#list textbookAchievement.awards as a]${a.name}[#if a_has_next],[/#if][/#list]
    [/@]
    [@b.col width="8%" property="textbook.publishedOn" title="出版年月"]${(textbook.publishedOn?string('yyyy-MM'))!}[/@]
  [/@]
 [/@]
[@b.foot/]
