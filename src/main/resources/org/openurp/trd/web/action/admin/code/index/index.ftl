[#ftl]
[@b.head/]
[#assign codes={}]
[#assign codes=codes+{'项目类别':'/admin/code/project-category'}]
[#assign codes=codes+{'研究级别':'/admin/code/rd-level'}]
[#assign codes=codes+{'项目状态':'/admin/code/project-status'}]
[#assign codes=codes+{'成果类型':'/admin/code/achievement-type'}]
[#assign codes=codes+{'获奖等级':'/admin/code/award-grade'}]

<ul class="nav nav-tabs nav-tabs-compact" id="code_nav">
  [#list codes?keys as code]
    [#assign link_class]${(code_index==0)?string("nav-link active","nav-link")}[/#assign]
  <li role="presentation" class="nav-item">[@b.a href=codes[code] class=link_class target="codelist"]${code}[/@]</li>
  [/#list]
</ul>
[@b.div id="codelist" href="/admin/code/project-category"/]
<script>
  jQuery(document).ready(function(){
    jQuery('#code_nav>li>a').bind("click",function(e){
      jQuery("#code_nav>li>a").removeClass("active");
      jQuery(this).addClass("active");
    });
  });
</script>
[@b.foot/]
