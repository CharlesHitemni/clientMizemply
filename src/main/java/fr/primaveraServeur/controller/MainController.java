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

import fr.primaveraServeur.model.DemandeFront;
import fr.primaveraServeur.model.Role;
import fr.primaveraServeur.model.User;
import fr.primaveraServeur.util.PostUtils;

@Controller
public class MainController {

	private String path = "/Users/diambe/git/clientMizemply/src/main/webapp/containers/";
	
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
        model.setViewName("login");
        
        return model;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpSession session) {
		
		ModelAndView model = new ModelAndView();
		session.removeAttribute("user");
        model.setViewName("login");
        
        return model;
	}
	
	@RequestMapping(value = "/redirectLogin", method = RequestMethod.POST)
	public ModelAndView redirectLogin(HttpSession session, @RequestParam("login") String login, @RequestParam("password") String password) {
		
		ModelAndView model = new ModelAndView();
		
		String urlService = getUrlServiceFromJson("http://10.10.81.39:8080/registryMairie/rest/registry/findServices/authentification");
		Role role = new Role();
		role.setNom(login);
		role.setPassword(password);
		
		String resultJson = PostUtils.appelPost(urlService, role);
		role = PostUtils.convertionJsonEnObject(resultJson, Role.class);
		
		session.setAttribute("role", role);
		if(null != role.getNom()) {
			if("riverain".equals(role.getNom())) {
				model.setViewName("redirect:accueilRiverain");
			}
			
			if("btp".equals(role.getNom())) {
				model.setViewName("redirect:accueilBTP");
			}
			
			if("inspecteur".equals(role.getNom())) {
				model.setViewName("redirect:accueilInspecteur");
			}
		}
		else {
			model.setViewName("redirect:login");
		}
        return model;
	}
	
	@RequestMapping(value = "/accueilInspecteur", method = RequestMethod.GET)
	public ModelAndView accueilInspecteur(HttpSession session) {
		ModelAndView model = new ModelAndView();
		String urlService = getUrlServiceFromJson("http://10.10.81.39:8080/registryMairie/rest/registry/findServices/findAllDemandesInspecteur");
		
		String resultJson =  PostUtils.appelGet(urlService);
		writeJsonIntoFile(resultJson, path+"demande_riverain.json");
		
        model.setViewName("accueilInspecteur");
        
        return model;
	}
	
	@RequestMapping(value = "/accueilMairie", method = RequestMethod.GET)
	public ModelAndView accueilMairie(HttpSession session) {
		ModelAndView model = new ModelAndView();
		String urlService = getUrlServiceFromJson("http://10.10.81.39:8080/registryMairie/rest/registry/findServices/findAllDemandesMairie");
		
		String resultJson =  PostUtils.appelGet(urlService);
		writeJsonIntoFile(resultJson, path+"demande_mairie.json");
		
        model.setViewName("accueilMairie");
        
        return model;
	}
	
	@RequestMapping(value = "/accueilBTP", method = RequestMethod.GET)
	public ModelAndView accueilBTP(HttpSession session) {
		
		ModelAndView model = new ModelAndView();
		String urlService = getUrlServiceFromJson("http://10.10.81.39:8080/registryMairie/rest/registry/findServices/findAllDemandesEntreprise");
		
		String resultJson =  PostUtils.appelGet(urlService);
		writeJsonIntoFile(resultJson, path+"demande_inspecteur.json");
        
        return model;
	}
	
	@RequestMapping(value = "/accueilComptable", method = RequestMethod.GET)
	public ModelAndView accueilComptable() {
		
		ModelAndView model = new ModelAndView();
//		String urlService = getUrlServiceFromJson("http://10.10.81.39:8080/registryMairie/rest/registry/findServices/demandeDao-findAllDemandes");
//		String resultJson = getResultService(urlService);
//		writeJsonIntoFile(resultJson, path+"paiements.json");
        model.setViewName("accueilComptable");
        
        return model;
	}
	
	@RequestMapping(value = "/routage", method = RequestMethod.POST)
	public ModelAndView entryPoint(@RequestParam(required=false, value="titre") String titre, 
				@RequestParam(required=false, value="description") String description,
				@RequestParam(required=false, value="adresse") String adresse, 
				@RequestParam(value="role") String role,
				@RequestParam(value="action", required=false) String action, HttpSession session) {
		
		if(role.equalsIgnoreCase("riverain")) {
			User user = (User)session.getAttribute("user");
			DemandeFront demande = new DemandeFront();
			demande.setTitre(titre);
			demande.setDescriptions(description);
			demande.setAdresse(adresse);
			demande.setLogin(user.getLogin());

			// La variable action contient le nom du service à appeler
			// On récupère ensuite l'url du service en faisant appel au registry
			String urlService = getUrlServiceFromJson("http://10.10.81.39:8080/registryMairie/rest/registry/findServices/saveDemandeRiverain");
			
			PostUtils.appelPost(urlService, demande);
			
			//Une fois la demande enregistrée, on récupère les demandes en base pour les réecrires dans le fichier .json
			urlService = getUrlServiceFromJson("http://10.10.81.39:8080/registryMairie/rest/registry/findServices/demandeDao-findAllDemandes");
			
			String resultJson =  PostUtils.appelPost(urlService, new String("inspecteur"));
			writeJsonIntoFile(resultJson, path+"demande_riverain.json");
			
			return new ModelAndView("redirect:accueilRiverain").addObject("messageSuccess", "Traitement effectué !");
			
		}
		
		if(role.equalsIgnoreCase("inspecteur")) {
			if(action.equalsIgnoreCase("envoyer")) {
				
			}
			else {
				
			}
			String urlService = getUrlServiceFromJson("http://192.168.1.241:8080/registryMairie/rest/registry/findServices/demandeDao-save");
			
			PostUtils.appelPost(urlService, null);
			
			return new ModelAndView("redirect:accueilInspecteur").addObject("messageSuccess", "Traitement effectué !");
		}
		
		if(role.equalsIgnoreCase("btp")) {
			String urlService = getUrlServiceFromJson("http://192.168.1.241:8080/registryMairie/rest/registry/findServices/demandeDao-save");
			
			PostUtils.appelPost(urlService, null);
			
			return new ModelAndView("redirect:accueilBTP").addObject("messageSuccess", "Traitement effectué !");
		}
		
//		Demande demande = new Demande();
//		Description descriptions = new Description();
//		Role roless = new Role();
//		role.setIdRole(1);
//		
//		descriptions.setDescription(description);
//		
//		demande.setTitre(titre);
//		demande.setAdresse(adresse);
//		demande.setDescriptions(null);
//		demande.setRole(role);
		
//		//On fait appel au service grâce au webService et on écrit la réponse dans un fichier .json
//		getAndWriteResponseIntoFile(urlService);
		
		return new ModelAndView("page404");
	}


	/**
	 * Récupère l'url d'un service à partir de son nom en interrogeant le registryacti
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
	private String getResultService(String urlService) {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(urlService);
		String result = "";
		try {
            HttpResponse response = client.execute(request);
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line ="";
            while ((line = br.readLine()) != null){
                result += line;
            }
            
		} catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return result;
	}
	
	
	/**
	 * Ecriture du JSON dans un fichier (contenu.json)
	 * 
	 * @param json 
	 * 		le contenu json à écrire dans le fichier
	 */
	private void writeJsonIntoFile(String json, String path) {
		try {
			//ecriture de l'objet converti dans un fichier .json
			FileWriter writer = new FileWriter(path);
			writer.write(json);
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}