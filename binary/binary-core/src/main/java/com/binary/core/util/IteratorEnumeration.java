package com.binary.core.util;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Iterator;

public class IteratorEnumeration<E> implements Enumeration<E>, Serializable {
	private static final long serialVersionUID = 2211890635856641874L;

	
	private Iterator<E> iterator;
	
	
	public IteratorEnumeration(Iterator<E> iterator) {
		BinaryUtils.checkNull(iterator, "iterator");
		this.iterator = iterator;
	}
	
	
	@Override
	public boolean hasMoreElements() {
		return this.iterator.hasNext();
	}

	@Override
	public E nextElement() {
		return iterator.next();
	}

	
	
	

}
