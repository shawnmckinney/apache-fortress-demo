This is free and unencumbered software released into the public domain.
_____________________________________________________________________________
#############################################################################
README for Fortress Demo # 2 End-to-End Security Example
Last updated: August 23, 2014
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
1. Download package: https://github.com/shawnmckinney/fortressdemo2/archive/master.zip
2. Extract
3. Set JAVA_HOME
4. Set M2_HOME
5. Set ANT_HOME
6. Run this command from the root package:
$ $M2_HOME/bin/mvn javadoc:javadoc
7. Open the help document: /*package home*/target/site/apidocs/overview-summary.html
8. Follow the steps under 'Sections required for installation'