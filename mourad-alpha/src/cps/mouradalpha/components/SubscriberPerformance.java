package cps.mouradalpha.components;

import java.util.ArrayList;
import java.util.Random;

import cps.mouradalpha.DataBrut;
import cps.mouradalpha.interfaces.ManagementCI;
import cps.mouradalpha.interfaces.MessageFilterI;
import cps.mouradalpha.interfaces.ReceptionCI;
import cps.mouradalpha.plugins.SubscriberPlugin;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
/**
 * 
 * @author Alpha Issiaga DIALLO / Mourad TOUATI
 * The class <code>SubscriberPerformance</code> is a component that use
 * <code>SubscriberPlugin</code> pluing in order to perform all subscriber methods and test them.
 * This component is used in part 4 of the project for integration tests, we will have a lot of them so 
 * we added some random operations in order to have different behavior for each component 
 */
@RequiredInterfaces(required = { ManagementCI.class })
@OfferedInterfaces(offered = { ReceptionCI.class })
public class SubscriberPerformance extends AbstractComponent {

    protected SubscriberPlugin plugin;
    protected String uriPrefix;
    protected String inBoundPortManagement;

    protected static final int NB_THREAD_ACCEPT = 2;

    protected SubscriberPerformance(String uriPrefix, String inBoundPortManagement) throws Exception {
        super(uriPrefix, 2, 0);

        this.uriPrefix = uriPrefix;
        this.inBoundPortManagement = inBoundPortManagement;

        //this.tracer.setTitle("SubscriberWithPlugin");
        //this.tracer.setRelativePosition(1, 1);
    }

    @Override
    public void start() throws ComponentStartException {
        this.logMessage("starting SubscriberWithPlugin component.");
        this.logMessage("subscriberURI_ONE");
        super.start();
    }

    @Override
    public void execute() throws Exception {
        assert this.isStarted();
        super.execute();
        
        int ACCEPT = this.createNewExecutorService("ac-handler"+this.uriPrefix,NB_THREAD_ACCEPT,false);
        plugin = new SubscriberPlugin(uriPrefix + "plugin", inBoundPortManagement,ACCEPT);
        this.installPlugin(plugin);
        

        ArrayList<String> subscriptionTopics = new ArrayList<>();
        ArrayList<MessageFilterI> filters    = DataBrut.filters();
        while(true){
            try {

                this.sleep(200);

                //Get topics from broker
                ArrayList<String> topics = new ArrayList<>();
                String[] createdTopics   = plugin.getTopics();
                for (int i = 0; i < createdTopics.length; i++) {
                    topics.add(createdTopics[i]);
                }

                int nbOfChoosenTopics = (new Random()).nextInt(3)+4;
                while(topics.size() > 0 && nbOfChoosenTopics > 0) {
                    int rand = (new Random()).nextInt(topics.size());
                    String topic = topics.get(rand);
                    topics.remove(rand);

                    //subscribe
                    int pileOrFace  = (new Random()).nextInt(2);
                    if(pileOrFace == 0) {
                        plugin.subscribe(topic, plugin.getInBoundPortReceptionURI());
                    }else {
                        int random            = (new Random()).nextInt(filters.size());
                        MessageFilterI filter = filters.get(random);
                        plugin.subscribe(topic, filter, plugin.getInBoundPortReceptionURI()); 
                    }

                    subscriptionTopics.add(topic);
                    nbOfChoosenTopics--;
                }

                this.sleep(400);
                int pileOrFace = (new Random()).nextInt(2);

                if(pileOrFace == 0) {
                    
                    for (String topic : subscriptionTopics) {
                        plugin.unsubscribe(topic, plugin.getInBoundPortReceptionURI());
                        this.sleep(10);
                    }
                    subscriptionTopics.clear();

                }else {
                    int rand = (new Random()).nextInt(subscriptionTopics.size());
                    for (int i = 0; i < rand; i++) {
                        int randTopic = (new Random()).nextInt(subscriptionTopics.size());
                        String topic  = subscriptionTopics.get(randTopic);
                        plugin.unsubscribe(topic, plugin.getInBoundPortReceptionURI());
                        subscriptionTopics.remove(randTopic);
                        this.sleep(10);
                    }
                }

                //Get topics from broker
                topics = new ArrayList<>();
                createdTopics   = plugin.getTopics();
                for (int i = 0; i < createdTopics.length; i++) {
                    topics.add(createdTopics[i]);
                }
                
                ArrayList<String> choosenTopicList = new ArrayList<>();
                nbOfChoosenTopics = (new Random()).nextInt(topics.size());
                while(topics.size() > 0 && nbOfChoosenTopics > 0) {
                    int rand = (new Random()).nextInt(topics.size());
                    String topic = topics.get(rand);
                    topics.remove(rand);

                    choosenTopicList.add(topic);
                    subscriptionTopics.add(topic);
                    nbOfChoosenTopics--;
                }
                
                String choosenTopicArray[] = new String[choosenTopicList.size()];
                int choosen = 0;
                for (String string : choosenTopicList) {
                    choosenTopicArray[choosen++] = string;
                }
                plugin.subscribe(choosenTopicArray, plugin.getInBoundPortReceptionURI());
                this.sleep(500);


                //modifier
                for (int i = 0; i < subscriptionTopics.size(); i++) {
                    int random            = (new Random()).nextInt(filters.size());
                    MessageFilterI filter = filters.get(random);
                    plugin.modifyFilter(subscriptionTopics.get(i), filter, plugin.getInBoundPortReceptionURI());
                }

                this.sleep(400);
                for (String topic : subscriptionTopics) {
                    plugin.unsubscribe(topic, plugin.getInBoundPortReceptionURI());
                    this.sleep(10);
                }

                this.sleep(300);
                subscriptionTopics.clear();

            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }

    @Override
    public void finalise() throws Exception {
        this.printExecutionLogOnFile(this.uriPrefix);
        super.finalise();
    }
    /**
	 * sleep with random amount of time between 40 and millisecons param
	 * 
	 * @param millisecons the uperbound of amount of sleeping
	 * @throws Exception
	 */
    public void sleep(int millisecons) throws Exception {
        Thread.sleep((new Random()).nextInt(millisecons)+40);
    }
}