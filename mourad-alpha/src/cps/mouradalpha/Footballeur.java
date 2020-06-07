package cps.mouradalpha;

import java.io.Serializable;

/**
 * The class <code>Footballeur</code> is used for testing our project it has
 * fields with different types
 * 
 * @author Alpha Issiaga DIALLO / Mourad TOUATI
 *
 */
public class Footballeur implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * player name
	 */
	String nom;
	/**
	 * player club
	 */
	String club;
	/**
	 * player speed
	 */
	Float vitesse;
	/**
	 * player height
	 */
	Double taille;
	/**
	 * player played time
	 */
	Long tempsDeJeu;
	/**
	 * goals scored
	 */
	Integer nombreDeButMarque;
	/**
	 * the message
	 */
	String message;

	/**
	 *  default constructor
	 * @param nom name of player 
	 * @param club club of player 
	 * @param vitesse speed of player 
	 * @param taille height of player 
	 * @param tempsDeJeu played time of player 
	 * @param nombreDeButMarque number goals scored
	 * @param message the message
	 */
	public Footballeur(String nom, String club, Float vitesse, Double taille, Long tempsDeJeu,
			Integer nombreDeButMarque, String message) {
		this.nom = nom;
		this.club = club;
		this.vitesse = vitesse;
		this.taille = taille;
		this.tempsDeJeu = tempsDeJeu;
		this.nombreDeButMarque = nombreDeButMarque;
		this.message = message;
	}
	/**
	 * 
	 * @return the name
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * set the name
	 * @param nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	/**
	 * 
	 * @return the club
	 */
	public String getClub() {
		return club;
	}
	/**
	 * set the club
	 * @param club
	 */
	public void setClub(String club) {
		this.club = club;
	}
	/**
	 * 
	 * @return the speed 
	 */
	public Float getVitesse() {
		return vitesse;
	}
	/**
	 * set the speed
	 * @param vitesse
	 */
	public void setVitesse(Float vitesse) {
		this.vitesse = vitesse;
	}
	/**
	 * 
	 * @return the height
	 */
	public Double getTaille() {
		return taille;
	}
	/**
	 * set the height
	 * @param taille
	 */
	public void setTaille(Double taille) {
		this.taille = taille;
	}
	/**
	 * 
	 * @return played time
	 */
	public Long getTempsDeJeu() {
		return tempsDeJeu;
	}
	/**
	 * set the played time
	 * @param tempsDeJeu
	 */
	public void setTempsDeJeu(Long tempsDeJeu) {
		this.tempsDeJeu = tempsDeJeu;
	}
	/**
	 * 
	 * @return number of goals scored
	 */
	public Integer getNombreDeButMarque() {
		return nombreDeButMarque;
	}
	/**
	 * set the number of goals scored
	 * @param nombreDeButMarque
	 */
	public void setNombreDeButMarque(Integer nombreDeButMarque) {
		this.nombreDeButMarque = nombreDeButMarque;
	}
	/**
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * set the message
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Footballeur(nom, club, vitesse, taille, tempsDeJeu, nombreDeButMarque, message);
    }
}