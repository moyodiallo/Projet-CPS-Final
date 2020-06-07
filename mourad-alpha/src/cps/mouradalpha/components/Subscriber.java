package cps.mouradalpha.components;

import cps.mouradalpha.connectors.ConnectorManagementCI;
import cps.mouradalpha.interfaces.ManagementCI;
import cps.mouradalpha.interfaces.MessageI;
import cps.mouradalpha.interfaces.ReceptionCI;
import cps.mouradalpha.ports.InBoundPortReception;
import cps.mouradalpha.ports.OutBoundPortManagement;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.exceptions.InvariantException;
import fr.sorbonne_u.components.exceptions.PostconditionException;
import fr.sorbonne_u.components.exceptions.PreconditionException;
import fr.sorbonne_u.components.ports.PortI;

/**
 * The class <code>Subscriber</code> implements a component that provides
 * <code>ReceptionCI</code> services, and requires <code>ReceptionCI</code>
 * services
 *
 * @author  Alpha Issiaga DIALLO
 *
 */
@OfferedInterfaces(offered = { ReceptionCI.class })
@RequiredInterfaces(required = { ManagementCI.class })
public class Subscriber extends AbstractComponent implements ReceptionCI {

	/** a string prefix that will identify the URI provider. */
	protected String uriPrefix;

	protected OutBoundPortManagement outBoundPortManagement;

	protected String inBoundPortReception;
	protected String inBoundPortManagement;

	/**
	 * check the invariant of the class on an instance.
	 *
	 * @param c the component to be tested.
	 */
	protected static void checkInvariant(Broker c) {
		assert c.uriPrefix != null : new InvariantException("The URI prefix is null!");

		assert c.isOfferedInterface(ReceptionCI.class) : new InvariantException(
				"The URI component should " + "offer the interface ReceptionCI!");
	}

	protected Subscriber(String uriPrefix, String inBoundPortManagement) throws Exception {

		super(uriPrefix, 1, 0);

		this.uriPrefix = uriPrefix;
		this.inBoundPortReception = "inboundPortReception" + uriPrefix;
		this.inBoundPortManagement = inBoundPortManagement;

		assert uriPrefix != null : new PreconditionException("uri can't be null!");
		assert inBoundPortReception != null : new PreconditionException("inBoundPortReception can't be null!");
		assert inBoundPortManagement != null : new PreconditionException("inBoundPortManagement can't be null!");

		// Reception Port
		PortI p = new InBoundPortReception(inBoundPortReception, this);
		p.publishPort();

		this.outBoundPortManagement = new OutBoundPortManagement(this.uriPrefix + "-port-management", this);
		this.outBoundPortManagement.localPublishPort();
		;

		if (AbstractCVM.isDistributed) {
			this.executionLog.setDirectory(System.getProperty("user.dir"));
		} else {
			this.executionLog.setDirectory(System.getProperty("user.home"));
		}

		this.tracer.setTitle("Subscriber");
		this.tracer.setRelativePosition(1, 1);

		Subscriber.checkInvariant(this);

		assert this.uriPrefix
				.equals(uriPrefix) : new PostconditionException("The URI prefix has not " + "been initialised!");
	}

	/**
	 * @see fr.sorbonne_u.components.AbstractComponent#start()
	 */
	@Override
	public void start() throws ComponentStartException {
		this.logMessage("starting Subscriber component.");

		try {

			this.doPortConnection(outBoundPortManagement.getPortURI(), this.inBoundPortManagement,
					ConnectorManagementCI.class.getCanonicalName());

		} catch (Exception e) {
			e.printStackTrace();
		}

		super.start();
	}

	@Override
	public void execute() throws Exception {
		assert this.isStarted();
		super.execute();

		Thread.sleep(100); // wait for the publisher

		try {
			// getTopics method
			String topics[] = this.outBoundPortManagement.getTopics();
			if (topics.length != 0) {
				this.logMessage("Sub " + topics[0]);
				// subscribe method
				this.outBoundPortManagement.subscribe(topics[0], this.inBoundPortReception);

				this.logMessage("Sub " + topics[1]);
				this.outBoundPortManagement.subscribe(topics[1], this.inBoundPortReception);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see fr.sorbonne_u.components.AbstractComponent#finalise()
	 */
	@Override
	public void	finalise() throws Exception {
		this.logMessage("stopping Subscriber component.") ;
		this.printExecutionLogOnFile(this.uriPrefix);
        super.finalise();
        this.doPortDisconnection(outBoundPortManagement.getPortURI());
    }

    @Override
    public void shutdown() throws ComponentShutdownException {
        
        try {
            PortI[] p = this.findPortsFromInterface(ManagementCI.class);
			p[0].unpublishPort();

			p = this.findPortsFromInterface(ReceptionCI.class);
			p[0].unpublishPort();

		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}

		super.shutdown();
	}

	@Override
	public void acceptMessage(MessageI m) {
		this.logMessage("accepted message: " + m.getPlayload());
	}

	@Override
	public void acceptMessages(MessageI[] ms) {
		for (MessageI messageI : ms) {
			this.acceptMessage(messageI);
		}
	}
}