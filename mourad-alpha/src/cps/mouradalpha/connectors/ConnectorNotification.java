package cps.mouradalpha.connectors;

import cps.mouradalpha.interfaces.MessageI;
import cps.mouradalpha.interfaces.NotificationCI;
import fr.sorbonne_u.components.connectors.AbstractConnector;
/**
 * The class <code>ConnectorNotification</code> implements a connector that links components  
 * which offer/require services that are present in <code>NotificationCI</code>
 * <p><strong>Description</strong></p>
 * a call of any method from the Broker (different jvm) component will be transfered and execute by the Broker  
 * @author  Mourad TOUATI / Alpha Issiaga DIALLO
 *  
 */
public class ConnectorNotification extends AbstractConnector implements NotificationCI {


	/**
	 * calling the inbound port with the corresponding offered method.
	 * @param topic the topic that was created
	 * @throws Exception
	 */
    @Override
    public void notifyTopic(String topic) throws Exception {
       ((NotificationCI)this.offering).notifyTopic(topic);
    }


	/**
	 * calling the inbound port with the corresponding offered method.
	 * @param m the message that was published
	 * @param topic the topic
	 * @throws Exception
	 */
    @Override
    public void notifyPublish(MessageI m, String topic) throws Exception{
        ((NotificationCI)this.offering).notifyPublish(m, topic);
    }


	/**
	 * calling the inbound port with the corresponding offered method.
	 * @param topic the message that was destroyed
	 * @throws Exception
	 */
    @Override
    public void notifyDestroyTopic(String topic) throws Exception{
        ((NotificationCI)this.offering).notifyDestroyTopic(topic);
    }

    @Override
    public void notifyPublish(MessageI[] ms, String topic) throws Exception {
        ((NotificationCI)this.offering).notifyPublish(ms, topic);
    }
    
}