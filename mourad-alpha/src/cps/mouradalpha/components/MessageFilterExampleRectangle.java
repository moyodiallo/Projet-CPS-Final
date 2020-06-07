package cps.mouradalpha.components;

import cps.mouradalpha.interfaces.MessageFilterI;
import cps.mouradalpha.interfaces.MessageI;

/**
 * The class <code>MessageFilterExampleRectangle</code> implements
 * <code>MessageFilterI</code>, it's an example of a filter used our demo.
 *
 * @author Alpha Issiaga DIALLO / Mourad TOUATI
 *
 */
public class MessageFilterExampleRectangle implements MessageFilterI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The filter need to satisfy this condition : 
	 * the message needs to have a property named "hauteur" and it's value = 30 ,
	 * the message needs also to have a property named "largeur " and it's value = 20 
	 */
	@Override
	public boolean filtrer(MessageI m) {

		if (m.getProprieties().getIntProp("hauteur") == null
				|| m.getProprieties().getIntProp("hauteur") != 30)
			return false;

		if (m.getProprieties().getIntProp("largeur") == null
				|| m.getProprieties().getIntProp("largeur") != 20)
			return false;

		return true;
	}
}
