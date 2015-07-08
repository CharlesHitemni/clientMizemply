package fr.primaveraServeur.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the description database table.
 * 
 */
@Entity
@Table(name="description")
@NamedQuery(name="Description.findAll", query="SELECT d FROM Description d")
public class Description implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int idDescription;

	@Column(length=200)
	private String description;

	//bi-directional many-to-one association to Demande
	@ManyToOne(fetch= FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="iddemande", nullable=true)
	private Demande demande;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="iduser", nullable=false)
	private User user;

	public Description() {
	}
	
	public Description(User user, String description) {
		this.user=user;
		this.description=description;
	}

	public int getIdDescription() {
		return this.idDescription;
	}

	public void setIdDescription(int idDescription) {
		this.idDescription = idDescription;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}