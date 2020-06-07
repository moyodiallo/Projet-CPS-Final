package cps.mouradalpha;

import cps.mouradalpha.components.Broker;
import cps.mouradalpha.components.PublisherWithPlugin;
import cps.mouradalpha.components.SubscriberWithPlugin;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;

/**
 * The class <code>CVMBasic2Broker</code> implements the single JVM for our
 * project, the idea is to have two brokers, one publisher and one subscriber.
 * The subscriber and the Publisher are linked to different Broker and of course
 * the brokers are linked together.
 * 
 * The main goal of this class is to test if a subscriber can subscribe to a
 * topic and get notified when a message is published by publisher even though
 * they are linked to different brokers, in other word test if the two broker
 * communicate together in order to transfer information
 * 
 * @author Mourad TOUATI / Alpha Issiaga DIALLO
 *
 */
public class CVMBasic2Broker extends AbstractCVM {

	protected static final String inBoundPortManagement1 = "inBoundPortManagementURI1";
	protected static final String inBoundPortManagement2 = "inBoundPortManagementURI2";

	protected static final String uriPrefixBroker1 = "brokerURI1";
	protected static final String uriPrefixBroker2 = "brokerURI2";

	protected static final String uriPrefixPublisher = "publisherURI";
	protected static final String uriPrefixSubscriber = "subscriberURI";

	String brokerURI1;
	String brokerURI2;

	String publisherURI;
	String subscriberURI;

	public CVMBasic2Broker() throws Exception {
		super();
	}

	/**
	 * instantiate the components, publish their port and interconnect them.
	 * 
	 * <p>
	 * <strong>Contract</strong>
	 * </p>
	 * 
	 * <pre>
	 * pre	!this.deploymentDone()
	 * post	this.deploymentDone()
	 * </pre>
	 * 
	 * @see fr.sorbonne_u.components.cvm.AbstractCVM#deploy()
	 */
	@Override
	public void deploy() throws Exception {

		assert !this.deploymentDone();

		// --------------------------------------------------------------------
		// Creation phase
		// --------------------------------------------------------------------

		String brokers[] = { uriPrefixBroker1, uriPrefixBroker2 };

		this.brokerURI1 = AbstractComponent.createComponent(Broker.class.getCanonicalName(),
				new Object[] { uriPrefixBroker1, inBoundPortManagement1, brokers });
		assert this.isDeployedComponent(brokerURI1);

		this.brokerURI2 = AbstractComponent.createComponent(Broker.class.getCanonicalName(),
				new Object[] { uriPrefixBroker2, inBoundPortManagement2, brokers });
		assert this.isDeployedComponent(brokerURI2);

		this.subscriberURI = AbstractComponent.createComponent(SubscriberWithPlugin.class.getCanonicalName(),
				new Object[] { uriPrefixSubscriber, inBoundPortManagement1 });
		assert this.isDeployedComponent(subscriberURI);

		this.publisherURI = AbstractComponent.createComponent(PublisherWithPlugin.class.getCanonicalName(),
				new Object[] { uriPrefixPublisher, inBoundPortManagement2 });

		assert this.isDeployedComponent(publisherURI);

		this.toggleTracing(this.publisherURI);
		this.toggleLogging(this.publisherURI);

		this.toggleTracing(this.brokerURI1);
		this.toggleLogging(this.brokerURI1);

		this.toggleTracing(this.brokerURI2);
		this.toggleLogging(this.brokerURI2);

		this.toggleTracing(this.subscriberURI);
		this.toggleLogging(this.subscriberURI);

		assert this.brokerURI1 != null : new Exception("The Broker1 URI is null!");

		assert this.brokerURI2 != null : new Exception("The Broker2 URI is null!");

		assert this.publisherURI != null : new Exception("The Publisher URI is null!");

		assert this.subscriberURI != null : new Exception("The subscriber URI is null");

		// --------------------------------------------------------------------
		// Connection phase
		// --------------------------------------------------------------------

		// The connections did by components themself when they start

		// --------------------------------------------------------------------
		// Deployment done
		// --------------------------------------------------------------------
		super.deploy();
		assert this.deploymentDone();
	}

	/**
	 * @see fr.sorbonne_u.components.cvm.AbstractCVM#finalise()
	 */
	@Override
	public void finalise() throws Exception {
		// Port disconnections can be done here for static architectures
		// otherwise, they can be done in the finalise methods of components.
		super.finalise();
	}

	/**
	 * disconnect the components and then call the base shutdown method.
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
	 * @see fr.sorbonne_u.components.cvm.AbstractCVM#shutdown()
	 */
	@Override
	public void shutdown() throws Exception {
		assert this.allFinalised();
		// any disconnection not done yet can be performed here
		super.shutdown();
	}

	public static void main(String[] args) {
		try {
			// Create an instance of the defined component virtual machine.
			CVMBasic2Broker a = new CVMBasic2Broker();
			// Execute the application.
			a.startStandardLifeCycle(20000L);
			// Give some time to see the traces (convenience).
			Thread.sleep(5000L);
			// Simplifies the termination (termination has yet to be treated
			// properly in BCM).
			System.exit(0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}