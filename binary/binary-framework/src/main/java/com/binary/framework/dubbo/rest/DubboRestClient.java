package com.binary.framework.dubbo.rest;

import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.binary.core.http.HttpUtils;
import com.binary.core.io.FileSystem;
import com.binary.core.lang.Conver;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.Local;
import com.binary.framework.bean.User;
import com.binary.framework.exception.DubboException;
import com.binary.framework.util.UserUtils;
import com.binary.jdbc.Page;
import com.binary.json.JSON;

public class DubboRestClient {
	
	
	
	//http://localhost:29801/paas-provider-sys
	private String restRoot;
	
	private String url;
	
	
	public DubboRestClient(String restRoot) {
		BinaryUtils.checkEmpty(restRoot, "restRoot");
		this.restRoot = HttpUtils.formatContextPath(restRoot).substring(1);
		this.url = this.restRoot + "/dubbo/rest/post";
	}
	
	
	
	
	public Object request(String beanName, String methodName, Object[] args, Class<?> resultType, Type resultGenericType) throws Throwable {
		BinaryUtils.checkEmpty(beanName, "beanName");
		BinaryUtils.checkEmpty(methodName, "methodName");
		
		DubboRestParam param = new DubboRestParam();
		param.setBeanName(beanName);
		param.setMethodName(methodName);
		
		List<String> jsonArgs = new ArrayList<String>();
		if(args!=null && args.length>0) {
			for(int i=0; i<args.length; i++) {
				Object arg = args[i];
				String s = null;
				if(arg != null) {
					s = JSON.toString(arg);
				}
				jsonArgs.add(s);
			}
		}
		param.setJsonArguments(jsonArgs);
		
		User user = Local.getUser();
		if(user != null) {
			param.setJsonUser(UserUtils.toJsonString(user));
		}
		
		String data = JSON.toString(param);
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(new URL(this.url).toURI());
        StringEntity dataEntity = new StringEntity(data, ContentType.APPLICATION_JSON);
        httpPost.setEntity(dataEntity);
        CloseableHttpResponse response = httpclient.execute(httpPost);
		
        Object result = null;
        try {
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                
                InputStream is = null;
                try {
                	String rs = FileSystem.read(entity.getContent(), "UTF-8");
                	if(BinaryUtils.isEmpty(rs)) {
                		throw new DubboException(" call remote service '"+beanName+"."+methodName+"()' error! ");
                	}
                	
                	DubboRestResult rr = JSON.toObject(rs, DubboRestResult.class);
                	if(!rr.isSuccess()) {
                		throw new DubboException(" call remote service '"+beanName+"."+methodName+"()' error! "+rr.getErrorMsg());
                	}
                	
                	String rdata = rr.getData();
                	if(rdata!=null && resultType!=null && resultType!=void.class) {
                		result = processResult(rdata, resultType, resultGenericType);
                	}
                }finally {
                	if(is != null) is.close();
                }
            } else {
                throw new RuntimeException("error code " + response.getStatusLine().getStatusCode()
                        + ":" + response.getStatusLine().getReasonPhrase());
            }
        } finally {
            response.close();
            httpclient.close();
        }
        
        return result;
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object processResult(String jsonString, Class<?> resultType, Type resultGenericType) {
		Object obj = JSON.toObject(jsonString);
		if(Page.class.isAssignableFrom(resultType)) {
			if(!(obj instanceof Map)) {
				throw new DubboException(" is wrong json-result '"+jsonString+"'! ");
			}
			Map map = (Map)obj;
			
			ParameterizedType pt = (ParameterizedType)resultGenericType;
			Type[] actualTypes = pt.getActualTypeArguments();
			if(actualTypes==null || actualTypes.length==0) {
				throw new DubboException(" not setting actualType at result-page type! ");
			}
			if(!(actualTypes[0] instanceof Class)) {
				throw new DubboException(" is wrong actualType '"+actualTypes[0]+"' at result-page type! ");
			}
			Class componentType = (Class)actualTypes[0];
			
			Page page = new Page();
			page.setPageNum(Conver.to(map.get("pageNum"), Long.class));
			page.setPageSize(Conver.to(map.get("pageSize"), Long.class));
			page.setTotalRows(Conver.to(map.get("totalRows"), Long.class));
			page.setTotalPages(Conver.to(map.get("totalPages"), Long.class));
			page.setData(toList((List)map.get("data"), componentType));
			return page;
		}else {
			return Conver.mapping(resultGenericType, obj);
		}
	}
	
	
	
	
	private static <T> List<T> toList(List<?> list, Class<T> clazz) {
		List<T> array = new ArrayList<T>();
		if(list != null) {
			for(int i=0; i<list.size(); i++) {
				Object item = list.get(i);
				array.add(Conver.mapping(clazz, item));
			}
		}
		return array;
	}
	
	
	

}
