/*
This software is allowed to use under GPL or you need to obtain Commercial or Enterise License
to use it in non-GPL project. Please contact sales@dhtmlx.com for details
*/
scheduler.config.year_x=4;scheduler.config.year_y=3;scheduler.xy.year_top=0;scheduler.templates.year_date=function(n){return scheduler.date.date_to_str(scheduler.locale.labels.year_tab+" %Y")(n)};scheduler.templates.year_month=scheduler.date.date_to_str("%F");scheduler.templates.year_scale_date=scheduler.date.date_to_str("%D");scheduler.templates.year_tooltip=function(n,q,u){return u.text};
(function(){function n(b){return m(b,function(a){return a.nodeName.toLowerCase()=="td"})}function q(b){return m(b,function(a){return a.nodeName.toLowerCase()=="table"})}function u(b){b=q(b);return m(b,function(a){return a.hasAttribute&&a.hasAttribute("date")})}function m(b,a){for(;b&&!a(b);)b=b.parentNode;return b}var k=function(){return scheduler._mode=="year"};scheduler.dblclick_dhx_month_head=function(b){if(k()){var a=b.target||b.srcElement;if(a.parentNode.className.indexOf("dhx_before")!=-1||
a.parentNode.className.indexOf("dhx_after")!=-1)return!1;var c=this.templates.xml_date(a.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.getAttribute("date"));c.setDate(parseInt(a.innerHTML,10));var e=this.date.add(c,1,"day");!this.config.readonly&&this.config.dblclick_create&&this.addEventNow(c.valueOf(),e.valueOf(),b)}};var v=scheduler.changeEventId;scheduler.changeEventId=function(){v.apply(this,arguments);k()&&this.year_view(!0)};var w=scheduler.render_data,x=scheduler.date.date_to_str("%Y/%m/%d"),
y=scheduler.date.str_to_date("%Y/%m/%d");scheduler.render_data=function(b){if(!k())return w.apply(this,arguments);for(var a=0;a<b.length;a++)this._year_render_event(b[a])};var z=scheduler.clear_view;scheduler.clear_view=function(){if(!k())return z.apply(this,arguments);for(var b in o)if(o.hasOwnProperty(b)){var a=o[b];a.className="dhx_month_head";a.setAttribute("date","")}o={}};scheduler._hideToolTip=function(){if(this._tooltip)this._tooltip.style.display="none",this._tooltip.date=new Date(9999,1,
1)};scheduler._showToolTip=function(b,a,c,e){if(this._tooltip){if(this._tooltip.date.valueOf()==b.valueOf())return;this._tooltip.innerHTML=""}else{var d=this._tooltip=document.createElement("DIV");d.className="dhx_year_tooltip";document.body.appendChild(d);d.onclick=scheduler._click.dhx_cal_data}for(var f=this.getEvents(b,this.date.add(b,1,"day")),l="",i=0;i<f.length;i++){var p=f[i],g=p.color?"background:"+p.color+";":"",j=p.textColor?"color:"+p.textColor+";":"";l+="<div class='dhx_tooltip_line' style='"+
g+""+j+"' event_id='"+f[i].id+"'>";l+="<div class='dhx_tooltip_date' style='"+g+""+j+"'>"+(f[i]._timed?this.templates.event_date(f[i].start_date):"")+"</div>";l+="<div class='dhx_event_icon icon_details'>&nbsp;</div>";l+=this.templates.year_tooltip(f[i].start_date,f[i].end_date,f[i])+"</div>"}this._tooltip.style.display="";this._tooltip.style.top="0px";this._tooltip.style.left=document.body.offsetWidth-a.left-this._tooltip.offsetWidth<0?a.left-this._tooltip.offsetWidth+"px":a.left+e.offsetWidth+"px";
this._tooltip.date=b;this._tooltip.innerHTML=l;this._tooltip.style.top=document.body.offsetHeight-a.top-this._tooltip.offsetHeight<0?a.top-this._tooltip.offsetHeight+e.offsetHeight+"px":a.top+"px"};scheduler._init_year_tooltip=function(){dhtmlxEvent(scheduler._els.dhx_cal_data[0],"mouseover",function(b){if(k()){var b=b||event,a=b.target||b.srcElement;if(a.tagName.toLowerCase()=="a")a=a.parentNode;(a.className||"").indexOf("dhx_year_event")!=-1?scheduler._showToolTip(y(a.getAttribute("date")),getOffset(a),
b,a):scheduler._hideToolTip()}});this._init_year_tooltip=function(){}};scheduler.attachEvent("onSchedulerResize",function(){return k()?(this.year_view(!0),!1):!0});scheduler._get_year_cell=function(b){var a=b.getMonth()+12*(b.getFullYear()-this._min_date.getFullYear())-this.week_starts._month,c=this._els.dhx_cal_data[0].childNodes[a],b=this.week_starts[a]+b.getDate()-1;return c.childNodes[2].firstChild.rows[Math.floor(b/7)].cells[b%7].firstChild};var o={};scheduler._mark_year_date=function(b,a){var c=
x(b),e=this._get_year_cell(b),d=this.templates.event_class(a.start_date,a.end_date,a);if(!o[c])e.className="dhx_month_head dhx_year_event",e.setAttribute("date",c),o[c]=e;e.className+=d?" "+d:""};scheduler._unmark_year_date=function(b){this._get_year_cell(b).className="dhx_month_head"};scheduler._year_render_event=function(b){for(var a=b.start_date,a=a.valueOf()<this._min_date.valueOf()?this._min_date:this.date.date_part(new Date(a));a<b.end_date;)if(this._mark_year_date(a,b),a=this.date.add(a,1,
"day"),a.valueOf()>=this._max_date.valueOf())break};scheduler.year_view=function(b){if(b){var a=scheduler.xy.scale_height;scheduler.xy.scale_height=-1}scheduler._els.dhx_cal_header[0].style.display=b?"none":"";scheduler.set_sizes();if(b)scheduler.xy.scale_height=a;scheduler._table_view=b;if(!this._load_mode||!this._load())if(b){scheduler._init_year_tooltip();scheduler._reset_year_scale();if(scheduler._load_mode&&scheduler._load())return scheduler._render_wait=!0;scheduler.render_view_data()}else scheduler._hideToolTip()};
scheduler._reset_year_scale=function(){this._cols=[];this._colsS={};var b=[],a=this._els.dhx_cal_data[0],c=this.config;a.scrollTop=0;a.innerHTML="";var e=Math.floor(parseInt(a.style.width)/c.year_x),d=Math.floor((parseInt(a.style.height)-scheduler.xy.year_top)/c.year_y);d<190&&(d=190,e=Math.floor((parseInt(a.style.width)-scheduler.xy.scroll_width)/c.year_x));for(var f=e-11,l=0,i=document.createElement("div"),p=this.date.week_start(scheduler._currentDate()),g=0;g<7;g++)this._cols[g]=Math.floor(f/(7-
g)),this._render_x_header(g,l,p,i),p=this.date.add(p,1,"day"),f-=this._cols[g],l+=this._cols[g];i.lastChild.className+=" dhx_scale_bar_last";for(var j=this.date[this._mode+"_start"](this.date.copy(this._date)),k=j,g=0;g<c.year_y;g++)for(var t=0;t<c.year_x;t++){var h=document.createElement("DIV");h.style.cssText="position:absolute;";h.setAttribute("date",this.templates.xml_format(j));h.innerHTML="<div class='dhx_year_month'></div><div class='dhx_year_week'>"+i.innerHTML+"</div><div class='dhx_year_body'></div>";
h.childNodes[0].innerHTML=this.templates.year_month(j);for(var o=this.date.week_start(j),n=this._reset_month_scale(h.childNodes[2],j,o),r=h.childNodes[2].firstChild.rows,s=r.length;s<6;s++){r[0].parentNode.appendChild(r[0].cloneNode(!0));for(var m=0;m<r[s].childNodes.length;m++)r[s].childNodes[m].className="dhx_after",r[s].childNodes[m].firstChild.innerHTML=scheduler.templates.month_day(n),n=scheduler.date.add(n,1,"day")}a.appendChild(h);h.childNodes[1].style.height=h.childNodes[1].childNodes[0].offsetHeight+
"px";var q=Math.round((d-190)/2);h.style.marginTop=q+"px";this.set_xy(h,e-10,d-q-10,e*t+5,d*g+5+scheduler.xy.year_top);b[g*c.year_x+t]=(j.getDay()-(this.config.start_on_monday?1:0)+7)%7;j=this.date.add(j,1,"month")}this._els.dhx_cal_date[0].innerHTML=this.templates[this._mode+"_date"](k,j,this._mode);this.week_starts=b;b._month=k.getMonth();this._min_date=k;this._max_date=j};var A=scheduler.getActionData;scheduler.getActionData=function(b){function a(a){a=u(a);if(!a)return null;var b=a.getAttribute("date");
return!b?null:scheduler.date.week_start(scheduler.templates.xml_date(b))}function c(a){var b=q(a);if(!b)return null;for(var d=0,c=0,d=0,e=b.rows.length;d<e;d++){for(var f=b.rows[d].getElementsByTagName("td"),c=0,h=f.length;c<h;c++)if(f[c]==a)break;if(c<h)break}return d<e?{day:c,week:d}:null}if(!k())return A.apply(scheduler,arguments);var e=b?b.target:event.srcElement,d=a(e),f=n(e),l=c(f);l&&d?(d=scheduler.date.add(d,l.week,"week"),d=scheduler.date.add(d,l.day,"day")):d=null;return{date:d,section:null}};
var B=scheduler._locate_event;scheduler._locate_event=function(b){if(!k())return B.apply(scheduler,arguments);var a=m(b,function(a){return a.className&&a.className.indexOf("dhx_year_event")!=-1&&a.hasAttribute&&a.hasAttribute("date")});if(!a||!a.hasAttribute("date"))return null;var c=scheduler.templates.xml_date(a.getAttribute("date")),e=scheduler.getEvents(c,scheduler.date.add(c,1,"day"));return!e.length?null:e[0].id}})();
