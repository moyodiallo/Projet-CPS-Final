package cps.mouradalpha;

import cps.mouradalpha.components.Broker;
import cps.mouradalpha.components.TestPublisherSubscriber;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;
/**
 * 
 * @author Alpha Issiaga DIALLO / Mourad TOUATI
 *
 */
public class TestVMSystem extends AbstractCVM {

    protected static final String inBoundPortManagement = "inBoundPortManagementURI";
    
    protected static final String uriPrefixBroker     = "brokerURI";
    protected static final String uriPrefixPublisher  = "publisherURI";
    protected static final String uriPrefixSubscriber = "subscriberURI";

    protected static final String uriTestSubscriberPublisher  = "testSubscriberPublisherURI";

    String brokerURI; 
    String publisherURI;
    String subscriberURI;

    String testSubscriberPublisher;

    public TestVMSystem() throws Exception {
        super();
    }

    /**
	 * instantiate the components, publish their port and interconnect them.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	!this.deploymentDone()
	 * post	this.deploymentDone()
	 * </pre>
	 * 
	 * @see fr.sorbonne_u.components.cvm.AbstractCVM#deploy()
	 */
	@Override
    public void deploy() throws Exception{
        assert !this.deploymentDone();

        // --------------------------------------------------------------------
		// Creation phase
        // --------------------------------------------------------------------
       
        this.brokerURI = AbstractComponent.createComponent(
            Broker.class.getCanonicalName(), 
            new Object[]{
                uriPrefixBroker,
                inBoundPortManagement,
                new String[0]
            });
        assert this.isDeployedComponent(brokerURI);


        this.testSubscriberPublisher = AbstractComponent.createComponent(
            TestPublisherSubscriber.class.getCanonicalName(), 
            new Object[]{
                uriTestSubscriberPublisher,
                inBoundPortManagement,
                uriPrefixPublisher,
                uriPrefixSubscriber
            });
        assert this.isDeployedComponent(testSubscriberPublisher);


        this.toggleTracing(this.brokerURI);
        this.toggleLogging(this.brokerURI);

        this.toggleTracing(this.testSubscriberPublisher);
        this.toggleLogging(this.testSubscriberPublisher);
        
        assert this.brokerURI != null : 
            new  Exception("The Broker URI is null!") ;
        
        assert this.testSubscriberPublisher != null:
            new Exception("The testSubscriberPublisher URI is null!") ;

        // --------------------------------------------------------------------
		// Connection phase
        // --------------------------------------------------------------------
        
        // The connections did by components themself when they start
        

        // --------------------------------------------------------------------
		// Deployment done
		// --------------------------------------------------------------------
		super.deploy();
		assert	this.deploymentDone() ;
    }

    /**
	 * @see fr.sorbonne_u.components.cvm.AbstractCVM#finalise()
	 */
	@Override
	public void finalise() throws Exception
	{
		// Port disconnections can be done here for static architectures
        // otherwise, they can be done in the finalise methods of components.
		super.finalise();
    }
    
    /**
	 * disconnect the components and then call the base shutdown method.
	 * 
	 * <p><strong>Contract</strong></p>
	 * 
	 * <pre>
	 * pre	true				// no more preconditions.
	 * post	true				// no more postconditions.
	 * </pre>
	 * 
	 * @see fr.sorbonne_u.components.cvm.AbstractCVM#shutdown()
	 */
	@Override
	public void				shutdown() throws Exception
	{
		assert	this.allFinalised() ;
		// any disconnection not done yet can be performed here
		super.shutdown();
	}

	public static void		main(String[] args)
	{
		try {
			// Create an instance of the defined component virtual machine.
			TestVMSystem a = new TestVMSystem() ;
			// Execute the application.
			a.startStandardLifeCycle(20000L) ;
			// Give some time to see the traces (convenience).
			Thread.sleep(5000L) ;
			// Simplifies the termination (termination has yet to be treated
			// properly in BCM).
			System.exit(0) ;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}