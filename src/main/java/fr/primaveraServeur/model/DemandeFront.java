package fr.primaveraServeur.model;

import java.io.Serializable;
import java.util.Set;


public class DemandeFront implements Serializable {

	private int idDemande;

	
	private String adresse;


	private String date;

	
	private String titre;
	
	private String descriptions;
	
	private boolean open;
	
	private String login;

	public DemandeFront() {
	}
	
	public DemandeFront(Demande demande) {
		
		this.idDemande=demande.getIdDemande();
		this.adresse=demande.getAdresse();
		this.date=demande.getDate();
		this.titre=demande.getTitre();
		this.descriptions=getDescriptionInscpeteur(demande.getDescriptions());
		
	}
	
	private String getDescriptionInscpeteur(Set<Description> listDescription){
		for(Description description : listDescription){
			if("inspecteur".equals(description.getUser().getRole().getNom())){
				return description.getDescription();
			}
		}
		return "";
	}

	public int getIdDemande() {
		return this.idDemande;
	}

	private static final long serialVersionUID = 1L;

	public void setIdDemande(int idDemande) {
		this.idDemande = idDemande;
	}

	public String getAdresse() {
		return this.adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTitre() {
		return this.titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescriptions() {
		return this.descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String idUser) {
		this.login = idUser;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	

}