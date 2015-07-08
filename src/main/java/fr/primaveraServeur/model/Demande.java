package fr.primaveraServeur.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the demande database table.
 * 
 */
@Entity
@Table(name="demande")
@NamedQuery(name="Demande.findAll", query="SELECT d FROM Demande d")
public class Demande implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int idDemande;

	@Column(length=100)
	private String adresse;

	@Column(length=50)
	private String date;

	@Column(columnDefinition = "TINYINT(1)")
	private boolean open;

	@Column(length=45)
	private String titre;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="idrole", nullable=false)
	private Role role;

	//bi-directional many-to-one association to Description
	@OneToMany(mappedBy="demande", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Description> descriptions;

	//bi-directional many-to-one association to Paiement
	@OneToMany(mappedBy="demande",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Set<Paiement> paiements;

	public Demande() {
	}
	
	public Demande(DemandeFront demandefront) {
		
		this.adresse=demandefront.getAdresse();
		this.date=demandefront.getDate();
		this.titre=demandefront.getTitre();
		
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

	public boolean isOpen() {
		return this.open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getTitre() {
		return this.titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Set<Description> getDescriptions() {
		return this.descriptions;
	}

	public void setDescriptions(Set<Description> descriptions) {
		this.descriptions = descriptions;
	}

	public Set<Paiement> getPaiements() {
		return this.paiements;
	}

	public void setPaiements(Set<Paiement> paiements) {
		this.paiements = paiements;
	}

}