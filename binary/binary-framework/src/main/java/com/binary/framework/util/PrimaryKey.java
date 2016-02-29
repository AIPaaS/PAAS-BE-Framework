package com.binary.framework.util;

import java.util.HashMap;
import java.util.Map;

import com.binary.core.http.HttpClient;
import com.binary.core.lang.Conver;
import com.binary.framework.exception.PrimaryKeyException;
import com.binary.json.JSON;


/**
 * 主键获取对象
 */
public class PrimaryKey {
	
	
	public static class PrimaryKeyRemoteResult {
		private boolean success;
		private String data;
		private int errorCode;
		private String errorMsg;
		public boolean isSuccess() {
			return success;
		}
		public void setSuccess(boolean success) {
			this.success = success;
		}
		public String getData() {
			return data;
		}
		public void setData(String data) {
			this.data = data;
		}
		public int getErrorCode() {
			return errorCode;
		}
		public void setErrorCode(int errorCode) {
			this.errorCode = errorCode;
		}
		public String getErrorMsg() {
			return errorMsg;
		}
		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}
	}
	
	
		
	/**
	 * 获取远程Key值url, 需在Frame环境变量中配置
	 */
	private static final String Single_Web_Root = "Single_Web_Root";
	private static final String Primary_Key_Batch = "Primary_Key_Batch";
	
	
	public static final String PUBLIC = "|PUBLIC|";
	public static final int DEFAULT_BATCH = 500;
	private static int frameBatch = -1;
	
	private static final Object sync = new Object();
	
	private static final Map<String, PrimaryKey> keys = new HashMap<String, PrimaryKey>();
	
	
	
	private String name;
	private int batch;
	
	
	private long curr;
	private long max;
	
	
	protected PrimaryKey(String name, int batch) {
		this.name = name.trim().toUpperCase();
		this.batch = batch;
		this.curr = 0;
		this.max = 0;
	}
	
	
	
	public synchronized long next() {
		if(this.curr >= this.max) {
			try {
				return getRemoteData();
			}catch(Exception e) {
				throw new PrimaryKeyException("connect remote data error!", e);
			}
		}
		this.curr ++ ;
		return this.curr;
	}
	
	
	
	private long getRemoteData() {
		FrameworkProperties properties = FrameworkProperties.getInstance();
		String url = properties.getString(Single_Web_Root);
		if(url==null || (url=url.trim()).length()==0) throw new PrimaryKeyException(" is not setting "+Single_Web_Root+" at framework-config! ");
		if(url.charAt(url.length()-1) != '/') url += "/";
		url += "do/simple/PrimaryKey/getKey";
		
		HttpClient client = HttpClient.getInstance(url);
		Map<String,String> form = new HashMap<String,String>();
		form.put("tx_ps", JSON.toString(new String[]{this.name, String.valueOf(this.batch)}));
		String s = client.request(form);
		
		if(s==null || (s=s.trim()).length()==0) {
			throw new PrimaryKeyException(" connect remote data: the return value is empty! ");
		}
		
		PrimaryKeyRemoteResult rs = JSON.toObject(s, PrimaryKeyRemoteResult.class);
		if(!rs.isSuccess()) throw new PrimaryKeyException(" connect remote data error: " + rs.getErrorMsg());
		
		String data = rs.getData();
		int index = data.indexOf("-");
		if(index <= 0) throw new PrimaryKeyException(" connect remote data: the return value is wrong '"+data+"'! ");
		
		String d1 = data.substring(0, index);
		String d2 = data.substring(index+1);
		
		this.curr = Long.parseLong(d1);
		this.max = Long.parseLong(d2);
		
		return this.curr;
	}
	
	
	
	
	
	public static PrimaryKey getInstance() {
		return getInstance(null, null);
	}
	
	public static PrimaryKey getInstance(String name) {
		return getInstance(name, null);
	}
	
	public static PrimaryKey getInstance(String name, Integer batch) {
		if(name==null || (name=name.trim()).length()==0) {
			name = PUBLIC;
		}
		
		if(batch == null) {
			if(frameBatch < 0) {
				synchronized(sync) {
					if(frameBatch < 0) {
						FrameworkProperties properties = FrameworkProperties.getInstance();
						String fbobj = properties.getString(Primary_Key_Batch);
						if(fbobj==null || (fbobj=fbobj.trim()).length()==0) {
							frameBatch = DEFAULT_BATCH;
						}else {
							int fb = Conver.to(fbobj, int.class);
							if(fb <= 0) throw new PrimaryKeyException(" the framework-property setting "+Primary_Key_Batch+" is wrong '"+fb+"'! ");
							frameBatch = fb;
						}
					}
				}
			}
			batch = frameBatch;
		}
		
		if(batch <= 0) throw new PrimaryKeyException(" the batch must > 0! ");
		
		name = name.toUpperCase();
		
		PrimaryKey key = keys.get(name);
		if(key == null) {
			synchronized(keys) {
				key = keys.get(name);
				if(key == null) {
					key = new PrimaryKey(name, batch);
					keys.put(name, key);
				}
			}
		}
		return key;
	}
	
	
	
}
