package com.baoju.common.util.str;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ThrowableToStr {
	/**将异常转化为字符串*/
	public static String getTrace(Throwable t) {
        StringWriter stringWriter= new StringWriter();
        PrintWriter writer= new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer= stringWriter.getBuffer();
        return buffer.toString();
    }
}
