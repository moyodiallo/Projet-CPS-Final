package cps.mouradalpha.ports;

import cps.mouradalpha.interfaces.MessageI;
import cps.mouradalpha.interfaces.ReceptionCI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
/**
 * The class <code>InBoundPortReception</code> defines the inbound port
 * exposing the interface <code>ReceptionCI</code> for components of
 * type <code>Subscriber</code>.
 * 
 * @author Alpha Issiaga DIALLO / Mourad TOUATI
 *
 */
public class InBoundPortReception extends AbstractInboundPort 
implements ReceptionCI {

    /**
     *  
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * create the port under some given URI and for a given owner, we need also to specify the index 
     * for the read executor service and for the write executor.
     * @param uri
     * @param owner
     * @throws Exception
     */
    public InBoundPortReception(
        String uri, 
        ComponentI owner) 
        throws Exception {
        super(uri, ReceptionCI.class, owner);
        assert uri != null;
    }
    /**
     * calls the service method (acceptMessage) of the owner component (Subscriber) by executing a task
	 * using one of the component's threads.
	 * @param m the message 
     */
    @Override
    public void acceptMessage(MessageI m) throws Exception{
        this.getOwner().handleRequestAsync(
                new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						 ((ReceptionCI)this.getServiceProviderReference()).
                            acceptMessage(m) ;
                            return null;
					}
				});
    }
    /**
     * calls the service method (publish) of the owner component (Subscriber) by executing a task
	 * using one of the component's threads.
	 * @param ms the messages 
     */
    @Override
    public void acceptMessages(MessageI[] ms) throws Exception{
        this.getOwner().handleRequestAsync(
                new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						 ((ReceptionCI)this.getServiceProviderReference()).
                            acceptMessages(ms) ;
                            return null;
					}
				});
    }
}
