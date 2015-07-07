package fr.primaveraServeur.controller;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

	private String currentDirectory = System.getProperty("user.dir" );
	
	@RequestMapping(value = "/accueilRiverain", method = RequestMethod.GET)
	public ModelAndView accueilRiverain() {
		
		ModelAndView model = new ModelAndView();
        model.addObject("title", "Accueil Riverain");
        model.addObject("message", "Cette page est accessible seulement par les riverains!");
        model.setViewName("accueilRiverain");
        
        return model;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView accueilLogin() {
		
		ModelAndView model = new ModelAndView();
        model.addObject("title", "Accueil Riverain");
        model.addObject("message", "Cette page est accessible seulement par les riverains!");
        model.setViewName("login");
        
        return model;
	}
	
	@RequestMapping(value = "/redirectLogin", method = RequestMethod.POST)
	public ModelAndView redirectLogin(HttpSession session, @RequestParam("login") String login, @RequestParam("password") String password) {
		
		ModelAndView model = new ModelAndView();
		session.setAttribute("login", login);
		
		if(login.equals("riverain"))
		{
			model.setViewName("redirect:accueilRiverain");
		}	
		if(login.equals("BTP"))
		{
			model.setViewName("redirect:accueilBTP");
		}
		
		if(login.equals("inspecteur"))
		{
			model.setViewName("redirect:accueilInspecteur");
		}
        return model;
	}
	
	@RequestMapping(value = "/accueilInspecteur", method = RequestMethod.GET)
	public ModelAndView accueilInspecteur() {
		
		ModelAndView model = new ModelAndView();
        model.addObject("title", "Accueil Riverain");
        model.addObject("message", "Cette page est accessible seulement par les riverains!");
        model.addObject("repertoireCourant", currentDirectory);
        model.setViewName("accueilInspecteur");
        
        return model;
	}
	
	@RequestMapping(value = "/accueilBTP", method = RequestMethod.GET)
	public ModelAndView accueilBTP() {
		
		ModelAndView model = new ModelAndView();
        model.addObject("title", "Accueil Riverain");
        model.addObject("message", "Cette page est accessible seulement par les riverains!");
        model.addObject("repertoireCourant", currentDirectory);
        model.setViewName("accueilBTP"); 
        
        return model;
	}
	
	@RequestMapping(value = "/accueilComptable", method = RequestMethod.GET)
	public ModelAndView accueilComptable() {
		
		ModelAndView model = new ModelAndView();
        model.addObject("title", "Accueil Comptable");
        model.addObject("message", "Cette page est accessible seulement par les comptables!");
        model.addObject("repertoireCourant", currentDirectory);
        model.setViewName("accueilComptable");
        
        return model;
	}
	
	@RequestMapping(value = "/accueil", method = RequestMethod.GET)
	public ModelAndView entryPoint(@RequestParam("action") String action) {
		
		// La variable action contient le nom du service à appeler
		// On récupère ensuite l'url du service en faisant appel au registry
		String urlService = getUrlServiceFromJson("http://192.168.2.28:8080/registryMairie/rest/registry/findServices/"+action);
		
		//On fait appel au service grâce au webService et on écrit la réponse dans un fichier .json
		getAndWriteResponseIntoFile(urlService);
		
		return new ModelAndView("accueilRiverain").addObject("url", urlService);
	}


	/**
	 * Récupère l'url d'un service à partir de son nom en interrogeant le registry
	 * 
	 * @param url
	 * 		url d'accès au registry avec le nom du service
	 * 
	 * @return l'url du service
	 */
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
			e.printStackTrace();
		}
		return urlService;
	}
	
	/**
	 * Interroge le webService pour récupérer les données demandées et les écrit dans un fichier (.json)
	 * 
	 * @param urlService
	 * 			l'url du service à appeler
	 */
	private void getAndWriteResponseIntoFile(String urlService) {
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
			FileWriter writer = new FileWriter("./containers/contenu.json");
			writer.write(json);
			writer.close();
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}