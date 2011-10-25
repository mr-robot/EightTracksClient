package com.wonderfulrobot.eighttrackandroid.data;

import java.util.ArrayList;
import java.util.List;

public class Response  extends GenericElement {
	
	private List<Mix> mixes;
	private List<Review> reviews;
	private Set set;
	private User user;
	private List<Track> trackList;
	
	public List<Mix> getMixes() {
		return mixes;
	}
	public void setMixes(List<Mix> mixes) {
		this.mixes = mixes;
	}
	public Set getSet() {
		return set;
	}
	public void setSet(Set set) {
		this.set = set;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public List<Review> getReviews() {
		return reviews;
	}
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public void setTracks(ArrayList<Track> trackList) {
		this.trackList = trackList;
	}
	public List<Track> getTracks() {
		return trackList;
	} 


}
