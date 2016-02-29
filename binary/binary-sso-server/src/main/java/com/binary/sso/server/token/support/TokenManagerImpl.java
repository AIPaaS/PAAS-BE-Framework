package com.binary.sso.server.token.support;

import java.util.Iterator;
import java.util.LinkedList;

import com.binary.sso.server.SsoServerConfiguration;
import com.binary.sso.server.cache.SsoCache;
import com.binary.sso.server.token.Token;
import com.binary.sso.server.token.TokenManager;
import com.binary.core.util.BinaryUtils;

public class TokenManagerImpl implements TokenManager {
	
	
	private final LinkedList<Token> tokens = new LinkedList<Token>();
	
	private SsoServerConfiguration configuration;
	private SsoCache cache;
	
	private TokenMonitor monitor;
	
	
	public TokenManagerImpl(SsoServerConfiguration configuration) {
		BinaryUtils.checkEmpty(configuration, "configuration");
		SsoCache cache = configuration.getSsoCache();
		BinaryUtils.checkEmpty(cache, "configuration.cache");
		
		this.cache = cache;
		this.monitor = new TokenMonitor(configuration.getScanTokenIntervalTime(), this);
		this.monitor.start();
		
		this.configuration = configuration;
	}
	
	



	@Override
	public Token newToken(long userId, String sessionId) {
		Token token = new Token(userId, sessionId);
		cache.setToken(token);
		tokens.add(token);
		return token;
	}
	
	
	
	
	/**
	 * 判断token是超时
	 * @param time : 当前时间
	 * @param token
	 * @return true=有效    false=无效
	 */
	private boolean verifyValid(long time, Token token) {
		return (token.getTime() + configuration.getTokenValidTime()*1000) >= time;
	}
	
	
	
	

	@Override
	public boolean verifyToken(String tokenId) {
		Token token = cache.getToken(tokenId);
		if(token == null) return false;
		
		long time = System.currentTimeMillis();
		return verifyValid(time, token);
	}

	
	

	@Override
	public Token getToken(String tokenId) {
		Token token = cache.getToken(tokenId);
		if(token == null) return null;
		
		long time = System.currentTimeMillis();
		boolean ba = verifyValid(time, token);
		
		return ba ? token : null;
	}
	
	
	
	@Override
	public void removeInvalidTokens() {
		long time = System.currentTimeMillis();
		
		Iterator<Token> itor = tokens.iterator();
		while(itor.hasNext()) {
			Token token = itor.next();
			//如果token已无效则清除
			if(!verifyValid(time, token)) {
				try {
					cache.removeToken(token.getId());
					itor.remove();
				}catch(Exception e) {
					//没有处理的意义
				}
			}
		}
	}





	@Override
	public TokenMonitor getTokenMonitor() {
		return this.monitor;
	}
	
	
	
	
	
	

}
