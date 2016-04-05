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

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import com.ibm.cics.server.examples.wlp.tsq.BrowseTsqOSGi;
import com.ibm.cics.server.examples.wlp.tsq.DeleteTsqOSGi;
import com.ibm.cics.server.examples.wlp.tsq.TsqInfoOSGi;
import com.ibm.cics.server.examples.wlp.tsq.WriteTsqOSGi;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

@javax.ws.rs.Path("/tsq/{tsqName}")
public class Tsq {

	/**
	 * The GET HTTP Method is used to get (or browse) a TSQ record. We receive
	 * the name of the tsq to browse in the URI string.
	 * 
	 * @param tsqName
	 *            - The name of the TSQ to browse
	 * @return The TSQ record requested in the URI as a JSONObject i.e
	 *         {"records"
	 *         :[{"record":"RECORD1"},{"record":"RECORD2"}],"tsqName":"TSQ"}
	 * @throws Exception 
	 */
	@javax.ws.rs.GET
	@Produces("application/json")
	public JSONObject browseTsq(@javax.ws.rs.PathParam("tsqName") String tsqName) throws Exception {
		// create the object that will interact with the TSQ
		BrowseTsqOSGi browseTsq = new BrowseTsqOSGi();

		// create an ArrayList, insert the TSQ records
		ArrayList<String> records = new ArrayList<String>();		
		records = browseTsq.browseTSQ(tsqName);
		
		// Put each record into a JSONObject and add it to a JSONArray
		JSONArray queueArray = new JSONArray();
		for (String record : records) {
			JSONObject jsonRecord = new JSONObject();
			jsonRecord.put("record", record);
			queueArray.add(jsonRecord);
		}

		// create an JSONObject to return
		JSONObject tsq = new JSONObject();

		// put the name of the TSQ
		tsq.put("tsqName", tsqName);

		// put the array of records
		tsq.put("records", queueArray);

		// return the object
		return tsq;
	}

	/**
	 * A HTTP PUT Method is usually only used to update a resource so we will
	 * use this method to update/write to an existing TSQ.
	 * 
	 * @return The result of writing to an existing TSQ in a JSON Object i.e
	 *         {result:"success or failure",tsqName:"TSQ"}
	 * @throws Exception 
	 */
	@javax.ws.rs.PUT
	@Consumes("application/json")
	@Produces("application/json")
	public JSONObject updateTsq(JSONObject jsonTsq) throws Exception {
		// string to hold the result of writing to the tsq
		String result = "";

		// Get the name of the TSQ to write to
		String tsqName = (String) jsonTsq.get("tsqName");

		// Create the object to see if the TSQ exists
		// and fetch the number of items in the queue
		TsqInfoOSGi tsqInfo = new TsqInfoOSGi(tsqName);
		int items = tsqInfo.getTSQLength();

		if (items > 0) {
			// The TSQ has existing items, get the record to be added from
			// the JSONObject
			String record = (String) jsonTsq.get("record");

			// Create the object that will actually write to the TSQ
			WriteTsqOSGi writeTsq = new WriteTsqOSGi();

			// write the record to the TSQ and store the result
			
				result = writeTsq.writeTSQ(tsqName, record);

		} else {
			result = "You are trying to write to an existing queue but TSQ " 
					+ tsqName
					+ " does not exist. Try creating a TSQ using a POST method instead.";
		}

		// create the result JSONObject
		JSONObject jsonResult = new JSONObject();

		// put the tsq name in the object
		jsonResult.put("tsqName", tsqName);

		// put the result in the object
		jsonResult.put("result", result);

		// return the object
		return jsonResult;
	}

	/**
	 * The HTTP POST Method is used to create a new resource at the given URI.
	 * We will only create a new TSQ when POST is used. Client supplies
	 * JSONObject containing the TSQ name and record data which is then written
	 * to a queue.
	 * 
	 * @return The result of creating a new TSQ and the name of the new queue as
	 *         a JSONObject i.e {result:"result from create",tsqName:"TSQ"}
	 * @throws Exception 
	 */
	@javax.ws.rs.POST
	@Consumes("application/json")
	@Produces("application/json")
	public JSONObject createTsq(JSONObject jsonTsq) throws Exception {
		// a string to store the result
		String result = "";

		// Get the name of the TSQ to write to
		String tsqName = (String) jsonTsq.get("tsqName");

		// Create the object to see if the TSQ exists
		// and fetch the number of items in the queue
		TsqInfoOSGi tsqInfo = new TsqInfoOSGi(tsqName);
		int items = tsqInfo.getTSQLength();

		if (items > 0) {
			result = "You are trying to create a TSQ but " + tsqName
					+ " already exists,"
					+ "try using a PUT method to write to an existing TSQ";
		} else {
			// no items found so TSQ does not exist
			// get the record from the JSONObject to be written to the TSQ
			String record = (String) jsonTsq.get("record");

			// Create the object that will actually write to the TSQ
			WriteTsqOSGi writeTsq = new WriteTsqOSGi();

			// write the record to the tsq and store the result
				result = writeTsq.writeTSQ(tsqName, record);

		}

		// create the result JSONObject
		JSONObject jsonResult = new JSONObject();

		// put the tsq name in the object
		jsonResult.put("tsqName", tsqName);

		// put the result in the object
		jsonResult.put("result", result);

		// return the object
		return jsonResult;
	}

	/**
	 * 
	 * delete the TSQ corresponding to the name received in the URI.
	 * 
	 * @param tsqName
	 *            - the name of the TSQ to DELETE.
	 *
	 * @return The result of the DELETE as a JSONObject i.e
	 *         {result:"result of delete",tsqName:"TSQ"}
	 * @throws Exception 
	 */
	@javax.ws.rs.DELETE
	@Produces("application/json")
	public JSONObject deleteTsq(@javax.ws.rs.PathParam("tsqName") String tsqName) throws Exception {
		
		String result = "";

		// create the object that will actually delete the TSQ
		DeleteTsqOSGi deleteTsq = new DeleteTsqOSGi();

		// delete the TSQ and store the result
		result = deleteTsq.deleteTSQ(tsqName);

		// create the result JSONObject
		JSONObject jsonResult = new JSONObject();

		// put the tsq name in the object
		jsonResult.put("tsqName", tsqName);

		// put the result in the object
		jsonResult.put("result", result);

		// return the object
		return jsonResult;
	}
}