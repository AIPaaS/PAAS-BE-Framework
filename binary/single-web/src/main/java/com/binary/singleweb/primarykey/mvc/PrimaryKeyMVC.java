package com.binary.singleweb.primarykey.mvc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.binary.core.lang.Conver;
import com.binary.core.util.BinaryUtils;
import com.binary.framework.exception.ControllerException;
import com.binary.framework.util.ControllerUtils;
import com.binary.json.JSON;
import com.binary.singleweb.primarykey.svc.PrimaryKeySvc;


@Controller
@RequestMapping("/do/simple/PrimaryKey")
public class PrimaryKeyMVC {
	
	
	@Autowired
	PrimaryKeySvc svc;
	
	
	/**
	 * 获取Key
	 * @param tx_ps: 参数
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getKey")
	public void getKey(HttpServletRequest request, HttpServletResponse response, String tx_ps) {
		BinaryUtils.checkEmpty(tx_ps, "tx_ps");
		
		Object obj = JSON.toObject(tx_ps);
		if(!(obj instanceof List)) throw new ControllerException(" is wrong tx_ps '"+tx_ps+"'! ");
		
		List list = (List)obj;
		if(list.size() == 0) throw new ControllerException(" is wrong tx_ps '"+tx_ps+"'! ");
		
		String name = Conver.to(list.get(0), String.class);
		Integer batch = list.size()>1 ? Conver.to(list.get(1), Integer.class) : null;
		
		String key = svc.getKey(name, batch);
		
		ControllerUtils.returnJson(request, response, key);
	}
	
	
	
	

}
