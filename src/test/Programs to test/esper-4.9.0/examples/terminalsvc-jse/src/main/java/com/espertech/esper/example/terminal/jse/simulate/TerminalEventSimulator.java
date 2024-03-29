/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.terminal.jse.simulate;

import com.espertech.esper.example.terminal.jse.event.BaseTerminalEvent;
import com.espertech.esper.example.terminal.jse.event.*;

import java.util.List;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The main class to run the simulation and observe the ESP/CEP statements issueing notification to the registered
 * event processing agents (EPA) turning them to complex composite events that end up in the registered
 * TerminalComplexEventListener
 * <p/>
 * Run with "java -cp ... TerminalEventSimulator" (or see Ant build.xml file) or use Eclipse "Run" menu in the Eclipse
 * provided project.
 * <p/>
 * You may want to change the number of ITERATION, the SLEEP period between each iteration (ms)
 * and you may familiarize yourself with the Esper ESP/CEP statements in the TerminalEventProcessingAgent class
 * <p/>
 * It is also possible to place breakpoints and launch this in debug mode from your IDE to better understand
 * the execution flow (put breakpoints in the BaseTerminalEvent subclasses like CountPerTypeListener
 */
public class TerminalEventSimulator {

    private static int ITERATION = 100;

    private static int SLEEP = 2000;

    private static String TCFILE = "";

    private final EventGenerator eventGenerator;

    private final TerminalEventProcessingAgent terminalEventProcessingAgent;

    public TerminalEventSimulator() {
        eventGenerator = new EventGenerator();
        terminalEventProcessingAgent = new TerminalEventProcessingAgent(new TerminalComplexEventListener());
    }

    public void sendEvents() throws InterruptedException {
        List<BaseTerminalEvent> eventsToSend = eventGenerator.generateBatch();

        for (BaseTerminalEvent theEvent : eventsToSend) {
            terminalEventProcessingAgent.sendEvent(theEvent);
        }

        // Throttle the sender to roughly send a batch every SLEEP ms
        if (SLEEP > 0) {
            //System.out.printf("\n\tSleeping %d ms\n", SLEEP);
            Thread.sleep(SLEEP);
        }
    }

    public void getsendEvents(String TCFILE) throws InterruptedException {
        
           List<BaseTerminalEvent> eventsToSend = eventGenerator.generateBatch();
	   try {
	   	   FileWriter File = new FileWriter(TCFILE, true);
		   BufferedWriter bw = new BufferedWriter(File);
                   for (BaseTerminalEvent theEvent : eventsToSend) {
		       bw.write(theEvent.getType() + " " + theEvent.getTerminal().getId() + " " + theEvent.getTimestamp() + "\n");
		       terminalEventProcessingAgent.sendEvent(theEvent); 
                   }
		   bw.write("\n");
		   bw.close();
	      } catch(IOException e){
		   e.printStackTrace();
		   System.out.println(e.getMessage());
	   }

        // Throttle the sender to roughly send a batch every SLEEP ms
        if (SLEEP > 0) {
            //System.out.printf("\n\tSleeping %d ms\n", SLEEP);
            Thread.sleep(SLEEP);
        }
    }

    public static ArrayList<String> gettingEvents(String TCFILE) throws InterruptedException, IOException, FileNotFoundException{
	ArrayList<String> eventsToSend = new ArrayList<String>();
	FileReader fr = null;
        BufferedReader br = null;
	fr = new FileReader(TCFILE);
    	br = new BufferedReader(fr);
	String line = "";
	while( (line = br.readLine()) != null){
		eventsToSend.add(line);
    	}
	br.close();
	fr.close();

	return eventsToSend;
    }

    public void sendEvents(ArrayList<String> eventsToSend) throws InterruptedException {
        
	int i = 0;
 	List<BaseTerminalEvent> batch = new LinkedList<BaseTerminalEvent>();
	
	while (i != eventsToSend.size()){
    		String event = eventsToSend.get(i);
		if (!event.isEmpty()){
		  BaseTerminalEvent theEvent = null;
		  String[] parts = event.split(" ");
		  Terminal t = new Terminal(parts[1]);
		  if (parts[0].equals("LowPaper")) {
			theEvent = new LowPaper(t);
		  }
		  if (parts[0].equals("Checkin")) {
			theEvent = new Checkin(t);
		  }
		  if (parts[0].equals("Cancelled")) {
			theEvent = new Cancelled(t);
		  }
		  if (parts[0].equals("Completed")) {
			theEvent = new Completed(t);
		  }
		  if (parts[0].equals("OutOfOrder")) {
			theEvent = new OutOfOrder(t);
		  }
		  if (parts[0].equals("Status")) {
			theEvent = new OutOfOrder(t);
		  }
		  theEvent.setTimestamp(Long.parseLong(parts[2]));
		  terminalEventProcessingAgent.sendEvent(theEvent); 
		} else {
	          // Throttle the sender to roughly send a batch every SLEEP ms
	           if (SLEEP > 0) {
	               //System.out.printf("\n\tSleeping %d ms\n", SLEEP);
	               Thread.sleep(SLEEP);
	           }
	    	}
	i++;
	}
    

    }

    public static void main(String args[]) throws Exception {

	if (args.length < 3) {
            System.out.println("Arguments are: <iteration> <sleep>");
            System.out.println("  number of iterations (e.g. 100)");
            System.out.println("  sleep seconds (e.g. 2000)");
	    System.out.println("  eventfile: file to save the event values");
            System.exit(-1);
        }

        try {
            ITERATION = Integer.parseInt(args[0]);
        } catch (NullPointerException e) {
            System.out.println("Invalid number of iterations:" + args[0]);
            System.exit(-2);
            return;
        }

        try {
            SLEEP = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid seconds to sleep:" + args[1]);
            System.exit(-2);
            return;
        }

        try {
            TCFILE = args[2];
        } catch (NullPointerException e) {
            System.out.println("Invalid file to use:" + args[2]);
            System.exit(-2);
            return;
        }
        //System.out.printf("TerminalEventSimulator starting %d iteration, sleep %d ms between\n", ITERATION, SLEEP);

        TerminalEventSimulator simulator = new TerminalEventSimulator();

	if (TCFILE != "") {
            File file = new File(TCFILE);
  	    if (file.length() == 0) {
               for (int i = 0; i < ITERATION; i++) {
                   simulator.getsendEvents(TCFILE);
               }
	    } else {
		//for (int i = 0; i < ITERATION; i++) {
		   ArrayList<String> eventsToSend = gettingEvents(TCFILE);
                   simulator.sendEvents(eventsToSend);
		//}
            }
	} else {
            for (int i = 0; i < ITERATION; i++) {
                simulator.sendEvents();
            }
	}

        //System.out.println("TerminalEventSimulator ended");
    }

}
