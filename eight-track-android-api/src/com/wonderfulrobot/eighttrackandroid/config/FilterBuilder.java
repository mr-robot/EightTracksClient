package com.wonderfulrobot.eighttrackandroid.config;

import com.wonderfulrobot.eighttrackandroid.EightTracksConstants;

public class FilterBuilder {

	public String getQuery() {
		return query;
	}
	
	public String getTags(){
		String tagString = null;
		if(tags != null){
			tagString = "";
			for(String tag : tags){
				tagString = tagString + tag + ",";
			}
		}
		return tagString;
	}

	public String getSort() {
		if(sort > 0){
			switch(sort){
				case EightTracksConstants.SORT_HOT:
					return "hot";
				case EightTracksConstants.SORT_POPULAR:
					return "popular";
				case EightTracksConstants.SORT_RANDOM:
					return "random";
				case EightTracksConstants.SORT_RECENT:
					return "recent";
			}
			return "recent";
		}
		return null;
	}

	private String query;
	private int sort = 0;
	private String[] tags;
	
	public FilterBuilder(String query){
		this.query = query;
	}
	
	public FilterBuilder(String[] tags){
		this.tags = tags;
	}
	
	
	public FilterBuilder(String query, int sort){
		this.query = query;
		this.sort = sort;
	}
	
	public FilterBuilder(int sort){
		this.sort = sort;
	}

	public void setSort(int sort2) {
		this.sort = sort2;
	}
}
