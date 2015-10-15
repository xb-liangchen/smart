function appendZero(str,lenth){
	for (var i = str.length; i < lenth; i++) {
		str="0"+str;
	}
	return str;
}