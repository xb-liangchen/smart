package com.baoju.common.dao;

import java.io.IOException;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.PrePersist;
import javax.sql.rowset.serial.SerialClob;

import com.baoju.common.util.str.ZipStrUtil;



public class JdbcConn {

	public static final String username="root";
	public static final String password="root";
	public static final String url="jdbc:mysql://127.0.0.1:3306/webapp?useUnicode=true&characterEncoding=utf-8&autoReconnect=true";
	public static final String driver="com.mysql.jdbc.Driver";
	//public static Connection conn=null;
	static SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
	static{
		
	}
	public static Connection getConn(){
		Connection conn=null;
		try {
			Driver.class.forName(driver);
			conn=DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return conn;
	}
	public static void closeConn(Connection conn,PreparedStatement ps,ResultSet rs){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(ps!=null){
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static long saveImage(String fileTitle,String extend,String realPath,String frommodule){
		//sys_attachment
		 Connection conn=null;
		   PreparedStatement ps=null;
		   String mes="";
		   SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		   String date=sdf.format(new Date());
		   Random r=new Random();
		   int rr=r.nextInt(99999);
		   long lid=0l;
			try {
				conn=getConn();
				
				conn.setAutoCommit(true);
				String sql="insert into sys_attachment(id,file_title,extend,realpath,from_module) values(?,?,?,?,?)";
				ps=conn.prepareStatement(sql);
				lid=Long.valueOf(date+rr);
				ps.setLong(1,lid );
				ps.setString(2, fileTitle);
				ps.setString(3,extend);
				ps.setString(4,realPath);
				ps.setString(5,frommodule);
				ps.execute();
				//System.out.println("批量增加成功！共插入记录条：="+i);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}finally{
				closeConn(conn, ps,null);
			}
			return lid;
	}
	
	public static void main(String[] args) throws IOException {
		String newcontent="中新网9月15日电 据北京市公安局官方微博      ad多岁的“sss”";
		String str=ZipStrUtil.compress(newcontent);
		String sql="insert into info_news(title,content) values('test','"+str+"')";
		//String sql2="select content from info_news t where t.id =(select max(id) from info_news)";
		String sql2="select content from info_news t where t.id =115";//115 119 ok
		PreparedStatement ps=null;
		ResultSet rs=null;
		Connection conn=null;
		try {
			conn=getConn();
		//	ps=conn.prepareStatement(sql);
		//	ps.execute();
			ps=conn.prepareStatement(sql2);
			rs=ps.executeQuery();
			if(rs.next()){
				String str1=rs.getString(1);
				String str2=ZipStrUtil.unCompress(str1);
				System.out.println("解压前："+str1.length());
				System.out.println("解压后："+str2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeConn(conn, ps, rs);
		}
		
	}
}
