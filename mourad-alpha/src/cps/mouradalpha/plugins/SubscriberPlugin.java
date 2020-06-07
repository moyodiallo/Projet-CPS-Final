package cps.mouradalpha.plugins;

import cps.mouradalpha.components.Message;
import cps.mouradalpha.connectors.ConnectorManagementCI;
import cps.mouradalpha.interfaces.MessageFilterI;
import cps.mouradalpha.interfaces.MessageI;
import cps.mouradalpha.interfaces.ReceptionCI;
import cps.mouradalpha.interfaces.SubscriptionImplementationI;
import cps.mouradalpha.ports.InBoundPortReceptionPlugin;
import cps.mouradalpha.ports.OutBoundPortManagement;
import cps.mouradalpha.util.MyConcurrentLinkedQueue;
import fr.sorbonne_u.components.AbstractPlugin;
import fr.sorbonne_u.components.ComponentI;
import fr.sorbonne_u.components.ports.PortI;
/**
 * The class <code>SubscriberPlugin</code> is a plugin that can be used by a component in order to get 
 * Subscriber services.
 *
 * @author Alpha Issiaga DIALLO / Mourad TOUATI
 *
 */
public class SubscriberPlugin extends AbstractPlugin implements
 ReceptionCI, SubscriptionImplementationI {

    /**  
    *
    */
    private static final long serialVersionUID = 1L;

    protected OutBoundPortManagement outBoundPortManagement;

    protected String inBoundPortReception;
    protected String inBoundPortManagement;

    protected MyConcurrentLinkedQueue<MessageI> messages;
    protected String uriPrefix;

    protected int acceptExecutor;

    public SubscriberPlugin(
        String uriPrefix,
        String inBoundPortManagement,int acceptExecutorIndex) 
        throws Exception {
        
            super();

        this.setPluginURI(uriPrefix);

        this.inBoundPortReception  = this.plugInURI+"inboundPortReception";
        this.inBoundPortManagement = inBoundPortManagement;
        this.uriPrefix = uriPrefix;

        this.acceptExecutor = acceptExecutorIndex;
            
        this.messages = new MyConcurrentLinkedQueue<MessageI>();

        assert messages != null:
            new Exception("messages can't be null!");
    }

    @Override
    public void installOn(ComponentI owner) throws Exception {
        super.installOn(owner);   
    }

    @Override
    public void initialise() throws Exception {
       
        this.outBoundPortManagement = 
            new OutBoundPortManagement(this.uriPrefix+"-port-management", owner);
        this.outBoundPortManagement.localPublishPort();  
        
        PortI p = 
           new InBoundPortReceptionPlugin(this.inBoundPortReception,this.plugInURI,this.owner,this.acceptExecutor);
        p.publishPort();
        

        this.owner.doPortConnection(
            this.outBoundPortManagement.getPortURI(), 
            this.inBoundPortManagement, 
            ConnectorManagementCI.class.getCanonicalName());

        super.initialise();
    }

    @Override
    public void finalise() throws Exception {
        if(outBoundPortManagement.isPublished()) {
            this.outBoundPortManagement.unpublishPort();
        }

        if(this.outBoundPortManagement.connected()) {
            this.owner.doPortDisconnection(this.outBoundPortManagement.getPortURI());
        }
        
        if(this.findPortFromURI(this.inBoundPortReception).isPublished()) {
            this.findPortFromURI(this.inBoundPortReception).unpublishPort();
        }
        super.finalise();
    }

    @Override
    public void uninstall() throws Exception {
        super.uninstall();
    }
    /**
     * add the received message to the list <code>messages</code>
     */
    @Override
    public void acceptMessage(MessageI m) {
        //this.messages.add(m);
        this.logMessage("messages|1| "+m.getPlayload());
    }
    /**
     * add the received messages to the list <code>messages</code>
     */
    @Override
    public void acceptMessages(MessageI[] ms) {
        this.logMessage("messages|"+ms.length);
        
        for (MessageI messageI : ms) {
            this.messages.add(messageI);
            //this.logMessage("mes_content| "+messageI.getPlayload());
        }
    }
    /**
     * subscribe to a topic using the <code>outBoundPortPublication</code>
     * @param topic the topic 
     * @param inboundPortURI the inboundPortURI
     */
    @Override
    public void subscribe(String topic, String inboundPortURI) throws Exception {
        try {
            this.outBoundPortManagement.subscribe(topic, inboundPortURI);
            this.logMessage("subscriptions|1|");
            this.logMessage("request");
        } catch (Exception e) {
        }
        
    }

    /**
     * subscribe to topics using the <code>outBoundPortPublication</code>
     * @param topic the topic 
     * @param inboundPortURI the inboundPortURI
     */
    @Override
    public void subscribe(String[] topics, String inboundPortURI) throws Exception {
        try {
            this.outBoundPortManagement.subscribe(topics, inboundPortURI);
            this.logMessage("subscriptions|"+topics.length+"|");
            this.logMessage("request");
        } catch (Exception e) {
        }
    }
    /**
     * subscribe to topics using the <code>outBoundPortPublication</code>
     * @param topic the topic 
     * @param filter the filter
     * @param inboundPortURI the inboundPortURI
     */
    @Override
    public void subscribe(String topic, MessageFilterI filter, String inboundPortURI)
    throws Exception {
        try {
            this.outBoundPortManagement.subscribe(topic, filter, inboundPortURI);
            this.logMessage("subscriptions|1|filter");
            this.logMessage("request");
        } catch (Exception e) {
        }
    }
    
    /**
     * Modify an existing filter to topics using the <code>outBoundPortPublication</code>
     * @param topic the topic 
     * @param newFilter the new filter
     * @param inboundPortURI the inboundPortURI
     */
    @Override
    public void modifyFilter(String topic, MessageFilterI newFilter, String inboundPortURI)
    throws Exception {
        try {
            this.outBoundPortManagement.modifyFilter(topic, newFilter, inboundPortURI);
            this.logMessage("fil_modification");
            this.logMessage("request");
        } catch (Exception e) {
        }
    }
    /**
     * unsubscribe from a topic using the <code>outBoundPortPublication</code>
     * @param topic the topic 
     * @param newFilter the new filter
     * @param inboundPortURI the inboundPortURI
     */
    @Override
    public void unsubscribe(String topic, String inboundPortURI) throws Exception {
        try {
            this.outBoundPortManagement.unsubscribe(topic, inboundPortURI);
            this.logMessage("unsubscription");
            this.logMessage("request");
        } catch (Exception e) {
        }
    }
    /**
     * get the InBoundPortReceptionURI in order be connected by the broker
     */
    public String getInBoundPortReceptionURI() throws Exception {
        return this.inBoundPortReception;
    }
    /**
     * poll a message from the list <code>messages</code>
     * @return
     * @throws Exception
     */
    public MessageI pollMessage() throws Exception {
        MessageI nullMessageI = new Message(null,null,null,null);
        MessageI myMessageI   = this.messages.poll();
        if(myMessageI == null) {
            return nullMessageI;
        }else {
            return myMessageI;
        }
    }

    public String[] getTopics() throws Exception {
        String[] topics = null;
        try {
            topics = this.outBoundPortManagement.getTopics();
            this.logMessage("topics");
            this.logMessage("request");
        } catch (Exception e) {
        }
        
        return topics;
    }
}
