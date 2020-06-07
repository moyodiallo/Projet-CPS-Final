package cps.mouradalpha;

import cps.mouradalpha.components.Broker;
import cps.mouradalpha.components.PublisherWithPlugin;
import cps.mouradalpha.components.SubscriberWithPlugin;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractDistributedCVM;
/**
 * The class <code>AbstractDistributedCVM</code> implements the multi-JVM assembly for our project, in this example 
 * we have two JVMs, one of them has a broker and a publisher, the other one has a broker and subscriber.
 * Both of brokers are connected so that the subscriber can subscribe for topics created by the publisher and get notify 
 * when there is a message published.
 * 
 * @author Alpha Issiaga DIALLO / Mourad TOUATI
 */
public class DCVMBasic
extends		AbstractDistributedCVM
{
	protected static final String inBoundPortManagement1 = "inBoundPortManagementURI1";
    protected static final String inBoundPortManagement2 = "inBoundPortManagementURI2";
    
    protected static final String jvm_uriPrefixBroker1     = "broker1";
    protected static final String jvm_uriPrefixBroker2     = "broker2";

    protected static final String uriPrefixPublisher  = "publisherURI";
    protected static final String uriPrefixSubscriber = "subscriberURI";

    String brokerURI1;
    String brokerURI2;

    String publisherURI;
    String subscriberURI;

	public				DCVMBasic(String[] args, int xLayout, int yLayout)
	throws Exception
	{
		super(args, xLayout, yLayout);
	}

	/**
	 * do some initialisation before anything can go on.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	true				// no more preconditions.
	 * post	true				// no more postconditions.
	 * </pre>
	 * 
	 * @see fr.sorbonne_u.components.cvm.AbstractDistributedCVM#initialise()
	 */
	@Override
	public void			initialise() throws Exception
	{
		// debugging mode configuration; comment and uncomment the line to see
		// the difference
//		AbstractCVM.DEBUG_MODE.add(CVMDebugModes.PUBLIHSING) ;
//		AbstractCVM.DEBUG_MODE.add(CVMDebugModes.CONNECTING) ;
//		AbstractCVM.DEBUG_MODE.add(CVMDebugModes.COMPONENT_DEPLOYMENT) ;

		super.initialise() ;
		// any other application-specific initialisation must be put here

	}

	/**
	 * instantiate components and publish their ports.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	true				// no more preconditions.
	 * post	true				// no more postconditions.
	 * </pre>
	 * 
	 * @see fr.sorbonne_u.components.cvm.AbstractDistributedCVM#instantiateAndPublish()
	 */
	@Override
	public void			instantiateAndPublish() throws Exception
	{
        String brokers[] = {jvm_uriPrefixBroker1,jvm_uriPrefixBroker2};

		if (thisJVMURI.equals(jvm_uriPrefixBroker1)) {

            //create broker1
            this.brokerURI1 = AbstractComponent.createComponent(
            Broker.class.getCanonicalName(), 
            new Object[]{
                jvm_uriPrefixBroker1,
                inBoundPortManagement1,
                brokers
            });
            assert this.isDeployedComponent(brokerURI1);

			// tracing operations
			this.toggleTracing(this.brokerURI1) ;
			this.toggleLogging(this.brokerURI1) ;
            assert	this.brokerURI1 != null && this.brokerURI2 == null: 
                new Exception("The Broker1 URI is null");

            //create Publisher
            this.publisherURI = AbstractComponent.createComponent(
                PublisherWithPlugin.class.getCanonicalName(),
                new Object[]{uriPrefixPublisher,
                    inBoundPortManagement2
            });

            this.toggleTracing(this.publisherURI);
            this.toggleLogging(this.publisherURI);

		} else if (thisJVMURI.equals(jvm_uriPrefixBroker2)) {

			//create broker1
            this.brokerURI2 = AbstractComponent.createComponent(
            Broker.class.getCanonicalName(), 
            new Object[]{
                jvm_uriPrefixBroker2,
                inBoundPortManagement2,
                brokers
            });
            assert this.isDeployedComponent(brokerURI2);

			// tracing operations
			this.toggleTracing(this.brokerURI2) ;
            this.toggleLogging(this.brokerURI2) ;
            
            assert	this.brokerURI2 != null && this.brokerURI1 == null: 
                new Exception("The Broker2 URI is null");
            
                
                this.subscriberURI = AbstractComponent.createComponent(
                SubscriberWithPlugin.class.getCanonicalName(),
                new Object[]{uriPrefixSubscriber,
                            inBoundPortManagement1});
                assert this.isDeployedComponent(subscriberURI);

                this.toggleTracing(this.subscriberURI);
                this.toggleLogging(this.subscriberURI);

		} else {

			System.out.println("Unknown JVM URI... " + thisJVMURI) ;

		}

		super.instantiateAndPublish();
	}

	/**
	 * interconnect the components.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	true				// no more preconditions.
	 * post	true				// no more postconditions.
	 * </pre>
	 * 
	 * @see fr.sorbonne_u.components.cvm.AbstractDistributedCVM#interconnect()
	 */
	@Override
	public void			interconnect() throws Exception
	{
		assert	this.isIntantiatedAndPublished();
		super.interconnect();
	}

	/**
	 * @see fr.sorbonne_u.components.cvm.AbstractDistributedCVM#finalise()
	 */
	@Override
	public void			finalise() throws Exception
	{
		super.finalise() ;
	}

	/**
	 * @see fr.sorbonne_u.components.cvm.AbstractDistributedCVM#shutdown()
	 */
	@Override
	public void			shutdown() throws Exception
	{
		super.shutdown();
	}

	public static void	main(String[] args)
	{
		try {
			DCVMBasic da  = new DCVMBasic(args, 2, 5) ;
			da.startStandardLifeCycle(15000L) ;
			Thread.sleep(10000L) ;
			System.exit(0) ;
		} catch (Exception e) {
			throw new RuntimeException(e) ;
		}
	}
}