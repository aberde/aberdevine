/**
 * 테마 변경.
 */
$.mobile.changeGlobalTheme = function(theme) {
	var themes = " a b c d e";
	
	function setTheme(cssSelector, themeClass, theme) {
		$(cssSelector)
			.removeClass(themes.split(" ").join(" " + themeClass + "-"))
			.addClass(themeClass + "-" + theme)
			.attr("data-theme", theme);
	}
	
	setTheme(".ui-mobile-viewport", "ui-overlay", theme);
	setTheme("[data-role='page']", "ui-body", theme);
	setTheme("[data-role='header']", "ui-bar", theme);
	setTheme("[data-role='panel']", "ui-body", theme);
	setTheme("[data-role='listview'] > li", "ui-bar", theme);
	setTheme(".ui-btn", "ui-btn-up", theme);
	setTheme(".ui-btn", "ui-btn-hover", theme);
};

/**
 * 기본 테마 설정.
 */
$(document).ready(function() {
	$.mobile.changeGlobalTheme("a");
	console.log("A");
});
