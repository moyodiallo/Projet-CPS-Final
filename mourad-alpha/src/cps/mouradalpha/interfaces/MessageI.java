package cps.mouradalpha.interfaces;

import java.io.Serializable;

import cps.mouradalpha.Proprieties;
import cps.mouradalpha.TimeStamp;
/**
 * The interface <code>MessageI</code> represent the specification the message used in this project, it contains the methods for handling Message.
 * @author Mourad TOUATI / Alpha Issiaga DIALLO
 *
 */
public interface MessageI  extends Serializable{
	/**
	 * 
	 * @return the URI of the message which is a unique identifier of a specific message
	 */
	public String getURI();
	/**
	 * 
	 * @return the timeStamp of the message
	 */
	public TimeStamp getTimeStamp();
	/**
	 * 
	 * @return the properties of the message
	 */
	public Proprieties getProprieties();
	/**
	 * 
	 * @return the continent of the message which can be any serializable java object
	 */
	Serializable getPlayload();
}
