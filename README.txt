This is free and unencumbered software released into the public domain.
_____________________________________________________________________________
#############################################################################
README for Apache Fortress Demo End-to-End Security Example
Last updated: February 3, 2015
_____________________________________________________________________________
#############################################################################
# Prerequisites:
#############################################################################
1. Debian or Redhat Liunx machine with <a href="http://www.openssl.org">OpenSSL</a> installed.
2. Java 7 (or greater) for both compilation (jdk) and runtime (jre) required for every software package covered by this tutorial.
3. Git
4. Apache Maven 3.
5. Firefox Web Browser
6. Completion of the <b>[directory-fortress-core.git] / README-TEN-MINUTE-GUIDE.txt</b>, located in the <a href="https://git-wip-us.apache.org/repos/asf?p=directory-fortress-core.git">Apache Fortress Core Git Repo</a>.
        (You may skip the section entitled: 'Build Apache Fortress Rest')

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
 a. <a href="./opensslheartbleed.html">Heartbleed Bug</a>
 b. <a href="./keys.html">Managing PKI Keys</a>
 c. <a href="./hosts.html">Set Hostname Entry</a>
 d. <a href="./apache-directory-ssl.html">Apache Directory SSL</a>
 e. <a href="./apache-fortress-core-ssl.html">Apache Fortress Core SSL</a>
 d. <a href="./mysql.html">Install MySQL</a>
 e. <a href="./apache-tomcat-ssl.html">Apache Tomcat SSL</a>
 f. <a href="./apache-fortress-demo.html">Apache Fortress Demo</a>
