package com.binary.core.bean.support;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.binary.core.bean.Property;

public class PropertyEntryIterator implements Iterator<Entry<String, Object>> {
	
	
	private Iterator<Property> iterator;
	private Map<Property,BeanEntry> entryMap;
	
	
	public PropertyEntryIterator(Iterator<Property> iterator, Map<Property,BeanEntry> entryMap) {
		this.iterator = iterator;
		this.entryMap = entryMap;
	}
	

	public boolean hasNext() {
		return this.iterator.hasNext();
	}

	
	public Entry<String, Object> next() {
		Property property = this.iterator.next();
		return this.entryMap.get(property);
	}

	
	public void remove() {
	}

	

}
