package cps.mouradalpha.ports;

import cps.mouradalpha.interfaces.MessageI;
import cps.mouradalpha.interfaces.ReceptionCI;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractOutboundPort;

/**
 * The class <code>OutboundPortReception</code> implements the outbound port
 * of a component that requires Reception service through the
 * <code>ReceptionCI</code> interface.
 * 
 * @author Alpha Issiaga DIALLO / Mourad TOUATI
 *
 */
public class OutboundPortReception extends AbstractOutboundPort implements ReceptionCI {

	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the port with the given owner
	 * 
	 * @param owner the owner of the port
	 * @throws Exception
	 */
	public OutboundPortReception(ComponentI owner) throws Exception {
		super(ReceptionCI.class, owner);
	}

	/**
	 * create the port with the given URI and the given owner.
	 * 
	 * @param uri the uri of the port
	 * @param owner the owner the port
	 * @throws Exception
	 */
	public OutboundPortReception(String uri, ComponentI owner) throws Exception {
		super(uri, ReceptionCI.class, owner);
	}

	/** Notify the Subscriber with the message 
	 * @param m the message 
	 */
	@Override
	public void acceptMessage(MessageI m) throws Exception {
		((ReceptionCI) this.connector).acceptMessage(m);
	}
	
	/** Notify the Subscriber with the messages 
	 * @param ms the messages 
	 */
	@Override
	public void acceptMessages(MessageI[] ms) throws Exception {
		((ReceptionCI) this.connector).acceptMessages(ms);
	}
}