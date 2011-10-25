package com.wonderfulrobot.eighttrackandroid;

public class EightTracksConstants {
	
	/*
	 * Methods
	 * 
	 */
	
	public static final String SESSION_LOGIN = "sessions";
	
	public static final String MIXES = "mixes";
	public static final String MIX = "mixes/%s";
	public static final String REVIEWS = "mixes/%s/reviews";
	

	public static final String TOGGLE_LIKE = "mixes/%s/toggle_like";

	public static final String TOGGLE_FAV = "tracks/%s/toggle_fav";

	public static final String TOGGLE_FOLLOW = "users/%s/follow";

	public static final String SET_SESSION = "sets/new";
	public static final String SET_PLAYBACK = "sets/%s/play";
	public static final String SET_MIX_NEXT = "sets/%s/next";
	public static final String SET_MIX_SKIP = "sets/%s/skip";
	public static final String SET_TRACKS_PLAYED = "sets/%s/tracks_played";

	public static final String USER = "users/%s";
	public static final String USER_MIXES = "users/%s/mixes";
	
	/*
	 * 
	 * Fields
	 * 
	 */
	
	public static final String MIX_ID_FIELD = "mix_id";
	
	/*
	 * 
	 * Filters
	 * 
	 */
	
	public static final int SORT_POPULAR = 21;
	public static final int SORT_HOT = 22;
	public static final int SORT_RECENT = 23;
	public static final int SORT_RANDOM = 24;

	/*
	 * Results
	 * 
	 */
	
	public static final String RESULT = "result";
	
	/*
	 * Default Service Configuration
	 * 
	 */

	public static final boolean GZIP = true;
	public static final int CONNECTION_TIMEOUT = 3000;
	public static final int SOCKET_TIMEOUT = 5000;

	public static final String GET = "GET";
	public static final String POST = "POST";

}
