package cps.mouradalpha.util;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * The class <code>MyConcurrentLinkedQueue</code> implements a Queue (a
 * LinkedList internally) allowing multiple readers at the same time but only
 * one writer with the help of ReentrantReadWriteLock class. It's used by the
 * Broker in order to manage concurrency between threads who execute read
 * operations and write operations.
 * 
 * @author Mourad TOUATI / Alpha Issiaga DIALLO
 * 
 * @param <T> the value type
 */
public class MyConcurrentLinkedQueue<T> {

	private Queue<T> q = new LinkedList<>();
	private ReentrantReadWriteLock linkedQueueLock = new ReentrantReadWriteLock();

	/**
	 * Returns true if the Queue contains the specified element. It's read operation
	 * hence it doesn't block other threads.
	 * 
	 * @param ele the element
	 * @return true if this collection contains the specified element

	 */
	public boolean contains(T ele) {

		boolean res;
		this.linkedQueueLock.readLock().lock();
		try {
			res = q.contains(ele);
		} finally {
			this.linkedQueueLock.readLock().unlock();
		}
		return res;
	}

	/**
	 * Inserts the specified element into the queue. It's a write operation, hence
	 * only one thread can access to this map during this operation.
	 * 
	 * @param ele the element
	 * @return true if the element was added
	 */
	public boolean add(T ele) {
		boolean res;
		this.linkedQueueLock.writeLock().lock();
		try {
			res = q.add(ele);
		} finally {
			this.linkedQueueLock.writeLock().unlock();
		}
		return res;
	}

	/**
	 * Removes a single instance of the specified element from the Queue. It's a
	 * write operation, hence only one thread can access to this map during this
	 * operation.
	 * 
	 * @param ele the element
	 * @return true if the element was removed
	 */
	public boolean remove(T ele) {
		boolean res;
		this.linkedQueueLock.writeLock().lock();
		try {
			res = q.remove(ele);
		} finally {
			this.linkedQueueLock.writeLock().unlock();
		}
		return res;
	}

	/**
	 * Retrieves and removes the head of this queue, or returns null if this queue
	 * is empty.It's a write operation, hence only one thread can access to this map
	 * during this operation.
	 * 
	 * @return the head of this queue, or null if this queue is empty
	 */
	public T poll() {
		T res;
		this.linkedQueueLock.writeLock().lock();
		try {
			res = q.poll();
		} finally {
			this.linkedQueueLock.writeLock().unlock();
		}
		return res;

	}

	/**
	 * Returns the number of key-value mappings in this map. It's read operation
	 * hence it doesn't block other threads.
	 * 
	 * @return
	 */
	public int size() {
		int res;
		this.linkedQueueLock.readLock().lock();
		try {
			res = q.size();

		} finally {
			this.linkedQueueLock.readLock().unlock();
		}

		return res;

	}

	/**
	 * Returns the element at the specified position in this list. It's read
	 * operation hence it doesn't block other threads.
	 * 
	 * @param i the position
	 * @return
	 */
	public T get(int i) {
		LinkedList<T> temp = (LinkedList<T>) q;
		T res;
		this.linkedQueueLock.readLock().lock();
		try {
			res = temp.get(i);
		} finally {
			this.linkedQueueLock.readLock().unlock();
		}
		return res;
	}

}
