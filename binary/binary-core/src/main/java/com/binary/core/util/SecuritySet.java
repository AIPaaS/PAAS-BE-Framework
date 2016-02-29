package com.binary.core.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import com.binary.core.exception.SecurityException;


/**
 * 安全的Iterator(屏蔽对容器产生变化的方法)
 */
public class SecuritySet<E> implements Set<E>, Serializable {
	private static final long serialVersionUID = -5562009602700960315L;
	
	
	private Set<E> set;
	
	
	public SecuritySet(Set<E> set) {
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
	
	
	
	public Iterator<E> iterator() {
		return new SecurityIterator<E>(this.set.iterator());
	}

	
	public Object[] toArray() {
		return this.set.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return this.set.toArray(a);
	}

	public boolean add(E e) {
		throw new SecurityException("No modifications are allowed to a security Set!");
	}

	public boolean remove(Object o) {
		throw new SecurityException("No modifications are allowed to a security Set!");
	}

	public boolean containsAll(Collection<?> c) {
		return this.set.containsAll(c);
	}

	public boolean addAll(Collection<? extends E> c) {
		throw new SecurityException("No modifications are allowed to a security Set!");
	}

	public boolean retainAll(Collection<?> c) {
		throw new SecurityException("No modifications are allowed to a security Set!");
	}

	public boolean removeAll(Collection<?> c) {
		throw new SecurityException("No modifications are allowed to a security Set!");
	}

	public void clear() {
		throw new SecurityException("No modifications are allowed to a security Set!");
	}
	
	
	
}
