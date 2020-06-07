#!/bin/bash
awk -F "|" '/brokerURI_ONE/{s=s+1}END     {print "  Nombre de brokers      : "s}' brokerURI* 
awk -F "|" '/subscriberURI_ONE/{s=s+1}END {print "  Nombre de subscribers  : "s}' subscriberURI* 
awk -F "|" '/publisherURI_ONE/{s=s+1}END  {print "  Nombre de publishers   : "s}' publisherURI*

echo
echo "------------------------brokers------------------------------"
awk -F "|" '/request/{s=s+1}END        {print "  Total des requettes recues       : "s}' brokerURI* 
awk -F "|" '/create_topic/{s=s+1}END   {print "  Nombre de topics crees           : "s}' brokerURI* 
awk -F "|" '/destroy_topic/{s=s+1}END  {print "  Nombre de topics detruits        : "s}' brokerURI*
echo 
awk -F "|" '/publications/{s=s+1} END  {print "  Nombre de publications de message recues                    : "s}' brokerURI*
awk -F "|" '/distributions/{s=s+1} END {print "  Nombre de distributions de message reussite aux Subscribers : "s}' brokerURI*
echo
awk -F "|" '/publications/{s=s+$3} END {print "  Total des messages recues                       : "s}' brokerURI*
awk -F "|" '/distributions/{s=s+$3} END{print "  Total des messages distribues au  Subscribers   : "s}' brokerURI*
echo
awk -F "|" '/notify_messages/{s=s+1}  END{print "  Total des notification effectuees sur les Brokers : "s}' brokerURI*
awk -F "|" '/notify_messages/{s=s+$3} END{print "  Total des messages notifies                       : "s}' brokerURI*
awk -F "|" '/notify_topic/{s=s+1} END    {print "  Total des topics notifies                         : "s}' brokerURI*
echo
awk -F "|" '/no_distrib/{s=s+1} END  {print "  distributions en attente  : "s}' brokerURI*
awk -F "|" '/no_distrib/{s=s+$3} END {print "  messages en attente       : "s}' brokerURI*
echo  
awk -F "|" '/dist_treatment/{s=s+1} END {print "  distributions traites            : "s}' brokerURI*
awk -F "|" '/dist_success/{s=s+1} END   {print "  distributions traites reussies : "s}' brokerURI*
echo
echo
echo "------------------------publishers---------------------------"
awk -F "|" '/request/{s=s+1} END      {print "  Total des requettes emises  (publish, createTopic, destroy, isTopic, getTopics)      : "s}' publisherURI*
awk -F "|" '/publications/{s=s+1} END {print "  Nombre de publications effectuees (call publish methods)                             : "s}' publisherURI*
awk -F "|" '/publications/{s=s+$3} END{print "  Nombre de messages publies (somme des messages publiés)                              : "s}' publisherURI*
echo
echo
echo "--------------------------- subscribers----------------------"
awk -F "|" '/request/{s=s+1} END  {print "  Total des requttes emises (subscribe, modifyFilter, unsubscribe, getTopics) : "s}' subscriberURI*
echo
awk -F "|" '/messages/{s=s+1} END  {print "  Nombre de fois la méthode accepte est appelée  : "s}' subscriberURI*
awk -F "|" '/messages/{s=s+$3} END {print "  Total des messages recus                       : "s}' subscriberURI*
echo
awk -F "|" '/subscriptions/{s=s+1} END  {print "  Nombre de fois où une méthode de subscription est appelée    : "s}' subscriberURI*
awk -F "|" '/subscriptions/{s=s+$3} END {print "  Total des topics subscrit                                    : "s}' subscriberURI*
echo
awk -F "|" '/filter/{s=s+1} END           {print "  Nombre de fois où une méthode subscription est appelée avec filtre : "s}' subscriberURI*
awk -F "|" '/fil_modification/{s=s+1} END {print "  Nombre de fois une modification de filtre est appelée              : "s}' subscriberURI*
echo
echo
echo "--------------------------THREADS BROKERS----------------------------"
awk -F "|" 'BEGIN{s=0}  /thread_read_access/{s=s+$3} END  {print "  thread_read_access   : "s}' brokerURI*
awk -F "|" 'BEGIN{s=0}  /thread_read_access/{s=s+$3} END  {print "  thread_write_access  : "s}' brokerURI*
echo
awk -F "|" 'BEGIN{s=0}  /thread_write_access_notify/{s=s+$3} END {print "  thread_write_access_notify  : "s}' brokerURI*
echo
awk -F "|" 'BEGIN{s=0}  /thread_distribution/{s=s+$3} END {print "  thread_distribution  : "s}' brokerURI*
echo
awk -F "|" 'BEGIN{s=0}  /thread_/{s=s+$3} END             {print "  threads total        : "s}' brokerURI*



