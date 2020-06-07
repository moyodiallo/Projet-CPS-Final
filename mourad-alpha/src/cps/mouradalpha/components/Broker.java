package cps.mouradalpha.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cps.mouradalpha.connectors.ConnectorNotification;
import cps.mouradalpha.connectors.ConnectorReceptionCI;
import cps.mouradalpha.interfaces.ManagementCI;
import cps.mouradalpha.interfaces.MessageFilterI;
import cps.mouradalpha.interfaces.MessageI;
import cps.mouradalpha.interfaces.NotificationCI;
import cps.mouradalpha.interfaces.PublicationCI;
import cps.mouradalpha.interfaces.ReceptionCI;
import cps.mouradalpha.ports.InBoundPortManagement;
import cps.mouradalpha.ports.InBoundPortNotification;
import cps.mouradalpha.ports.InBoundPortPublication;
import cps.mouradalpha.ports.OutBoundPortNotification;
import cps.mouradalpha.ports.OutboundPortReception;
import cps.mouradalpha.util.MyConcurrentHashMap;
import cps.mouradalpha.util.MyConcurrentLinkedQueue;
import fr.sorbonne_u.components.AbstractComponent;
import fr.sorbonne_u.components.annotations.OfferedInterfaces;
import fr.sorbonne_u.components.annotations.RequiredInterfaces;
import fr.sorbonne_u.components.cvm.AbstractCVM;
import fr.sorbonne_u.components.exceptions.ComponentShutdownException;
import fr.sorbonne_u.components.exceptions.ComponentStartException;
import fr.sorbonne_u.components.exceptions.InvariantException;
import fr.sorbonne_u.components.exceptions.PostconditionException;
import fr.sorbonne_u.components.exceptions.PreconditionException;
import fr.sorbonne_u.components.ports.PortI;

/**
 * The class <code>Broker</code> implements a component that provides
 * <code>ManagementCI</code> , <code>PublicationCI</code> and
 * <code>NotificationCI</code> services, it requires also
 * <code>ReceptionCI</code> and <code>NotificationCI</code> services
 *
 * @author Alpha Issiaga DIALLO / Mourad TOUATI
 *
 */
@OfferedInterfaces(offered = { ManagementCI.class, PublicationCI.class, NotificationCI.class })
@RequiredInterfaces(required = { ReceptionCI.class, NotificationCI.class })
public class Broker extends AbstractComponent implements ManagementCI, PublicationCI, NotificationCI {

	/**
	 * This class is used to define a subscription for a topic, we store the inboundPortURI, the filter and 
	 * the outboundPortReception as informations in order to reach a subscriber when a message is published.
	 * @author Mourad TOUATI / Alpha Issiaga DIALLO
	 */
	class SubscriptionTopic {
		/**
		 * inboundPortURI of the subscriber
		 */
		public String inboundPortURI;
		/**
		 * the filter that the subscriber want to apply
		 */
		public MessageFilterI mFilterI;
		/**
		 * the outboundPortReception to notify the subscriber when a message is
		 * published for that topic
		 */
		public OutboundPortReception outbound;

		/**
		 * constructor with fields for SubscriptionTopic class
		 * 
		 * @param inboundPortURI inboundPortURI of the subscriber
		 * @param mFilterI       the filter that the subscriber want to apply
		 * @param outbount       the outboundPortReception to notify the subscriber when
		 *                       a message is published for that topic
		 */
		SubscriptionTopic(String inboundPortURI, MessageFilterI mFilterI, OutboundPortReception outbount) {

			this.mFilterI = mFilterI;

			this.inboundPortURI = inboundPortURI;
			this.outbound = outbount;
		}

		@Override
		public boolean equals(Object obj) {
			String sobj = ((SubscriptionTopic) obj).inboundPortURI;
			return this.inboundPortURI.equals(sobj);
		}
	}

	/**
	 * This class is used when a single message or multiple message are published for a specific topic.
	 * we use it to distribute the message(s) for the subscribers 
	 * @author Mourad TOUATI / Alpha Issiaga DIALLO
	 *
	 */
	class MessageOut {
		/**
		 * the topic
		 */
		public String topic;
		/**
		 * the message ( when a single message is published )
		 */
		public MessageI message;
		/**
		 * the messages ( when multiple message are published )
		 */
		public MessageI messages[];
		/**
		 * constructor with fields when a single message is published 
		 * @param topic the topic 
		 * @param message the messages 
		 */
		MessageOut(String topic, MessageI message) {
			this.message = message;
			this.topic = topic;
			this.messages = null;
		}
		/**
		 * constructor with fields when a multiple messages are published 
		 * @param topic the topic 
		 * @param messages the messages 
		 */
		MessageOut(String topic, MessageI messages[]) {
			this.topic = topic;
			this.messages = messages;
			this.message = null;
		}

	}

	// ------------------------------------------------------------------------
	// Constructors and instance variables
	// ------------------------------------------------------------------------

	/**
	 * stors all topics and their availabilty using a boolean true -> the topic
	 * still exist false -> the topic is no longer availble
	 */
    protected MyConcurrentHashMap<String, Boolean> topics;
    protected MyConcurrentHashMap<String, Boolean> topicsLogs;

	/**
	 * for each subscribtion -> create an outBoundPortReception and associate it
	 * with the inboundPort
	 */
	protected MyConcurrentHashMap<String, OutboundPortReception> outBounds;

	/** for a specific topic -> save all subscribtion */
	protected MyConcurrentHashMap<String, MyConcurrentLinkedQueue<SubscriptionTopic>> subscriptions;

	/** a each topic match to many messages */
	protected MyConcurrentHashMap<String, MyConcurrentLinkedQueue<MessageOut>> messagesTopics;

	/** a string prefix that will identify the URI provider. */
	protected String uriPrefix;
	protected String inBoundPortPublication;

	/** URI of the readers' pool of threads. */
	public static final String READ_ACCESS_HANDLER_URI = "rah";
	/** URI of the writers' pool of threads. */
	public static final String WRITE_ACCESS_HANDLER_URI = "wah";

	public static final String READ_ACCESS_HANDLER_URI_NOTIFY = "rah2";
    public static final String WRITE_ACCESS_HANDLER_URI_NOTIFY = "wah2";
    
    public static final String DISTRIBUTION_RUNNABLE = "drun";

	private List<OutBoundPortNotification> notifyRemoteBrokers;
	private String brokersRemoteURI[];

    protected String inBoundPortNotification;
    
    protected static final int NB_THREAD_READ_ACCESS  = 10;
    protected static final int NB_THREAD_WRITE_ACCESS = 7;

    protected static final int NB_THREAD_DISTRIBUTION = 10;

    protected static final int NB_THREAD_WRITE_ACCESS_NOTIFY = 7;

    protected Integer treatment;


	/**
	 * check the invariant of the class on an instance.
	 *
	 * @param c the component to be tested.
	 */
	protected static void checkInvariant(Broker c) {
		assert c.uriPrefix != null : new InvariantException("The URI prefix is null!");
		assert c.isOfferedInterface(ManagementCI.class) : new InvariantException(
				"The URI component should " + "offer the interface ManagementCI!");
		assert c.isOfferedInterface(PublicationCI.class) : new InvariantException(
				"The URI component should " + "offer the interface PublicationCI!");
    }

	protected Broker(String uriPrefix, String inBoundPortManagement, String brokersRemoteURI[]) throws Exception {

        super(uriPrefix, 2,0);

        System.out.println("brokerIn-" + inBoundPortManagement);
        this.treatment = 0;

		this.inBoundPortPublication = uriPrefix + "-port-publication";
		this.inBoundPortNotification = uriPrefix + "-port-notification";

		assert uriPrefix != null : new PreconditionException("uri can't be null!");
		assert inBoundPortManagement != null : new PreconditionException("inBoundPortManagement can't be null!");
		assert inBoundPortPublication != null : new PreconditionException("inBoundPortPublication can't be null");

		this.uriPrefix = uriPrefix;

		this.createNewExecutorService(READ_ACCESS_HANDLER_URI + this.uriPrefix, NB_THREAD_READ_ACCESS, false);
        this.createNewExecutorService(WRITE_ACCESS_HANDLER_URI + this.uriPrefix, NB_THREAD_WRITE_ACCESS, false);

        this.createNewExecutorService(DISTRIBUTION_RUNNABLE+this.uriPrefix,NB_THREAD_DISTRIBUTION,false);


		if (brokersRemoteURI.length > 1) {
			this.createNewExecutorService(WRITE_ACCESS_HANDLER_URI_NOTIFY + this.uriPrefix,NB_THREAD_WRITE_ACCESS_NOTIFY,false);
        }

		notifyRemoteBrokers = new ArrayList<OutBoundPortNotification>();
		this.brokersRemoteURI = brokersRemoteURI;

		/** The outBound of each subscriber */
		this.outBounds = new MyConcurrentHashMap<String, OutboundPortReception>();

		/** Store all topics */
        this.topics     = new MyConcurrentHashMap<String, Boolean>();
        this.topicsLogs = new MyConcurrentHashMap<String, Boolean>();

		/**
		 * The representation of a subscrib in the Broker The same subscriber can have
		 * many subscribs
		 */
		this.subscriptions = new MyConcurrentHashMap<String, MyConcurrentLinkedQueue<SubscriptionTopic>>();

		/** Each topic match to many messages */
		this.messagesTopics = new MyConcurrentHashMap<String, MyConcurrentLinkedQueue<MessageOut>>();

		// Management Port
		PortI p = new InBoundPortManagement(inBoundPortManagement, this,
				this.getExecutorServiceIndex(READ_ACCESS_HANDLER_URI + this.uriPrefix),
				this.getExecutorServiceIndex(WRITE_ACCESS_HANDLER_URI + this.uriPrefix));
		p.publishPort();

		// Publication Port
		p = new InBoundPortPublication(this.inBoundPortPublication, this,
				this.getExecutorServiceIndex(WRITE_ACCESS_HANDLER_URI + this.uriPrefix));
		p.publishPort();

		// Notification Port
		if (brokersRemoteURI.length > 1) {
			p = new InBoundPortNotification(this.inBoundPortNotification, this,
					this.getExecutorServiceIndex(WRITE_ACCESS_HANDLER_URI_NOTIFY + this.uriPrefix));
			p.publishPort();
		}

		if (AbstractCVM.isDistributed) {
			this.executionLog.setDirectory(System.getProperty("user.dir"));
		} else {
			this.executionLog.setDirectory(System.getProperty("user.home"));
		}

		this.tracer.setTitle(uriPrefix);
		this.tracer.setRelativePosition(1, 0);

		Broker.checkInvariant(this);

		assert this.uriPrefix
				.equals(uriPrefix) : new PostconditionException("The URI prefix has not " + "been initialised!");

		assert this.isPortExisting(inBoundPortManagement) : new PostconditionException(
				"The component must have a " + "port with inBoundPortManagement " + inBoundPortManagement);
		assert this.findPortFromURI(inBoundPortManagement).getImplementedInterface()
				.equals(ManagementCI.class) : new PostconditionException(
						"The component must have a " + "port with implemented interface ManagementCI");
		assert this.findPortFromURI(inBoundPortManagement).isPublished() : new PostconditionException(
				"The component must have a " + "port published with URI " + inBoundPortManagement);

		assert this.isPortExisting(inBoundPortPublication) : new PostconditionException(
				"The component must have a " + "port with inBoundPortPublication " + inBoundPortPublication);
		assert this.findPortFromURI(inBoundPortPublication).getImplementedInterface()
				.equals(PublicationCI.class) : new PostconditionException(
						"The component must have a " + "port with implemented interface ManagementCI");
		assert this.findPortFromURI(inBoundPortPublication).isPublished() : new PostconditionException(
				"The component must have a " + "port published with URI " + inBoundPortPublication);

	}

	/**
	 * @see fr.sorbonne_u.components.AbstractComponent#start()
	 */
	@Override
	public void start() throws ComponentStartException {
        this.logMessage("starting Broker component.");
        this.logMessage("brokerURI_ONE");

        this.logMessage("thread_read_access         |"+NB_THREAD_READ_ACCESS);
        this.logMessage("thread_write_access        |"+NB_THREAD_WRITE_ACCESS);
        this.logMessage("thread_write_access_notify |"+NB_THREAD_WRITE_ACCESS_NOTIFY);
        this.logMessage("thread_distribution        |"+NB_THREAD_DISTRIBUTION);

		super.start();

		// Connection to other brokers
		for (String broker : this.brokersRemoteURI) {
			if (!broker.equals(uriPrefix)) {
				try {
					OutBoundPortNotification outNotification = new OutBoundPortNotification(broker + this.uriPrefix,
							this);
					outNotification.localPublishPort();
					notifyRemoteBrokers.add(outNotification);
					String brokerInBound = broker + "-port-notification";
					this.doPortConnection(outNotification.getPortURI(), brokerInBound,
							ConnectorNotification.class.getCanonicalName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void execute() throws Exception {
		super.execute();
	}

	/**
	 * @see fr.sorbonne_u.components.AbstractComponent#finalise()
	 */
	@Override
	public void finalise() throws Exception {
		this.logMessage("stopping Broker component.");
        
        System.out.println("finalise "+this.uriPrefix);
		super.finalise();
	}

	/**
	 * @see fr.sorbonne_u.components.AbstractComponent#shutdown()
	 */
	@Override
	public void shutdown() throws ComponentShutdownException {

		try {
			PortI[] p = this.findPortsFromInterface(ManagementCI.class);
			p[0].unpublishPort();

			p = this.findPortsFromInterface(PublicationCI.class);
			p[0].unpublishPort();

			p = this.findPortsFromInterface(ReceptionCI.class);

			if (p != null) {
				for (PortI portI : p) {
					if (portI instanceof OutboundPortReception) {
						portI.unpublishPort();
					}
				}
			}

			for (OutBoundPortNotification port : this.notifyRemoteBrokers) {
				port.unpublishPort();
				port.doDisconnection();
			}

			if (notifyRemoteBrokers.size() > 0) {
				this.findPortFromURI(this.inBoundPortNotification).unpublishPort();
			}

		} catch (Exception e) {
			throw new ComponentShutdownException(e);
        }

        
        try {
            //Connexion is closed, compute the waiting distributions
            //Purge all waiting distributions
            Set<String> set =  this.topicsLogs.keySet();
            int msgOutSize  = 0;
            Iterator it  = set.iterator();
            while(it.hasNext()){
                String topic = (String)it.next();
                msgOutSize = this.messagesTopics.get(topic).size();
                for(int i=0; i < msgOutSize; i++) {
                    distributeToSubscribers(topic);
                }
            }   
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        this.printExecutionLogOnFile(this.uriPrefix);
        
        super.shutdown();
        System.out.println("shutdown: "+this.uriPrefix);
        System.out.println("treatment "+this.treatment);
	}

	/**
	 * @see fr.sorbonne_u.components.AbstractComponent#shutdownNow()
	 */
	@Override
	public void shutdownNow() throws ComponentShutdownException {
		try {
			PortI[] p = this.findPortsFromInterface(ManagementCI.class);
			p[0].unpublishPort();

			p = this.findPortsFromInterface(PublicationCI.class);
			p[0].unpublishPort();

			p = this.findPortsFromInterface(ReceptionCI.class);
			for (PortI portI : p) {
				if (portI instanceof OutboundPortReception) {
					portI.unpublishPort();
				}
			}

			for (OutBoundPortNotification port : this.notifyRemoteBrokers) {
				port.unpublishPort();
				port.doDisconnection();
			}

			this.findPortFromURI(this.inBoundPortNotification).unpublishPort();

		} catch (Exception e) {
			throw new ComponentShutdownException(e);
		}

		super.shutdownNow();
    }
    

	/**
	 * Create a topic by adding it to the list topics. the part that is responsible
	 * for the creation is synchronized hence only one thread can this method at the
	 * same time. once the topic is created it notify the other brokers.
	 * 
	 * @param topic the topic
	 */
	@Override
	public void createTopic(String topic) throws Exception {
        
		boolean created = false; // Avoid to wait notify so long
		synchronized (topics) {
			if (!topics.containsKey(topic)) {
                topics.put(topic, true);
				created = true;
            }
            
            if(!topicsLogs.containsKey(topic)) {
                topicsLogs.put(topic,true);
            }

			if (!messagesTopics.containsKey(topic)) {
				messagesTopics.put(topic, new MyConcurrentLinkedQueue<MessageOut>());
				subscriptions.put(topic, new MyConcurrentLinkedQueue<SubscriptionTopic>());
            }
            
        }

		// Notify Remote Brokers
		if (created && notifyRemoteBrokers.size() != 0) {
			for (OutBoundPortNotification outBound : notifyRemoteBrokers) {
				outBound.notifyTopic(topic);
				//System.out.println("notifyed " + outBound.getPortURI());
			}
        }

        this.logMessage("request");
		this.logMessage("create_topic | " + topic);
	}

	/**
	 * create topics by adding them to the list, it call the createTopic with a
	 * single topic behind the scene
	 * 
	 * @param topics the topics
	 */
	@Override
	public void createTopic(String[] topics) throws Exception {
		for (String topic : topics) {
			this.createTopic(topic);
        }
        this.logMessage("request");
	}

	/**
	 * destroy a topic by deleting it to the list topics. the part that is
	 * responsible for the destruction is synchronized hence only one thread can
	 * this method at the same time. once the topic is deleted it notify the other
	 * brokers.
	 * 
	 * @param topic the topic
	 */
	@Override
	public void destroyTopic(String topic) throws Exception {
       
		boolean destroyed = false; // Avoid to wait notify so long
		synchronized (topics) {
			if (this.topics.containsKey(topic)) {
				this.topics.remove(topic);
				destroyed = true;
			}
		}

		// Notify Remote Brokers
		if (destroyed && notifyRemoteBrokers.size() != 0) {
			for (OutBoundPortNotification outBound : notifyRemoteBrokers) {
				outBound.notifyDestroyTopic(topic);
			}
        }
        
        this.logMessage("destroy_topic");
        this.logMessage("request");
	}

	/**
	 * check whether the topic is present within the broker(s).
	 * 
	 * @param topic the topic
	 * @return true if the topic is present, false otherwise
	 */
	@Override
	public boolean isTopic(String topic) {
        this.logMessage("request");
		return topics.get(topic) == null ? false : true;
	}

	/**
	 * get the topics available in the broker(s)
	 * 
	 * @return the list of the topics
	 */
	@Override
	public String[] getTopics() {
        this.logMessage("request");
		return (String[]) (this.topics.keySet().toArray(new String[0]));
	}

	/**
	 * add a link between the subscriber and the topic by updating the subscriptions
	 * list
	 * 
	 * @param topic          the topic
	 * @param inboundPortURI the inbound port uri of the subscriber.
	 */
	@Override
	public void subscribe(String topic, String inboundPortURI) throws Exception {
        String outURI = "to_" + inboundPortURI;
		OutboundPortReception s = outBounds.get(inboundPortURI);
		OutboundPortReception bound = null;
		if (s == null) {
			bound = new OutboundPortReception(outURI, this);
			bound.localPublishPort();
			outBounds.put(inboundPortURI, bound);
		} else {
			bound = s;
		}

		// connect the Broker to the subscriber
		if (!this.isPortConnected(outURI)) {
			this.doPortConnection(outURI, inboundPortURI, ConnectorReceptionCI.class.getCanonicalName());
        }
        
		SubscriptionTopic sub = new SubscriptionTopic(inboundPortURI, null, bound);
		if (this.subscriptions.get(topic) != null) {
			if (!subscriptions.get(topic).contains(sub))
				this.subscriptions.get(topic).add(sub);

			// this.logMessage("in subs size "+this.subscriptions.get(topic).size());
        }
        this.logMessage("request");
	}

	/**
	 * add a link between the subscriber and the topics by updating the
	 * subscriptions list
	 * 
	 * @param topic          the topics
	 * @param inboundPortURI the inbound port uri of the subscriber.
	 */
	@Override
	public void subscribe(String[] topics, String inboundPortURI) throws Exception {
		for (String topic : topics) {
			String outURI = "to_" + inboundPortURI;
            OutboundPortReception s = outBounds.get(inboundPortURI);
            OutboundPortReception bound = null;
            if (s == null) {
                bound = new OutboundPortReception(outURI, this);
                bound.localPublishPort();
                outBounds.put(inboundPortURI, bound);
            } else {
                bound = s;
            }

            // connect the Broker to the subscriber
            if (!this.isPortConnected(outURI)) {
                this.doPortConnection(outURI, inboundPortURI, ConnectorReceptionCI.class.getCanonicalName());
            }
            
            SubscriptionTopic sub = new SubscriptionTopic(inboundPortURI, null, bound);
            if (this.subscriptions.get(topic) != null) {
                if (!subscriptions.get(topic).contains(sub))
                    this.subscriptions.get(topic).add(sub);

                // this.logMessage("in subs size "+this.subscriptions.get(topic).size());
            }
        }
        this.logMessage("request");
	}

	/**
	 * add a link between the subscriber and the topic by updating the subscriptions
	 * list
	 * 
	 * @param topic          the topic
	 * @param filter         the filter
	 * @param inboundPortURI the inbound port uri of the subscriber.
	 */
	@Override
	public void subscribe(String topic, MessageFilterI filter, String inboundPortURI) throws Exception {

		String outURI = "to_" + inboundPortURI;
		OutboundPortReception s = outBounds.get(inboundPortURI);
		OutboundPortReception bound = null;
		if (s == null) {
			bound = new OutboundPortReception(outURI, this);
			bound.localPublishPort();
			outBounds.put(inboundPortURI, bound);
		} else {
			bound = s;
		}

		// connect the Broker to the subscriber
		if (!this.isPortConnected(outURI)) {
			this.doPortConnection(outURI, inboundPortURI, ConnectorReceptionCI.class.getCanonicalName());
        }
        
		SubscriptionTopic sub = new SubscriptionTopic(inboundPortURI, filter, bound);
		if (this.subscriptions.get(topic) != null) {
			if (!subscriptions.get(topic).contains(sub))
				this.subscriptions.get(topic).add(sub);

			// this.logMessage("in subs size "+this.subscriptions.get(topic).size());
        }
        this.logMessage("subscription Topic(" + topic + ") inPort(" + inboundPortURI + ")");
        this.logMessage("request");
    }

	/**
	 * change the filter previously used in the subscription
	 * 
	 * @param topic          the topic
	 * @param newFilter      the new filter
	 * @param inboundPortURI the inbound port uri of the subscriber.
	 */
	@Override
	public void modifyFilter(String topic, MessageFilterI newFilter, String inboundPortURI) {
		try {
			this.subscriptions.get(topic).remove(new SubscriptionTopic(inboundPortURI, null, null));
			this.subscriptions.get(topic)
					.add(new SubscriptionTopic(inboundPortURI, newFilter, this.outBounds.get(inboundPortURI)));
		} catch (Exception e) {
			//System.err.println("false filter of subscription modification");
        }
        this.logMessage("request");
	}

	/**
	 * unsubscribe from a topic so that the subscriber will not receive messages for
	 * that topic anymore.
	 * 
	 * @param topic          the topic
	 * @param inboundPortURI the inbound port uri of the subscriber.
	 */
	@Override
	public void unsubscribe(String topic, String inboundPortURI) {
		try {
			if(this.subscriptions.get(topic).contains(new SubscriptionTopic(inboundPortURI, null, null)))
			    this.subscriptions.get(topic).remove(new SubscriptionTopic(inboundPortURI, null, null));

		} catch (Exception e) {
			//System.err.println("false Unsubscription");
        }
        this.logMessage("request");
	}

	/**
	 * publish a message in a specific topic, the idea is to start by notifying
	 * other brokers about the new message before starting distributing it to the
	 * subscriber because the the distribution is a heavy operation.
	 * 
	 * @param m     the message
	 * @param topic the topic
	 */
	@Override
	public void publish(MessageI m, String topic) throws Exception {
        
		// Notify Remote Brokers
		if (notifyRemoteBrokers.size() != 0) {
			for (OutBoundPortNotification outBound : notifyRemoteBrokers) {
				outBound.notifyPublish(m, topic);
			}
		}
        
		try {
			this.messagesTopics.get(topic).add(new MessageOut(topic, m));
			distribute(topic);
		} catch (Exception e) {
			//System.err.println("topic doesn't exist");
        }
        
        this.logMessage("request");
        this.logMessage("publications|1| " + m.getPlayload() + " |is publised in topic | " + topic);
	}

	/**
	 * publish a message in a specific topics, it call the publish method behind the
	 * scene
	 * 
	 * @param m      the message
	 * @param topics the topics
	 */
	@Override
	public void publish(MessageI m, String[] topics) throws Exception {

		for (String topic : topics) {
            // Notify Remote Brokers
            if (notifyRemoteBrokers.size() != 0) {
                for (OutBoundPortNotification outBound : notifyRemoteBrokers) {
                    outBound.notifyPublish(m, topic);
                }
            }
            
            try {
                this.messagesTopics.get(topic).add(new MessageOut(topic, m));
                distribute(topic);
            } catch (Exception e) {
                //System.err.println("topic doesn't exist");
            }
        }
        
        this.logMessage("request");
        this.logMessage("publications|1| " + m.getPlayload());
	}

	/**
	 * publish messages in a specific topic, it call the publish method behind the
	 * scene
	 * 
	 * @param ms    the message
	 * @param topic the topics
	 */
	@Override
	public void publish(MessageI[] ms, String topic) throws Exception {

		//Notify Remote Brokers
        if(notifyRemoteBrokers.size() != 0) {
            for (OutBoundPortNotification outBound : notifyRemoteBrokers) {
                outBound.notifyPublish(ms,topic);
            }
        }

		try {
			this.messagesTopics.get(topic).add(new MessageOut(topic, ms));
            distribute(topic);
		} catch (Exception e) {
			//System.err.println("topic doesn't exist");
        }

        this.logMessage("publications|" + ms.length + " | is publised in topic | " + topic);
        this.logMessage("request");
        
	}

	/**
	 * publish messages in a specific topics, it call the publish method behind the
	 * scene
	 * 
	 * @param ms     the message
	 * @param topics the topics
	 */
	@Override
	public void publish(MessageI[] ms, String[] topics) throws Exception {

		for (String topic : topics) {
            //Notify Remote Brokers
            if(notifyRemoteBrokers.size() != 0) {
                for (OutBoundPortNotification outBound : notifyRemoteBrokers) {
                    outBound.notifyPublish(ms,topic);
                }
            }

            try {
                this.messagesTopics.get(topic).add(new MessageOut(topic, ms));
                distribute(topic);
            } catch (Exception e) {
                System.err.println("topic doesn't exist");
            }
        }
        
        this.logMessage("publications|" + ms.length);
        this.logMessage("request");
	}

	@Override
	public String getPublicationPortURI() throws Exception {
        this.logMessage("request");
		return this.inBoundPortPublication;
    }
    
    
    public void distribute(String topic) {
        this.logMessage("dist_treatment");
        synchronized(treatment){
            treatment++;
        }
        this.getExecutorService(DISTRIBUTION_RUNNABLE+this.uriPrefix).execute(new Runnable(){
            @Override
            public void run() {
                distributeToSubscribers(topic);
            }
        });
    }

	/**
	 * it called when a new message is published, basically it distribute the
	 * message for every subscriber and eventually passe through a filter
	 * 
	 * @param topic the topic where the message is published
	 */
    private void distributeToSubscribers(String topic) {
		
		MessageOut msg = this.messagesTopics.get(topic).poll();

		if (msg == null) {
			return;
        }

        MyConcurrentLinkedQueue<SubscriptionTopic> receiver = this.subscriptions.get(topic);
        for(int i = 0 ; i < receiver.size();  i++) {

            SubscriptionTopic sub = null;
            try {
                sub = receiver.get(i);
            } catch (IndexOutOfBoundsException e) {
                return;
            }
            

            if (sub.mFilterI != null) {
                if(msg.messages != null) {
                    
                    ArrayList<MessageI> messages = new ArrayList<MessageI>();
                    for (MessageI messageI : msg.messages) {
                        if (messageI != null && messageI.getProprieties() != null && sub.mFilterI.filtrer(messageI)) {
                            messages.add(messageI);
                        }
                    }

                    if(messages.size() > 0) {
                        try {

                            if(!this.isStarted()){
                                throw new Exception();
                            }
                            sub.outbound.acceptMessages(messages.toArray(new MessageI[0]));
                            this.logMessage("distributions| " + msg.messages.length +"|filter");
                           
                        }catch(AssertionError e) { //catching when this.isStarted() == false
                            this.logMessage("no_distrib|1|"+topic+"|filter");
                        }catch (Exception e) {
                            //e.printStackTrace();
                            //System.err.println("fail to distribute");
                            this.logMessage("no_distrib|"+msg.messages.length+"|filter");
                        }   
                    }

                }else if (msg.message != null && msg.message.getProprieties() != null && sub.mFilterI.filtrer(msg.message)) {
                    try {

                        if(!this.isStarted()){
                            throw new Exception();
                        }
                        sub.outbound.acceptMessage(msg.message);
                        this.logMessage("distributions|1|"+topic+"|filter");

                    }catch(AssertionError e) { //catching when this.isStarted() == false
                        this.logMessage("no_distrib|1|"+topic+"|filter");
                    }catch (Exception e) {
                        //e.printStackTrace();
                        //System.err.println("fail to distribute");
                        this.logMessage("no_distrib|1|"+topic+"|filter");
                    }
                }

            } else {

                try {
                    if(!this.isStarted()) {
                        throw new Exception();
                    }
                    if(msg.message != null) {
                        sub.outbound.acceptMessage(msg.message);
                        this.logMessage("distributions|1| " + topic);
                    }else {
                        sub.outbound.acceptMessages(msg.messages);
                        this.logMessage("distributions| " + msg.messages.length);
                    }

                }catch(AssertionError e) { //catching when this.isStarted() == false
                    this.logMessage("no_distrib|1|"+topic+"|filter");
                }catch (Exception e) {
                    //System.err.println("fail to distribute");
                    //e.printStackTrace();
                    if(msg.messages!=null)
                        this.logMessage("no_distrib|"+msg.messages.length);
                    else
                        this.logMessage("no_distrib|1|"+topic);
                }
            }
        } 
        if(this.isStarted())
            this.logMessage("dist_success");
    }

	/**
	 * this method is used to notify other brokers that are in different jvm that a
	 * new topic is created hence update there structures
	 * 
	 * @param topic the topic created
	 */
	@Override
	public void notifyTopic(String topic) throws Exception {
		this.logMessage("notify_topic " + topic);
		synchronized (topics) {
			if (!topics.containsKey(topic)) {
				topics.put(topic, true);
			}

			if (!messagesTopics.containsKey(topic)) {
				messagesTopics.put(topic, new MyConcurrentLinkedQueue<MessageOut>());
				subscriptions.put(topic, new MyConcurrentLinkedQueue<SubscriptionTopic>());
			}
		}
	}

	/**
	 * this method is used to notify other brokers that are in different jvm that a
	 * new message for a specific topic is published hence send it to their
	 * subscriber
	 * 
	 * @param m     the message
	 * @param topic the topic
	 * 
	 */
	@Override
	public void notifyPublish(MessageI m, String topic) throws Exception {
		try {
			this.messagesTopics.get(topic).add(new MessageOut(topic, m));
			distribute(topic);
		} catch (Exception e) {
			//System.err.println("topic doesn't exist");
        }
        this.logMessage("notify_messages |1| " + m.getPlayload() + " is publised in topic | " + topic);
	}

	/**
	 * this method is used to notify other brokers that are in different jvm that a
	 * topic is destroyed hence update their structures
	 * 
	 * @param topic the topic
	 * 
	 */
	@Override
	public void notifyDestroyTopic(String topic) throws Exception {
		synchronized (topics) {
			if (this.topics.containsKey(topic)) {
				this.topics.remove(topic);
			}
		}
	}

    @Override
    public void notifyPublish(MessageI[] ms, String topic) throws Exception {
		try {
            this.messagesTopics.get(topic).add(new MessageOut(topic, ms));
			distribute(topic);
		} catch (Exception e) {
			//System.err.println("topic doesn't exist");
        }
        this.logMessage("notify_messages | " + ms.length + " | is publised in topic | " + topic);
    }

}