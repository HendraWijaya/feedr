package feedr.fetcher

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

import com.sun.net.httpserver.Headers
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer

class TesRssServer {
	// The classpath to the root of the context
	public static final String DEFAULT_CONTEXT_ROOT = "resources/rss"
	
	private HttpServer server
	private ExecutorService executor
	private String contextRoot
	
	private RssHttpHandler httpHandler
	
	TesRssServer(int port, String contextRoot) {
		this.httpHandler = new RssHttpHandler()
		this.server = HttpServer.create(new InetSocketAddress(port), 0)
		this.contextRoot = contextRoot
	}
	
	TesRssServer(int port) {
		this(port, DEFAULT_CONTEXT_ROOT)
	}
	
	void start() {
		server.createContext ("/", httpHandler)
		executor = Executors.newCachedThreadPool()
		server.setExecutor(executor);
		server.start();
	}
	
	void stop() {
		server.stop(0)
		executor.shutdownNow()
	}
	
	void setResponseHeaders(int statusCode, Map<String, String> responseHeaders) {
		httpHandler.setResponseHeaders(statusCode, responseHeaders)
	}
	
	private static class RssHttpHandler implements HttpHandler {
		private Map<String, String> headers
		private int statusCode
		
		/**
		 * Handle for each HTTP request connection
		 */
		void handle(HttpExchange exchange) {
			String uri = exchange.requestURI
			InputStream resourceStream = 
				this.getClass()
					.getClassLoader()
					.getResourceAsStream("${DEFAULT_CONTEXT_ROOT}${uri}")
			
			if(resourceStream) {
				byte[] resourceBytes = resourceStream.bytes
				
				sendResponseHeaders(exchange, resourceBytes.length)
				OutputStream responseBody = exchange.getResponseBody()
				responseBody.write(resourceBytes)
				responseBody.close()
			} else {
				exchange.sendResponseHeaders(404, 0)
			}
			
			// Close the request connection
			exchange.close()
		}
		
		void setResponseHeaders(int statusCode, Map<String, String> responseHeaders) {
			this.statusCode = statusCode
			this.headers = responseHeaders
		}
		
		private void sendResponseHeaders(HttpExchange exchange, int contentLength) {
			Headers responseHeaders = exchange.responseHeaders
			headers.each {  responseHeaders.set(it.key, it.value) }
			exchange.sendResponseHeaders(statusCode, contentLength)
		}
	}
}
