package cps.mouradalpha.components;

import cps.mouradalpha.plugins.SubscriberPlugin;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.exceptions.ComponentStartException;

/**
 * The class <code>SubscriberWithPluginFiltered</code> represent a Subscriber
 * that use <code>SubscriberPlugin</code> in order to perform Subscriber
 * operations and uses also <code>MessageFilterExampleRectangle</code> to filter messages.
 *
 * @author Mourad TOUATI / Alpha Issiaga DIALLO
 */
public class SubscriberWithPluginFiltered extends AbstractComponent {
	protected SubscriberPlugin plugin;

	protected SubscriberWithPluginFiltered(String uriPrefix, String inBoundPortManagement) throws Exception {
		super(uriPrefix, 1, 0);
        int ACCEPT = this.createNewExecutorService("ac-handler"+uriPrefix,20,false);
        plugin = new SubscriberPlugin(uriPrefix + "plugin", inBoundPortManagement,ACCEPT);
		this.installPlugin(plugin);

		this.tracer.setTitle("SubscriberWithPlugin");
		this.tracer.setRelativePosition(1, 1);
	}

	@Override
	public void start() throws ComponentStartException {
		System.out.println("starting");
		this.logMessage("starting SubscriberWithPlugin component.");
		super.start();
	}

	@Override
	public void execute() throws Exception {
		assert this.isStarted();
		super.execute();
		Thread.sleep(10);
		plugin.subscribe("FootBall", new MessageFilterExampleRectangle(), plugin.getInBoundPortReceptionURI());

		this.logMessage("Sub to " + "Football With rectangle filtering");
	}
}
