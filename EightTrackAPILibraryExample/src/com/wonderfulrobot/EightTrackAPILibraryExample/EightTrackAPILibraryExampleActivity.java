package com.wonderfulrobot.EightTrackAPILibraryExample;

import java.util.List;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.wonderfulrobot.bandcampapi.core.BandcampException;
import com.wonderfulrobot.bandcampapi.core.BandcampService;
import com.wonderfulrobot.eighttrackandroid.EightTracksConstants;
import com.wonderfulrobot.eighttrackandroid.EightTracksException;
import com.wonderfulrobot.eighttrackandroid.EightTracksService;
import com.wonderfulrobot.eighttrackandroid.EightTracksServiceFactory;
import com.wonderfulrobot.eighttrackandroid.config.FilterBuilder;
import com.wonderfulrobot.eighttrackandroid.data.Mix;

/**
 * 
 * Example of Communicating with 8tracks.com using the eight-track-android-api library 
 * 
 * The Class contains an in-line Asynctask for accessing the API library on another (background) thread
 * 
 * @author mr_robot
 *
 */
public class EightTrackAPILibraryExampleActivity extends ListActivity {
	
	
    private static final String key = "";


	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); 
        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
        setContentView(R.layout.ic_activity_list);
        handleIntent(getIntent());

    }
        

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_options_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()){
	        case R.id.search:
	        	onSearchRequested();
	            return true;
            case R.id.quit:
            	finish();
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }
    

    /**
     * 
     * Handle New Search for a running Activity
     * 
     */
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    
    /**
     * 
     * Handle an Intent - Invoke Search if required, or perform a default search 
     * (so we have some content to display)
     * 
     * @param intent
     */
    private void handleIntent(Intent intent) {        
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
        	String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);

        	doSearch(query);
        }
        else{
        	doDefaultMixRetrieve();
        }

    }
    
    private void showProgressBar(){
    	setProgressBarIndeterminate(true);
    	setProgressBarIndeterminateVisibility(true);
    }
    
    private void hideProgressBar(){
    	setProgressBarIndeterminate(false);
    	setProgressBarIndeterminateVisibility(false);
    }
    
    /**
     * 
     * Invokes a Search by Creating an Asynctask 
     * 
     * @param query - Name of the Artist to Search
     */
    private void doSearch(String query){
    	showProgressBar();
    	
    	FilterBuilder fb = new FilterBuilder(query);
    	
    	RetrieveTask searchTask = new RetrieveTask(key);
		searchTask.execute(new FilterBuilder[]{fb});
    }
    
    private void doDefaultMixRetrieve(){

    	showProgressBar();
    	
    	FilterBuilder fb = new FilterBuilder(EightTracksConstants.SORT_HOT);
    	
    	RetrieveTask searchTask = new RetrieveTask(key);
		searchTask.execute(new FilterBuilder[]{fb});
    }
    
    /**
     * 
     * Binds a set of results to the List
     * 
     * Only creates the Adapter if required
     * 
     * @param result
     */
	private void setMixResult(List<Mix> result){
		hideProgressBar();
		if(result != null){
			if(getListAdapter() == null){
				ListAdapter mAdapter = new ArrayAdapter<Mix>(this, android.R.layout.simple_list_item_1,  result);
	
				setListAdapter(mAdapter);			
			}
			else{
				ArrayAdapter<Mix> adapter = ((ArrayAdapter<Mix>) getListAdapter());
				adapter.clear();
				for(Mix d : result){
					adapter.add(d);
				}
			}
		}
	}
	
	private void displayError(EightTracksException exception) {
		Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT);
	}
    
    
	/**
	 *
	 * Simple Asynctask to invoke a search on Bandcamp.com on a background thread
	 * 
	 * Creates an instance of the {@link BandcampService} and executes a search on it
	 * 
	 * @author T804772
	 *
	 */
    private class RetrieveTask extends AsyncTask<FilterBuilder, String, List<Mix>>{
    	private EightTracksException exception;
    	
    	private EightTracksServiceFactory mEightTracksServiceFactory;
    	
    	private EightTracksService mEightTracksService;
    	
    	public RetrieveTask(String key){
    		mEightTracksServiceFactory = new EightTracksServiceFactory(key);
    	}
    	

    	public EightTracksService getEightTracksService(){
    		if(mEightTracksService == null)
    			try {
    				mEightTracksService = mEightTracksServiceFactory.buildService();
    			} catch (EightTracksException e) {
    				e.printStackTrace();
    			}
    		return mEightTracksService;
    	}
    	
        protected void onPostExecute(List<Mix> result) {
        	if(exception != null){
        		displayError(exception);
        	}
        	if(result != null){
        		setMixResult(result);

        	}
        }

        /**
         * 
         * Search Occurs in two steps:
         * 
         * 1. Searching for the Band (Artist) and returning the relevant Artist ID's
         * 2. Retrieving the Discographies for the Band (Artist) ID's
         *           
         * @param query - Band/Artist Name
         * @return
         * @throws BandcampException
         */
    	private List<Mix> retrieve(FilterBuilder query) throws EightTracksException{
    		
    		List<Mix> result = getEightTracksService().getMixes(query);
    		
    		return result;
    	}
    	

		@Override
		protected List<Mix> doInBackground(FilterBuilder... params) {
				try {
					
					return retrieve(params[0]);
				} catch (EightTracksException e) {
					exception = e;
				}

			return null;
		}
    }
}