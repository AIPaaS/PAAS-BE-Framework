package com.binary.sso.server.token;

import com.binary.sso.server.token.support.TokenMonitor;



/**
 * token管理器
 * @author wanwb
 */
public interface TokenManager {

	
	
	
	/**
	 * 跟据用户ID创建一个新的token
	 * @param userId
	 * @param sessionId
	 * @return
	 */
	public Token newToken(long userId, String sessionId);
	
	
	
	/**
	 * 验证token是否有效
	 * @param tokenId
	 * @return
	 */
	public boolean verifyToken(String tokenId);
	
	
	
	/**
	 * 跟据tokenId获取token信息, 如果token已过时则返回null
	 * @param tokenId
	 * @return
	 */
	public Token getToken(String tokenId);
	
	
	
	
	/**
	 * 删除所有无效的Token
	 */
	public void removeInvalidTokens();
	
	
	
	
	/**
	 * 获取Token监测对象
	 * @return
	 */
	public TokenMonitor getTokenMonitor();
	
	
	
	
}
