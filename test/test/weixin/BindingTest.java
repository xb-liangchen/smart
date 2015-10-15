package test.weixin;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.baoju.common.util.Consts;
import com.baoju.common.util.url.HttpConnUtil;

public class BindingTest {

	public static void main(String[] args) {
		Consts.ACCESS_TOKEN="VPCKhUTpccYXXuAF5MNOrbgPppRugtuAk_mCnbdiJzHgV1LnlvK3bSnNhUJ6iRw0tUOT9N7-bZ8OuiT79eSFvvrWQSKE_jxvbKqqX65lkkk";
		String mac="ACCF234B0AD4";
		System.out.println(deviceAuth(mac));
	}
	/**
	 * 设备授权
	 * @param access_token
	 * @param deviceId
	 * @return
	 */
	public static boolean deviceAuth(String mac){
		
		String URL = "https://api.weixin.qq.com/device/authorize_device?access_token="+Consts.ACCESS_TOKEN;
		
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("device_num", 3);
		JSONArray jsonArray = new JSONArray();
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("id", "gh_5f5612f02294_52cfee84dbe50c993e23fa26f29ea61f");
		jsonObj.put("mac",mac);
		jsonObj.put("connect_protocol", "4");//wifi
		jsonObj.put("auth_key", "");
		jsonObj.put("close_strategy", "3");
		jsonObj.put("conn_strategy", "8");
		jsonObj.put("crypt_method", "0");
		jsonObj.put("auth_ver", "0");
		jsonObj.put("manu_mac_pos", "-2");
		jsonObj.put("ser_mac_pos", "-2");
		jsonArray.add(jsonObj);
		
		JSONObject jsonObj2 = new JSONObject();
		jsonObj2.put("id", "gh_5f5612f02294_111f6d247719bb7bfb40b37c3f0096ad");
		jsonObj2.put("mac",mac);
		jsonObj2.put("connect_protocol", "4");//wifi
		jsonObj2.put("auth_key", "");
		jsonObj2.put("close_strategy", "3");
		jsonObj2.put("conn_strategy", "8");
		jsonObj2.put("crypt_method", "0");
		jsonObj2.put("auth_ver", "0");
		jsonObj2.put("manu_mac_pos", "-2");
		jsonObj2.put("ser_mac_pos", "-2");
		jsonArray.add(jsonObj2);
		
		
		JSONObject jsonObj3 = new JSONObject();
		jsonObj3.put("id", "gh_5f5612f02294_c2c1e12121fc784ba448e6f7fc1a0e1e");
		jsonObj3.put("mac",mac);
		jsonObj3.put("connect_protocol", "4");//wifi
		jsonObj3.put("auth_key", "");
		jsonObj3.put("close_strategy", "3");
		jsonObj3.put("conn_strategy", "8");
		jsonObj3.put("crypt_method", "0");
		jsonObj3.put("auth_ver", "0");
		jsonObj3.put("manu_mac_pos", "-2");
		jsonObj3.put("ser_mac_pos", "-2");
		jsonArray.add(jsonObj3);
		
		
		jsonObject.put("device_list", jsonArray);
		jsonObject.put("op_type", "1");
		
		String res=HttpConnUtil.getRespJsonStr(URL, Consts.Method_POST, jsonObject);
		
		JSONObject resobj=JSONObject.fromObject(res);
		if(resobj.get("errcode")==null){
			//操作成功
			return true;
		}else{
			//操作失败
			System.out.println("设备授权 失败："+resobj.get("errmsg"));
			return false;
		}
	}
}
