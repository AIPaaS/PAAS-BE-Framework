package com.binary.core.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import com.binary.core.exception.SecurityException;

public class SecurityCollection<E> implements Collection<E>, Serializable {
	private static final long serialVersionUID = 6842341379612098798L;
	
	
	private Collection<E> collection;
	
	
	
	public SecurityCollection(Collection<E> collection) {
		this.collection = collection;
	}
	
	
	public int size() {
		return this.collection.size();
	}

	public boolean isEmpty() {
		return this.collection.isEmpty();
	}

	public boolean contains(Object o) {
		return this.collection.contains(o);
	}

	public Iterator<E> iterator() {
		return new SecurityIterator<E>(this.collection.iterator());
	}

	public Object[] toArray() {
		return this.collection.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return this.collection.toArray(a);
	}

	public boolean add(E e) {
		throw new SecurityException("No modifications are allowed to a security Collection!");
	}

	public boolean remove(Object o) {
		throw new SecurityException("No modifications are allowed to a security Collection!");
	}

	public boolean containsAll(Collection<?> c) {
		return this.collection.containsAll(c);
	}

	public boolean addAll(Collection<? extends E> c) {
		throw new SecurityException("No modifications are allowed to a security Collection!");
	}

	public boolean removeAll(Collection<?> c) {
		throw new SecurityException("No modifications are allowed to a security Collection!");
	}

	public boolean retainAll(Collection<?> c) {
		throw new SecurityException("No modifications are allowed to a security Collection!");
	}

	public void clear() {
		throw new SecurityException("No modifications are allowed to a security Collection!");
	}

}
