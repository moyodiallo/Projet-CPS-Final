package cps.mouradalpha;

import cps.mouradalpha.components.Broker;
import cps.mouradalpha.components.PublisherWithPlugin;
import cps.mouradalpha.components.SubscriberWithPlugin;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;

/**
 * The class <code>CVMBasicWithPlugins</code> implements the single JVM assembly
 * for our project, in this example we use plugins for the Publisher and
 * Subscriber with <code>PublisherWithPlugin</code> class and
 * <code>SubscriberWithPlugin</code>.
 * 
 * @author Mourad TOUATI / Alpha Issiaga DIALLO
 *
 */
public class CVMBasicWithPlugins extends AbstractCVM {

	protected static final String inBoundPortManagement = "inBoundPortManagementURI";
	protected static final String inBoundPortPublication = "inBoundPortPublicationURI";

	protected static final String uriPrefixBroker = "brokerURI";
	protected static final String uriPrefixPublisher = "publisherURI";
	protected static final String uriPrefixSubscriber = "subscriberURI";

	String brokerURI;
	String publisherURI;
	String subscriberURI;

	public CVMBasicWithPlugins() throws Exception {
		super();
	}

	@Override
	public void deploy() throws Exception {

		this.brokerURI = AbstractComponent.createComponent(Broker.class.getCanonicalName(),
				new Object[] { uriPrefixBroker, inBoundPortManagement, new String[0] });
		assert this.isDeployedComponent(brokerURI);

		this.publisherURI = AbstractComponent.createComponent(PublisherWithPlugin.class.getCanonicalName(),
				new Object[] { uriPrefixPublisher, inBoundPortManagement });
		assert this.isDeployedComponent(publisherURI);

		this.subscriberURI = AbstractComponent.createComponent(SubscriberWithPlugin.class.getCanonicalName(),
				new Object[] { uriPrefixSubscriber, inBoundPortManagement });
		assert this.isDeployedComponent(subscriberURI);

		this.toggleTracing(this.publisherURI);
		this.toggleLogging(this.publisherURI);

		this.toggleTracing(this.brokerURI);
		this.toggleLogging(this.brokerURI);

		this.toggleTracing(this.subscriberURI);
		this.toggleLogging(this.subscriberURI);

		assert this.brokerURI != null : new Exception("The Broker URI is null!");

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

	@Override
	public void finalise() throws Exception {
		// Port disconnections can be done here for static architectures
		// otherwise, they can be done in the finalise methods of components.
		super.finalise();
	}

	@Override
	public void shutdown() throws Exception {
		assert this.allFinalised();
		// any disconnection not done yet can be performed here
		super.shutdown();
	}

	public static void main(String[] args) {
		try {
			CVMBasicWithPlugins c = new CVMBasicWithPlugins();
			c.startStandardLifeCycle(20000L);
			Thread.sleep(5000L);
			System.exit(0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
