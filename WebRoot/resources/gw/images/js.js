var fgm = {
 shit: !-[1,] && !window.XMLHttpRequest, 
 scrollTop: function() {
  return document.documentElement.scrollTop || document.body.scrollTop;
 },
 currentStyle: function(obj, attr) {
  return parseInt(obj.currentStyle ? obj.currentStyle[attr] : getComputedStyle(obj, null)[attr]);
 },
 fixed: function(element) {
  if(this.shit) {
   var top = this.currentStyle(element, "top") || 0,
   dd = "(document.documentElement)";
   document.documentElement.style.textOverflow = "ellipsis";
   element.style.position = "absolute";
   element.style.setExpression("top", "eval(" + dd + ".scrollTop + " + (top - this.scrollTop()) + ') + "px"');
  }
  else {
   element.style.position = "fixed"; 
  }
 },
 doMove: function(obj, iTarget, callback) {
  clearInterval(obj.timer);
  obj.timer = setInterval(function() {
   var iCur = fgm.currentStyle(obj, "height"),
   iSpeed = (iTarget - iCur) / 5;
   iSpeed = iSpeed > 0 ? Math.ceil(iSpeed) : Math.floor(iSpeed);
   iTarget == iCur ? (clearInterval(obj.timer), callback && callback.call(obj)) : obj.style.height = iSpeed + iCur + "px";
  }, 30);  
 }
};
window.onload = function() {
 var oWrap = document.getElementById("wrap"),
 oTopBar = document.getElementById("topBar"),
 oNav = document.getElementById("nav"),
 fnStatus = function(status) {
  !!status ?
  (function() {
   oNav.className = "hide";
   fgm.doMove(oTopBar, 4);
   fgm.doMove(oNav, 30);
  })() :
  (function() {
   fgm.doMove(oTopBar, 28);
   fgm.doMove(oNav, 50, function() {
    this.className = "" ;
   })
  })();
 }; 
 fgm.fixed(oWrap);//IE6 Fixed
 window.onscroll = function() {
  var iScrollTop = fgm.scrollTop();
  fnStatus(iScrollTop > 0);
  document.onmouseover = function(ev) {
   var oEv = ev || event,
   oTarget = oEv.target || oEv.srcElement,
   contains = function() {
    if(oWrap.contains) {
     return oWrap.contains(oTarget); 
    }
    else if(oWrap.compareDocumentPosition) {
     return !!(oWrap.compareDocumentPosition(oTarget) & 16);
    }
   }();
   fnStatus(!contains && iScrollTop > 0);
  };
 };
 window.onscroll();
};
