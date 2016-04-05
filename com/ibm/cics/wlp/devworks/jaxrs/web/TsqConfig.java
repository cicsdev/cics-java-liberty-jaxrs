/* Licensed Materials - Property of IBM                                   */
/*                                                                        */
/* SAMPLE                                                                 */
/*                                                                        */
/* (c) Copyright IBM Corp. 2016 All Rights Reserved                       */       
/*                                                                        */
/* US Government Users Restricted Rights - Use, duplication or disclosure */
/* restricted by GSA ADP Schedule Contract with IBM Corp                  */
/*                                                                        */    

package com.ibm.cics.wlp.devworks.jaxrs.web;
import java.util.HashSet;
import java.util.Set;


public class TsqConfig extends javax.ws.rs.core.Application
{
	
	//List the JAX-RS classes that contain annotations
	public Set<Class<?>> getClasses()
	{
		Set<Class<?>> classes = new HashSet<Class<?>>();
		
		classes.add(com.ibm.cics.wlp.devworks.jaxrs.tsq.Tsq.class);
		
		return classes;
	}
}
