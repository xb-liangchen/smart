//控制不同型号手机的字体大小
function zy_init(){

	if(window.navigator.platform=="Win32")
		document.body.style.fontSize=window.localStorage["defaultfontsize"];
}