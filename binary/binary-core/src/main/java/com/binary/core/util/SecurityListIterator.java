package com.binary.core.util;

import java.io.Serializable;
import java.util.ListIterator;

import com.binary.core.exception.SecurityException;


/**
 * 安全的ListIterator(屏蔽remove方法)
 */
public class SecurityListIterator<E> implements ListIterator<E>, Serializable {
	private static final long serialVersionUID = 8463047426923124678L;
	
	
	private ListIterator<E> iterator;
	
	
	
	public SecurityListIterator(ListIterator<E> iterator) {
		this.iterator = iterator;
	}


	public boolean hasNext() {
		return this.iterator.hasNext();
	}


	public E next() {
		return this.iterator.next();
	}



	public boolean hasPrevious() {
		return this.iterator.hasPrevious();
	}



	public E previous() {
		return this.iterator.previous();
	}



	public int nextIndex() {
		return this.iterator.nextIndex();
	}



	public int previousIndex() {
		return this.iterator.previousIndex();
	}



	public void remove() {
		throw new SecurityException("No modifications are allowed to a security ListIterator!");
	}



	public void set(E e) {
		throw new SecurityException("No modifications are allowed to a security ListIterator!");
	}



	public void add(E e) {
		throw new SecurityException("No modifications are allowed to a security ListIterator!");
	}
	
	
	
	
}
