This is free and unencumbered software released into the public domain.
_____________________________________________________________________________
#############################################################################
README for Apache Fortress Demo End-to-End Security Example
Last updated: February 15, 2015
_____________________________________________________________________________
#############################################################################
# Prerequisites for target machine:
#############################################################################
1. Debian or Redhat Liunx machine with OpenSSL installed.

2. Java 7 (or greater) sdk

3. Git

4. Apache Maven 3

5. Firefox Web Browser

6. Apache Fortress Ten Minute Guide:

    Follow the [directory-fortress-core.git] / README-TEN-MINUTE-GUIDE.txt
    https://git-wip-us.apache.org/repos/asf?p=directory-fortress-core.git.

    Complete these steps under 'Navigation Links':
      a. Setup Apache Directory Server
      b. Setup Apache Directory Studio
      c. Build Apache Fortress Core
      d. Build Apache Fortress Realm
      e. Setup Apache Tomcat Web Server
      f. Build Apache Fortress Rest (this step not required)
      g. Build Apache Fortress Web

#############################################################################
# Instructions for downloading app and generating the install doc:
#############################################################################

1. Download apache-fortress-demo package from github: https://github.com/shawnmckinney/apache-fortress-demo/archive/master.zip

2. Extract the zip archive to your local machine.

3. cd to apache-fortress-demo-master

4. Set JAVA_HOME and M2_HOME

5. Run this command from the root package:
# mvn javadoc:javadoc

6. Point your web browser to the following location:
file:///[apache-fortress-demo]//target/site/apidocs/overview-summary.html

(where [apache-fortress-demo] is location of this package)

7. Follow the steps under 'Demo Installation Instructions':
 a. Heartbleed Bug
 b. Managing PKI Keys
 c. Set Hostname Entry
 d. Apache Directory SSL
 e. Apache Fortress Core SSL
 d. Install MySQL
 e. Apache Tomcat SSL
 f. Apache Fortress Demo
