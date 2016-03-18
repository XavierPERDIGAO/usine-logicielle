#!/bin/bash

#conf tomcat
sudo sed -r 's/<Connector port="[0-9]*"/<Connector port="9020"/' /var/lib/tomcat7/conf/server.xml >> ~/test
sudo mv ~/test /var/lib/tomcat7/conf/server.xml

sudo sed -r 's/#JAVA_HOME=.*/JAVA_HOME=\/usr\/lib\/jvm\/java-8-openjdk-amd64\/jre/' /etc/default/tomcat7 >> ~/tomcat7_bis
sudo mv ~/tomcat7_bis /etc/default/tomcat7

#conf tomcat admin
sudo sed -i '/<tomcat-users>/a\ <user username="admin" password="admin" roles="manager-gui,admin-gui"/>' /etc/tomcat7/tomcat-users.xml