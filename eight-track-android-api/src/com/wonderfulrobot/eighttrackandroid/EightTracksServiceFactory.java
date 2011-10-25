package com.wonderfulrobot.eighttrackandroid;

import com.wonderfulrobot.eighttrackandroid.config.ServiceConfiguration;

public class EightTracksServiceFactory {
	
	private ServiceConfiguration defaultConfig;
	
	public EightTracksServiceFactory(String apiKey){
		this.defaultConfig = buildDefaultConfig(apiKey);
	}
	
	public EightTracksService buildService(String apiKey){
		return new EightTracksService(buildDefaultConfig(apiKey));
	}


	public EightTracksService buildService() throws EightTracksException{

		if(defaultConfig != null)
			return new EightTracksService(this.defaultConfig);
		else
			throw new EightTracksException("Null ServiceConfiguration");
	}


	public EightTracksService buildService(ServiceConfiguration config) throws EightTracksException{

		if(config != null)
			return new EightTracksService(config);
		else
			throw new EightTracksException("Null ServiceConfiguration");
	}
	
	private ServiceConfiguration buildDefaultConfig(String apiKey) {
		return new ServiceConfiguration()
		.setGzip(true)
		.setSocketTimeout(3000)
		.setTimeout(5000)
		.key(apiKey);
	}

}
