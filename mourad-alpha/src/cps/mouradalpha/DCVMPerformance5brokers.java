package cps.mouradalpha;


import cps.mouradalpha.components.Broker;
import cps.mouradalpha.components.PublisherPerformance;
import cps.mouradalpha.components.SubscriberPerformance;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractDistributedCVM;
/**
 * The class <code>DCVMPerformance5brokers</code> implements the multi-JVM
 * assembly for our project, and as its name implies in this example we have five JVMs, each one of them will have 
 * one broker and a set of Subscribers and Publisher. 
 * Notice we use <code>PublisherPerformance</code> and <code>SubscriberPerformance</code> in order to perfom the tests. 
 * 
 * @author Alpha Issiaga DIALLO / Mourad TOUATI
 *
 */
public class DCVMPerformance5brokers extends AbstractDistributedCVM {
    protected static final String inBoundPortManagement = "inBoundPortManagementURI";
    protected static final String group_publisher = "group-pubs-";
    protected static final String group_subscriber = "group-subs-";

    protected static final String jvm_uriPrefixBroker = "brokerURI";

    protected static final String uriPrefixPublisher = "publisherURI";
    protected static final String uriPrefixSubscriber = "subscriberURI";

    String brokerURI;

    String publisherURI;
    String subscriberURI;

    protected static final int NB_BROKER                = 5;
    protected static final int NB_PUBLISHER_PER_BROKER  = 30;
    protected static final int NB_SUBSCRIBER_PER_BROKER = 30; 

    protected static final long CYCLE_LIFE = 35000L;

    public DCVMPerformance5brokers(String[] args, int xLayout, int yLayout) throws Exception {
        super(args, xLayout, yLayout);
    }

    /**
     * do some initialisation before anything can go on.
     * 
     * <p>
     * <strong>Contract</strong>
     * </p>
     * 
     * <pre>
     * pre	true				// no more preconditions.
     * post	true				// no more postconditions.
     * </pre>
     * 
     * @see fr.sorbonne_u.components.cvm.AbstractDistributedCVM#initialise()
     */
    @Override
    public void initialise() throws Exception {
        // debugging mode configuration; comment and uncomment the line to see
        // the difference
        // AbstractCVM.DEBUG_MODE.add(CVMDebugModes.PUBLIHSING) ;
        // AbstractCVM.DEBUG_MODE.add(CVMDebugModes.CONNECTING) ;
        // AbstractCVM.DEBUG_MODE.add(CVMDebugModes.COMPONENT_DEPLOYMENT) ;

        super.initialise();
        // any other application-specific initialisation must be put here

    }

    /**
     * instantiate components and publish their ports.
     * 
     * <p>
     * <strong>Contract</strong>
     * </p>
     * 
     * <pre>
     * pre	true				// no more preconditions.
     * post	true				// no more postconditions.
     * </pre>
     * 
     * @see fr.sorbonne_u.components.cvm.AbstractDistributedCVM#instantiateAndPublish()
     */
    @Override
    public void instantiateAndPublish() throws Exception {
        String brokers[] = new String[NB_BROKER];
        String portMan[] = new String[NB_BROKER];

        for (int i = 0; i < brokers.length; i++) {
            brokers[i] = DCVMPerformance3brokers.jvm_uriPrefixBroker +"-"+ i;
            portMan[i] = DCVMPerformance3brokers.inBoundPortManagement + i;
        }

        boolean brokerCreated = false;
        for (int i = 0; i < brokers.length; i++) {

            if (thisJVMURI.equals(brokers[i])) {

                // creating a broker
                this.brokerURI = AbstractComponent.createComponent(Broker.class.getCanonicalName(),
                        new Object[] { brokers[i], portMan[i], brokers});

                assert this.isDeployedComponent(brokerURI);

                // tracing operations
                //this.toggleTracing(this.brokerURI);
                this.toggleLogging(this.brokerURI);
                assert brokers[i] != null : new Exception("The Broker1 URI is null");

                brokerCreated = true;

                for (int j = 0; j < NB_PUBLISHER_PER_BROKER; j++) {

                    // create Publisher
                    this.publisherURI = AbstractComponent.createComponent(
                        PublisherPerformance.class.getCanonicalName(),
                            new Object[] {
                                    DCVMPerformance3brokers.uriPrefixPublisher + "-" + i + "-" + j,
                                    portMan[i]});

                    //this.toggleTracing(this.publisherURI);
                    this.toggleLogging(this.publisherURI);

                }

                for (int j = 0; j < NB_SUBSCRIBER_PER_BROKER; j++) {

                    this.subscriberURI = AbstractComponent.createComponent(
                            SubscriberPerformance.class.getCanonicalName(),
                            new Object[] {
                                    DCVMPerformance3brokers.uriPrefixSubscriber + "-" + i + "-" + j,
                                     portMan[i] });
                    assert this.isDeployedComponent(subscriberURI);

                    //this.toggleTracing(this.subscriberURI);
                    this.toggleLogging(this.subscriberURI);

                }
            }
        }

        if(!brokerCreated) {
            System.out.println("Unknown JVM URI... " + thisJVMURI) ;
        }

        super.instantiateAndPublish();
    }

    /**
     * interconnect the components.
     * 
     * <p>
     * <strong>Contract</strong>
     * </p>
     * 
     * <pre>
     * pre	true				// no more preconditions.
     * post	true				// no more postconditions.
     * </pre>
     * 
     * @see fr.sorbonne_u.components.cvm.AbstractDistributedCVM#interconnect()
     */
    @Override
    public void interconnect() throws Exception {
        assert this.isIntantiatedAndPublished();
        super.interconnect();
    }

    /**
     * @see fr.sorbonne_u.components.cvm.AbstractDistributedCVM#finalise()
     */
    @Override
    public void finalise() throws Exception {
        super.finalise();
    }

    /**
     * @see fr.sorbonne_u.components.cvm.AbstractDistributedCVM#shutdown()
     */
    @Override
    public void shutdown() throws Exception {
        System.out.println("CYCLE_LIFE :"+CYCLE_LIFE+" JVM: "+thisJVMURI);
        super.shutdown();
    }

    public static void main(String[] args) {
        try {
            DCVMPerformance5brokers da = new DCVMPerformance5brokers(args, 2, 5);
			da.startStandardLifeCycle(CYCLE_LIFE) ;
			Thread.sleep(10000L) ;
			System.exit(0) ;
		} catch (Exception e) {
			throw new RuntimeException(e) ;
		}
	}
}