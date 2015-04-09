package com.ona.softwarecodetest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.util.Log;


public class Network_Connector
{
	public final static int GET = 1;
	public final static int POST = 2;
	String filename="UserFile";

	
	private HttpClient client;
	private HttpPost post_request;
	private HttpGet get_request;
	public HttpResponse response;
	private String accessTokens;
	private Context context;
	
	public Network_Connector(Context contexts)
	{
		this.context = contexts;
	}
	
	
	
	public String GetData(String url)
	{
		String responseBody = "";
		try
		{
			
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 600000);
			HttpConnectionParams.setSoTimeout(httpParams, 1200000);
			HttpClient client = new DefaultHttpClient(httpParams);
			HttpGet request = new HttpGet(url);
			request.setHeader("Content-Type", "application/json");
			HttpResponse response = client.execute(request);
			
			
			if (response.getStatusLine().getStatusCode() == 200) 
			{
				Log.i("statuscode", response.getStatusLine().getStatusCode()+"a");
				
				
			}
			
			HttpEntity entity=	response.getEntity();
			Log.d("", "m"+entity);
			responseBody= EntityUtils.toString(entity);
			Log.i("response", responseBody);
		} 
		catch (Throwable t) 
		{
			t.printStackTrace();
		}
		
		return responseBody;
	}


	
	public int errorsCodes()
	{
		int code = response.getStatusLine().getStatusCode();
		return code;
	}
	
}
