package com.wonderfulrobot.eighttrackandroid.data;

import java.util.List;

public class User extends GenericElement {
	
	private ImageSet avatars;
	
	private List<Mix> mixes;

	public ImageSet getAvatars() {
		return avatars;
	}

	public void setAvatars(ImageSet avatars) {
		this.avatars = avatars;
	}
	
	public List<Mix> getMixes() {
		return mixes;
	}
	public void setMixes(List<Mix> mixes) {
		this.mixes = mixes;
	}

	public Long getId() {
		return Long.parseLong(""+getAttribute(EightTracksDataConstants.USER_ID));
	}
	
	public String getSlug() {
		return ""+getAttribute(EightTracksDataConstants.USER_SLUG);
	}
	public boolean getFollow() {
		return (Boolean) getAttribute(EightTracksDataConstants.FOLLOW_BY);
	}
	
	public void toggleFollow() {
		if((Boolean) getAttribute(EightTracksDataConstants.FOLLOW_BY))
			setAttribute(EightTracksDataConstants.FOLLOW_BY, false);
		else
			setAttribute(EightTracksDataConstants.FOLLOW_BY, true);
	}


}
