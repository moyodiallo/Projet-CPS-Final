package cps.mouradalpha.components;

import cps.mouradalpha.Proprieties;
import cps.mouradalpha.TimeStamp;
import cps.mouradalpha.interfaces.ManagementCI;
import cps.mouradalpha.interfaces.MessageI;
import cps.mouradalpha.interfaces.PublicationCI;
import cps.mouradalpha.plugins.PublisherPlugin;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentStartException;

/**
 * The class <code>PublisherWithPlugin</code> represent a publisher that use
 * <code>PublisherPlugin</code> in order to perform publisher operations.
 *
 * @author Mourad TOUATI / Alpha Issiaga DIALLO
 *
 */
@RequiredInterfaces(required = { ManagementCI.class, PublicationCI.class })
public class PublisherWithPlugin extends AbstractComponent {

	protected PublisherPlugin plugin;
	protected String uriPrefix;
	protected String inBoundPortManagement;

	protected PublisherWithPlugin(String uriPrefix, String inBoundPortManagement) throws Exception {
		super(uriPrefix, 1, 0);

		this.uriPrefix = uriPrefix;
		this.inBoundPortManagement = inBoundPortManagement;

		this.tracer.setTitle("PublisherWithPlugin: " + uriPrefix);
		this.tracer.setRelativePosition(1, 2);

	}

	@Override
	public void	start() throws ComponentStartException {
        this.logMessage("starting PublisherWithPlugin component.") ;
        super.start();
    }
		 
    @Override
    public void execute() throws Exception {

        assert	this.isStarted() ;
        super.execute();
        
        //creating and installing plugin
        plugin = new PublisherPlugin(uriPrefix+"plugin", inBoundPortManagement);
        this.installPlugin(plugin) ;
        
        plugin.createTopic("FootBall");
        this.logMessage("create topic : FootBall ");
        
        Thread.sleep(1000); //wait for subscriber to subscribe
        int i = 0;
        while(true) {
            MessageI m = new Message(plugin.getPluginURI(), new String("message  : "+i),null,null);
        	plugin.publish(m,"FootBall" );
            this.logMessage("pub to Football "+i++);
            
            Thread.sleep(1000);
            if(true == false) break;
        }
        
        //sending the last message for filtering
        Proprieties prop = new Proprieties();
        prop.putProp("hauteur", 30);
        prop.putProp("largeur", 20);
        System.out.println(prop.getIntProp("hauteur"));
        String strMsg = new String("Dimension Stade filtered "+this.plugin.getPluginURI());
        MessageI msg = new Message(plugin.getPluginURI(), strMsg, new TimeStamp(), prop);
        plugin.publish(msg, "FootBall");
        
        this.logMessage("pub to Football Dimension Stade");
        
        this.logMessage("end publishing messages");
    }

    @Override
    public void finalise() throws Exception {
        this.printExecutionLogOnFile(this.uriPrefix);
        super.finalise();
    }
}
