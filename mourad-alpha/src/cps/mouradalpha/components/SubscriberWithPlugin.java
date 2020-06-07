package cps.mouradalpha.components;

import cps.mouradalpha.interfaces.ManagementCI;
import cps.mouradalpha.interfaces.ReceptionCI;
import cps.mouradalpha.plugins.SubscriberPlugin;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentStartException;

/**
 * The class <code>SubscriberWithPlugin</code> represent a Subscriber that use
 * <code>SubscriberPlugin</code> in order to perform Subscriber operations.
 *
 * @author Mourad TOUATI / Alpha Issiaga DIALLO
 *
 */
@RequiredInterfaces(required = { ManagementCI.class })
@OfferedInterfaces(offered = { ReceptionCI.class })
public class SubscriberWithPlugin extends AbstractComponent {

    protected SubscriberPlugin plugin;
    protected String uriPrefix;
    protected String inBoundPortManagement;

    protected SubscriberWithPlugin(String uriPrefix, String inBoundPortManagement) throws Exception {
        super(uriPrefix, 1, 0);

        this.uriPrefix = uriPrefix;
        this.inBoundPortManagement = inBoundPortManagement;

        this.tracer.setTitle("SubscriberWithPlugin");
        this.tracer.setRelativePosition(1, 1);
    }

    @Override
    public void start() throws ComponentStartException {
        this.logMessage("starting SubscriberWithPlugin component.");
        super.start();
    }

    @Override
    public void execute() throws Exception {
        assert this.isStarted();
        super.execute();

        this.logMessage("Executing SubscriberWithPlugin");

        int ACCEPT = this.createNewExecutorService("ac-handler"+this.uriPrefix,20,false);
        plugin = new SubscriberPlugin(uriPrefix + "plugin", inBoundPortManagement,ACCEPT);
        this.installPlugin(plugin);

        Thread.sleep(100); // waiting for publisher
        plugin.subscribe("FootBall", plugin.getInBoundPortReceptionURI());

        this.logMessage("Sub to " + "Football");
    }

    @Override
    public void finalise() throws Exception {
        this.printExecutionLogOnFile(this.uriPrefix);
        super.finalise();
    }
}
