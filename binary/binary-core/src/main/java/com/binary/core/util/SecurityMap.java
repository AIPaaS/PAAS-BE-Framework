package com.binary.core.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.binary.core.exception.SecurityException;

public class SecurityMap<K,V> implements Map<K,V>, Serializable {
	private static final long serialVersionUID = 6842341379612098798L;
	
	
	private Map<K,V> map;
	
	
	public SecurityMap(Map<K,V> map) {
		this.map = map;
	}


	public int size() {
		return this.map.size();
	}


	public boolean isEmpty() {
		return this.map.isEmpty();
	}


	public boolean containsKey(Object key) {
		return this.map.containsKey(key);
	}


	public boolean containsValue(Object value) {
		return this.map.containsValue(value);
	}


	public V get(Object key) {
		return this.map.get(key);
	}


	public V put(K key, V value) {
		throw new SecurityException("No modifications are allowed to a security Map!");
	}


	public V remove(Object key) {
		throw new SecurityException("No modifications are allowed to a security Map!");
	}


	public void putAll(Map<? extends K, ? extends V> m) {
		throw new SecurityException("No modifications are allowed to a security Map!");
	}


	public void clear() {
		throw new SecurityException("No modifications are allowed to a security Map!");
	}


	public Set<K> keySet() {
		return new SecuritySet<K>(this.map.keySet());
	}


	public Collection<V> values() {
		return new SecurityCollection<V>(this.map.values());
	}


	public Set<Entry<K, V>> entrySet() {
		return new SecurityEntrySet<K,V>(this.map.entrySet());
	}
	
	
	
}


