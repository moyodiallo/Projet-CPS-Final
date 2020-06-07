package cps.mouradalpha.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * The class <code>MyConcurrentHashMap</code> implements a hash map allowing
 * multiple readers at the same time but only one writer with the help of
 * ReentrantReadWriteLock class. It's used by the Broker in order to manage
 * concurrency between threads who execute read operations and write operations.
 * 
 * @author Mourad TOUATI / Alpha Issiaga DIALLO
 *
 * @param <K> The key type
 * @param <V> The value type
 */
public class MyConcurrentHashMap<K, V> {

	private Map<K, V> map = new HashMap<K, V>();
	private ReentrantReadWriteLock hashMapLock = new ReentrantReadWriteLock();

	/**
	 * Associate to the key a value. It's a write operation, hence only one thread
	 * can access to this map during this operation.
	 * 
	 * @param key     the key
	 * @param element The value
	 * @return the previous value associated with key, or null if there was no mapping for key. 
	 */
	public V put(K key, V element) {
		V res;
		this.hashMapLock.writeLock().lock();
		try {
			res = map.put(key, element);
		} finally {
			this.hashMapLock.writeLock().unlock();
		}
		return res;
	}

	/**
	 * Check whether the map contains a specific key. It's read operation hence it
	 * doesn't block other threads.
	 * 
	 * @param key The key
	 * @return true if the map contains the key, false otherwise.
	 */
	public boolean containsKey(K key) {
		boolean res;
		this.hashMapLock.readLock().lock();
		try {
			res = map.containsKey(key);

		} finally {
			this.hashMapLock.readLock().unlock();
		}
		return res;
	}

	/**
	 * Removes the mapping for a key from this map if it is present. It's Write
	 * operation hence it blocks other threads.
	 * 
	 * @param key the key
	 * @return the value removed or null if the map doesn't contains the key
	 */
	public V remove(K key) {
		V res;
		this.hashMapLock.writeLock().lock();
		try {
			res = map.remove(key);
		} finally {
			this.hashMapLock.writeLock().unlock();
		}
		return res;
	}

	/**
	 * Returns the value to which the specified key is mapped, or null if this map
	 * contains no mapping for the key. It's read operation hence it doesn't block
	 * other threads.
	 * 
	 * @param key
	 * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key
	 */
	public V get(K key) {
		V res;
		this.hashMapLock.readLock().lock();
		try {
			res = map.get(key);

		} finally {
			this.hashMapLock.readLock().unlock();
		}
		return res;
	}

	/**
	 * Returns a Set view of the keys contained in this map.
	 * It's read operation hence it doesn't block other threads.
	 * @return a set view of the keys contained in this map
	 */
	public Set<K> keySet() {
		Set<K> res;
		this.hashMapLock.readLock().lock();
		try {
			res = map.keySet();

		} finally {
			this.hashMapLock.readLock().unlock();
		}
		return res;
	}

	/**
	 * Returns the number of key-value mappings in this map.
	 * It's read operation hence it doesn't block other threads.
	 * @return
	 */
	public int size() {
		int res = 0;
		this.hashMapLock.readLock().lock();
		try {
			res = map.size();

		} finally {
			this.hashMapLock.readLock().unlock();
		}
		return res;
	}
}
