package cps.mouradalpha.components;

import java.io.Serializable;

import cps.mouradalpha.Proprieties;
import cps.mouradalpha.TimeStamp;
import cps.mouradalpha.interfaces.MessageI;;

/**
 * The class <code>Message</code> implements <code>MessageI</code>, it represents the message structure
 * exchanged with different components in this project.
 *
 * @author Mourad TOUATI / Alpha Issiaga DIALLO
 *
 */
public class Message implements MessageI {

	Serializable message;
	String uriMessage;
	TimeStamp timeStamp;
	Proprieties proprieties;

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * constructor with parameters 
	 * @param uriMessage the uri of the message
	 * @param message the message 
	 * @param timeStamp the timesStamp of the message 
	 * @param proprieties the properties of the message 
	 */
	public Message(String uriMessage, Serializable message, TimeStamp timeStamp, Proprieties proprieties) {

		this.message = message;
		this.uriMessage = uriMessage;
		this.timeStamp = timeStamp;
		this.proprieties = proprieties;

	}
	/**
	 * get the uri of the message
	 */
	@Override
	public String getURI() {
		return this.uriMessage;
	}
	/**
	 * get the timeStamp of the message
	 */
	@Override
	public TimeStamp getTimeStamp() {
		return this.timeStamp;
	}
	/**
	 * get the properties of the message
	 */
	@Override
	public Proprieties getProprieties() {
		return this.proprieties;
	}
	/**
	 * get the message content
	 */
	@Override
	public Serializable getPlayload() {
		return this.message;
	}

	@Override
	public String toString() {
		return "Message [message=" + message + ", uriMessage=" + uriMessage + ", timeStamp=" + timeStamp
				+ ", proprieties=" + proprieties + "]";
	}

}