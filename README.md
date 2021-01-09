# README for Apache Fortress Demo End-to-End Security Example

## Security Layers in this Demo
 ![Apache Fortress Demo Security Layers](src/main/javadoc/doc-files/Demo2-Block-Diagram.png  "Apache Fortress Demo")

## Document Overview
 This document is the starting point for the apache fortress demo web app.  It provides directions to download the source package from github, and generate a java document that contains the actual installation instructions.

## Prerequisites for target environment
1. Debian or Redhat Liunx variant machine with OpenSSL installed.
2. Java 8
3. Apache Maven 3++
4. Apache Tomcat 8++
5. Firefox Web Browser
6. Successful installation (of an LDAP server) and passing integration tests in either of these two Apache Fortress Core Quickstart guides:
    * *SECTION 3. Apache Fortress Core Integration Test* in [OPENLDAP QUICKSTART](https://github.com/apache/directory-fortress-core/blob/master/README-QUICKSTART-SLAPD.md)
    * *SECTION 4. Apache Fortress Core Integration Test* in [APACHEDS QUICKSTART](https://github.com/apache/directory-fortress-core/blob/master/README-QUICKSTART-APACHEDS.md)
   - OR -
    * Run the slapd playbook: [README.md](https://gitlab.symas.net/symas-public/ansible-apache-fortress/-/blob/master/README.md)

 These instructions have been recently modified to use either openldap and apacheds LDAP servers.  Be sure to choose the correct instructions, either [Apache Directory SSL](http://shawnmckinney.github.io/apache-fortress-demo/apidocs/doc-files/apache-directory-ssl.html) or [OpenLDAP SSL](http://shawnmckinney.github.io/apache-fortress-demo/apidocs/doc-files/openldap-ssl.html).

## (NEW) Instructions for installing automatically with Ansible:

 * Contains Ansible playbooks to perform all of the steps to install and setup Apache Fortress Demo, including OpenLDAP, Apache Tomcat, MariaDB, generating certs, etc.
 * [README.md](https://gitlab.symas.net/symas-public/ansible-apache-fortress/-/blob/master/README.md)

## Instructions for installing manually:

1. [Download ZIP](https://github.com/shawnmckinney/apache-fortress-demo/archive/master.zip)

2. Extract the contents to your local machine.

3. cd apache-fortress-demo-master

4. Set java and maven home env variables.

5. Generate the java document that contains the demo installation instructions:

 ````maven
 mvn javadoc:javadoc
 ````

6. Perform these steps:
 * [Managing PKI Keys](http://shawnmckinney.github.io/apache-fortress-demo/apidocs/doc-files/keys.html)
 * [Set Hostname Entry](http://shawnmckinney.github.io/apache-fortress-demo/apidocs/doc-files/hosts.html)
 * [Apache Directory SSL](http://shawnmckinney.github.io/apache-fortress-demo/apidocs/doc-files/apache-directory-ssl.html) or [OpenLDAP SSL](http://shawnmckinney.github.io/apache-fortress-demo/apidocs/doc-files/openldap-ssl.html)
 * [Apache Fortress Core SSL](http://shawnmckinney.github.io/apache-fortress-demo/apidocs/doc-files/apache-fortress-core-ssl.html)
 * [Install MySQL](http://shawnmckinney.github.io/apache-fortress-demo/apidocs/doc-files/mysql.html)
 * [Apache Tomcat SSL](http://shawnmckinney.github.io/apache-fortress-demo/apidocs/doc-files/apache-tomcat-ssl.html)
 * [Apache Fortress Demo](http://shawnmckinney.github.io/apache-fortress-demo/apidocs/doc-files/apache-fortress-demo.html)
