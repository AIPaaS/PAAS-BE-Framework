package com.binary.sso.server.cache.support;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.binary.sso.server.cache.SsoCache;
import com.binary.sso.server.exception.SsoException;
import com.binary.sso.server.token.Token;

public class SimpleSsoCache implements SsoCache {

	
	/** Token存放缓存前缀 **/
	private static final String TOKEN_PREFIX = "BINARY-SSO-TOKEN_";
	
	/** Session中键名存放缓存前缀 **/
	private static final String SESSION_KEYS_PREFIX = "BINARY-SSO-SESSION-KEYS_";
	
	/** Session中值存放缓存前缀 **/
	private static final String SESSION_VALUE_PREFIX = "BINARY-SSO-SESSION-VALUE_";
	
	
	
	private Map<String, Object> cache;
	
	
	public SimpleSsoCache() {
		this.cache = new HashMap<String, Object>();
	}

	
	private String getTokenKey(String tokenId) {
		if(tokenId==null || (tokenId=tokenId.trim().toUpperCase()).length()==0) {
			throw new SsoException(" the tokenId is empty argument! ");
		}
		return TOKEN_PREFIX + tokenId;
	}
	
	private String getSessionKeysKey(String sessionId) {
		if(sessionId==null || (sessionId=sessionId.trim().toUpperCase()).length()==0) {
			throw new SsoException(" the sessionId is empty argument! ");
		}
		return SESSION_KEYS_PREFIX + sessionId;
	}
	
	private String getSessionValueKey(String sessionId, String attributeKey) {
		if(sessionId==null || (sessionId=sessionId.trim().toUpperCase()).length()==0) {
			throw new SsoException(" the sessionId is empty argument! ");
		}
		if(attributeKey==null || (attributeKey=attributeKey.trim()).length()==0) {
			throw new SsoException(" the attributeKey is empty argument! ");
		}
		return SESSION_VALUE_PREFIX + sessionId + "_" + attributeKey;
	}
	


	@Override
	public void setToken(Token token) {
		String key = getTokenKey(token.getId());
		this.cache.put(key, token);
	}



	@Override
	public Token getToken(String tokenId) {
		String key = getTokenKey(tokenId);
		return (Token)this.cache.get(key);
	}

	

	@Override
	public boolean removeToken(String tokenId) {
		String key = getTokenKey(tokenId);
		return this.cache.remove(key) != null;
	}

	
	

	@Override
	public boolean containsSessionAttribute(String sessionId, String attributeKey) {
		if(attributeKey==null || (attributeKey=attributeKey.trim()).length()==0) {
			return false;
		}
		
		String keysKey = getSessionKeysKey(sessionId);
		Set<?> set = (Set<?>)this.cache.get(keysKey);
		if(set==null || set.size()==0) {
			return false;
		}
		
		return set.contains(attributeKey);
	}


	
	@Override
	public Object getSessionAttribute(String sessionId, String attributeKey) {
		String key = getSessionValueKey(sessionId, attributeKey);
		return this.cache.get(key);
	}


	
	@SuppressWarnings("unchecked")
	@Override
	public void setSessionAttribute(String sessionId, String attributeKey, Object attributeValue) {
		String valueKey = getSessionValueKey(sessionId, attributeKey);
		this.cache.put(valueKey, attributeValue);
		
		String keysKey = getSessionKeysKey(sessionId);
		Set<String> set = (Set<String>)this.cache.get(keysKey);
		if(set == null) {
			set = new HashSet<String>();
		}
		set.add(attributeKey.trim());
		this.cache.put(keysKey, set);
	}

	

	@SuppressWarnings("unchecked")
	@Override
	public boolean removeSessionAttribute(String sessionId, String attributeKey) {
		if(attributeKey==null || (attributeKey=attributeKey.trim()).length()==0) {
			return false;
		}
		
		String keysKey = getSessionKeysKey(sessionId);
		Set<String> set = (Set<String>)this.cache.get(keysKey);
		if(set==null || !set.contains(attributeKey)) {
			return false;
		}
		
		set.remove(attributeKey);
		this.cache.put(keysKey, set);
		
		String valueKey = getSessionValueKey(sessionId, attributeKey);
		this.cache.remove(valueKey);
		
		return true;
	}


	@SuppressWarnings("unchecked")
	@Override
	public int clearSessionAttribute(String sessionId) {
		String keysKey = getSessionKeysKey(sessionId);
		Set<String> set = (Set<String>)this.cache.get(keysKey);
		if(set==null || set.size()==0) {
			return 0;
		}
		
		this.cache.remove(keysKey);
		
		Iterator<String> itor = set.iterator();
		while(itor.hasNext()) {
			String attrKey = itor.next();
			String valueKey = getSessionValueKey(sessionId, attrKey);
			this.cache.remove(valueKey);
		}
		
		return set.size();
	}
	
	


	@SuppressWarnings("unchecked")
	@Override
	public Set<String> getSessionAttributeKeySet(String sessionId) {
		String keysKey = getSessionKeysKey(sessionId);
		Set<String> set = (Set<String>)this.cache.get(keysKey);
		if(set == null) set = new HashSet<String>();
		return set;
	}
	
	
	
	
	
	
}
