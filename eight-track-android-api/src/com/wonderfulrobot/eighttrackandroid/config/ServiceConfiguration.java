package com.wonderfulrobot.eighttrackandroid.config;

import com.wonderfulrobot.eighttrackandroid.EightTracksConstants;

public class ServiceConfiguration {
	
	public boolean isGzip() {
		return gzip;
	}

	public ServiceConfiguration setGzip(boolean gzip) {
		this.gzip = gzip;
		return this;
	}

	public int getTimeout() {
		return socketTimeout;
	}

	public ServiceConfiguration setTimeout(int timeout) {
		this.socketTimeout = timeout;
		return this;
	}

	private boolean gzip;
	private int socketTimeout;
	private int connectionTimeout;
	private String secret;
	public String getSecret() {
		return secret;
	}

	public String getApiKey() {
		return apiKey;
	}

	private String apiKey;
	
	public int getSocketTimeout() {
		return socketTimeout;
	}

	public ServiceConfiguration setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
		return this;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public ServiceConfiguration(){
		this.gzip = EightTracksConstants.GZIP;
		this.socketTimeout = EightTracksConstants.SOCKET_TIMEOUT;
		this.connectionTimeout = EightTracksConstants.CONNECTION_TIMEOUT;
	}


	public ServiceConfiguration key(String apiKey) {
		this.apiKey = apiKey;
		return this;
	}
}
