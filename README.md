> [!WARNING]
> **This repository has been archived**
> 
> IBM Legacy Public Repository Disclosure: All content in this repository including code has been provided by IBM under the associated open source software license and IBM is under no obligation to provide enhancements, updates, or support.
> IBM developers produced this code as an open source project (not as an IBM product), and IBM makes no assertions as to the level of quality nor security, and will not be maintaining this code going forward.

See [cics-java-liberty-restapp](https://github.com/cicsdev/cics-java-liberty-restapp) for an alternative sample that provides a sample JAX-RS RESTful web application for Liberty that links to a CICS® COBOL program.

# cics-java-liberty-jaxrs

RESTful service for CICS TSQ browsing using Liberty and JAX-RS


The following Java source components are supplied in the src directory in this repository.

## Java package com.ibm.cics.wlp.devworks.jaxrs.web
* [`Tsq`](src/com/ibm/cics/wlp/devworks/jaxrs/web/Tsq.java) - RESTful TSQ browser 
* [`TsqConfig`](src/com/ibm/cics/wlp/devworks/jaxrs/web/TsqConfig.java) - IBMRestServlet configuration class 

## Pre-reqs

* CICS TS V5.1 or later
* Java SE 1.8 or later on the workstation
* Eclipse with WebSphere Developer Tools and CICS Explorer SDK installed

## Configuration

The sample  code can be added to a dynamic web project and deployed into a CICS Liberty JVM server as a web archive (WAR).

## Reference

More information about this sample can be found at the accompanying tutorial [Developing RESTful web services in Liberty with JAX-RS](blog.md)



