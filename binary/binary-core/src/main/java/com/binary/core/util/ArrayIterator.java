package com.binary.core.util;

import java.io.Serializable;
import java.util.Iterator;

import com.binary.core.exception.CoreException;

public class ArrayIterator<E> implements Iterator<E>, Serializable {
	private static final long serialVersionUID = -9099634686244826444L;
	
	
	public E[] array;
	private int index = -1;
	
	
	public ArrayIterator(E[] array) {
		this.array = array;
	}
	

	public boolean hasNext() {
		return this.array!=null && this.array.length>0 && this.index<this.array.length-1;
	}

	
	public E next() {
		if(array == null) throw new CoreException(" the nested array is NULL! ");
		this.index ++ ;
		if(this.index >= this.array.length) {
			throw new CoreException(" the array index is out of bounds! ");
		}
		return this.array[this.index];
	}
	
	
	public void remove() {
	}
	
	
	
}
