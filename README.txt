This is free and unencumbered software released into the public domain.
_____________________________________________________________________________
#############################################################################
README for Apache Fortress Demo End-to-End Security Example
Last updated: February 15, 2015
_____________________________________________________________________________
#############################################################################
# Prerequisites:
#############################################################################
1. Debian or Redhat Liunx machine with <a href="http://www.openssl.org">OpenSSL</a> installed.

2. Java 7 (or greater) for both compilation (jdk) and runtime (jre) required for every software package covered by this tutorial.

3. Git installed to target machine.

4. Apache Maven 3 installed to target machine.

5. Firefox Web Browser installed to target machine.

6. Apache Fortress installed to target machine.

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

3. Set JAVA_HOME and M2_HOME

4. Run this command from the root package:
# $M2_HOME/bin/mvn javadoc:javadoc

5. Open the help document: /*package home*/target/site/apidocs/overview-summary.html

6. Follow the steps under 'Sections required for installation':
 a. Heartbleed Bug
 b. Managing PKI Keys
 c. Set Hostname Entry
 d. Apache Directory SSL
 e. Apache Fortress Core SSL
 d. Install MySQL
 e. Apache Tomcat SSL
 f. Apache Fortress Demo
