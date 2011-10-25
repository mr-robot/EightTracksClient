package com.wonderfulrobot.eighttrackandroid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.wonderfulrobot.eighttrackandroid.config.FilterBuilder;
import com.wonderfulrobot.eighttrackandroid.config.Paging;
import com.wonderfulrobot.eighttrackandroid.config.ServiceConfiguration;
import com.wonderfulrobot.eighttrackandroid.data.EightTracksDataConstants;
import com.wonderfulrobot.eighttrackandroid.data.Mix;
import com.wonderfulrobot.eighttrackandroid.data.Response;
import com.wonderfulrobot.eighttrackandroid.data.Review;
import com.wonderfulrobot.eighttrackandroid.data.Set;
import com.wonderfulrobot.eighttrackandroid.data.Track;
import com.wonderfulrobot.eighttrackandroid.data.User;
import com.wonderfulrobot.eighttrackandroid.json.JSONDeserialiser;

public class EightTracksService {
		
	private final EightTracksApi api;
	
	private String userToken;
	
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public EightTracksService(ServiceConfiguration config) {
		api = new EightTracksApi(config);
	}
	
	public EightTracksService(EightTracksApi api) {
		this.api = api;
	}
	
	public EightTracksApi getApi() {
		return api;
	}
	
	public Map<String, Object> getPasswordFields(String login, String password){
		HashMap<String, Object> fields = new HashMap<String, Object>();
		if(login!= null);
			fields.put(EightTracksDataConstants.USER_LOGIN, login);
		if(password!= null);
			fields.put(EightTracksDataConstants.USER_PASSWORD, password);
		return fields;
	}
	
	public Map<String, Object> getDefaultFields(){
		HashMap<String, Object> fields = new HashMap<String, Object>();
		if(userToken!= null);
			fields.put(EightTracksDataConstants.USER_TOKEN, userToken);
		return fields;
	}
	
	public Map<String, Object> getFields(Paging p){
		HashMap<String, Object> fields = new HashMap<String, Object>();
		if(userToken!= null);
			fields.put(EightTracksDataConstants.USER_TOKEN, userToken);
		fields.put(EightTracksDataConstants.PAGE, p.getPage());
		fields.put(EightTracksDataConstants.COUNT, p.getCount());
			
		return fields;
	}
	
	public Map<String, Object> getFields(FilterBuilder fb, Paging p){
		HashMap<String, Object> fields = new HashMap<String, Object>();
		if(userToken!= null);
			fields.put(EightTracksDataConstants.USER_TOKEN, userToken);
		fields.put(EightTracksDataConstants.PAGE, p.getPage());
		fields.put(EightTracksDataConstants.COUNT, p.getCount());
		if(fb.getSort()!= null)
			fields.put(EightTracksDataConstants.SORT, fb.getSort());
		if(fb.getQuery()!= null)
			fields.put(EightTracksDataConstants.QUERY, fb.getQuery());
		if(fb.getTags() != null)
			fields.put(EightTracksDataConstants.MIX_TAGS, fb.getTags());
		
		return fields;
	}
	
	
	public Object[] verifyCredentials(String login, String password) throws EightTracksException{
		try {
			JSONObject json = api.sendRequest(EightTracksConstants.POST, EightTracksConstants.SESSION_LOGIN, new Object[]{}, getPasswordFields(login, password), true);
			Response r = JSONDeserialiser.deserialiseResponse(json);
			
			if(r.getAttribute(EightTracksDataConstants.USER_TOKEN)!= null){
				return new Object[]{r.getUser(),(String) r.getAttribute(EightTracksDataConstants.USER_TOKEN)};
			}
			else{
				throw new EightTracksException("Login Failed");
			}
			
			
		} catch (Exception e) {
			throw new EightTracksException(e.getMessage(), e.getCause());
		}
	}
	

	public List<Mix> getMixes() throws EightTracksException{
		
		try {
			JSONObject json = api.sendRequest(EightTracksConstants.MIXES, getDefaultFields());
			Response r = JSONDeserialiser.deserialiseResponse(json);
			return r.getMixes();
		} catch (Exception e) {
			throw new EightTracksException(e.getMessage(), e.getCause());
		}
	}
	
	public List<Mix> getMixes(Paging p)throws EightTracksException{
		try {
			JSONObject json = api.sendRequest(EightTracksConstants.MIXES, getFields(p));
			Response r = JSONDeserialiser.deserialiseResponse(json);
			return r.getMixes();
		} catch (Exception e) {
			throw new EightTracksException(e.getMessage(), e.getCause());
		}
	}
	public List<Mix> getMixes(FilterBuilder query)throws EightTracksException{
		throw new UnsupportedOperationException();
	}
	
	public List<Mix> getMixes(FilterBuilder query, Paging p)throws EightTracksException{
		try {
			JSONObject json = api.sendRequest(EightTracksConstants.MIXES, getFields(query, p));
			Response r = JSONDeserialiser.deserialiseResponse(json);
			return r.getMixes();
		} catch (Exception e) {
			throw new EightTracksException(e.getMessage(), e.getCause());
		}
	}
	

	
	public Mix getMix(long mix_id)throws EightTracksException{
		try {
			JSONObject json = api.sendRequest(EightTracksConstants.MIX, new Object[]{""+mix_id}, getDefaultFields());
			Response r = JSONDeserialiser.deserialiseResponse(json);
			if(r.getMixes().size() >= 1)
				return r.getMixes().get(0);
			else
				throw new EightTracksException("Not Found");
		} catch (Exception e) {
			throw new EightTracksException(e.getMessage(), e.getCause());
		}
	}
	
	public String getNewSessionToken() throws EightTracksException{

		try {
		JSONObject json = api.sendRequest(EightTracksConstants.SET_SESSION, getDefaultFields());
		
		Response r = JSONDeserialiser.deserialiseResponse(json);
		
		
		return ((String)r.getAttribute(EightTracksDataConstants.SET_PLAYTOKEN));
		} catch (Exception e) {
			throw new EightTracksException(e.getMessage(), e.getCause());
		}
	}
	
	public Track getPlayTrack(String session_id, long mix_id) throws EightTracksException{		
		try{
			Map<String, Object> fields = getDefaultFields();
			fields.put(EightTracksConstants.MIX_ID_FIELD, mix_id);
			
			JSONObject json = api.sendRequest(EightTracksConstants.SET_PLAYBACK, new Object[]{session_id} ,fields);
		
			Response r = JSONDeserialiser.deserialiseResponse(json);
			Set s = r.getSet();
		
			return s.getTrack();		
		} catch (Exception e) {
			throw new EightTracksException(e.getMessage(), e.getCause());
		}
	}
	
	public Track getNextTrack(String session_id, long mix_id) throws EightTracksException{	
		try{
			Map<String, Object> fields = getDefaultFields();
			fields.put(EightTracksConstants.MIX_ID_FIELD, mix_id);
			
			JSONObject json = api.sendRequest(EightTracksConstants.SET_MIX_NEXT, new Object[]{session_id}, fields);
		
			Response r = JSONDeserialiser.deserialiseResponse(json);
			Set s = r.getSet();
		
			return s.getTrack();		
		} catch (Exception e) {
			throw new EightTracksException(e.getMessage(), e.getCause());
		}
	}
	
	public Track getSkipTrack(String session_id, long mix_id) throws EightTracksException{
		try{
			Map<String, Object> fields = getDefaultFields();
			fields.put(EightTracksConstants.MIX_ID_FIELD, mix_id);
			
			JSONObject json = api.sendRequest(EightTracksConstants.SET_MIX_SKIP, new Object[]{session_id}, fields);
		
			Response r = JSONDeserialiser.deserialiseResponse(json);
			Set s = r.getSet();
		
			return s.getTrack();		
		} catch (Exception e) {
			throw new EightTracksException(e.getMessage(), e.getCause());
		}
	}
	
	public List<Track> getPlayedTracks(String session_id, long mix_id) throws EightTracksException{
		try{
			Map<String, Object> fields = getDefaultFields();
			fields.put(EightTracksConstants.MIX_ID_FIELD, mix_id);
			
			JSONObject json = api.sendRequest(EightTracksConstants.SET_TRACKS_PLAYED, new Object[]{session_id}, fields);
		
			Response r = JSONDeserialiser.deserialiseResponse(json);
			
			if(r.getTracks() != null)
				return r.getTracks();
			else
				throw new EightTracksException("Not Found");	
		} catch (Exception e) {
			throw new EightTracksException(e.getMessage(), e.getCause());
		}
	}
	
	public List<Track> getNextMix(String session_id, long mix_id){
		return null;
	}
	
	public User getUser(long user_id) throws EightTracksException{
		try {
			JSONObject json = api.sendRequest(EightTracksConstants.USER, new Object[]{""+user_id}, getDefaultFields());
			Response r = JSONDeserialiser.deserialiseResponse(json);
			return r.getUser();
		} catch (Exception e) {
			throw new EightTracksException(e.getMessage(), e.getCause());
		}
	}
	
	public List<Mix> getUserMixes(long user_id) throws EightTracksException{
		try {
			JSONObject json = api.sendRequest(EightTracksConstants.USER_MIXES, new Object[]{""+user_id}, getDefaultFields());
			Response r = JSONDeserialiser.deserialiseResponse(json);
			if( r.getMixes() != null){
				return r.getMixes();
			}
			else
				throw new EightTracksException("Not Found");
		} catch (Exception e) {
			throw new EightTracksException(e.getMessage(), e.getCause());
		}
	}
	
	public List<Mix> getUserLikedMixes(long user_id) throws EightTracksException{
		try {
			Map<String, Object> fields = getDefaultFields();
			fields.put(EightTracksDataConstants.VIEW, EightTracksDataConstants.LIKED);
			
			JSONObject json = api.sendRequest(EightTracksConstants.USER_MIXES, new Object[]{""+user_id}, fields);
			Response r = JSONDeserialiser.deserialiseResponse(json);
			if(r.getUser() != null && r.getUser().getMixes() != null){
				return r.getUser().getMixes();
			}else if(r.getMixes() != null){
				return r.getMixes();
			}
			else
				throw new EightTracksException("Not Found");
		} catch (Exception e) {
			throw new EightTracksException(e.getMessage(), e.getCause());
		}
	}
	
	public List<Review> getReviews(long mix_id) throws EightTracksException{
		try {
			JSONObject json = api.sendRequest(EightTracksConstants.REVIEWS, new Object[]{""+mix_id}, getDefaultFields());
			Response r = JSONDeserialiser.deserialiseResponse(json);
			if(r.getReviews() != null){
				return r.getReviews();
			}
			else
				throw new EightTracksException("Not Found");
		} catch (Exception e) {
			throw new EightTracksException(e.getMessage(), e.getCause());
		}
	}
	
	public List<Review> getReviews(long mix_id, Paging p) throws EightTracksException{
		try {
			JSONObject json = api.sendRequest(EightTracksConstants.REVIEWS, new Object[]{""+mix_id}, getFields(p));
			Response r = JSONDeserialiser.deserialiseResponse(json);
			if(r.getReviews() != null){
				return r.getReviews();
			}
			else
				throw new EightTracksException("Not Found");
		} catch (Exception e) {
			throw new EightTracksException(e.getMessage(), e.getCause());
		}
	}
	
	public Response postReview(long mix_id, Review review){
		throw new UnsupportedOperationException();
	}
	
	public Response toggleLike(long mix_id) throws EightTracksException{		
		try {
			JSONObject json = api.sendRequest(EightTracksConstants.TOGGLE_LIKE, new Object[]{""+mix_id}, getDefaultFields());
			Response r = JSONDeserialiser.deserialiseResponse(json);
			return r;
		} catch (Exception e) {
			throw new EightTracksException(e.getMessage(), e.getCause());
		}
	}
	
	public Response toggleFav(long track_id) throws EightTracksException{
		try {
			JSONObject json = api.sendRequest(EightTracksConstants.TOGGLE_FAV, new Object[]{""+track_id}, getDefaultFields());
			Response r = JSONDeserialiser.deserialiseResponse(json);
			return r;
		} catch (Exception e) {
			throw new EightTracksException(e.getMessage(), e.getCause());
		}
	}
	
	public Response toggleFollow(long user_id) throws EightTracksException{
		try {
			JSONObject json = api.sendRequest(EightTracksConstants.TOGGLE_FOLLOW, new Object[]{""+user_id}, getDefaultFields());
			Response r = JSONDeserialiser.deserialiseResponse(json);
			return r;
		} catch (Exception e) {
			throw new EightTracksException(e.getMessage(), e.getCause());
		}
	}


}
