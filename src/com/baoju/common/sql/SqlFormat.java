package com.baoju.common.sql;

import java.text.MessageFormat;


/**
 * 防止sql注入
 * @author dengxp
 *
 */
public class SqlFormat {
	/**
	 * 使用  demo
	 *String findchildHql="from InfoNewsComment where newsId=''{0}'' and state=''{1}'' order by createTime asc ";
	 * String fhql=SqlFormat.geteFormatString(findchildHql, id,state);
	 */
	public static String geteFormatString(String template,Object... value){
		String formatSql=MessageFormat.format(template, value);
		return formatSql;
	}
	
}
