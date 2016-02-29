package com.binary.core.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import com.binary.core.exception.SecurityException;

public class SecurityEntrySet<K,V> implements Set<Entry<K,V>> {
	
	
	private Set<Entry<K,V>> set;
	
	
	public SecurityEntrySet(Set<Entry<K,V>> set) {
		this.set = set;
	}
	
	
	public int size() {
		return this.set.size();
	}

	public boolean isEmpty() {
		return this.set.isEmpty();
	}

	public boolean contains(Object o) {
		return this.set.contains(o);
	}

	public Iterator<Entry<K, V>> iterator() {
		return new SecurityEntryIterator<K, V>(this.set.iterator());
	}

	public Object[] toArray() {
		return this.set.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return this.set.toArray(a);
	}

	public boolean add(Entry<K, V> e) {
		throw new SecurityException("No modifications are allowed to a security EntrySet!");
	}

	public boolean remove(Object o) {
		throw new SecurityException("No modifications are allowed to a security EntrySet!");
	}

	public boolean containsAll(Collection<?> c) {
		return this.set.containsAll(c);
	}

	public boolean addAll(Collection<? extends Entry<K, V>> c) {
		throw new SecurityException("No modifications are allowed to a security EntrySet!");
	}

	public boolean retainAll(Collection<?> c) {
		throw new SecurityException("No modifications are allowed to a security EntrySet!");
	}

	public boolean removeAll(Collection<?> c) {
		throw new SecurityException("No modifications are allowed to a security EntrySet!");
	}

	public void clear() {
		throw new SecurityException("No modifications are allowed to a security EntrySet!");
	}

}
