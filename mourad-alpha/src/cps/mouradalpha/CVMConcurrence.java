package cps.mouradalpha;

import cps.mouradalpha.components.Broker;
import cps.mouradalpha.components.PublisherWithPlugin;
import cps.mouradalpha.components.SubscriberWithPlugin;
import cps.mouradalpha.components.SubscriberWithPluginFiltered;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.cvm.AbstractCVM;

/**
 * The class <code>CVMConcurrence</code> implements the single JVM assembly for
 * our project, in this example we test the concurrency of the <code>Broker</code> with
 * multiple <code>Subscriber</code>  and <code>Publisher</code>.
 * 
 * @author Alpha Issiaga DIALLO / Mourad TOUATI
 *
 */
public class CVMConcurrence extends AbstractCVM {

	protected static final String inBoundPortManagement = "inBoundPortManagementURI";
	protected static final String inBoundPortPublication = "inBoundPortPublicationURI";

	protected static final String uriPrefixBroker = "brokerURI";
	protected static final String uriPrefixPublisher = "publisherURI";
	protected static final String uriPrefixSubscriber = "subscriberURI";

	public CVMConcurrence() throws Exception {
		super();
	}

	@Override
	public void deploy() throws Exception {

		String brokerURI = AbstractComponent.createComponent(Broker.class.getCanonicalName(),
				new Object[] { uriPrefixBroker, inBoundPortManagement, new String[0] });

		assert this.isDeployedComponent(brokerURI);

		assert brokerURI != null : new Exception("The Broker URI is null!");

		this.toggleTracing(brokerURI);
		this.toggleLogging(brokerURI);

		// Creating Publishers for testing concurrence
		int numberPublisher = 5;
		for (int i = 0; i < numberPublisher; i++) {
			String URIPublisher = uriPrefixPublisher + i;
			String publisherURI = AbstractComponent.createComponent(PublisherWithPlugin.class.getCanonicalName(),
					new Object[] { URIPublisher + i, inBoundPortManagement });

			assert this.isDeployedComponent(publisherURI);

			assert publisherURI != null : new Exception("The Publisher URI is null!");

			this.toggleTracing(publisherURI);
			this.toggleLogging(publisherURI);
		}

		// Creating subscribers for testing concurrence
		int numberSubscriber = 5;
		for (int i = 0; i < numberSubscriber; i++) {
			String URISubcriber = uriPrefixSubscriber + i;
			String subscriberURI = AbstractComponent.createComponent(SubscriberWithPlugin.class.getCanonicalName(),
					new Object[] { URISubcriber, inBoundPortManagement });
			assert this.isDeployedComponent(subscriberURI);

			assert subscriberURI != null : new Exception("The subscriber URI is null");

			this.toggleTracing(subscriberURI);
			this.toggleLogging(subscriberURI);
		}

		// Create a Subscriber waiting a message filter subscription
		String URISubcriber = uriPrefixSubscriber + numberSubscriber;
		String subscriberURI = AbstractComponent.createComponent(SubscriberWithPluginFiltered.class.getCanonicalName(),
				new Object[] { URISubcriber, inBoundPortManagement });
		assert this.isDeployedComponent(subscriberURI);

		assert subscriberURI != null : new Exception("The subscriber URI is null");

		this.toggleTracing(subscriberURI);
		this.toggleLogging(subscriberURI);

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
			CVMConcurrence c = new CVMConcurrence();
			c.startStandardLifeCycle(20000L);
			Thread.sleep(20000L);
			System.exit(0);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
