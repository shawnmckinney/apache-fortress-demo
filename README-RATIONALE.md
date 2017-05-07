# RATIONALE for Apache Fortress Demo End-to-End Security Example

### Introduction

This application corresponds with a technical talk that has been given many times.  It contains a program that demonstrates end-to-end security of a typical java web app:
 - apache wicket web application simulating business logic with links, buttons, dials, etc...
 - relational database for the business data that must be secured
 - ldap server to store the security policies

What is needed to run this demo is either included by this project, or referenced by it.  The intent is that you can follow these instructions, and learn how to do end-to-end security.

### Rationale for the apache fortress demo application

The idea for this talk started several years back, when I first began working full-time with Symas. I was working on a project that spanned multiple companies with my friend and colleague, John Field, who's a security architect at EMC, now Pivotal.

At the time, we were working on a process to migrate legacy COBOL apps from running on their native IBM z/Series mainframe platform to run on top of open systems architectures, i.e. Linux.

These were massive programs with millions of lines of code, built over decades. Their conversion processes required mimicking the mainframe's legendary security controls onto Linux platforms, using what was available to us via native and non-native security controls.

This meant dealing with a multitude of security concerns across every tier of the system and into many of its sub-layers as well. Mandatory access controls were enforced on every node in the system.

Linux systems had to be hardened to the nth degree and at the same time, multiple grains of authorization were required within the platform layers. Fortunately, everything we needed to do all this was already readily available and usable, and easily found within the public domain.
Only open, established, and timeworn practices were being targeted. That is, technologies released under permissible licenses, like the Apache software license, and these things were allowed into the final design. Our problem wasn't with how to design the security system, per se, nor how to build it. Strangely, those were the easy parts.

The hard part for us was how do we convey the contents of its complex design to others in a way that is understandable? Because, many of us are not, shall we say, security afflicted, so despite recommending only best practices, their concepts remain arcane, complicated, and generally not known to the masses.

To break through this complexity barrier, John and I borrowed an idea remembered from our youth, and that is those science textbooks that depict the human anatomy. You remember the ones that use translucent pages, each with a particular organ, all overlaying together comprising the comprehensive image of the human body, complete with all of its sub-systems?

We thought this a good way to communicate our complicated security system design to others. We adapted this idea for our end-to-end security design layout. Each image corresponds with an individual security component contained within a typical web app from it's outer to innermost layers.

What's unique about this particular talk is that it started with those initial visual images of a typical web security system architecture.  Next, a test application was created to go along with those anatomy images. The test app mimicked a typical web system, complete with test pages, links, buttons, database tables, et cetera, all of which are under tight security controls of various types.  The goal of the test app was to create a comprehensive tutorial demonstrating all of the pertinent security controls that were contained within the anatomy diagram. Finally, we added instructions to install, deploy, and run the test app and published it all to GitHub.  The project is called The Apache Fortress Demo, and we used it in our live demos and it could also be used by anyone else who wants to try it out at home. During our live demos, we would simultaneously dissect and discuss the web system security functionality, switching between the Power Point slides visually depicting the images and into the concrete demo to show how it all worked in a live system.

### The Anatomy Diagram
 ![Apache Fortress Demo Security Layers](src/main/javadoc/doc-files/Demo2-Block-Diagram.png  "Apache Fortress Demo")


