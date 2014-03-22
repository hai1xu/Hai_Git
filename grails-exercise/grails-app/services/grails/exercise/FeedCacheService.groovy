package grails.exercise

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpRequest;
import org.apache.http.protocol.HttpContext;
import org.apache.http.HttpException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.HttpResponse;
import org.apache.http.HttpEntity;

import javax.net.ssl.X509TrustManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import org.apache.http.util.EntityUtils;

class FeedCacheService {
	private Map<String, Object> cache = new HashMap<String, Object>();
	private ReadWriteLock rwl = new ReentrantReadWriteLock();
  
	def getData(String key) {
		rwl.readLock().lock();
		Object value = null;
		try {
			value = cache.get(key);
			if (value == null) {
				rwl.readLock().unlock();
				
				if (value == null) {
					if (key == "Books") {
						value = getBooksFeedOnline("https://s3.amazonaws.com/conmio-recruitment/api/books-feed.json");
						putData(key, value);
					}
					if (key == "Top3") {
						value = getBooksFeedOnline("https://s3.amazonaws.com/conmio-recruitment/api/sales-feed.json");
						putData(key, value);
					}
				}
				
				rwl.readLock().lock();
			}
		} finally {
			rwl.readLock().unlock();
		}
		return value;
	}
	
	def putData(String key, Object value) {
		rwl.writeLock().lock();
		try {
			return cache.put(key, value)
		} finally {
			rwl.writeLock().unlock();
		}
	}
	
	def getBooksFeedOnline(String url) {
		DefaultHttpClient httpclient0 = new DefaultHttpClient();
		DefaultHttpClient httpclient=useTrustingTrustManager(httpclient0);
		try {
			httpclient.addRequestInterceptor(new HttpRequestInterceptor() {
				public void process(
						final HttpRequest request,
						final HttpContext context) throws HttpException, IOException {
					if (!request.containsHeader("Accept-Encoding")) {
						request.addHeader("Accept", "application/json");
					}
				}
 
			});

			HttpGet httpget = new HttpGet(url);
			 
			// Execute HTTP request
			System.out.println("executing request " + httpget.getURI());
			HttpResponse response = httpclient.execute(httpget);
			System.out.println(response.getStatusLine());
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String content = EntityUtils.toString(entity);
				return content;
			}
			return null;
			
		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}
	}
	
	def DefaultHttpClient useTrustingTrustManager(DefaultHttpClient httpClient) {
		
		 
	   try {
			// First create a trust manager that won't care.
			def trustManager = new MyX509TrustManager ()

			// Now put the trust manager into an SSLContext.
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, [trustManager] as TrustManager[], null);
			
			// Use the above SSLContext to create your socket factory
			// (I found trying to extend the factory a bit difficult due to a
			// call to createSocket with no arguments, a method which doesn't
			// exist anywhere I can find, but hey-ho).

			SSLSocketFactory sf = new SSLSocketFactory(sslcontext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			
			// If you want a thread safe client, use the ThreadSafeConManager, but
			// otherwise just grab the one from the current client, and get hold of its
			// schema registry. THIS IS THE KEY THING.
			ClientConnectionManager ccm = httpClient.getConnectionManager();
			SchemeRegistry schemeRegistry = ccm.getSchemeRegistry();
			
			// Register our new socket factory with the typical SSL port and the
			// correct protocol name.
			schemeRegistry.register(new Scheme("https", sf, 443));
			
			// Finally, apply the ClientConnectionManager to the Http Client
			// or, as in this example, create a new one.
			return new DefaultHttpClient(ccm, httpClient.getParams());
		}
		catch(Throwable t) {
			// AND NEVER EVER EVER DO THIS, IT IS LAZY AND ALMOST ALWAYS WRONG!
			t.printStackTrace();
			return null;
		}
	}
}

class MyX509TrustManager implements X509TrustManager	{
	public void checkClientTrusted(X509Certificate[] chain, String authType)
	throws CertificateException {
		// Don't do anything.
		}

	public void checkServerTrusted(X509Certificate[] chain, String authType)
	throws CertificateException {
		// Don't do anything.
		}

	public X509Certificate[] getAcceptedIssuers() {
		// Don't do anything.
		return null;
		}
	}


