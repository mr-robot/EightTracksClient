package com.wonderfulrobot.eighttrackandroid.data;

public class Track extends GenericElement {
	
	public String getTitle(){
		if(getAttribute(EightTracksDataConstants.TRACK_NAME) != null)
			return (String) getAttribute(EightTracksDataConstants.TRACK_NAME);
		else
			return "";
	}
	
	public String getURL(){
		if(getAttribute(EightTracksDataConstants.TRACK_URL) != null)
			return (String) getAttribute(EightTracksDataConstants.TRACK_URL);
		else
			return "";
	}
	
	public long getId(){
		if(getAttribute(EightTracksDataConstants.TRACK_ID) != null)
			return (Long) Long.parseLong("" + getAttribute(EightTracksDataConstants.TRACK_ID));
		else
			return -1L;
	}

}
