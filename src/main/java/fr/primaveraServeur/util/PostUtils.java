package fr.primaveraServeur.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public final class PostUtils {

	public static String appelPost(String url, Object object){		
		ClientResponse response = null;
		String reponseJson = ""; 
		try {
			Client client = Client.create();
			WebResource webResource = client
					.resource(url);
			String json = convertionObjetEnJson(object);
			response = webResource.type("application/json")
					.post(ClientResponse.class, json);
			if(response!=null){
				reponseJson = response.getEntity(String.class);
				//reponseJson="";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return reponseJson;
	}
	
	public static String appelGet(String url){		
		ClientResponse response = null;
		String reponseJson = ""; 
		try {
			Client client = Client.create();
			WebResource webResource = client
					.resource(url);
			response = webResource.type("application/json")
					.get(ClientResponse.class);
			reponseJson = response.getEntity(String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return reponseJson;
	}
	public static  String convertionObjetEnJson(Object object){
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		return gson.toJson(object);
	}
	
	public static <T> T convertionJsonEnObject(String json,Class<T> c){
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		return gson.fromJson(json, c);
	}
	

}

