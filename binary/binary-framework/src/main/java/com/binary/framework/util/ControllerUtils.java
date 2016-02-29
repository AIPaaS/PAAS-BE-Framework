package com.binary.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.binary.core.http.HttpUtils;
import com.binary.core.io.FileSystem;
import com.binary.core.io.Resource;
import com.binary.core.lang.Conver;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.Local;
import com.binary.framework.exception.ControllerException;
import com.binary.framework.web.RemoteResult;
import com.binary.json.JSON;

public abstract class ControllerUtils {
	
	
	
	public static ModelAndView returnJson(HttpServletRequest request, HttpServletResponse response, Object result) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		
		RemoteResult jr = new RemoteResult(result);
		try {
			PrintWriter pw = response.getWriter();
			pw.write(JSON.toString(jr));
			pw.flush();
		} catch (IOException e) {
			throw new ControllerException(e);
		}
		return null;
	}
	
	
	
	public static ModelAndView returnSimpleJson(HttpServletRequest request, HttpServletResponse response, Object result) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		
		if(result != null) {
			try {
				PrintWriter pw = response.getWriter();
				pw.write(JSON.toString(result));
				pw.flush();
			} catch (IOException e) {
				throw new ControllerException(e);
			}
		}
		
		return null;
	}
	
	
	
	public static String[] splitStringPattern(String strPattern) {
		if(strPattern.indexOf(";") > 0) {
			return strPattern.split("[;]");
		}else {
			return new String[]{strPattern};
		}
	}
	
	
	
	public static ModelAndView returnResource(HttpServletRequest request, HttpServletResponse response, Resource resource) {
		return returnResource(request, response, resource, null, false, null);
	}
	
	
	
	public static ModelAndView returnResource(HttpServletRequest request, HttpServletResponse response, Resource resource, String contentType) {
		return returnResource(request, response, resource, contentType, false, null);
	}
	
	
	
	/**
	 * 下载文件
	 * @param request
	 * @param response
	 * @param resource
	 * @param contentType
	 * @param openWindow : 是否弹出"另存为"窗口
	 * @param fileName : 在"另存为"窗口中显示的名字
	 * @return
	 */
	public static ModelAndView returnResource(HttpServletRequest request, HttpServletResponse response, Resource resource, String contentType, boolean openWindow, String fileName) {
		if(resource==null || !resource.exists()) {
			throw new ControllerException(" is not found resource '"+resource+"'! ");
		}
		if(BinaryUtils.isEmpty(contentType)) {
			contentType = "application/octet-stream";
		}
		if(openWindow) {
			if(BinaryUtils.isEmpty(fileName)) {
				fileName = "file";
			}
			try {
				response.addHeader("Content-Disposition","attachment;filename="+URLEncoder.encode(fileName, Local.getCharset()));
			} catch (UnsupportedEncodingException e1) {
				throw BinaryUtils.transException(e1, ControllerException.class);
			}
		}
		
		try {
			OutputStream os = response.getOutputStream();
			InputStream is = resource.getInputStream();
			try {
				FileSystem.copy(is, os);
				os.flush();
			}finally {
				if(is != null) is.close();
			}
		} catch (IOException e) {
			throw new ControllerException(e);
		}
		
		return null;
	}

	
	
	
	


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T toRemoteJsonObject(String jsonstring, Class<T> clazz) {
		Map<String,Object> obj = (Map)JSON.toObject(jsonstring);
		Object data = obj.get("data");
		boolean success = Conver.to(obj.get("success"), Boolean.class);
		String errorMsg = (String)obj.get("errorMsg");
		
		if(success) {
			return Conver.mapping(clazz, data);
		}else {
			throw new ControllerException(" call remote error! " + errorMsg);
		}
	}
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> List<T> toRemoteJsonList(String jsonstring, Class<T> clazz) {
		Map<String,Object> obj = (Map)JSON.toObject(jsonstring);
		Object data = obj.get("data");
		boolean success = Conver.to(obj.get("success"), Boolean.class);
		String errorMsg = (String)obj.get("errorMsg");
		
		if(success) {
			List<?> list = (List<?>)data;
			
			if(list != null) {
				List<T> rs = new ArrayList<T>();
				for(int i=0; i<list.size(); i++) {
					Object o = list.get(i);
					rs.add(Conver.mapping(clazz, o));
				}
				return rs;
			}else {
				return null;
			}
		}else {
			throw new ControllerException(" call remote error! " + errorMsg);
		}
	}
	
	
	
	
	
	/**
	 * 获取客户端IP
	 * @param request
	 * @return
	 */
	public static String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        
        if(BinaryUtils.isEmpty(ip)) {
        	throw new ControllerException(" is unknown client ip! ");
        }
        return ip;
	}
	
	
	
	
	/**
	 * 格式化ContextPath
	 * @param contextPath
	 * @return
	 */
	public static String formatContextPath(String contextPath) {
		return HttpUtils.formatContextPath(contextPath);
	}
	
	
	
	
	
	
}
