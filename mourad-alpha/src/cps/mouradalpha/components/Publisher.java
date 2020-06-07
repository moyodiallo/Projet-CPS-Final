package cps.mouradalpha.components;

import cps.mouradalpha.connectors.ConnectorManagementCI;
import cps.mouradalpha.connectors.ConnectorPublicationCI;
import cps.mouradalpha.interfaces.ManagementCI;
import cps.mouradalpha.interfaces.MessageI;
import cps.mouradalpha.interfaces.PublicationCI;
import cps.mouradalpha.ports.OutBoundPortManagement;
import cps.mouradalpha.ports.OutBoundPortPublication;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.exceptions.InvariantException;
import fr.sorbonne_u.components.exceptions.PostconditionException;
import fr.sorbonne_u.components.exceptions.PreconditionException;
import fr.sorbonne_u.components.ports.PortI;
/**
 * The class <code>Publisher</code> implements a component that requires 
 * <code>ManagementCI</code> and <code>PublicationCI</code> services
 *
 * @author Alpha Issiaga DIALLO / Mourad TOUATI
 *
 */
@RequiredInterfaces(required = {ManagementCI.class,PublicationCI.class})
public class Publisher extends AbstractComponent { 
       
    /**	a string prefix that will identify the URI provider.				*/
    protected String uriPrefix;

    protected OutBoundPortManagement outBoundPortManagement;
    protected OutBoundPortPublication outBoundPortPublication;

    protected String inBoundPortManagement;

     /**
	 * check the invariant of the class on an instance.
	 *
	 * @param c	the component to be tested.
	 */
	protected static void	checkInvariant(Publisher c)
	{
		assert	c.uriPrefix != null :
                    new InvariantException("The URI prefix is null!") ;
        assert c.isRequiredInterface(ManagementCI.class):
            new InvariantException("The URI component should "
                            + "require the interface ManagementCI!") ;
        assert	c.isRequiredInterface(PublicationCI.class) :
					new InvariantException("The URI component should "
                            + "require the interface PublicationCI!") ;

    }
	/**
	 * 
	 * @param uriPrefix
	 * @param inBoundPortManagement
	 * @throws Exception
	 */
    protected Publisher(String uriPrefix,
        String inBoundPortManagement
    )throws Exception{

        super(uriPrefix, 1, 0);

        assert uriPrefix != null : 
            new PreconditionException("uri can't be null!");

        assert	inBoundPortManagement != null :
            new PreconditionException("inBoundPortManagement can't be null!") ;
        
        this.uriPrefix              = uriPrefix;
        this.inBoundPortManagement  = inBoundPortManagement;

        this.outBoundPortManagement = 
            new OutBoundPortManagement(this.uriPrefix+"-port-management", this);
        
        this.outBoundPortPublication = 
            new OutBoundPortPublication(this.uriPrefix+"-port-publication", this);

        this.outBoundPortManagement.localPublishPort();
        this.outBoundPortPublication.localPublishPort();

        if (AbstractCVM.isDistributed) {
            this.executionLog.setDirectory(System.getProperty("user.dir"));
        } else {
            this.executionLog.setDirectory(System.getProperty("user.home"));
        }

        this.tracer.setTitle("Publisher");
        this.tracer.setRelativePosition(1, 2);

        Publisher.checkInvariant(this);

        assert	this.uriPrefix.equals(uriPrefix) :
					new PostconditionException("The URI prefix has not "
                                                + "been initialised!") ;
        
    }

     /**
	 * @see fr.sorbonne_u.components.AbstractComponent#start()
	 */
	@Override
	public void	start() throws ComponentStartException
	{
        super.start();
        this.logMessage("starting Publisher component.");
        try {
            this.doPortConnection(
                outBoundPortManagement.getPortURI(),
                inBoundPortManagement, 
                ConnectorManagementCI.class.getCanonicalName());

            String inBoundPortPublication = this.outBoundPortManagement.getPublicationPortURI();

            this.logMessage("outbound " + inBoundPortPublication);
            
            assert inBoundPortPublication != null:
                new Exception("inBoundPortPublication has not been published by the Broker");

            this.doPortConnection(
                this.outBoundPortPublication.getPortURI(),
                inBoundPortPublication, 
                ConnectorPublicationCI.class.getCanonicalName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute() throws Exception {
         super.execute();

         try{
            outBoundPortManagement.createTopic("moyo");
            outBoundPortManagement.createTopic("topic");
            this.logMessage("create topic.");
        }catch(Exception e){
            System.out.println(e);
        }

        int i = 0;
        while(true){
            if(i == 10) break;
            i++;
            try {
                for (int j = 0; j < 10; j++) {
                    this.logMessage("publish message "+j);
                    MessageI m = new Message(uriPrefix, new String("i like ocaml"+j+" - "+i),null,null);
                    outBoundPortPublication.publish(m,"moyo");
                }
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    
    /**
	 * @see fr.sorbonne_u.components.AbstractComponent#shutdown()
	 */
    @Override
    public void shutdown() throws ComponentShutdownException {
        this.logMessage("shutdown Publisher component");
        

        try {
            PortI[] p = this.findPortsFromInterface(ManagementCI.class);
			p[0].unpublishPort();

			p = this.findPortsFromInterface(PublicationCI.class);
            p[0].unpublishPort();
            

		} catch (Exception e) {
			throw new ComponentShutdownException(e);
        }

        super.shutdown();
    }

	/**
	 * @see fr.sorbonne_u.components.AbstractComponent#finalise()
	 */
	@Override
	public void	finalise() throws Exception
	{
		this.logMessage("finalize Publisher component.") ;
		this.printExecutionLogOnFile(this.uriPrefix) ;
        super.finalise();
        
        this.doPortDisconnection(outBoundPortManagement.getPortURI());
        this.doPortDisconnection(outBoundPortPublication.getPortURI());
	}
}