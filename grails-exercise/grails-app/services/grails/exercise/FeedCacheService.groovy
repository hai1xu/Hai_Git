package grails.exercise

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class FeedCacheService {
	def feedOnlineService
	
	// create HashMap cache
	private Map<String, Object> cache = new HashMap<String, Object>();
	// create read write lock
	private ReadWriteLock rwl = new ReentrantReadWriteLock();
  
	def getData(String key) {
		// set readlock to lock, as this is a read function
		rwl.readLock().lock();
		// set return value as null
		Object value = null;
		try {
			// try to get value according to key
			value = cache.get(key);
			
			// if value didn't found from cache
			if (value == null) {
				// set readlock unlock, prepare to get(set/put) missing value to cache
				rwl.readLock().unlock();
				
				// check if the key is valid
				if (key == "Books") {
					// get value from network
					value = feedOnlineService.getBooksFeedOnline("https://s3.amazonaws.com/conmio-recruitment/api/books-feed.json");
					// put key and value to HashMap (cache)
					putData(key, value);
				}
				if (key == "Top3") {
					// get value from network
					value = feedOnlineService.getBooksFeedOnline("https://s3.amazonaws.com/conmio-recruitment/api/sales-feed.json");
					// put key and value to HashMap (cache)
					putData(key, value);
				}
				
				// write action done, set readlock to lock as this is a read function
				rwl.readLock().lock();
			}
		} finally {
			// release readlock (set unlock)
			rwl.readLock().unlock();
		}
		return value;
	}
	
	def putData(String key, Object value) {
		// set writelock to lock, prepare to write(put)
		rwl.writeLock().lock();
		try {
			// write(put) key and value to HashMap (cache)
			return cache.put(key, value)
		} finally {
			// release writelock
			rwl.writeLock().unlock();
		}
	}	
}




