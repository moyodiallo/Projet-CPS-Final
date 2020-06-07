package cps.mouradalpha.ports;

import cps.mouradalpha.components.Broker;
import cps.mouradalpha.interfaces.MessageI;
import cps.mouradalpha.interfaces.NotificationCI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;
/**
 * The class <code>InBoundPortNotification</code> defines the inbound port
 * exposing the interface <code>NotificationCI</code> for components of
 * type <code>Broker</code>.
 * 
 * @author Alpha Issiaga DIALLO / Mourad TOUATI
 *
 */
public class InBoundPortNotification extends AbstractInboundPort 
implements NotificationCI {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    protected final int writeExecutorIndex;

    /**
     * create the port under some given URI and for a given owner, we need also to specify the index 
     * for the read executor service and for the write executor.
     * @param uri the uri 
     * @param owner the owner
     * @param writeExecutorIndex the write executor index
     * @throws Exception
     */
    public InBoundPortNotification(String uri, ComponentI owner, int writeExecutorIndex) throws Exception {

        super(uri,NotificationCI.class,owner);
        this.writeExecutorIndex = writeExecutorIndex;

        assert uri != null && owner instanceof Broker;
    }

    /**
     * calls the service method (notifyTopic) of the owner component (Broker) by executing a task
	 * using one of the component's threads.
	 * @param topic the topic 
     */
    @Override
    public void notifyTopic(String topic) throws Exception {
       
        this.getOwner().handleRequestAsync(writeExecutorIndex,
        new AbstractComponent.AbstractService<Void>(){
            @Override
            public Void call() throws Exception {
                ((NotificationCI)this.getServiceProviderReference()).notifyTopic(topic);
                return null;
            }
        });

    }
    /**
     * calls the service method (notifyPublish) of the owner component (Broker) by executing a task
	 * using one of the component's threads.
	 * @param m the message 
	 * @param topic the topic
     */
    @Override
    public void notifyPublish(MessageI m, String topic) throws Exception {
        this.getOwner().handleRequestAsync(writeExecutorIndex,
            new AbstractComponent.AbstractService<Void>(){
                @Override
                public Void call() throws Exception {
                    ((NotificationCI)this.getServiceProviderReference()).notifyPublish(m, topic);
                    return null;
                }
            }
        );
    }
 
    /**
     * calls the service method (notifyDestroyTopic) of the owner component (Broker) by executing a task
	 * using one of the component's threads.
	 * @param topic the topic
     */
    @Override
    public void notifyDestroyTopic(String topic) throws Exception {
        this.getOwner().handleRequestAsync(writeExecutorIndex,
            new AbstractComponent.AbstractService<Void>(){
                @Override
                public Void call() throws Exception {
                    ((NotificationCI)this.getServiceProviderReference()).notifyDestroyTopic(topic);
                    return null;
                }
            }
        );
    }

    /**
     * calls the service method (notifyPublish) of the owner component (Broker) by executing a task
	 * using one of the component's threads.
	 * @param ms the messages 
	 * @param topic the topic
     */
    @Override
    public void notifyPublish(MessageI[] ms, String topic) throws Exception {
        this.getOwner().handleRequestAsync(writeExecutorIndex,
            new AbstractComponent.AbstractService<Void>(){
                @Override
                public Void call() throws Exception {
                    ((NotificationCI)this.getServiceProviderReference()).notifyPublish(ms, topic);
                    return null;
                }
            }
        );
    }
}