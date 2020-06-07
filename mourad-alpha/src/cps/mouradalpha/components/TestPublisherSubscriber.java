package cps.mouradalpha.components;

import java.util.UUID;

import cps.mouradalpha.interfaces.ManagementCI;
import cps.mouradalpha.interfaces.MessageI;
import cps.mouradalpha.interfaces.PublicationCI;
import cps.mouradalpha.interfaces.ReceptionCI;
import cps.mouradalpha.plugins.PublisherPlugin;
import cps.mouradalpha.plugins.SubscriberPlugin;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
/**
 * 
 * @author Alpha Issiaga DIALLO / Mourad TOUATI
 *
 */
@RequiredInterfaces(required = { ManagementCI.class, PublicationCI.class })
@OfferedInterfaces(offered = { ReceptionCI.class })
public class TestPublisherSubscriber extends AbstractComponent {

    PublisherPlugin publisherPlugin;
    SubscriberPlugin subscriberPlugin;

    String uriPublisher;
    String uriSubscriber;

    String inBoundPortManagement;
    
    protected TestPublisherSubscriber(
        String uriPrefix,
        String inBoundPortManagement, 
        String uriPublisher, 
        String uriSubscriber) throws Exception
    {
        super(uriPrefix,2,0);

        this.inBoundPortManagement = inBoundPortManagement;
        this.uriPublisher          = uriPublisher;
        this.uriSubscriber         = uriSubscriber;
    }

    @Override
    public void execute() throws Exception {
        assert this.isStarted();
        super.execute();
        System.out.println("Executing fonctional test\n");

        
        publisherPlugin  = new PublisherPlugin(this.uriPublisher, this.inBoundPortManagement);

        System.out.println("Installing publisherPlugin\n");
        this.installPlugin(publisherPlugin);
        
        int ACCEPT = this.createNewExecutorService("ac-handler"+this.uriPublisher,2,false);
        subscriberPlugin = new SubscriberPlugin(this.uriSubscriber, this.inBoundPortManagement,ACCEPT);

        System.out.println("Installing subscriberPlugin\n");
        this.installPlugin(subscriberPlugin);

        /********** Creating topic Test ************/
        System.out.println("Creating topic: topic");
        publisherPlugin.createTopic("topic");
        Thread.sleep(100);
        assert publisherPlugin.isTopic("topic"):
            new Exception("fail creating topic");
        System.out.println("Creating topic Passed\n");
        /*******************************************/

        /************ Publishing topic Test*************/
        System.out.println("Subscribing and Publishing");
        //Let's notice inBoundPortReception = uriSubscriber+"inboundPortReception"
        subscriberPlugin.subscribe("topic", null, this.uriSubscriber+"inboundPortReception");
        Thread.sleep(100);
        
        MessageI m = new Message(UUID.randomUUID().toString(), new String("HelloWorld"),null,null);
        publisherPlugin.publish(m, "topic");
        Thread.sleep(100);

        MessageI myMsg = subscriberPlugin.pollMessage();
        String msg = (String)(myMsg.getPlayload());
        assert msg != null && msg.equals("HelloWorld"):
            new Exception("fail subscribing and publishing message to a topic");
        System.out.println("Subscribing and Publishing message Passed\n");
        /***********************************************************/
    }

}