package fr.primaveraServeur.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the demande database table.
 * 
 */
@Entity
@Table(name="demande")
@NamedQuery(name="Demande.findAll", query="SELECT d FROM Demande d")
public class Demande implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_demande", unique=true, nullable=false)
	private int idDemande;

	@Column(length=255)
	private String adresse;

	@Column(length=10)
	private String date;

	@Column(length=45)
	private String description;

	private float montant;

	@Column(name="nombre_paiement")
	private int nombrePaiement;

	@Column(length=45)
	private String titre;
	
	@Column(columnDefinition="TINYINT(1)")
	private boolean close;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="id_role", nullable=false)
	private Role role;

	public Demande() {
	}

	public int getIdDemande() {
		return this.idDemande;
	}

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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getMontant() {
		return this.montant;
	}

	public void setMontant(float montant) {
		this.montant = montant;
	}

	public int getNombrePaiement() {
		return this.nombrePaiement;
	}

	public void setNombrePaiement(int nombrePaiement) {
		this.nombrePaiement = nombrePaiement;
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

	public boolean isClose() {
		return close;
	}

	public void setClose(boolean close) {
		this.close = close;
	}
	
	

}