package cps.mouradalpha.components;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import cps.mouradalpha.DataBrut;
import cps.mouradalpha.Footballeur;
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
 * The class <code>PublisherPerformance</code> is a component that use
 * <code>PublisherPlugin</code> pluing in order to perform all publisher methods and test them.
 * This component is used in part 4 of the project for integration tests, we will have a lot of them so 
 * we added some random operations in order to have different behavior for each component 
 *
 * 
 * @author Alpha Issiaga DIALLO / Mourad TOUATI
 *
 */
@RequiredInterfaces(required = { ManagementCI.class, PublicationCI.class })
public class PublisherPerformance extends AbstractComponent {

	protected PublisherPlugin plugin;
	protected String uriPrefix;
	protected String inBoundPortManagement;

	protected PublisherPerformance(final String uriPrefix, final String inBoundPortManagement) throws Exception {
		super(uriPrefix, 1, 0);

		this.uriPrefix = uriPrefix;
		this.inBoundPortManagement = inBoundPortManagement;

		this.tracer.setTitle("PublisherPerformance " + uriPrefix);
		this.tracer.setRelativePosition(1, 2);

	}

	public void	start() throws ComponentStartException {
        this.logMessage("starting PublisherPerformance component") ;
        this.logMessage("publisherURI_ONE");
        super.start();
    }
		 
    @Override
    public void execute() throws Exception {

        assert	this.isStarted() ;
        super.execute();

        this.logMessage("Executing PublisherPerformance");

        ArrayList<Footballeur> footballeurs = DataBrut.data();
        
        //creating and installing plugin
        plugin = new PublisherPlugin(uriPrefix+"plugin", inBoundPortManagement);
        this.installPlugin(plugin) ;


        int MESSAGE = 0;
        int TOPIC   = 0;

        ArrayList<String> topicsList = new ArrayList<>();

        while (true) {

            this.sleep(50);

            int pileOrFace = (new Random()).nextInt(2);
            int numberOfTopics;

            if(pileOrFace == 1) {
                numberOfTopics = 2;
            }else {
                numberOfTopics = 3;
            }

            //creating topics
            for (int i = 0; i < numberOfTopics; i++) {
                topicsList.add(this.uriPrefix+"topic"+TOPIC);
                plugin.createTopic(this.uriPrefix+"topic"+TOPIC);
                TOPIC++;
            }

            
            this.sleep(50);

            //publish n message in eachTopic
            for (int j = 0; j < topicsList.size(); j++) {
                
                int toSend = (new Random()).nextInt(5)+5;
                for (int i = 0; i < toSend; i++) {
                    
                    int footballeursMessage = (new Random().nextInt(footballeurs.size()));
                    
                    Footballeur footballeur = (Footballeur)footballeurs.get(footballeursMessage).clone();
                    footballeur.setMessage("message "+MESSAGE++);

                    Proprieties prop = new Proprieties();
                    prop.putProp("nom", footballeur.getNom());
                    prop.putProp("club", footballeur.getClub());
                    prop.putProp("vitesse", footballeur.getVitesse());
                    prop.putProp("taille", footballeur.getTaille());
                    prop.putProp("tempsDeJeu", footballeur.getTempsDeJeu());
                    prop.putProp("nombreDeButMarque", footballeur.getNombreDeButMarque());

                    MessageI m = new Message(UUID.randomUUID().toString(), footballeur, new TimeStamp(), prop);
                    plugin.publish(m, topicsList.get(j));
                }
                this.sleep(10);
            }

            String topics[] = plugin.getTopics();

            //System.out.println("size top "+topics.length);

            try {

                //choose 5 topics
                String topcsToPublish[] = new String[5];
                int j = 0;
                for (int i = 0; i < 5; i++) {
                   topcsToPublish[j++] = topics[(new Random()).nextInt(topics.length)];
                }

                //Choose n message
                int toSend = (new Random()).nextInt(10)+10;
                MessageI msg[] = new MessageI[toSend];
                for (int i = 0; i < msg.length; i++) {
                    int footballeursMessage = (new Random().nextInt(footballeurs.size()));
                    
                    Footballeur footballeur = (Footballeur)footballeurs.get(footballeursMessage).clone();
                    footballeur.setMessage("message "+MESSAGE++);

                    Proprieties prop = new Proprieties();
                    prop.putProp("nom", footballeur.getNom());
                    prop.putProp("club", footballeur.getClub());
                    prop.putProp("vitesse", footballeur.getVitesse());
                    prop.putProp("taille", footballeur.getTaille());
                    prop.putProp("tempsDeJeu", footballeur.getTempsDeJeu());
                    prop.putProp("nombreDeButMarque", footballeur.getNombreDeButMarque());

                    MessageI m = new Message(UUID.randomUUID().toString(), footballeur, new TimeStamp(), prop);
                    msg[i] = m;
                }

                plugin.publish(msg, topcsToPublish);

                //choose n messages
                toSend = (new Random()).nextInt(5)+5;
                msg = new MessageI[toSend];
                for (int i = 0; i < msg.length; i++) {
                    int footballeursMessage = (new Random().nextInt(footballeurs.size()));
                    
                    Footballeur footballeur = (Footballeur)footballeurs.get(footballeursMessage).clone();
                    footballeur.setMessage("message "+MESSAGE++);

                    Proprieties prop = new Proprieties();
                    prop.putProp("nom", footballeur.getNom());
                    prop.putProp("club", footballeur.getClub());
                    prop.putProp("vitesse", footballeur.getVitesse());
                    prop.putProp("taille", footballeur.getTaille());
                    prop.putProp("tempsDeJeu", footballeur.getTempsDeJeu());
                    prop.putProp("nombreDeButMarque", footballeur.getNombreDeButMarque());

                    MessageI m = new Message(UUID.randomUUID().toString(), footballeur, new TimeStamp(), prop);
                    msg[i] = m;
                }

                topics = plugin.getTopics();
                
                //choose 5 topics
                j = 0;
                for (int i = 0; i < 5; i++) {
                   topcsToPublish[j++] = topics[(new Random()).nextInt(topics.length)];
                }

                //this.sleep(30);
                for (int i = 0; i < msg.length; i++) {
                    plugin.publish(msg[i], topcsToPublish);
                    //this.sleep(10);
                }

                
                //choose 1 message
                int footballeursMessage = (new Random().nextInt(footballeurs.size()));
                Footballeur footballeur = (Footballeur)footballeurs.get(footballeursMessage).clone();
                footballeur.setMessage("message "+MESSAGE++);
                Proprieties prop = new Proprieties();
                prop.putProp("nom", footballeur.getNom());
                prop.putProp("club", footballeur.getClub());
                prop.putProp("vitesse", footballeur.getVitesse());
                prop.putProp("taille", footballeur.getTaille());
                prop.putProp("tempsDeJeu", footballeur.getTempsDeJeu());
                prop.putProp("nombreDeButMarque", footballeur.getNombreDeButMarque());
                MessageI m = new Message(UUID.randomUUID().toString(), footballeur, new TimeStamp(), prop);

                for (int i = 0; i < 3; i++) {
                    plugin.publish(m, topicsList.get((new Random()).nextInt(topicsList.size())));
                    //this.sleep(10);
                }

                //this.sleep(50);

                toSend = (new Random()).nextInt(7)+15;
                msg = new MessageI[toSend];
                for (int i = 0; i < msg.length; i++) {
                    footballeursMessage = (new Random().nextInt(footballeurs.size()));
                    footballeur = (Footballeur)footballeurs.get(footballeursMessage).clone();
                    footballeur.setMessage("message "+MESSAGE++);
                    prop = new Proprieties();
                    prop.putProp("nom", footballeur.getNom());
                    prop.putProp("club", footballeur.getClub());
                    prop.putProp("vitesse", footballeur.getVitesse());
                    prop.putProp("taille", footballeur.getTaille());
                    prop.putProp("tempsDeJeu", footballeur.getTempsDeJeu());
                    prop.putProp("nombreDeButMarque", footballeur.getNombreDeButMarque());

                    m = new Message(UUID.randomUUID().toString(), footballeur, new TimeStamp(), prop);
                    msg[i] = m;
                }

                for (String topic : topicsList) {
                    plugin.publish(msg, topic);
                    //this.sleep(10);
                }

                for (int i = 0; i < 2; i++) {
                    int rand  = (new Random()).nextInt(topicsList.size());
                    String top = topicsList.get(rand);
                    topicsList.remove(rand);
                    if(plugin.isTopic(top)) {
                        plugin.destroyTopic(top);
                        //System.out.println("destroying topics");
                    }
                }
                
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
	 * sleep with random amount of time between 10 and millisecons param
	 * 
	 * @param millisecons the uperbound of amount of sleeping
	 * @throws Exception
	 */
    public void sleep(int millisecons) throws Exception {
        Thread.sleep((new Random()).nextInt(millisecons)+10);
    }
}