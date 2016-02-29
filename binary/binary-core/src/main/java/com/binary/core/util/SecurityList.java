package com.binary.core.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.binary.core.exception.SecurityException;

public class SecurityList<E> implements List<E>, Serializable {
	private static final long serialVersionUID = 6842341379612098798L;
	
	
	private List<E> list;
	
	
	
	public SecurityList(List<E> list) {
		this.list = list;
	}



	public int size() {
		return this.list.size();
	}



	public boolean isEmpty() {
		return this.list.isEmpty();
	}



	public boolean contains(Object o) {
		return this.list.contains(o);
	}



	public Iterator<E> iterator() {
		return new SecurityIterator<E>(this.list.iterator());
	}


	public Object[] toArray() {
		return this.list.toArray();
	}



	public <T> T[] toArray(T[] a) {
		return this.list.toArray(a);
	}



	public boolean add(E e) {
		throw new SecurityException("No modifications are allowed to a security List!");
	}

	public boolean remove(Object o) {
		throw new SecurityException("No modifications are allowed to a security List!");
	}

	public boolean containsAll(Collection<?> c) {
		return this.list.containsAll(c);
	}

	public boolean addAll(Collection<? extends E> c) {
		throw new SecurityException("No modifications are allowed to a security List!");
	}



	public boolean addAll(int index, Collection<? extends E> c) {
		throw new SecurityException("No modifications are allowed to a security List!");
	}

	public boolean removeAll(Collection<?> c) {
		throw new SecurityException("No modifications are allowed to a security List!");
	}

	public boolean retainAll(Collection<?> c) {
		throw new SecurityException("No modifications are allowed to a security List!");
	}

	public void clear() {
		throw new SecurityException("No modifications are allowed to a security List!");
	}
	
	public E get(int index) {
		return this.list.get(index);
	}

	public E set(int index, E element) {
		throw new SecurityException("No modifications are allowed to a security List!");
	}

	public void add(int index, E element) {
		throw new SecurityException("No modifications are allowed to a security List!");
	}

	public E remove(int index) {
		throw new SecurityException("No modifications are allowed to a security List!");
	}

	public int indexOf(Object o) {
		return this.list.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return this.list.lastIndexOf(o);
	}



	public ListIterator<E> listIterator() {
		return new SecurityListIterator<E>(this.list.listIterator());
	}



	public ListIterator<E> listIterator(int index) {
		return new SecurityListIterator<E>(this.list.listIterator(index));
	}



	public List<E> subList(int fromIndex, int toIndex) {
		return new SecurityList<E>(this.list.subList(fromIndex, toIndex));
	}
	
	
	

}
