package com.binary.tools.mail;



import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.binary.core.io.Resource;
import com.binary.core.io.ResourceResolver;
import com.binary.core.lang.StringLinker;
import com.binary.core.util.BinaryUtils;
import com.binary.tools.exception.EmailException;




/**
 * 邮件模板
 * @author wanwb
 */
public class EmailTemplate {
	
	
	/**
	 * TemplateReader读取器
	 * 外放接口，供外部拦截使用
	 */
	public static interface TemplateReader {
		public String read(String url);
	}
	
	
	
	private static final Map<String, EmailTemplate> templateStore = new HashMap<String, EmailTemplate>();
	
	
	
	private StringLinker linker;
	
	
	
	private EmailTemplate(StringLinker linker) {
		this.linker = linker;
	}
	
	
	
	
	/**
	 * 获取邮件内容
	 * @param paramsInstance: 参数对象Map/Bean
	 * @see getContent(Object paramsInstance, boolean ignoreNull, boolean validKeyExists)
	 * @return
	 */
	public String getContent(Object paramsInstance) {
		return getContent(paramsInstance, true);
	}
	
	
	/**
	 * 获取邮件内容
	 * @param paramsInstance: 参数对象Map/Bean
	 * @param ignoreNull: 是否忽略null值, 忽略表示动态值在参数对象中没有找到, 则以空字段串代替, 否则拼入null
	 * @see getContent(Object paramsInstance, boolean ignoreNull, boolean validKeyExists)
	 * @return
	 */
	public String getContent(Object paramsInstance, boolean ignoreNull) {
		return getContent(paramsInstance, ignoreNull, true);
	}
	
	
	/**
	 * 获取邮件内容
	 * @param paramsInstance: 参数对象Map/Bean
	 * @param ignoreNull: 是否忽略null值, 忽略表示动态值在参数对象中没有找到, 则以空字段串代替, 否则拼入null
	 * @param validKeyExists: 验证键名是否存在
	 * @return
	 */
	public String getContent(Object paramsInstance, boolean ignoreNull, boolean validKeyExists) {
		StringBuilder sb = new StringBuilder();
		this.linker.link(paramsInstance, ignoreNull, validKeyExists, sb);
		return sb.toString();
	}
	
	
	
	
	
	/**
	 * 获取Email模块
	 * @param url: 模块存放路径
	 * @see getTemplate(String url, boolean saveCache, TemplateReader reader)
	 * @return
	 */
	public static EmailTemplate getTemplate(String url) {
		return getTemplate(url, true, null, null);
	}
	
	
	/**
	 * 获取Email模块
	 * @param url: 模块存放路径
	 * @param reader: 模板文件读取器
	 * @see getTemplate(String url, boolean saveCache, TemplateReader reader)
	 * @return
	 */
	public static EmailTemplate getTemplate(String url, TemplateReader reader) {
		return getTemplate(url, true, reader, null);
	}
	
	
	/**
	 * 获取Email模块
	 * @param url: 模块存放路径
	 * @param reader: 模板文件读取器
	 * @param charset: 字符集
	 * @see getTemplate(String url, boolean saveCache, TemplateReader reader)
	 * @return
	 */
	public static EmailTemplate getTemplate(String url, TemplateReader reader, String charset) {
		return getTemplate(url, true, reader, charset);
	}
	
	
	
	/**
	 * 获取Email模块
	 * @param url: 模块存放路径
	 * @param saveCache: 是否存入缓存
	 * @param reader: 模板文件读取器
	 * @param charset: 字符集
	 * @return
	 */
	public static EmailTemplate getTemplate(String url, boolean saveCache, TemplateReader reader, String charset) {
		EmailTemplate tpl = templateStore.get(url);
		if(tpl == null) {
			if(saveCache) {
				tpl = buildSingleTemplate(url, reader, charset);
			}else {
				tpl = createTemplate(url, reader, charset);
			}
		}
		return tpl;
	}
	
	
	private synchronized static EmailTemplate buildSingleTemplate(String url, TemplateReader reader, String charset) {
		EmailTemplate tpl = templateStore.get(url);
		if(tpl == null) {
			tpl = createTemplate(url, reader, charset);
			templateStore.put(url, tpl);
		}
		return tpl;
	}
	
	
	
	private static EmailTemplate createTemplate(String url, TemplateReader reader, String charset) {
		try {
			String content = null;
			if(reader != null) {
				content = reader.read(url);
			}else {
				Resource res = ResourceResolver.getResource(url);
				if(res==null || !res.exists()) throw new EmailException(" is not found email template '"+url+"'! ");
				
				InputStream is = res.getInputStream();
				if(!(is instanceof BufferedInputStream)) {
					is = new BufferedInputStream(is);
				}
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				int n = -1;
				try {
					while((n=is.read()) != -1) {
						os.write(n);
					}
				}finally {
					if(is != null) is.close();
				}
				
				if(BinaryUtils.isEmpty(charset)) charset = "UTF-8";
				content = os.toString(charset);
			}
			
			if(content==null || content.length()==0) throw new EmailException(" the email-template is empty:'"+url+"'!");
			
			return new EmailTemplate(BinaryUtils.parseExpression(content));
		}catch(Exception e) {
			throw BinaryUtils.transException(e, EmailException.class);
		}
	}
	
	
	
	
	
	
	
	
}
