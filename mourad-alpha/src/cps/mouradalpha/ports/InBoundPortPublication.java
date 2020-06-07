package cps.mouradalpha.ports;

import cps.mouradalpha.components.Broker;
import cps.mouradalpha.interfaces.MessageI;
import cps.mouradalpha.interfaces.PublicationCI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

/**
 * The class <code>InBoundPortPublication</code> defines the inbound port
 * exposing the interface <code>PublicationCI</code> for components of
 * type <code>Broker</code>.
 * 
 * @author Alpha Issiaga DIALLO / Mourad TOUATI
 *
 */
public class InBoundPortPublication extends AbstractInboundPort 
implements PublicationCI {

    /**   
     *
     */
    private static final long serialVersionUID = 1L;
    protected final int	writeExecutorIndex ;
    /**
     * create the port under some given URI and for a given owner, we need also to specify the index 
     * for the read executor service and for the write executor.
     * @param uri the uri 
     * @param owner the owner
     * @param writeExecutorIndex the write executor index
     * @throws Exception
     */
    public InBoundPortPublication(
        String uri,
        ComponentI owner,
        int writeExecutorIndex
    		) 
        throws Exception {
        super(uri, PublicationCI.class, owner);
        this.writeExecutorIndex = writeExecutorIndex;
        assert uri != null && owner instanceof Broker;
    }
    /**
     * calls the service method (publish) of the owner component (Broker) by executing a task
	 * using one of the component's threads.
	 * @param m the message 
	 * @param topic the topic
     */
    @Override
    public void publish(MessageI m, String topic) throws Exception{
        this.getOwner().handleRequestAsync(writeExecutorIndex,
                new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						 ((PublicationCI)this.getServiceProviderReference()).
                            publish(m,topic);
                            return null;
					}
				});
    }
    /**
     * calls the service method (publish) of the owner component (Broker) by executing a task
	 * using one of the component's threads.
	 * @param m the message 
	 * @param topics the topics
     */
    @Override
    public void publish(MessageI m, String[] topics) throws Exception {
        this.getOwner().handleRequestAsync(writeExecutorIndex,
                new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						 ((PublicationCI)this.getServiceProviderReference()).
                            publish(m,topics);
                            return null;
					}
				});
    }
    /**
     * calls the service method (publish) of the owner component (Broker) by executing a task
	 * using one of the component's threads.
	 * @param ms the messages 
	 * @param topic the topic
     */
    @Override
    public void publish(MessageI[] ms, String topic) throws Exception{
        this.getOwner().handleRequestAsync(writeExecutorIndex,
            new AbstractComponent.AbstractService<Void>() {
                @Override
                public Void call() throws Exception {
                     ((PublicationCI)this.getServiceProviderReference()).
                        publish(ms,topic);
                        return null;
                }
            });
    }
    /**
     * calls the service method (publish) of the owner component (Broker) by executing a task
	 * using one of the component's threads.
	 * @param ms the message 
	 * @param topics the topics
     */
    @Override
    public void publish(MessageI[] ms, String[] topics) throws Exception {
        this.getOwner().handleRequestAsync(writeExecutorIndex,
                new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						 ((PublicationCI)this.getServiceProviderReference()).
                            publish(ms,topics);
                            return null;
					}
				});
    }
}