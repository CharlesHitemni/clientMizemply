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

import fr.primaveraServeur.model.Demande;
import fr.primaveraServeur.model.Role;
import fr.primaveraServeur.util.PostUtils;

@Controller
public class MainController {

	private String path = "/Users/diambe/git/clientMizemply/src/main/webapp/containers/";

	@RequestMapping(value = "/accueilRiverain", method = RequestMethod.GET)
	public ModelAndView accueilRiverain() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Accueil Riverain");
		model.addObject("message",
				"Cette page est accessible seulement par les riverains!");
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
	public ModelAndView redirectLogin(HttpSession session,
			@RequestParam("login") String login,
			@RequestParam("password") String password) {

		ModelAndView model = new ModelAndView();

		String urlService = getUrlServiceFromJson("http://192.168.1.241:8080/registryMairie/rest/registry/findServices/Authentification");
		Role role = new Role();
		role.setNom(login);
		role.setPassword(password);

		String resultJson = PostUtils.appelPost(urlService, role);
		role = PostUtils.convertionJsonEnObject(resultJson, Role.class);

		session.setAttribute("role", role);
		if (null != role.getNom()) {
			if ("riverain".equals(role.getNom())) {
				model.setViewName("redirect:accueilRiverain");
			}

			if ("entreprise".equals(role.getNom())) {
				model.setViewName("redirect:accueilBTP");
			}

			if ("inspecteur".equals(role.getNom())) {
				model.setViewName("redirect:accueilInspecteur");
			}

			if ("mairie".equals(role.getNom())) {
				model.setViewName("redirect:accueilMairie");
			}
		} else {
			model.setViewName("redirect:login");
		}
		return model;
	}

	@RequestMapping(value = "/accueilInspecteur", method = RequestMethod.GET)
	public ModelAndView accueilInspecteur(HttpSession session) {
		ModelAndView model = new ModelAndView();
		String urlService = getUrlServiceFromJson("http://192.168.1.241:8080/registryMairie/rest/registry/findServices/findAllDemandesInspecteur");

		String resultJson = PostUtils.appelGet(urlService);
		writeJsonIntoFile(resultJson, path + "demande_mairie.json");

		model.setViewName("accueilInspecteur");

		return model;
	}

	@RequestMapping(value = "/accueilMairie", method = RequestMethod.GET)
	public ModelAndView accueilMairie(HttpSession session) {
		ModelAndView model = new ModelAndView();
		String urlService = getUrlServiceFromJson("http://192.168.1.241:8080/registryMairie/rest/registry/findServices/findAllDemandesMairie");

		String resultJson = PostUtils.appelGet(urlService);
		writeJsonIntoFile(resultJson, path + "demande_riverain.json");

		model.setViewName("accueilMairie");

		return model;
	}

	@RequestMapping(value = "/accueilBTP", method = RequestMethod.GET)
	public ModelAndView accueilBTP(HttpSession session) {

		ModelAndView model = new ModelAndView();
		String urlService = getUrlServiceFromJson("http://192.168.1.241:8080/registryMairie/rest/registry/findServices/findAllDemandesEntreprise");

		String resultJson = PostUtils.appelGet(urlService);
		writeJsonIntoFile(resultJson, path + "demande_inspecteur.json");

		return model;
	}

	@RequestMapping(value = "/accueilComptable", method = RequestMethod.GET)
	public ModelAndView accueilComptable() {

		ModelAndView model = new ModelAndView();
		
		//Récupération paiements entreprises
		String urlService = getUrlServiceFromJson("http://192.168.1.241:8080/registryMairie/rest/registry/findServices/findAllDemandesComptableByRole");
		String resultJson = PostUtils.appelGet(urlService+"/entreprise");
		writeJsonIntoFile(resultJson, path + "paiement_entreprise.json");
		
		
		//Récupération paiements inspecteurs
		resultJson = PostUtils.appelGet(urlService+"/inspecteur");
		writeJsonIntoFile(resultJson, path + "paiement_inspecteur.json");
		
		model.setViewName("accueilComptable");

		return model;
	}

	@RequestMapping(value = "/routage", method = RequestMethod.POST)
	public ModelAndView entryPoint(
			@RequestParam(required = false, value = "titre") String titre,
			@RequestParam(required = false, value = "description") String description,
			@RequestParam(required = false, value = "adresse") String adresse,
			@RequestParam(required = false, value = "nombrepaiement") int nombrepaiement,
			@RequestParam(required = false, value = "prix") float prix,
			@RequestParam(value = "role") String role,
			@RequestParam(value = "action", required = false) String action,
			@RequestParam(value = "idDemande", required = false) int idDemande,
			HttpSession session) {

		Role roleUser = (Role) session.getAttribute("role");
		Demande demande = new Demande();
		demande.setTitre(titre);
		demande.setDescription(description);
		demande.setAdresse(adresse);
		demande.setRole(roleUser);
		
		if (role.equalsIgnoreCase("riverain")) {

			// La variable action contient le nom du service à appeler
			// On récupère ensuite l'url du service en faisant appel au registry
			String urlService = getUrlServiceFromJson("http://192.168.1.241:8080/registryMairie/rest/registry/findServices/saveDemandeRiverain");

			PostUtils.appelPost(urlService, demande);

			// Une fois la demande enregistrée, on récupère les demandes en base
			// pour les réecrires dans le fichier .json
			urlService = getUrlServiceFromJson("http://192.168.1.241:8080/registryMairie/rest/registry/findServices/findAllDemandesMairie");

			String resultJson = PostUtils.appelGet(urlService);
			writeJsonIntoFile(resultJson, path + "demande_riverain.json");

			return new ModelAndView("redirect:accueilRiverain");
		}

		if (role.equalsIgnoreCase("mairie")) {
			if (action.equalsIgnoreCase("envoyer")) {
				demande.setNombrePaiement(nombrepaiement);
				demande.setMontant(prix);

				String urlService = getUrlServiceFromJson("http://192.168.1.241:8080/registryMairie/rest/registry/findServices/saveDemandeMairie");
				PostUtils.appelPost(urlService, demande);
				demande.setIdDemande(idDemande);
				demande.setClose(true);
				PostUtils.appelPost(urlService, demande);
				 
				//Actualisation fichier inspecteur
				urlService = getUrlServiceFromJson("http://192.168.1.241:8080/registryMairie/rest/registry/findServices/findAllDemandesInspecteur");
				String resultJson = PostUtils.appelGet(urlService);
				writeJsonIntoFile(resultJson, path + "demande_mairie.json");
				
				//Actualisation fichier mairie
				urlService = getUrlServiceFromJson("http://192.168.1.241:8080/registryMairie/rest/registry/findServices/findAllDemandesMairie");
				resultJson = PostUtils.appelGet(urlService);
				writeJsonIntoFile(resultJson, path + "demande_riverain.json");
				
			} else {
				//Cloture de la demande
				demande.setClose(true);
				demande.setIdDemande(idDemande);
				String urlService = getUrlServiceFromJson("http://192.168.1.241:8080/registryMairie/rest/registry/findServices/saveDemandeMairie");
				PostUtils.appelPost(urlService, demande);
				
				//Actualisation fichier mairie
				urlService = getUrlServiceFromJson("http://192.168.1.241:8080/registryMairie/rest/registry/findServices/findAllDemandesMairie");
				String resultJson = PostUtils.appelGet(urlService);
				writeJsonIntoFile(resultJson, path + "demande_riverain.json");
				
			}
			return new ModelAndView("redirect:accueilMairie");
		}

		if (role.equalsIgnoreCase("inspecteur")) {
			if (action.equalsIgnoreCase("envoyer")) {
				String urlService = getUrlServiceFromJson("http://192.168.1.241:8080/registryMairie/rest/registry/findServices/saveDemandeInspecteur");
				PostUtils.appelPost(urlService, demande);
				//Fermeture de la demande que l'on vient d'envoyer
				demande.setIdDemande(idDemande);
				demande.setClose(true);
				PostUtils.appelPost(urlService, demande);
				
				//Actualisation fichier entreprise
				urlService = getUrlServiceFromJson("http://192.168.1.241:8080/registryMairie/rest/registry/findServices/findAllDemandesEntreprise");
				String resultJson = PostUtils.appelGet(urlService);
				writeJsonIntoFile(resultJson, path + "demande_inspecteur.json");
				
				//Actualisation fichier inspecteur
				urlService = getUrlServiceFromJson("http://192.168.1.241:8080/registryMairie/rest/registry/findServices/findAllDemandesInspecteur");
				resultJson = PostUtils.appelGet(urlService);
				writeJsonIntoFile(resultJson, path + "demande_mairie.json");
			} else {
				demande.setClose(true);
				demande.setIdDemande(idDemande);
				String urlService = getUrlServiceFromJson("http://192.168.1.241:8080/registryMairie/rest/registry/findServices/saveDemandeInspecteur");
				PostUtils.appelPost(urlService, demande);
				
				//Actualisation fichier mairie
				urlService = getUrlServiceFromJson("http://192.168.1.241:8080/registryMairie/rest/registry/findServices/findAllDemandesInspecteur");
				String resultJson = PostUtils.appelGet(urlService);
				writeJsonIntoFile(resultJson, path + "demande_mairie.json");
			}

			return new ModelAndView("redirect:accueilInspecteur");
		}

		if (role.equalsIgnoreCase("btp")) {
			demande.setIdDemande(idDemande);
			demande.setClose(true);
			String urlService = getUrlServiceFromJson("http://192.168.1.241:8080/registryMairie/rest/registry/findServices/saveDemandeEntreprise");
			PostUtils.appelPost(urlService, demande);
			
			//Reactualisation du fichier json
			urlService = getUrlServiceFromJson("http://192.168.1.241:8080/registryMairie/rest/registry/findServices/findAllDemandesEntreprise");
			String resultJson = PostUtils.appelGet(urlService);
			writeJsonIntoFile(resultJson, path + "demande_inspecteur.json");

			return new ModelAndView("redirect:accueilBTP");
		}

		return new ModelAndView("page404");
	}

	/**
	 * Récupère l'url d'un service à partir de son nom en interrogeant le
	 * registryacti
	 * 
	 * @param url
	 *            url d'accès au registry avec le nom du service
	 * 
	 * @return l'url du service
	 */
	private String getUrlServiceFromJson(String url) {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		String urlService = "";
		try {
			HttpResponse response = client.execute(request);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			String line = "";
			String result = "";
			while ((line = br.readLine()) != null) {
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
	 * Interroge le webService pour récupérer les données demandées et les écrit
	 * dans un fichier (.json)
	 * 
	 * @param urlService
	 *            l'url du service à appeler
	 */
	private String getResultService(String urlService) {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(urlService);
		String result = "";
		try {
			HttpResponse response = client.execute(request);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			String line = "";
			while ((line = br.readLine()) != null) {
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
	 *            le contenu json à écrire dans le fichier
	 */
	private void writeJsonIntoFile(String json, String path) {
		try {
			// ecriture de l'objet converti dans un fichier .json
			FileWriter writer = new FileWriter(path);
			writer.write(json);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}