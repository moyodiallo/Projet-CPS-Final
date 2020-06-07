package cps.mouradalpha.ports;

import cps.mouradalpha.interfaces.MessageI;
import cps.mouradalpha.interfaces.ReceptionCI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.forplugins.AbstractInboundPortForPlugin;
/**
 * The class <code>InBoundPortReceptionPlugin</code> defines the inbound port
 * exposing the interface <code>ReceptionCI</code> for components of
 * type <code>Broker</code>.
 * 
 * @author Alpha Issiaga DIALLO / Mourad TOUATI
 *
 */
public class InBoundPortReceptionPlugin extends AbstractInboundPortForPlugin 
implements ReceptionCI {

    /**
     *   
     */
    private static final long serialVersionUID = 1L;
    protected int executorIndex;
    
    /**
     * create the port under some given URI and for a given owner, we need also to specify the index 
     * for the read executor service and for the write executor.
     * @param uri 
     * @param pluginURI
     * @param owner
     * @throws Exception
     */
    public InBoundPortReceptionPlugin(
        String uri,
        String pluginURI,
        ComponentI owner,int executorIndex) 
        throws Exception { 
        super(uri, ReceptionCI.class, pluginURI, owner);
        this.executorIndex = executorIndex;
        
        assert uri != null && pluginURI != null;
    }
    /**
     * calls the service method (acceptMessage) of the owner component (Subscriber) by executing a task
	 * using one of the component's threads.
	 * @param m the message 
     */

    @Override
    public void acceptMessage(MessageI m) throws Exception {
        this.getOwner().handleRequestAsync(this.executorIndex,
                new AbstractComponent.AbstractService<Void>(this.pluginURI) {
					@Override
					public Void call() throws Exception {
                        ((ReceptionCI)this.getServiceProviderReference())
                            .acceptMessage(m);
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
        this.getOwner().handleRequestAsync(this.executorIndex,
                new AbstractComponent.AbstractService<Void>(this.pluginURI) {
					@Override
					public Void call() throws Exception {
                        ((ReceptionCI)this.getServiceProviderReference())
                            .acceptMessages(ms);
                            return null;
					}
				});
    }
}
