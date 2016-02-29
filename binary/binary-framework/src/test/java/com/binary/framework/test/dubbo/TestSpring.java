package com.binary.framework.test.dubbo;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.binary.framework.test.TestTemplate;
import com.binary.framework.test.dubbo.bean.CSysCodeDef;
import com.binary.framework.test.dubbo.bean.SysCodeDef;
import com.binary.jdbc.Page;
import com.binary.json.JSON;

public class TestSpring extends TestTemplate {
	
	SysCodeSvc svc;
	
	
	
	@Before
	public void init() {
		svc = getBean(SysCodeSvc.class);
	}
	
	
	
	@Test
	public void queryDefById() {
		SysCodeDef def = svc.queryDefById(1l);
		System.out.println(JSON.toString(def));
	}

	
	@Test
	public void queryDefPage() {
		CSysCodeDef cdt = new CSysCodeDef();
		cdt.setStartId(1l);
		cdt.setStatus(1);
		Page<SysCodeDef> page = svc.queryDefPage(1, 20, cdt, " ID ");
		
		printPage(page);
	}
	
	
	
	@Test
	public void queryDefList() {
		CSysCodeDef cdt = new CSysCodeDef();
		cdt.setEndId(10l);
		cdt.setStatus(1);
		List<SysCodeDef> ls = svc.queryDefList(cdt, " ID ");
		
		printList(ls);
	}
	
	
	
}
