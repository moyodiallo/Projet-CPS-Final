package cps.mouradalpha.connectors;

import cps.mouradalpha.interfaces.MessageI;
import cps.mouradalpha.interfaces.ReceptionCI;
import fr.sorbonne_u.components.connectors.AbstractConnector;

/**
 * The class <code>ConnectorReceptionCI</code> implements a connector that links components  
 * which offer/require services that are present in <code>ReceptionCI</code>
 * <p><strong>Description</strong></p>
 * a call of any method from the Broker component will be transfered and execute by the Subscriber  
 * @author  Mourad TOUATI / Alpha Issiaga DIALLO
 *  
 */
public class ConnectorReceptionCI extends AbstractConnector 
implements ReceptionCI {

	/**
	 * calling the inbound port with the corresponding offered method.
	 * @param m the message that was puplished
	 * @throws Exception
	 */
    @Override
    public void acceptMessage(MessageI m) throws Exception{
       ((ReceptionCI)this.offering).acceptMessage(m);
    }
	/**
	 * calling the inbound port with the corresponding offered method.
	 * @param ms the messages that was puplished
	 * @throws Exception
	 */
    @Override
    public void acceptMessages(MessageI[] ms)throws Exception {
       ((ReceptionCI)this.offering).acceptMessages(ms);
    }
}