package cps.mouradalpha.plugins;

import cps.mouradalpha.connectors.ConnectorManagementCI;
import cps.mouradalpha.connectors.ConnectorPublicationCI;
import cps.mouradalpha.interfaces.ManagementCI;
import cps.mouradalpha.interfaces.ManagementImplementationI;
import cps.mouradalpha.interfaces.MessageI;
import cps.mouradalpha.interfaces.PublicationCI;
import cps.mouradalpha.ports.OutBoundPortManagement;
import cps.mouradalpha.ports.OutBoundPortPublication;
import fr.sorbonne_u.components.AbstractPlugin;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;


/**
 * The class <code>PublisherPlugin</code> is a plugin that can be used by a component in order to get 
 * Publisher services.
 *
 * @author Alpha Issiaga DIALLO / Mourad TOUATI
 *
 */
@RequiredInterfaces(required = { ManagementCI.class, PublicationCI.class })
public class PublisherPlugin 
extends AbstractPlugin implements PublicationCI, ManagementImplementationI {

    /**
     *   
     */
    private static final long serialVersionUID = 1L;

    /** a string prefix that will identify the URI provider. */
    protected String uriPrefix;

    protected String inBoundPortManagement;
    protected String inBoundPortPublication;

    protected OutBoundPortManagement outBoundPortManagement;
    protected OutBoundPortPublication outBoundPortPublication;

    public PublisherPlugin(String uriPrefix, String inBoundPortManagement) throws Exception {

        super();

        this.setPluginURI(uriPrefix);

        this.uriPrefix = uriPrefix;
        this.inBoundPortManagement = inBoundPortManagement;
    }

    @Override
    public void installOn(ComponentI owner) throws Exception {
        super.installOn(owner);
    }

    @Override
    public void initialise() throws Exception {

        this.outBoundPortManagement = 
            new OutBoundPortManagement(this.uriPrefix + "port-mangement",this.owner);
        outBoundPortManagement.localPublishPort();

        this.outBoundPortPublication = 
            new OutBoundPortPublication(this.uriPrefix + "port-publication",this.owner);
        outBoundPortPublication.localPublishPort();

        this.owner.doPortConnection(outBoundPortManagement.getPortURI(), this.inBoundPortManagement,
                ConnectorManagementCI.class.getCanonicalName());

        String inBoundPortPublication = this.outBoundPortManagement.getPublicationPortURI();
        assert inBoundPortPublication != null : new Exception(
                "inBoundPortPublication has not been published by the Broker");

        this.owner.doPortConnection(outBoundPortPublication.getPortURI(), inBoundPortPublication,
                ConnectorPublicationCI.class.getCanonicalName());

        super.initialise();
    }

    @Override
    public void finalise() throws Exception {
        
        if(this.outBoundPortManagement.isPublished()) {
            this.outBoundPortManagement.unpublishPort();
        }

        if(this.outBoundPortPublication.isPublished()) {
            this.outBoundPortPublication.unpublishPort();
        }

        if(outBoundPortManagement.connected()) {
            this.owner.doPortDisconnection(outBoundPortManagement.getPortURI());
        }

        if(outBoundPortPublication.connected()) {
            this.owner.doPortDisconnection(outBoundPortPublication.getPortURI());
        }
        
        super.finalise();
    }

    @Override
    public void uninstall() throws Exception {
        super.uninstall();
    }
    /**
     * publish a message for a specific topic using the <code>outBoundPortPublication</code>
     * @param m the message 
     * @param topic the topic 
     */
    @Override
    public void publish(MessageI m, String topic) throws Exception {
        this.logMessage("publications |1| " + topic);
        this.logMessage("request");
        this.outBoundPortPublication.publish(m, topic);
    }
    /**
     * publish a message for a specific topics using the <code>outBoundPortPublication</code>
     * @param m the message 
     * @param topics the topics 
     */
    @Override
    public void publish(MessageI m, String[] topics) throws Exception {
        this.logMessage("publications|1|");
        this.logMessage("request");
        this.outBoundPortPublication.publish(m, topics);
    }
    /**
     * publish a messages for a specific topic using the <code>outBoundPortPublication</code>
     * @param ms the message 
     * @param topic the topic 
     */
    @Override
    public void publish(MessageI[] ms, String topic) throws Exception {       
        this.logMessage("publications|"+ms.length+"| is publised in topic | " + topic);
        this.logMessage("request"); 
        this.outBoundPortPublication.publish(ms, topic);
    }
    /**
     * publish a message for a specific topic using the <code>outBoundPortPublication</code>
     * @param ms the messages 
     * @param topics the topics 
     */
    @Override
    public void publish(MessageI[] ms, String[] topics) throws Exception {
        this.logMessage("publications|"+ms.length);
        this.logMessage("request");  
        this.outBoundPortPublication.publish(ms, topics);
    } 
    /**
     * create a topic using the <code>outBoundPortPublication</code>
     * @param topic the topic 
     */
    @Override
    public void createTopic(String topic) throws Exception {
        this.logMessage("request");
        this.outBoundPortManagement.createTopic(topic);
    }
    /**
     * create a topics using the <code>outBoundPortPublication</code>
     * @param topics the topic 
     */
    @Override
    public void createTopic(String[] topics) throws Exception {
        this.logMessage("request");
        this.outBoundPortManagement.createTopic(topics);
    }
    /**
     * destroy a topic using the <code>outBoundPortPublication</code>
     * @param topic the topic 
     */
    @Override
    public void destroyTopic(String topic) throws Exception {
        this.logMessage("request");
        this.outBoundPortManagement.destroyTopic(topic);
    }
    /**
     * check whether a topic exist or not using the <code>outBoundPortPublication</code>
     * @param topic the topic 
     */
    @Override
    public boolean isTopic(String topic) throws Exception {
        this.logMessage("request");
        return this.outBoundPortManagement.isTopic(topic);
    }
    /**
     * get the topics using the <code>outBoundPortPublication</code>
     */
    @Override
    public String[] getTopics() throws Exception {
        this.logMessage("request");
        return this.outBoundPortManagement.getTopics();
    }
    /**
     * 
     */
    @Override
    public String getPublicationPortURI() throws Exception {
        this.logMessage("request");
        return this.outBoundPortManagement.getPublicationPortURI();
    }
}
