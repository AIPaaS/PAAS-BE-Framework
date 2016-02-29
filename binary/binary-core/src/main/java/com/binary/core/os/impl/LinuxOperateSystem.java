package com.binary.core.os.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.binary.core.os.Cpu;
import com.binary.core.os.OperateSystemType;
import com.binary.core.util.BinaryUtils;
import com.binary.core.util.Command;

public class LinuxOperateSystem extends AbstractOperateSystem {
	
	
	


	@Override
	public OperateSystemType getType() {
		return OperateSystemType.LINUX;
	}
	
	
	

	@Override
	public List<Cpu> getCpuList() {
		String cmd = "cat /proc/cpuinfo";
		String str = Command.exec(cmd);
		
		String[] arr = str.split("[\n][\n]");
		List<Cpu> list = new ArrayList<Cpu>();
		
		Map<String,String> map = new HashMap<String,String>();
		Set<String> physicals = new HashSet<String>();
		for(int i=0; i<arr.length; i++) {
			String[] items = arr[i].split("[\n]");
			
			if(map.size() > 0) map.clear();
			for(int j=0; j<items.length; j++) {
				int index = items[j].indexOf(':');
				if(index < 0) continue ;
				String key = items[j].substring(0, index).trim().toLowerCase().replaceAll("[\\s]", "");
				String value = items[j].substring(index+1).trim();
				map.put(key, value);
			}
			
			String physicalId = map.get("physicalid");
			if(physicals.contains(physicalId)) continue ;
			physicals.add(physicalId);
			
			Cpu info = new Cpu();
			String cpucores = map.get("cpucores");
			info.setCores(BinaryUtils.isEmpty(cpucores)?1:Integer.valueOf(cpucores));
			info.setFrequency((long)(Double.parseDouble(map.get("cpumhz"))*1000000));
			info.setModel(map.get("modelname"));
			list.add(info);
		}
		
		return list;
	}
	
	
	
	
	private Map<String,String> getMemoryInfo() {
		String cmd = "cat /proc/meminfo";
		String str = Command.exec(cmd);
		
		String[] arr = str.split("[\n]");
		Map<String,String> map = new HashMap<String,String>();
		for(int i=0; i<arr.length; i++) {
			int index = arr[i].indexOf(':');
			if(index < 0) continue ;
			String key = arr[i].substring(0, index).trim().toLowerCase().replaceAll("[\\s]", "");
			String value = arr[i].substring(index+1).trim();
			if(value.indexOf(' ') > 0) value = value.substring(0, value.indexOf(' '));
			map.put(key, value);
		}
		
		return map;
	}
	
	

	@Override
	public Long getMemory() {
		Map<String,String> map = getMemoryInfo();
		return Long.parseLong(map.get("memtotal"))*1024;
	}

	
	
	//arr[0]=total	arr[1]=used
	private Long[] getDiskInfo() {
		String cmd = "df -lh";
		String str = Command.exec(cmd);
		
		String[] arr = str.split("[\n]");
		long total = 0;
		long used = 0;
		for(int i=1; i<arr.length; i++) {
			if(arr[i].indexOf(' ') <= 0) {
				if(i == arr.length-2) break;
				arr[i+1] = arr[i] + " " + arr[i+1];
				continue ;
			}
			String stotal = arr[i].substring(arr[i].indexOf(' ')).trim();
			String sused = stotal.substring(stotal.indexOf(' ')).trim();
			stotal = stotal.substring(0, stotal.indexOf(' '));
			sused = sused.substring(0, sused.indexOf(' '));
			
			total += BinaryUtils.parsetByteUnit(stotal);
			used += BinaryUtils.parsetByteUnit(sused);
		}
		
		return new Long[]{total, used};
	}
	
	
	public static void main(String[] args) {
		
	}
	
	
	
	@Override
	public Long getDisk() {
		return getDiskInfo()[0];
	}

	
	

	
	@Override
	public Integer getCpuRate() {
		String cmd = "sar -u";
		String str = Command.exec(cmd);
		String avg = str.substring(str.lastIndexOf('\n') + 1).trim();
		String rate = avg.substring(avg.lastIndexOf(' ') + 1).trim();
		Integer ir = null;
		if(rate!=null && rate.matches("([0-9]|[.])+")) {
			ir = 10000-(int)(Double.parseDouble(rate)*100);
		}
		return ir;
	}

	
	
	
	@Override
	public Integer getMemoryRate() {
		Map<String,String> map = getMemoryInfo();
		long total = Long.parseLong(map.get("memtotal"));
		long free = Long.parseLong(map.get("memfree"));
		long used = total - free;
		return (int)((double)used*10000/total);
	}
	
	
	
	
	@Override
	public Integer getDiskRate() {
		Long[] arr = getDiskInfo();
		long total = arr[0];
		long used = arr[1];
		return (int)((double)used*10000/total);
	}




	@Override
	public Integer getIoRate() {
		String cmd = "iostat -x";
		String str = Command.exec(cmd).trim();
		str = str.substring(str.indexOf("%util")+5).trim();
		
		String[] arr = str.split("[\n]");
		double td = 0;
		for(int i=0; i<arr.length; i++) {
			String rate = arr[i].substring(arr[i].lastIndexOf(' ') + 1).trim();
			double d = Double.parseDouble(rate)*100;
			td += d;
		}
		return (int)(td/arr.length);
	}
	
	
	

	@Override
	public Long[] getNetRT() {
		String cmd = "cat /proc/net/dev";
		String str = Command.exec(cmd).trim();
		
		str = str.replace('|', ' ').replace(':', ' ');
		String[] rows = str.split("[\n]");
		
		String[] keys = null;
		long[] values = null;
		for(int i=1; i<rows.length; i++) {
			if(i == 1) {
				keys = splitSpace(rows[i]);
				values = new long[keys.length];
			}else {
				String[] arr = splitSpace(rows[i]);
				for(int j=1; j<arr.length; j++) {
					values[j] += Long.parseLong(arr[j]);
				}
			}
		}
		
		Map<String, Long> map = new HashMap<String, Long>();
		int bytescount = 0;
		for(int i=1; i<keys.length; i++) {
			String k = keys[i].trim().toLowerCase();
			if(k.equals("bytes")) bytescount ++ ;
			
			if(bytescount <= 1) {
				k = "r_" + k;
			}else {
				k = "t_" + k;
			}
			
			map.put(k, values[i]);
		}
		
		Long rb = map.get("r_bytes");
		Long tb = map.get("t_bytes");
		
		return new Long[]{rb, tb};
	}




	@Override
	public String getEnvironmentSeparator() {
		return ":";
	}
	
	

	
	
	

}
