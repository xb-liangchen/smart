package com.baoju.common.util.str;

public class CharName {

	public static String dynamicName(String str){
		char [] chars={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		//System.out.println(chars[1]);
		String res="A";
		if(str!=null && !"".equals(str)){
			str=str.toUpperCase();
			for (int i = 0; i < chars.length; i++) {
				String id=""+chars[i];
				if(str.endsWith(id)){
					res=""+chars[i+1];
					break;
				}else if(str.endsWith("Z")){
					res="A";
					break;
				}
			}
		}
		return res; 
	}
	public static String getOldName(String str){
		char [] chars={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		//System.out.println(chars[1]);
		String res="A";
		if(str!=null && !"".equals(str)){
			str=str.toUpperCase();
			for (int i = 0; i < chars.length; i++) {
				String id=""+chars[i];
				if(str.contains(id)){
					res=""+chars[i];
					break;
				}
			}
		}
		return res; 
	}
	
	
	public static void main(String[] args) {
		System.out.println(dynamicName("探测器Z"));
	}
}
