package com.binary.dubbo.monitor;

import java.util.List;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.monitor.MonitorService;



public class BinaryMonitorService implements MonitorService {

	public void collect(URL statistics) {
	}

	public List<URL> lookup(URL query) {
		return null;
	}
	
}