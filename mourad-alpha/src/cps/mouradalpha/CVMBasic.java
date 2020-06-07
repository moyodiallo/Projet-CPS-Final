package cps.mouradalpha;

import cps.mouradalpha.components.Broker;
import cps.mouradalpha.components.Publisher;
import cps.mouradalpha.components.Subscriber;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;
/**
 * The class <code>CVMBasic</code> implements the single JVM assembly for our project 
 * We have a <code>Publisher</code>, a <code>Subscriber</code> and a <code>Broker</code> 
 * in the middle providing its services for them.
 * 
 * @author Alpha Issiaga DIALLO / Alpha Issiaga DIALLO
 *
 */
public class CVMBasic extends AbstractCVM {

    protected static final String inBoundPortManagement = "inBoundPortManagementURI";
    
    protected static final String uriPrefixBroker     = "brokerURI";
    protected static final String uriPrefixPublisher  = "publisherURI";
    protected static final String uriPrefixSubscriber = "subscriberURI";

    String brokerURI; 
    String publisherURI;
    String subscriberURI;

    public CVMBasic() throws Exception {
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

        
        this.subscriberURI = AbstractComponent.createComponent(
            Subscriber.class.getCanonicalName(),
            new Object[]{uriPrefixSubscriber,
                        inBoundPortManagement});
        assert this.isDeployedComponent(subscriberURI);
    
        this.publisherURI = AbstractComponent.createComponent(
            Publisher.class.getCanonicalName(),
            new Object[]{uriPrefixPublisher,
                inBoundPortManagement
            });

        assert this.isDeployedComponent(publisherURI);

        this.toggleTracing(this.publisherURI);
        this.toggleLogging(this.publisherURI);

        this.toggleTracing(this.brokerURI);
        this.toggleLogging(this.brokerURI);

        this.toggleTracing(this.subscriberURI);
        this.toggleLogging(this.subscriberURI);
        
        assert this.brokerURI != null : 
            new  Exception("The Broker URI is null!") ;

        assert this.publisherURI != null:
            new Exception("The Publisher URI is null!") ;

        assert this.subscriberURI != null:
            new Exception("The subscriber URI is null");

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
			CVMBasic a = new CVMBasic() ;
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