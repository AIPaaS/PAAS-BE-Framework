package com.binary.framework.util;

import com.binary.core.util.BinaryUtils;
import com.binary.framework.bean.SimpleUser;
import com.binary.framework.bean.User;
import com.binary.json.JSON;

public abstract class UserUtils {
	
	
	
	/**
	 * 将user对象转换成SimpleUser对象
	 * @param user
	 * @return
	 */
	public static SimpleUser toSimpleUser(User user) {
		return toSimpleUser(user, true);
	}
	
	
	
	/**
	 * 将user对象转换成SimpleUser对象
	 * @param user
	 * @param createNew : 如果user typeof SimpleUser时, 是否创建新对象
	 * @return
	 */
	public static SimpleUser toSimpleUser(User user, boolean createNew) {
		BinaryUtils.checkEmpty(user, "user");
		
		if((user instanceof SimpleUser) && !createNew) {
			return (SimpleUser)user;
		}
		
		SimpleUser simpleUser = new SimpleUser();
		simpleUser.setId(user.getId());
		simpleUser.setUserId(user.getUserId());
		simpleUser.setUserCode(user.getUserCode());
		simpleUser.setUserName(user.getUserName());
		simpleUser.setLoginCode(user.getLoginCode());
		simpleUser.setAuthCode(user.getAuthCode());
		
		return simpleUser;
	}
	
	
	
	
	/**
	 * 将JSON字符串转换成User对象
	 * @param jsonString
	 * @return
	 */
	public static User valueOf(String jsonString) {
		BinaryUtils.checkEmpty(jsonString, "jsonString");
		return JSON.toObject(jsonString, SimpleUser.class);
	}
	
	
	
	
	/**
	 * 将User对象转换成JSON字符串 (只包含User中能get的字段)
	 * @param user
	 * @return
	 */
	public static String toJsonString(User user) {
		BinaryUtils.checkEmpty(user, "user");
		user = toSimpleUser(user, true);
		return JSON.toString(user);
	}
	
	
	
	
	
	
	

}
