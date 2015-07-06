package fr.primaveraServeur.controller;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class actionController {


	//controleur
	
	@RequestMapping(value = "/accueil", method = RequestMethod.GET)
	public ModelAndView afficheFiche() {
		
		String urlService = getUrlServiceFromJson("http://192.168.2.28:8080/registryMairie/rest/registry/findServices/serviceRiverain");
		
		return new ModelAndView("accueil").addObject("url", urlService);
	}


	private String getUrlServiceFromJson(String url) {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		String urlService = "";
		try {
            HttpResponse response = client.execute(request);
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line ="";
            String result="";
            while ((line = br.readLine()) != null){
                result += line;
            }
            JSONObject json = new JSONObject(result);
            urlService = json.getString("urlService");
            
		} catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return urlService;
	}
	
	private void writeResponseIntoFile(String urlService) {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(urlService);
		try {
            HttpResponse response = client.execute(request);
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line ="";
            String result="";
            while ((line = br.readLine()) != null){
                result += line;
            }
            //Ecriture de l'objet dans un fichier JSON
            writeJsonIntoFile(result);
            
		} catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	
	/**
	 * Ecriture du JSON dans un fichier (contenu.json)
	 * 
	 * @param json 
	 * 		le contenu json à écrire dans le fichier
	 */
	private void writeJsonIntoFile(String json) {
		try {
			//Récupération du répertoire courant
			String currentDirectory = System.getProperty("user.dir" );
			
			//ecriture de l'objet converti dans un fichier .json
			FileWriter writer = new FileWriter(currentDirectory+"/contenu.json");
			writer.write(json);
			writer.close();
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}