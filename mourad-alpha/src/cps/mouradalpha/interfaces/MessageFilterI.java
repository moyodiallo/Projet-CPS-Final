package cps.mouradalpha.interfaces;

import java.io.Serializable;

/**
 * The interface <code>MessageFilterI</code> is a FunctionalInterface that can be implementing with a class or a lambda expression, it's used by 
 * a Consumer in order to filter messages published in a specific topic
 * 
 * @author Mourad TOUATI / Alpha Issiaga DIALLO
 *
 */
@FunctionalInterface
public interface MessageFilterI extends Serializable {
	/**
	 * 
	 * @param m The message on which the filter will be applied 
	 * @return true if the message satisfy the condition of the filter, false otherwise 
	 */
	public boolean filtrer(MessageI m) ; 
}
