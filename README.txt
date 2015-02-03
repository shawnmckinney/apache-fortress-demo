This is free and unencumbered software released into the public domain.
_____________________________________________________________________________
#############################################################################
README for Apache Fortress Demo End-to-End Security Example
Last updated: February 3, 2015
_____________________________________________________________________________
#############################################################################
# Prerequisites:
#############################################################################
1. Linux machine (tested on Ubuntu 12.04 & Centos 6.3)
2. Internet access to retrieve dependencies from online Maven repo.
3. Java SDK Version 7 (or greater), Apache Ant 1.8 (or greater) and Apache Maven.
4. Firefox browser installed (for automated unit testing with Apache Solenium).
5. All other packages will be covered inside the help document.
#############################################################################
# Instructions for downloading app and generating the install doc:
#############################################################################
1. Clone the directory-fortress-core from apache git repo:
# git clone https://git-wip-us.apache.org/repos/asf/directory-fortress-core.git
# cd directory-fortress-core/
2. Set JAVA_HOME
3. Set M2_HOME
4. Run this command from the root package:
# $M2_HOME/bin/mvn clean install -DskipTests
5. Clone the directory-fortress-realm from apache git repo:
# git clone https://git-wip-us.apache.org/repos/asf/directory-fortress-realm.git
# cd directory-fortress-realm/
# $M2_HOME/bin/mvn install -DskipTests
6. Download apache-fortress-demo package from github: https://github.com/shawnmckinney/apache-fortress-demo/archive/master.zip
7. Extract the zip archive to your local machine.
8. Run this command from the root package:
# $M2_HOME/bin/mvn javadoc:javadoc
9. Open the help document: /*package home*/target/site/apidocs/overview-summary.html
10. Follow the steps under 'Sections required for installation'

Note: This tutorial is a work-in-progress.