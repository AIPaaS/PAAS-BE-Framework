package com.binary.core.util;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map.Entry;

import com.binary.core.exception.SecurityException;

public class SecurityEntryIterator<K,V> implements Iterator<Entry<K,V>>, Serializable {
	private static final long serialVersionUID = -4048598551793493117L;
	

	public static class SecurityEntry<K, V> implements Entry<K, V> {
		
		private Entry<K, V> entry;
		
		
		public SecurityEntry(Entry<K, V> entry) {
			this.entry = entry;
		}
		
		
		public K getKey() {
			return entry.getKey();
		}

		public V getValue() {
			return entry.getValue();
		}

		public V setValue(V value) {
			throw new SecurityException("No modifications are allowed to a security EntryIterator!");
		}
	}
	
	
	private Iterator<Entry<K, V>> iterator;
	
	
	public SecurityEntryIterator(Iterator<Entry<K, V>> iterator) {
		this.iterator = iterator;
	}
	


	public boolean hasNext() {
		return iterator.hasNext();
	}


	public Entry<K, V> next() {
		return new SecurityEntry<K, V>(iterator.next());
	}
	

	public void remove() {
		throw new SecurityException("No modifications are allowed to a security EntryIterator!");
	}

	

	
	
}
