# README for Apache Fortress Demo End-to-End Security Example

## Last updated: March 15, 2015

## Prerequisites for target machine:
1. Debian or Redhat Liunx machine with OpenSSL installed.
2. Java 7 (or greater) sdk
3. Git
4. Apache Maven 3
5. Firefox Web Browser
6. Completion of the [Apache Fortress Ten Minute Guide](http://symas.com/javadocs/apache-fortress-core/org/apache/directory/fortress/core/doc-files/ten-minute-guide.html):
    * [Setup Apache Directory Server](http://symas.com/javadocs/apache-fortress-core/org/apache/directory/fortress/core/doc-files/apache-directory-server.html)
    * [Setup Apache Directory Studio](http://symas.com/javadocs/apache-fortress-core/org/apache/directory/fortress/core/doc-files/apache-directory-studio.html)
    * [Build Apache Fortress Core](http://symas.com/javadocs/apac1he-fortress-core/org/apache/directory/fortress/core/doc-files/apache-fortress-core.html)
    * [Build Apache Fortress Realm](http://symas.com/javadocs/apache-fortress-core/org/apache/directory/fortress/core/doc-files/apache-fortress-realm.html)
    * [Setup Apache Tomcat Web Server](http://symas.com/javadocs/apache-fortress-core/org/apache/directory/fortress/core/doc-files/apache-tomcat.html)
    * [Build Apache Fortress Web](http://symas.com/javadocs/apache-fortress-core/org/apache/directory/fortress/core/doc-files/apache-fortress-web.html)


## Instructions for downloading app and generating the install doc:

1. [Download ZIP](github: https://github.com/shawnmckinney/apache-fortress-demo/archive/master.zip)

2. Extract the contents zip archive to your local machine.

3. cd apache-fortress-demo-master

4. Set java and maven home env variables.

5. Generate the package javadoc which contains the demo installation instructions.
 ````
mvn javadoc:javadoc
 ````

 note: if using java 8, add this param to the pom.xml:
 ```
<plugin>
    ...
    <artifactId>maven-javadoc-plugin</artifactId>
    <configuration>
        <additionalparam>-Xdoclint:none</additionalparam>
        ...
    </configuration>
</plugin>
 ```

6. Point your web browser to the javadoc: [Apache Fortress Overview](target/site/apidocs/overview-summary.html)

7. Follow the steps under 'Demo Installation Instructions':
    * [Heartbleed Bug](target/site/apidocs/doc-files/opensslheartbleed.html)
    * [Managing PKI Keys](target/site/apidocs/doc-files/keys.html)
    * [Set Hostname Entry](target/site/apidocs/doc-files/hosts.html)
    * [Apache Directory SSL](target/site/apidocs/doc-files/apache-directory-ssl.html)
    * [Apache Fortress Core SSL](target/site/apidocs/doc-files/apache-fortress-core-ssl.html)
    * [Install MySQL](target/site/apidocs/doc-files/mysql.html)
    * [Apache Tomcat SSL](target/site/apidocs/doc-files/apache-tomcat-ssl.html)
    * [Apache Fortress Demo](target/site/apidocs/doc-files/apache-fortress-demo.html)