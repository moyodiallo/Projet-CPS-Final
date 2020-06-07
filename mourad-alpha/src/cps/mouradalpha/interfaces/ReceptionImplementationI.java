package cps.mouradalpha.interfaces;
/**
 * The interface <code>ReceptionImplementationI</code> defines the methods that allows to a Subscriber to get messages for the topics that he subscribed.
 * @author Mourad TOUATI / Alpha Issiaga DIALLO
 *
 */
public interface ReceptionImplementationI {
	/**
	 * this method allow to a Subscriber to a specific topic to get notified when there is a publication
	 * @param m the message published 
	 * @throws Exception
	 */
    public void  acceptMessage(MessageI m) throws Exception;
    /**
	 * this method allow to a Subscriber to a specific topic to get notified when there is publications
     * @param ms the messages published
     * @throws Exception
     */
    public void  acceptMessages(MessageI[] ms) throws Exception;
    
}
