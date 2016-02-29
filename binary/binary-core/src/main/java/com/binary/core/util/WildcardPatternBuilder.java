package com.binary.core.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class WildcardPatternBuilder {

	
	private static Pattern starPtn = Pattern.compile("\\*");
	
	private Set<String> rules = new HashSet<String>();
	private List<String> ptns = new ArrayList<String>();
	
	
	private void add(String rule) {
		if (!BinaryUtils.isEmpty(rule) && rules.add(rule)) {
			String[] stars = starPtn.split(rule, -1);
			if (stars.length > 1) {
				StringBuilder regex = new StringBuilder();
				for (int i = 0; i < stars.length - 1; i++) {
					if ("".equals(stars[i])) {
						if ("".equals(stars[i + 1])) {
							regex.append(".*");
							i++;
						} else {
							regex.append("[^/]*");
						}
					} else {
						regex.append(Pattern.quote(stars[i]));
					}
				}
				if ("".equals(stars[stars.length - 1])) {
					regex.append("[^/]*");
				} else {
					regex.append(Pattern.quote(stars[stars.length - 1]));
				}
				ptns.add(regex.toString());
			} else {
				ptns.add(Pattern.quote(stars[0]));
			}
		}
	}
	
	
	
	
	public Pattern getPattern() {
		StringBuilder sb = new StringBuilder();
		int size = ptns.size();
		if (size > 0) {
			sb.append("(").append(ptns.get(0)).append(")");
		}
		for (int i = 1; i < size; i++) {
			sb.append("|(").append(ptns.get(i)).append(")");
		}
		return Pattern.compile(sb.toString());
	}
	
	
	
	
	public static Pattern build(String[] rules) {
		if(rules==null || rules.length==0) return null;
		
		WildcardPatternBuilder b = new WildcardPatternBuilder();
		for(int i=0; i<rules.length; i++) {
			b.add(rules[i]);
		}
		return b.getPattern();
	}
	
	
	
	
	
}




