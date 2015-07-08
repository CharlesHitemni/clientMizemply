package fr.primaveraServeur.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the paiement database table.
 * 
 */

@Entity
@Table(name="paiement")
@NamedQuery(name="Paiement.findAll", query="SELECT p FROM Paiement p")
public class Paiement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int idPaiement;

	@Column(length=50)
	private String date;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="iddemande", nullable=true)
	private Demande demande;

	private float montant;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="iduser", nullable=false)
	private User user;

	public Paiement() {
	}

	public int getIdPaiement() {
		return this.idPaiement;
	}

	public void setIdPaiement(int idPaiement) {
		this.idPaiement = idPaiement;
	}

	public String getDate() {
		
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public float getMontant() {
		return this.montant;
	}

	public void setMontant(float montant) {
		this.montant = montant;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}