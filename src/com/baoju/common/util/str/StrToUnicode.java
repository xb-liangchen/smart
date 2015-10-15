package com.baoju.common.util.str;

import java.io.*;

public class StrToUnicode
{ 
 static Integer outIng;
 static String unicodeStr;
 static boolean isSend = true;

    public static void main(String[] args)
 {     try{
                InputStreamReader reader = new InputStreamReader(System.in);
    BufferedReader input = new BufferedReader(reader);
    System.out.println("Enter your number:(input end,the app exit) ");
    String name = input.readLine();
       
                if(name !="end")
    {    //方法一:将asceII字符串转成unicode字符串
              //String unicodeTmp=toUnicode(name);
           //System.out.println("unicodeTmp="+unicodeTmp);
      //方法二:将asceII字符串转成unicode字符串
          //String unicodeTmp = new String(name.getBytes("iso-8859-1"),"gb2312");
                         //System.out.println("unicodeTmp="+unicodeTmp);
                     //将字符串转化成十六进制unicode码
           String unicodeTmp=toFormatUnicode(name);
        System.out.println("unicodeTmp="+unicodeTmp);
       
    }
         }catch(Exception ex){
    System.out.println("Exception :"+ex);
   }
     }
   public static String toStr(String unicode)
  {  String strRet = "";
        

        return strRet;
   }  

  public static String toUnicode(String strText)
  throws UnsupportedEncodingException {
   char c;
   String strRet = "";
   int intAsc;
   String strHex;
   for (int i = 0; i < strText.length(); i++) {
    c = strText.charAt(i);
    intAsc = (int) c;
    if (intAsc > 128) {
     strHex = Integer.toHexString(intAsc);
     strRet += "\\u" + strHex;
    } else {
      strRet = strRet + c;
    }
   }
   return strRet;
  }
     public static String toFormatUnicode(String strText)
   throws UnsupportedEncodingException {
           char c;
   String strRet = "";
   int intAsc;
   String strHex;
   for (int i = 0; i < strText.length(); i++) {
        c = strText.charAt(i);
        intAsc = (int) c;
     strHex = Integer.toHexString(intAsc);
     if (intAsc > 128) {
          strRet += "\\u" + strHex;
     }else{
       strRet += "\\" + strHex;
     }
   } 
  return strRet;
  }

  public String readUnicodeFile(String filename) {
  StringBuffer buffer = null;
  InputStream is = null;
  InputStreamReader isr = null;
  try {
   Class c = this.getClass();
   is = c.getResourceAsStream(filename);
   if (is == null)  throw new Exception("File Does Not Exist");

    isr = new InputStreamReader(is,"UTF8");

     buffer = new StringBuffer();
   int ch;
   while ((ch = isr.read()) > -1) {
    buffer.append((char)ch);
   }
   if (isr != null)
    isr.close();
     } catch (Exception ex) {
   System.out.println(ex);
  }
  return buffer.toString();
      }
 /*

////////22222
String str="test测试";
new String(str.getBytes("iso-8859-1"));

///////33333
new String(str.getBytes("iso-8859-1"),"gb2312");
*/
   private String convertStrToUnicode(String value)
 {
  short valueAsShort = Short.parseShort(value.trim(),16);
  return String.valueOf((char)valueAsShort);
 }
  public String convertUnicodeToStr(String unicode)
 { 
      
    try{ 
     byte[]  temp_t=unicode.getBytes("ISO8859-1"); 
           String  temp=new  String(temp_t); 
           System.out.println(temp);
     return temp; 
          }catch(Exception  e){} 
  return null; 
     }}