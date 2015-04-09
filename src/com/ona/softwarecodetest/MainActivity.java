package com.ona.softwarecodetest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


public class MainActivity extends Activity {
	String url = "https://raw.githubusercontent.com/onaio/ona-tech/master/data/water_points.json";
	JSONArray waterpoints;
	TextView hello;
	int functional_waterpoints, non_functional_waterpoints, total_waterpoints, total;
	Map<String,Integer> map, map_functional, map_non_functional;
	ArrayList<String> a, b, c;
	String community;
	 String key, key1;
	 int  value, value1, rank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        hello = (TextView) findViewById(R.id.hello);
        new Calculate().execute();
   
    }
    
    
  class Calculate extends AsyncTask<Void, Void, JSONArray>
    {

		@Override
		protected JSONArray doInBackground(Void... arg0) {
			
			
				Log.d("We get to ", "Downloading..");
			  Network_Connector conn = new Network_Connector(getApplicationContext());
		       String data = conn.GetData(url);
		       
		       try {
				 waterpoints = new JSONArray(data);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		       
			// TODO Auto-generated method stub
			return waterpoints;
		}
		
		@Override
		protected void onPostExecute(JSONArray result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			functional_waterpoints = 0;
			non_functional_waterpoints = 0;
			total_waterpoints = 0;
			total = 0;
			a = new ArrayList<String>();
			b = new ArrayList<String>();
			c = new ArrayList<String>();
			for (int i = 0; i < result.length() ; i++) 
			{
			
				try {
					JSONObject waterpoints = result.getJSONObject(i);
					//Log.i("waterpoints", waterpoints+"");
					
					
					if(waterpoints.isNull("water_functioning"))
					{
						Log.i("Invalid", "Invalid");
					}
					
					else
					{
					String waterpoint_functional = waterpoints.getString("water_functioning");
					Log.i("functional", waterpoint_functional);
					total_waterpoints = total_waterpoints + 1;
				    community = waterpoints.getString("communities_villages");
					Log.i("total", total_waterpoints + "");	
					a.add(community);
					
					
					
					if(waterpoint_functional.matches("no"))
					{
						non_functional_waterpoints = non_functional_waterpoints + 1;
						String community = waterpoints.getString("communities_villages");
						Log.i("non_functional_communities", community);
						
						b.add(community);
						
					}
					
					else if(waterpoint_functional.matches("yes"))
					{
						functional_waterpoints = functional_waterpoints + 1;
						String community = waterpoints.getString("communities_villages");
						Log.i("functional_communities", community);
						c.add(community);

					}
					
				
					
					}
					
					//hello.setText(community);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			Log.i("list", a.size()+"");
			

			String[] myArray = new String[a.size()];
			a.toArray(myArray);
			
			String [] array_non_functional = new String[b.size()];
			b.toArray(array_non_functional);
			String [] array_functional = new String [c.size()];
			c.toArray(array_functional);
			
			
			map = new HashMap<String,Integer>();
			
			map_non_functional = new HashMap<String, Integer>();
			
			
			for (int j = 0; j < myArray.length; j++) {
			    String word=myArray[j];
			    if (!map.containsKey(word)){
			        map.put(word,1);
			    } else {
			        map.put(word, map.get(word) +1);   
			    }
			}
			
			for (int x = 0; x < array_non_functional.length; x++) {
			    String word=myArray[x];
			    if (!map_non_functional.containsKey(word)){
			    	map_non_functional.put(word,1);
			    } else {
			    	map_non_functional.put(word, map_non_functional.get(word) +1);   
			    }
			}
		
			JSONObject water_points_community = new JSONObject();
			JSONObject water_points_community_nonfunc = new JSONObject();
			JSONObject ranking = new JSONObject();
		    Set<Entry<String,Integer>> hashSet=map.entrySet();
	        for(Entry entry:hashSet ) {

	            System.out.println("Key="+entry.getKey()+", Value="+entry.getValue());
	            try {
					water_points_community.put(entry.getKey()+"", entry.getValue());
					
					String val = entry.getValue().toString();
				
					total = total + Integer.valueOf(val);
					
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
	        } 
	        
	       
		    Set<Entry<String,Integer>> hashSetNonFunc=map_non_functional.entrySet();
	        for(Entry entry:hashSetNonFunc ) {

	            System.out.println("Key="+entry.getKey()+", Value="+entry.getValue());
	            try {
					water_points_community_nonfunc.put(entry.getKey()+"", entry.getValue());
					
					String val = entry.getValue().toString();
				
					total = total + Integer.valueOf(val);
					
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
	        } 
	        
	        Log.i("communities", water_points_community.toString());
	        Log.i("communities_non_func", water_points_community_nonfunc.toString());
			
	        
	        Iterator iter = water_points_community_nonfunc.keys();
	        Iterator iter_total = water_points_community.keys();
	       
	 
	        
	        while (iter_total.hasNext())
		        {
	            	key1 = (String) iter_total.next();
	            	
	            	
	            	while(iter.hasNext())
	            	{
	            		key = (String)iter.next();
	            		try {
 		            		value = Integer.valueOf(water_points_community_nonfunc.getString(key));
 		            		Log.i("value", value +"");
 		
 						} catch (JSONException e) {
 							// TODO Auto-generated catch block
 							e.printStackTrace();
 						}
//	    	            
	    	            Log.i("key", key);
	            	
	    	            
	            	    	 try {
	      						value1 = Integer.valueOf(water_points_community.getString(key));
	      						Log.i("value1", value1 +"");
	      					} catch (JSONException e) {
	      						// TODO Auto-generated catch block
	      						e.printStackTrace();
	      					}
	            	    	 
	            	    	 
	            	    	 rank = (int)(((double)value/(double)value1) * 100);
	            	    	 
	            	    	 
	            	    	try {
								ranking.put(key, rank);
								
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} 
	        			   Log.i("rank", rank +"");
	        			    
	            	}
	            	
//	            	try {
//						ranking.put(key1, 0+"");
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
	            	    
		        } 

	        
	        JSONObject final_dataset = new JSONObject();
	        try {
				final_dataset.put("number_functional", functional_waterpoints);
				final_dataset.put("number_water_points", water_points_community);
				final_dataset.put("community_ranking", ranking);
	
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        
	    
	        
	        
			Log.i("functional_final", functional_waterpoints + "");
			Log.i("broken_waterpoints", non_functional_waterpoints+"");
			Log.i("total_waterpoints", total_waterpoints+"");
			
			Log.i("final_dataset", final_dataset.toString());
			
			hello.setText(final_dataset.toString());
			
		}
    	
    }
}
