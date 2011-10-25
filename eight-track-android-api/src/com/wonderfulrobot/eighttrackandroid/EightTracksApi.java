package com.wonderfulrobot.eighttrackandroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.util.Log;

import com.wonderfulrobot.eighttrackandroid.config.ServiceConfiguration;

public class EightTracksApi {
	
	public static final String HOST = "8tracks.com";
	public static final int PORT = 80;
	public static final String REQUEST_PATH = "/";
	public static final int TRIES = 3;
	public static final String SUFFIX = ".json";
	
	private boolean gzip;
	private String key;
	private HttpClient httpClient;
	
	public EightTracksApi( ServiceConfiguration config) {
		if(config == null)
			throw new NullPointerException("Config should be not Null");
		
		this.key = config.getApiKey();
		
		this.gzip = config.isGzip();
		
		createClient(config);
	}
	
	private void createClient(ServiceConfiguration config){
		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		int timeoutConnection = config.getConnectionTimeout();
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT) 
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = config.getSocketTimeout();
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		
		//httpParameters.setParameter(CookieSpecPNames.DATE_PATTERNS, Arrays.asList("EEE, dd MMM yyyy HH:mm:ss z"));

		httpClient = new DefaultHttpClient(httpParameters);
		((AbstractHttpClient) httpClient).setHttpRequestRetryHandler(new
            		DefaultHttpRequestRetryHandler(1,false));
	}
	
	public void shutdown(){
        httpClient.getConnectionManager().shutdown();
	}
	

	public JSONObject sendRequest(String endpoint,  Map<String, Object> fields) throws IOException, JSONException  {
		return sendRequest(EightTracksConstants.GET,endpoint, new Object[]{}, fields, false);
	}
	
	public JSONObject sendRequest(String endpoint, Object[] idValues, Map<String, Object> fields) throws IOException, JSONException  {
		return sendRequest(EightTracksConstants.GET,endpoint, idValues,fields, false);
	}
	
	public JSONObject sendRequest(String method,String endpoint, Object[] idValues, Map<String, Object> fields) throws IOException, JSONException  {
		return sendRequest(method,endpoint, idValues,fields, false);
	}

	/**
	 * Performs 8Tracks API call. 
	 *
	 * For a list of valid method names, see:
	 * http://www.scribd.com/developers/api?method_name=docs.search
	 * @param method The API method name.
	 * @param idValues An Array of Id's (e.g. Mix Id) to substitute into a URL string
	 * @param fields A map of API parameters.
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws Exception 
	 */
	public JSONObject sendRequest(String method,String endpoint, Object[] idValues, Map<String, Object> fields, boolean isSecure) throws IOException, JSONException  {
		if (key == null) {
			throw new NullPointerException();
		}

		if (method == null) {
			throw new NullPointerException("Method should be given");
		}
		
		Uri uri = buildEndPoint(endpoint, idValues, fields, isSecure);
		
		Log.i("Calling", "" + uri.toString());

		InputStream instream = null;
		try {
			
			HttpResponse response = null;
			
			response = executeRequest(method,uri, httpClient);
			
			
			if (response != null) {
				if(response.getEntity()!= null){
					instream = response.getEntity().getContent();
					
					Header contentEncoding = response.getFirstHeader("Content-Encoding");
					if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
					    instream = new GZIPInputStream(instream);
					}
	
	
					JSONObject json = read(instream);
					
					errorCheck(json, endpoint);
	
					return json;
				}
			}
			
			
		}  catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}  finally {
			// Closing the input stream will trigger connection release
	        if (instream != null) {
	        	try {
	        		instream.close();
				} catch (IOException e) {
					//We shouldn't handle this exception as we won't be changing state after this point
				}
	        }
		    // When HttpClient instance is no longer needed, 
		    // shut down the connection manager to ensure
		    // immediate deallocation of all system resources
	    }
		
		return null;
	}
	
	private Uri buildEndPoint(String endpoint, Object[] idValues, Map<String, Object> fields, boolean isSecure){
		//Build the Base Path
		StringBuilder requestPathBuilder = new StringBuilder();
		requestPathBuilder.append(REQUEST_PATH);
		
		//Add the Variable Substiution
		if(idValues.length > 0)
			requestPathBuilder.append(String.format(endpoint, idValues));
		else
			requestPathBuilder.append(endpoint);
		requestPathBuilder.append(SUFFIX);
		
		//Add the API Key
		fields.put("api_key", key);

		removeNulls(fields);
		
		Uri uri = buildUri(requestPathBuilder.toString(), fields, isSecure);
		
		return uri;
	}
	
	private Uri buildUri(String requestPath, Map<String, Object> fields, boolean isSecure){
		Uri.Builder requestUriBuilder = new Uri.Builder()
	    .authority(HOST)
	    .path(requestPath);
		
		if(isSecure)
			requestUriBuilder.scheme("https");
		else
			requestUriBuilder.scheme("http");
		//Add Query Parameters
		for (String key : fields.keySet()) {
			requestUriBuilder.appendQueryParameter(key, fields.get(key).toString());
		}
		Log.i("Query", requestUriBuilder.build().toString());
		return requestUriBuilder.build();
	}
	

	private void removeNulls(Map<String, Object> fields) {
		Iterator<Object> iter = fields.values().iterator();
		while (iter.hasNext()) {
			if (iter.next() == null) {
				iter.remove();
			}
		}
	}
	
	
	private HttpResponse executeRequest(String method, Uri uri,  HttpClient client) throws IOException {
	    
		HttpGet get = null;
		HttpPost post = null;
		
		try {
			HttpResponse response = null; 

			if(method.equals(EightTracksConstants.GET)){
				get = new HttpGet(uri.toString());
				

				if(gzip)
					get.addHeader("Accept-Encoding", "gzip");
				
				response = client.execute(get);
			}
			else{
				post = new HttpPost(uri.toString());
				
				if(gzip)
					post.addHeader("Accept-Encoding", "gzip");
				
				response = client.execute(post);
			}
			
			return response;
		} catch (ClientProtocolException e) {
	        // In case of an unexpected exception you may want to abort
	        // the HTTP request in order to shut down the underlying
	        // connection and release it back to the connection manager.
			if(get != null)
				get.abort();
			if(post != null)
				post.abort();
	        //throw new RuntimeException(e);
		}
		return null;
	}
	

	
	private JSONObject read(InputStream instream) throws IOException, ParserConfigurationException, JSONException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		
		JSONObject json = new JSONObject(sb.toString());

		return json;
	}

	
	
	private void errorCheck(JSONObject doc, String method) {
	}

}
