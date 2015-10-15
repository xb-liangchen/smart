package test;

import java.util.Date;
import java.util.List;
import java.io.Serializable;

import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jsoup.Jsoup;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baoju.weixin.entity.SysDevice;
import com.baoju.weixin.entity.SysUser;
import com.baoju.weixin.service.IUserService;

public class MainTest {
	 public static void main(String[] args) throws DocumentException {  
		 ClassPathXmlApplicationContext ca = new ClassPathXmlApplicationContext(new String[]{"classpath:config/xml/spring-hibernate.xml","classpath:config/xml/spring-dao.xml"});  
	    //  System.out.println("----1----");
	      IUserService service=(IUserService)ca.getBean("userService");
	  //    System.out.println(service.getDevBySql(10).getIsOnline());
	 }  
}
