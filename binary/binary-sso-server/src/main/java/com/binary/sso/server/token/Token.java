package com.binary.sso.server.token;

import java.io.Serializable;

import com.binary.core.util.BinaryUtils;

public class Token implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	/** tokenId **/
	private String id;
	
	
	/** 产生token时间 **/
	private long time;
	
	
	/** token所对应的用户ID **/
	private long userId;
	
	
	/** token所对应的SessionId **/
	private String sessionId;
	
	
	
	public Token(long userId, String sessionId) {
		this.id = BinaryUtils.getUUID();
		this.time = System.currentTimeMillis();
		this.userId = userId;
		this.sessionId = sessionId;
	}

	

	public String getId() {
		return id;
	}


	public long getTime() {
		return time;
	}


	public long getUserId() {
		return userId;
	}



	public String getSessionId() {
		return sessionId;
	}


	
	
	
	
	
	
	
	
	

}
