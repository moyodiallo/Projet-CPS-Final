package cps.mouradalpha.ports;

import cps.mouradalpha.components.Broker;
import cps.mouradalpha.interfaces.ManagementCI;
import cps.mouradalpha.interfaces.MessageFilterI;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.AbstractInboundPort;

/**
 * The class <code>InBoundPortManagement</code> defines the inbound port
 * exposing the interface <code>ManagementCI</code> for components of
 * type <code>Broker</code>.
 * 
 * @author Alpha Issiaga DIALLO / Mourad TOUATI
 *
 */
public class InBoundPortManagement extends AbstractInboundPort 
implements ManagementCI {

    /**   
     *
     */
    private static final long serialVersionUID = 1L;
    protected final int	readExecutorIndex ;
    protected final int	writeExecutorIndex ;
    
    /**
     * create the port under some given URI and for a given owner, we need also to specify the index 
     * for the read executor service and for the write executor.
     * @param uri the uri 
     * @param owner the owner
     * @param readExecutorIndex the read executor index
     * @param writeExecutorIndex the write executor index
     * @throws Exception
     */
    public InBoundPortManagement(String uri, ComponentI owner,int readExecutorIndex, int writeExecutorIndex) 
    throws Exception {
        super(uri, ManagementCI.class, owner);
        this.readExecutorIndex = readExecutorIndex ; 
        this.writeExecutorIndex = writeExecutorIndex;
        assert uri != null && owner instanceof Broker;
    }
    /**
     * calls the service method (subscribe) of the owner component (Broker) by executing a task
	 * using one of the component's threads.
	 * @param topic the topic 
	 * @param inboundPortURI the inboundPortURI
     */
    @Override
    public void subscribe(String topic, String inboundPortURI) throws Exception{

        this.getOwner().handleRequestSync(writeExecutorIndex,
                new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						 ((ManagementCI)this.getServiceProviderReference()).
                            subscribe(topic,inboundPortURI) ;
                            return null;
					}
				});
    }
    /**
     * calls the service method (subscribe) of the owner component (Broker) by executing a task
	 * using one of the component's threads.
	 * @param topics the topics 
	 * @param inboundPortURI the inboundPortURI
     */
    @Override
    public void subscribe(String[] topics, String inboundPortURI) throws Exception {
        this.getOwner().handleRequestSync(writeExecutorIndex,
            new AbstractComponent.AbstractService<Void>() {
                @Override
                public Void call() throws Exception {
                     ((ManagementCI)this.getServiceProviderReference()).
                        subscribe(topics,inboundPortURI) ;
                        return null;
                }
            });
    }
    /**
     * calls the service method (subscribe) of the owner component (Broker) by executing a task
	 * using one of the component's threads.
	 * @param topic the topic 
	 * @param filter the filter
	 * @param inboundPortURI the inboundPortURI
     */
    @Override
    public void subscribe(String topic, MessageFilterI filter, String inboundPortURI) 
    throws Exception {
        
        this.getOwner().handleRequestSync(writeExecutorIndex,
            new AbstractComponent.AbstractService<Void>() {
                @Override
                public Void call() throws Exception {
                     ((ManagementCI)this.getServiceProviderReference()).
                        subscribe(topic,filter,inboundPortURI) ;
                        return null;
                }
            });

    }
    /**
     * calls the service method (modifyFilter) of the owner component (Broker) by executing a task
	 * using one of the component's threads.
	 * @param topic the topic 
	 * @param newFilter the new filter
	 * @param inboundPortURI the inboundPortURI
     */
    @Override
    public void modifyFilter(String topic, MessageFilterI newFilter, 
        String inboundPortURI) throws Exception {

            this.getOwner().handleRequestAsync(writeExecutorIndex,
                new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						 ((ManagementCI)this.getServiceProviderReference()).
                            modifyFilter(topic, newFilter, inboundPortURI) ;
                            return null;
					}
				});
    }
    /**
     * calls the service method (unsubscribe) of the owner component (Broker) by executing a task
	 * using one of the component's threads.
	 * @param topic the topic 
	 * @param inboundPortURI the inboundPortURI
     */
    @Override
    public void unsubscribe(String topic, String inboundPortURI) 
    throws Exception {
      
        this.getOwner().handleRequestAsync(writeExecutorIndex,
            new AbstractComponent.AbstractService<Void>() {
                @Override
                public Void call() throws Exception {
                     ((ManagementCI)this.getServiceProviderReference()).
                        unsubscribe(topic,inboundPortURI) ;
                        return null;
                }
            });
    }
    
    /**
     * calls the service method (createTopic) of the owner component (Broker) by executing a task
	 * using one of the component's threads.
	 * @param topic the topic 
	 * @param inboundPortURI the inboundPortURI
     */
    @Override
    public void createTopic(String topic) throws Exception {
       
        this.getOwner().handleRequestSync(writeExecutorIndex,
                new AbstractComponent.AbstractService<Void>() {
					@Override
					public Void call() throws Exception {
						 ((ManagementCI)this.getServiceProviderReference()).
                            createTopic(topic);
                            return null;
					}
                });      
    }
    /**
     * calls the service method (createTopic) of the owner component (Broker) by executing a task
	 * using one of the component's threads.
	 * @param topics the topics 
     */
    @Override
    public void createTopic(String[] topics) throws Exception {
        
        this.getOwner().handleRequestSync(writeExecutorIndex,
            new AbstractComponent.AbstractService<Void>() {
                @Override
                public Void call() throws Exception {
                     ((ManagementCI)this.getServiceProviderReference()).
                        createTopic(topics) ;
                        return null;
                }
            });
    }
    /**
     * calls the service method (destroyTopic) of the owner component (Broker) by executing a task
	 * using one of the component's threads.
	 * @param topics the topics 
     */
    @Override
    public void destroyTopic(String topic) throws Exception {
        this.getOwner().handleRequestAsync(writeExecutorIndex,
            new AbstractComponent.AbstractService<Void>() {
                @Override
                public Void call() throws Exception {
                     ((ManagementCI)this.getServiceProviderReference()).
                        destroyTopic(topic) ;
                        return null;
                }
            });
    }
    /**
     * calls the service method (isTopic) of the owner component (Broker) by executing a task
	 * using one of the component's threads.
	 * @param topic the topic
	 * @return true if the topic exist, false otherwise
     */
    @Override
    public boolean isTopic(String topic) throws Exception{
    	
        return this.getOwner().handleRequestSync(readExecutorIndex,
            new AbstractComponent.AbstractService<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return  ((ManagementCI)this.getServiceProviderReference()).
                        isTopic(topic) ;
                    
                }
            });
    }
    /**
     * calls the service method (getTopics) of the owner component (Broker) by executing a task
	 * using one of the component's threads.
	 * @return list of URI corresponding to the topics that exists in the Broker

     */
    @Override
    public String[] getTopics() throws Exception {
        return this.getOwner().handleRequestSync(readExecutorIndex,
            new AbstractComponent.AbstractService<String[]>() {
                @Override
                public String[] call() throws Exception {
                    return ((ManagementCI)this.getServiceProviderReference()).
                        getTopics() ;
                }
            });
    }
    /**
     * calls the service method (getPublicationPortURI) of the owner component (Broker) by executing a task
	 * using one of the component's threads.
	 * 
     */
    @Override
    public String getPublicationPortURI() throws Exception {
        return this.getOwner().handleRequestSync(readExecutorIndex,
            new AbstractComponent.AbstractService<String>() {
                @Override
                public String call() throws Exception {
                    return ((ManagementCI)this.getServiceProviderReference()).
                        getPublicationPortURI();
                }
            });
    }
}