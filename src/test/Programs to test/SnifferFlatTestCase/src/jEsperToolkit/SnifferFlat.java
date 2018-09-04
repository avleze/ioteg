package jEsperToolkit;

import jEsperToolkit.EsperRuntimeFacade;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import jEsperToolkit.JsonToSnifferEventTransformer;
import org.codehaus.jackson.map.*;
import org.codehaus.jackson.map.DeserializationConfig.Feature;

import cljSnifferEventGenerator.JavaTools;
import clj_net_pcap.CljNetPcapJavaAdapter;

import com.espertech.esper.client.EPStatement;
import com.fasterxml.jackson.databind.DeserializationFeature;

public class SnifferFlat {
	@SuppressWarnings("unchecked")
	public static void main(String args[]) throws Exception {

		String parameters = args[0];
		
		String sampledata = parameters.split(" ")[0];
		String PCAPFeeds = parameters.split(" ")[1];
		String JSONFeeds = parameters.split(" ")[2];
		
		// Example for loading event data from a json file.
		// The input is a json file with packet capturing data.
		// The output is a list that contains for each packet in the input data a map with the corresponding event data.

		List<Map<String, Object>> data = null;
		
		if (!sampledata.equals("null") && !JSONFeeds.equals("null")) {
			data = new ArrayList<Map<String, Object>> (CljNetPcapJavaAdapter.extractMapsFromPcapFile(PCAPFeeds));
			data.addAll(JsonToSnifferEventTransformer.transformMessage(JSONFeeds));
		}
		
		if (sampledata.equals("null")) {
			data = JsonToSnifferEventTransformer.transformMessage(JSONFeeds);
		}
		
		if (JSONFeeds.equals("null")) {	
			data = CljNetPcapJavaAdapter.extractMapsFromPcapFile(PCAPFeeds);
		}
		
		CountDownLatch cdl = new CountDownLatch(10000000); //An elevate number of events that it is not expected to be meet
		
		// Create a new EsperRuntimeFacade that is used as abstraction for using Esper.
	
		EsperRuntimeFacade erf = new EsperRuntimeFacade();
	
		// Define the raw event input event stream based on a sample.
		// This is required for feeding the events into the CEP engine later on.
		
		ObjectMapper objectm = new ObjectMapper();
		objectm.configure(Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, false);
		
		if (!sampledata.equals("null")) {
			HashMap<String,Object> sniffer = objectm.readValue(new File(sampledata), HashMap.class);
			erf.addEventType("sniffer.header.parsed.flat", sniffer);
		}
		
		if (sampledata.equals("null") && !JSONFeeds.equals("null")) {
			erf.addEventType("sniffer.header.parsed.flat", data.get(0));
		}
		
		// Once the raw event type and stream is defined, based on this raw event stream,
		// event patterns can be used to infer new events.
		
		erf.createStatement(
				"INSERT INTO " +
				"cep.icmp.echo.request " +
				"(timestamp, source, destination, sequence) " +
				"SELECT " +
				"ts, ipSrc, ipDst, icmpEchoSeq " +
				"FROM " +
				"sniffer.header.parsed.flat " +
				"WHERE " +
				"icmpType = \"echo request\""
				);
		
		erf.createStatement(
				"INSERT INTO " +
				"cep.icmp.echo.reply " +
				"(timestamp, source, destination, sequence) " +
				"SELECT " +
				"ts, ipSrc, ipDst, icmpEchoSeq " +
				"FROM " +
				"sniffer.header.parsed.flat " +
				"WHERE " +
				"icmpType = \"echo reply\""
				);
		
		erf.createStatement(
				"INSERT INTO " +
				"cep.icmp.ping.response.time " +
				"(requestTimestamp, responseTimestamp, time, source, destination) " +
				"SELECT " +
				"a.timestamp, b.timestamp, " +
				"b.timestamp - a.timestamp, " +
				"a.source, a.destination " +
				"FROM PATTERN [ " +
				"EVERY " +
				"a = cep.icmp.echo.request  -> " +
				"b = cep.icmp.echo.reply(" +
				"source = a.destination AND " +
				"destination = a.source AND " +
				"sequence = a.sequence)]"
				);
		
		erf.createStatement(
				"INSERT INTO " +
				"cep.icmp.ping.response.time " +
				"(requestTimestamp, responseTimestamp, time, source, destination) " +
				"SELECT " +
				"a.ts, b.ts, " +
				"b.ts - a.ts, " +
				"a.ipSrc, a.ipDst " +
				"FROM PATTERN [ " +
				"EVERY " +
				"a = sniffer.header.parsed.flat( icmpType = \"echo request\" ) " +
				"-> b = sniffer.header.parsed.flat(" + 
				"icmpType = \"echo reply\" AND " +
				"ipSrc = a.ipDst AND " +
				"ipDst = a.ipSrc AND " +
				"icmpEchoSeq = a.icmpEchoSeq)]"
				);
		
		erf.createStatement(
				"INSERT INTO " +
				"cep.tcp.connection.established " +
				"(timestamp, source, destination, sourcePort, destinationPort) " +
				"SELECT " +
				"c.ts, " +
				"a.ipSrc, a.ipDst, " +
				"a.tcpSrc, a.tcpDst " +
				"FROM PATTERN [ " +
				"EVERY " +
				"a = sniffer.header.parsed.flat( tcpFlags =  2) -> " +
				"b = sniffer.header.parsed.flat(" + 
				"tcpFlags = 18 AND " +
				"ipSrc = a.ipDst AND ipDst = a.ipSrc AND " +
				"tcpSrc = a.tcpDst AND tcpDst = a.tcpSrc) " +
				"-> c = sniffer.header.parsed.flat(" +
				"tcpFlags = 16 AND " +
				"ipSrc = a.ipSrc AND ipDst = a.ipDst AND " +
				"tcpSrc = a.tcpSrc AND tcpDst = a.tcpDst)]"
				);
		
		erf.createStatement(
				"INSERT INTO " +
				"cep.tcp.connection.finished " +
				"(timestamp, source, destination, sourcePort, destinationPort) " +
				"SELECT " +
				"c.ts, a.ipSrc, a.ipDst, a.tcpSrc, a.tcpDst " +
				"FROM PATTERN [	" +
				"EVERY " +
				"a = sniffer.header.parsed.flat(" + 	
				"tcpFlags = 20) -> " + 
				"b = sniffer.header.parsed.flat(" +
				//"Tcp.flags.contains(\"FIN\") AND Tcp.flags.contains(\"ACK\") AND " +
				"tcpFlags = 2 AND " +
				"ipSrc = a.ipDst AND " +
				"ipDst = a.ipSrc AND " +
				"tcpSrc = a.tcpDst AND tcpDst = a.tcpSrc) -> " +
				"c = sniffer.header.parsed.flat(" +
				//"Tcp.flags.contains(\"ACK\") AND " +
				"tcpFlags = 1 AND " +
				"ipSrc = a.ipSrc AND " +
				"ipDst = a.ipDst AND " +
				"tcpSrc = a.tcpSrc AND tcpDst = a.tcpDst)]"
		        );
		erf.createStatement(
		        "INSERT INTO " +
		        "cep.tcp.connection.duration " +
		        "(startTimestamp, endTimestamp, duration, " +
		        "source, destination, sourcePort, destinationPort) " +
		        "SELECT " +
		        "a.timestamp, b.timestamp, b.timestamp - a.timestamp, " +
		        "a.source, a.destination, a.sourcePort, a.destinationPort " +
		        "FROM PATTERN [ " +
		        "EVERY " +
		        "a = cep.tcp.connection.established -> " +
		        "b = cep.tcp.connection.finished(" +
		        "(source = a.source AND destination = a.destination AND " +
		        "sourcePort = a.sourcePort AND destinationPort = a.destinationPort) " +
		        "OR (source = a.destination AND destination = a.source AND " +
		        "sourcePort = a.destinationPort AND destinationPort = a.sourcePort))]"
		        );
		erf.createStatement(
				"INSERT INTO " +
				"cep.arp.request " +
				"(timestamp, source, destination, " +
				"sourceMac, sourceIp, targetMac, targetIp) " +
				"SELECT " +
				"ts, ethSrc, ethDst, " +
				"arpSourceMac, arpSourceIp, arpTargetMac, arpTargetIp " +
				"FROM " +
				"sniffer.header.parsed.flat " +
				"WHERE " +
				"arpOpDesc = \"REQUEST\""
		        );
		erf.createStatement(
				"INSERT INTO " +
				"cep.arp.reply " +
				"(timestamp, source, destination, " +
				"sourceMac, sourceIp, targetMac, targetIp) " +
				"SELECT " +
				"ts, ethSrc, ethDst, " +
				"arpSourceMac, arpSourceIp, arpTargetMac, arpTargetIp " +
				"FROM " +
				"sniffer.header.parsed.flat " +
				"WHERE " +
				"arpOpDesc = \"REPLY\""
				);
		
		erf.createStatement(
				"INSERT INTO " +
				"cep.tcp.syn.sent " +
				"(timestamp, source, destination, " +
				"sourcePort, destinationPort) " +
				"SELECT " +
				"ts, ipSrc, ipDst, " +
				"tcpSrc, tcpDst " +
				"FROM " +
				"sniffer.header.parsed.flat " +
				"WHERE " +
				//"Tcp.flags.contains(\"SYN\") AND " +
				"tcpFlags = 1"
				);
		
		erf.createStatement(
				"INSERT INTO " +
				"cep.dns " +
				"(timestamp, source, destination) " +
				"SELECT " +
				"ts, ipSrc, ipDst " +
				"FROM " +
				"sniffer.header.parsed.flat " +
				"WHERE " +
				"udpSrc = 53 OR udpDst = 53"
				);
		
		// The above example shows how event patterns in the form of Java strings are used.

		CustomAwareListener genericListener = new CustomAwareListener();
		
		EPStatement a = erf.createStatement("SELECT * FROM cep.icmp.echo.request");
		a.addListener(genericListener);
		EPStatement b = erf.createStatement("SELECT * FROM cep.icmp.echo.reply");
		b.addListener(genericListener);
		EPStatement c = erf.createStatement("SELECT * FROM cep.icmp.ping.response.time");
		c.addListener(genericListener);
		EPStatement d = erf.createStatement("SELECT * FROM cep.tcp.connection.established");
		d.addListener(genericListener);
		EPStatement e = erf.createStatement("SELECT * FROM cep.tcp.connection.finished");
		e.addListener(genericListener);
		EPStatement f = erf.createStatement("SELECT * FROM cep.tcp.connection.duration");
		f.addListener(genericListener);
		EPStatement g = erf.createStatement("SELECT * FROM cep.arp.request");
		g.addListener(genericListener);
		EPStatement h = erf.createStatement("SELECT * FROM cep.arp.reply");
		h.addListener(genericListener);
		EPStatement i = erf.createStatement("SELECT * FROM cep.tcp.syn.sent");
		i.addListener(genericListener);
		EPStatement j = erf.createStatement("SELECT * FROM cep.dns");
		j.addListener(genericListener);
		
		// Send the raw events into the CEP engine.
				
		for (Map<?, ?> event : data) {
			erf.send("sniffer.header.parsed.flat", event);
			//System.out.println(event.keySet() + " " + event.values());
			Thread.sleep(100);
		}

		while (System.currentTimeMillis() - genericListener.getLastTimeEvent() < 10000){ // 10 seconds since the last event
			cdl.await(1000, TimeUnit.MILLISECONDS);
		}
		
		erf.destroy();
	}
}

