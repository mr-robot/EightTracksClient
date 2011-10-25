package com.wonderfulrobot.eighttrackandroid.data;


public class Mix extends GenericElement {
	

	private ImageSet covers;
	public ImageSet getCovers() {
		return covers;
	}
	public void setCovers(ImageSet covers) {
		this.covers = covers;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	private User user;
	
	public long getMixId() {
		return Long.parseLong(""+getAttribute(EightTracksDataConstants.MIX_ID));
	}
	
	public boolean getLike() {
		return (Boolean) getAttribute(EightTracksDataConstants.LIKE);
	}
	
	public String toString(){
		return (String) getAttribute(EightTracksDataConstants.MIX_NAME);
	}
	public void toggleLike() {
		// TODO Auto-generated method stub
		if((Boolean) getAttribute(EightTracksDataConstants.LIKE))
			setAttribute(EightTracksDataConstants.LIKE, false);
		else
			setAttribute(EightTracksDataConstants.LIKE, true);
	}
	

}
