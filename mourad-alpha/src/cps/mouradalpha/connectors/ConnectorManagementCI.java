package cps.mouradalpha.connectors;

import cps.mouradalpha.interfaces.ManagementCI;
import cps.mouradalpha.interfaces.MessageFilterI;
import fr.sorbonne_u.components.connectors.AbstractConnector;
 
/**
 * The class <code>ConnectorManagementCI</code> implements a connector that links components  
 * which offer/require services that are present in <code>ManagementCI</code>
 * <p><strong>Description</strong></p>
 * a call of any method from the Publisher/Subscriber component will be transfered and execute by the Broker  
 * @author  Mourad TOUATI / Alpha Issiaga DIALLOs
 *  
 */
public class ConnectorManagementCI extends AbstractConnector 
implements ManagementCI {
	/**
	 * calling the inbound port with the corresponding offered method. 
	 * @param topic URI of the topic
	 * @param inboundPortURI the inbound port of the Broker
	 * @throws Exception
	 */
    @Override
    public void subscribe(String topic, String inboundPortURI) throws Exception{
        ((ManagementCI)this.offering).subscribe(topic, inboundPortURI);
    }
    /**
     * calling the inbound port with the corresponding offered method.
     * @param topics URIs of the topics
     * @param inboundPortURI the inbound port of the Broker
     * @throws Exception
     */
    @Override
    public void subscribe(String[] topics, String inboundPortURI) throws Exception{
        ((ManagementCI)this.offering).subscribe(topics, inboundPortURI);
    }
    /**
     * calling the inbound port with the corresponding offered method.
     * @param topic URI of the topic
     * @param filter the filter that will be used to get only the messages that satisfy the filter
     * @param inboundPortURI  the inbound port of the Broker
     * @throws Exception
     */
    @Override
    public void subscribe(String topic, MessageFilterI filter, String inboundPortURI)throws Exception{
       ((ManagementCI)this.offering).subscribe(topic, filter, inboundPortURI);
    }
    /**
     * calling the inbound port with the corresponding offered method.
     * @param topic URI of the topic
     * @param newFilter the new filter that will be used to get only the messages that satisfy the filter
     * @param inboundPortURI the inbound port of the Broker
     * @throws Exception
     */
    @Override
    public void modifyFilter(String topic, MessageFilterI newFilter, String inboundPortURI)throws Exception{
       ((ManagementCI)this.offering).modifyFilter(topic, newFilter, inboundPortURI);
    }
    /**
     * calling the inbound port with the corresponding offered method.
     * @param topic URI of the topic
     * @param inboundPortURI inbound port of the Broker
     * @throws Exception
     */
    @Override
    public void unsubscribe(String topic, String inboundPortURI)throws Exception{
        ((ManagementCI)this.offering).unsubscribe(topic, inboundPortURI);
    }
    /**
	 * calling the inbound port with the corresponding offered method. 
	 * @param topic URI of the topic
	 * @throws Exception
	 */
    @Override
    public void createTopic(String topic)throws Exception{
        ((ManagementCI)this.offering).createTopic(topic);
    }
    /**
     * calling the inbound port with the corresponding offered method. 
     * @param topics URIs of the topics
     * @throws Exception
     */
    @Override
    public void createTopic(String[] topics)throws Exception{
       ((ManagementCI)this.offering).createTopic(topics);
    }
    /**
     * calling the inbound port with the corresponding offered method.
     * @param topic URI of the topic
     * @throws Exception
     */
    @Override
    public void destroyTopic(String topic)throws Exception{
        ((ManagementCI)this.offering).destroyTopic(topic);
    }
    /**
     * calling the inbound port with the corresponding offered method.
     * @param  topic URI of the topic
     * @return true if the URI corresponding to the topic exists or false otherwise
     * @throws Exception
     */
    @Override
    public boolean isTopic(String topic)throws Exception{
        return ((ManagementCI)this.offering).isTopic(topic);
    }
    /**
     * calling the inbound port with the corresponding offered method.
     * @return get the list of URI corresponding to the topics that exists in the Broker
     * @throws Exception
     */
    @Override
    public String[] getTopics()throws Exception{
        return ((ManagementCI)this.offering).getTopics();
    }
    /**
     * calling the inbound port with the corresponding offered method.
     * @return get the URI of a port offering the interface PublicationCI which allow the Publisher to connect and publish its messages
     * @throws Exception
     */
    @Override
    public String getPublicationPortURI() throws Exception {
        return ((ManagementCI)this.offering).getPublicationPortURI();
    }

}