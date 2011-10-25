package com.wonderfulrobot.eighttrackandroid;

public class EightTracksException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7086758131485388486L;

	public EightTracksException() {
	}

	public EightTracksException(String detailMessage) {
		super(detailMessage);
	}

	public EightTracksException(Throwable throwable) {
		super(throwable);
	}

	public EightTracksException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
