This is free and unencumbered software released into the public domain.
_____________________________________________________________________________
#############################################################################
README for Apache Fortress Demo End-to-End Security Example
Last updated: January 27, 2015
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
1. Download fortress core package: https://git-wip-us.apache.org/repos/asf?p=directory-fortress-core.git
2. Extract
3. Set JAVA_HOME
4. Set M2_HOME
5. Set ANT_HOME
6. Run this command from the root package:
7 $M2_HOME/bin/mvn clean install -DskipTests
8. Download package: https://github.com/shawnmckinney/apache-fortress-demo/archive/master.zip
9. Extract
10. Run this command from the root package:
11 $M2_HOME/bin/mvn javadoc:javadoc
12. Open the help document: /*package home*/target/site/apidocs/overview-summary.html
13. Follow the steps under 'Sections required for installation'

Note: This tutorial is a work-in-progress.