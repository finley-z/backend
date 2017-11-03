package com.finley.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * 
 * @description 根据ip地址获取,城市归属地
 * @author sunxc
 * @version 1.0
 */

public class IpUtil {

	private final static Logger logger = Logger
			.getLogger(IpUtil.class);
	
	/**
	 * 获取登录用户的IP
	 * 
	 * @param
	 * @return
	 */
	public static String getIp() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "本地";
		}
		if (ip.split(",").length > 1) {
			ip = ip.split(",")[0];
		}
		return ip;
	}

	/**
	 * 通过IP获取地址吗，返回json格式 （json格式的）国家 、省（自治区或直辖市）、市（县）、运营商
	 * {"code":0,"data":{"ip"
	 * :"210.75.225.254","country":"\u4e2d\u56fd","area":"\u534e\u5317",
	 * "region"
	 * :"\u5317\u4eac\u5e02","city":"\u5317\u4eac\u5e02","county":"","isp"
	 * :"\u7535\u4fe1",
	 * "country_id":"86","area_id":"100000","region_id":"110000"
	 * ,"city_id":"110000", "county_id":"-1","isp_id":"100017"}}
	 * 其中code的值的含义为，0：成功，1：失败。
	 * 
	 * @param ip
	 * @return
	 */
	public static String getIpInfo(String ip) {
		String info = "";
		try {
			URL url = new URL("http://ip.taobao.com/service/getIpInfo.php?ip="
					+ ip);
			HttpURLConnection htpcon = (HttpURLConnection) url.openConnection();
			htpcon.setRequestMethod("GET");
			htpcon.setDoOutput(true);
			htpcon.setDoInput(true);
			htpcon.setUseCaches(false);

			InputStream in = htpcon.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(in));
			StringBuffer temp = new StringBuffer();
			String line = bufferedReader.readLine();
			while (line != null) {
				temp.append(line).append("\r\n");
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
				JSONObject obj = JSONObject.parseObject(temp.toString());
			if (obj.getInteger("code") == 0) {
				JSONObject data = obj.getJSONObject("data");
				info += data.getString("country") + " ";
				info += data.getString("region") + " ";
				info += data.getString("city") + " ";
				info += data.getString("isp");
			}
		} catch (MalformedURLException e) {
			logger.error("根据IP获取地址MalformedURLException异常!", e);
		} catch (ProtocolException e) {
			logger.error("根据IP获取地址ProtocolException异常!", e);
		} catch (IOException e) {
			logger.error("根据IP获取地址IOException异常!", e);
		} catch (Exception e){
			logger.error("根据IP获取地址信息异常!", e);
		}
		return info;
	}

	
}
