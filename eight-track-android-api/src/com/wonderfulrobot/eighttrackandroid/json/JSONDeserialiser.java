package com.wonderfulrobot.eighttrackandroid.json;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wonderfulrobot.eighttrackandroid.data.EightTracksDataConstants;
import com.wonderfulrobot.eighttrackandroid.data.ImageSet;
import com.wonderfulrobot.eighttrackandroid.data.Mix;
import com.wonderfulrobot.eighttrackandroid.data.Response;
import com.wonderfulrobot.eighttrackandroid.data.Review;
import com.wonderfulrobot.eighttrackandroid.data.Set;
import com.wonderfulrobot.eighttrackandroid.data.Track;
import com.wonderfulrobot.eighttrackandroid.data.User;

public class JSONDeserialiser {
	
	/**
	 * 
	 * Returning Typed Responses (Preferred to enforce some clean structure)
	 *  Could be Lazy and just use Recursion 
	 * 
	 * 
	 * @param object
	 * @return
	 * @throws JSONException
	 */
	public static Response deserialiseResponse(JSONObject object) throws JSONException{
		Response r = new Response();
	    Iterator<String> keys = object.keys();
	    String currentKey = null;
	    while ( keys.hasNext() ){
	      currentKey =  keys.next();
	      if(currentKey.equals(EightTracksDataConstants.MIXES)){
	    	  ArrayList<Mix> mixList = new ArrayList<Mix>();
	    	  JSONArray mixes = object.getJSONArray(currentKey);
	    	  for(int i =0; i < mixes.length(); i++){
	    		  mixList.add(deserialiseMix(mixes.getJSONObject(i)));
	    	  }
	    	  r.setMixes(mixList);
	      }
	      
	      else if(currentKey.equals(EightTracksDataConstants.MIX)){
	    	  ArrayList<Mix> mixList = new ArrayList<Mix>();
	    	  mixList.add(deserialiseMix(object.getJSONObject(currentKey)));
	    	  r.setMixes(mixList);
	      }
	      else if(currentKey.equals(EightTracksDataConstants.REVIEWS)){

	    	  ArrayList<Review> reviewList = new ArrayList<Review>();
	    	  JSONArray review = object.getJSONArray(currentKey);
	    	  for(int i =0; i < review.length(); i++){
	    		  reviewList.add(deserialiseReview(review.getJSONObject(i)));
	    	  }
	    	  
	    	  r.setReviews(reviewList);
	      }
	      else if(currentKey.equals(EightTracksDataConstants.USER)||currentKey.equals(EightTracksDataConstants.CURRENT_USER)){
	    	  User user = deserialiseUser(object.getJSONObject(currentKey));
	    	  r.setUser(user);
	      }
	      else if(currentKey.equals(EightTracksDataConstants.TRACKS)){
	    	  ArrayList<Track> trackList = new ArrayList<Track>();
	    	  JSONArray tracks = null;
	    	  try{
	    	    	 tracks  = object.getJSONArray(currentKey);
	    	    	  
	    		  
	    	  }catch (JSONException e){
	    		  
	    	  }
	    	  if(tracks != null){
	    		  for(int i =0; i < tracks.length(); i++){
    	    		  trackList.add(deserialiseTrack(tracks.getJSONObject(i)));
    	    	  } 
	    	  }
	    	  
	    	  r.setTracks(trackList);
	      }
	      else if(currentKey.equals(EightTracksDataConstants.SET)){

	    	  Set s = deserialiseSet(object.getJSONObject(currentKey));
	    	  r.setSet(s);
	      }
	      else{
	    	  r.setAttribute(currentKey, object.get(currentKey));
	      }
	      
	    }

		
		return r;
	}
	
	public static Mix deserialiseMix(JSONObject object) throws JSONException{
		Mix m = new Mix();
	
	    Iterator<String> keys = object.keys();
	    String currentKey = null;
	    while ( keys.hasNext() ){
		  currentKey =  keys.next();
	      if(currentKey.equals(EightTracksDataConstants.USER)){
	    	  User user = deserialiseUser(object.getJSONObject(currentKey));
	    	  m.setUser(user);
	      }
	      else if(currentKey.equals(EightTracksDataConstants.COVER_URLS)){
	    	  ImageSet covers = deserialiseImageSet(object.getJSONObject(currentKey));
	    	  m.setCovers(covers);
	      }
	      else{
	    	  m.setAttribute(currentKey, object.get(currentKey));
	      }
	    }
		return m;
	}
	
	public static User deserialiseUser(JSONObject object) throws JSONException{
		User u = new User();
		
	    Iterator<String> keys = object.keys();
	    String currentKey = null;
	    while ( keys.hasNext() ){
		  currentKey =  keys.next();
	      if(currentKey.equals(EightTracksDataConstants.AVATAR_URLS)){
	    	  ImageSet avatars = deserialiseImageSet(object.getJSONObject(currentKey));
	    	  u.setAvatars(avatars);
	      }
	      else if(currentKey.equals(EightTracksDataConstants.MIXES)){
	    	  ArrayList<Mix> mixList = new ArrayList<Mix>();
	    	  JSONArray mixes = object.getJSONArray(currentKey);
	    	  for(int i =0; i < mixes.length(); i++){
	    		  mixList.add(deserialiseMix(mixes.getJSONObject(i)));
	    	  }
	    	  u.setMixes(mixList);
	      }
	      else{
	    	  u.setAttribute(currentKey, object.get(currentKey));
	      }
	    }
		return u;
	}
	
	public static ImageSet deserialiseImageSet(JSONObject object) throws JSONException{
		ImageSet is = new ImageSet();
		
	    Iterator<String> keys = object.keys();
	    String currentKey = null;
	    while ( keys.hasNext() ){
		  currentKey =  keys.next();

	    	is.setAttribute(currentKey, object.get(currentKey));
	      
	    }
		return is;
	}
	
	public static Set deserialiseSet(JSONObject object) throws JSONException{
		Set s = new Set();
		
	    Iterator<String> keys = object.keys();
	    String currentKey = null;
	    while ( keys.hasNext() ){

			  currentKey =  keys.next();
	      if(currentKey.equals(EightTracksDataConstants.TRACK)){
	    	  Track t = deserialiseTrack(object.getJSONObject(currentKey));
	    	  s.setTrack(t);
	      }
	    	
	    }
		return s;
	}
	
	public static Track deserialiseTrack(JSONObject object) throws JSONException{
		Track t = new Track();
		
	    Iterator<String> keys = object.keys();
	    String currentKey = null;
	    while ( keys.hasNext() ){
		  currentKey =  keys.next();

	    	t.setAttribute(currentKey, object.get(currentKey));
	      
	    }
		return t;
	}
	
	public static Review deserialiseReview(JSONObject object) throws JSONException{
		Review r = new Review();
	
	    Iterator<String> keys = object.keys();
	    String currentKey = null;
	    while ( keys.hasNext() ){

			  currentKey =  keys.next();
	      if(currentKey.equals(EightTracksDataConstants.MIX)){
	    	  Mix m = deserialiseMix(object.getJSONObject(currentKey));
	    	  r.setMix(m);
	      }
	    	
	    }
		return r;
	}

}
