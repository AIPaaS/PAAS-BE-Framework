package com.binary.task.support;

import java.util.Iterator;

import com.binary.task.TaskJob;

public class TaskJobIterator implements Iterator<TaskJob> {

	
	private Iterator<TaskJobEntity> entityIterator;
	
	
	public TaskJobIterator(Iterator<TaskJobEntity> entityIterator) {
		this.entityIterator = entityIterator;
	}
	
	
	
	@Override
	public boolean hasNext() {
		return this.entityIterator.hasNext();
	}

	
	
	@Override
	public TaskJob next() {
		return this.entityIterator.next().getJob();
	}

	
	
	@Override
	public void remove() {
	}

	
	
	
	
}
