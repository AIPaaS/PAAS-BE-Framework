package com.binary.sso.server.cache;

import java.util.Set;

import com.binary.sso.server.token.Token;


/**
 * SSO缓存接口
 * @author wanwb
 */
public interface SsoCache {
	
	
	
	/**
	 * 将token添加至缓存中
	 * @param token
	 */
	public void setToken(Token token);
	
	
	
	/**
	 * 跟据tokenId从缓存中获取Token
	 * @param key
	 * @return
	 */
	public Token getToken(String tokenId);
	
	
	
	
	/**
	 * 跟据tokenId删除缓存中的Token
	 * @param tokenId
	 * @return
	 */
	public boolean removeToken(String tokenId);
	
	
	
	
	/**
	 * 判断Session中指定的键是否存在
	 * @param sessionId
	 * @param attributeKey
	 * @return
	 */
	public boolean containsSessionAttribute(String sessionId, String attributeKey);
	
	
	
	
	/**
	 * 获取Session属性值
	 * @param sessionId : SessionID
	 * @param sessionKey : 属性名
	 * @return
	 */
	public Object getSessionAttribute(String sessionId, String attributeKey);
	
	
	
	
	/**
	 * 设置Session属性
	 * @param sessionId : SessionID
	 * @param attributeKey : 属性名
	 * @param attributeValue : 属性值
	 */
	public void setSessionAttribute(String sessionId, String attributeKey, Object attributeValue);
	
	
	
	
	/**
	 * 删除Session属性
	 * @param sessionId : SessionID
	 * @param attributeKey : 属性名
	 */
	public boolean removeSessionAttribute(String sessionId, String attributeKey);
	
	
	
	
	
	/**
	 * 清空当前Session所有属性值
	 * @param sessionId
	 * @return 被清空的属性个数
	 */
	public int clearSessionAttribute(String sessionId);
	
	
	
	
	/**
	 * 获取Session中所有属性名
	 * @param sessionId
	 * @return
	 */
	public Set<String> getSessionAttributeKeySet(String sessionId);
	
	
	
	

}
