package jEsperToolkit;

import jEsperToolkit.EsperRuntimeFacade;

import java.util.Map;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import jEsperToolkit.JsonToIslandEventTransformer;
import com.espertech.esper.client.EPStatement;

public class EcologicalIsland {
	@SuppressWarnings("unchecked")
	public static void main(String args[]) throws Exception {

		String Feeds = args[0];
		
		// Example for loading event data from a json file.
		// The input is a json file with packet capturing data.
		// The output is a list that contains for each packet in the input data a map with the corresponding event data.

		List<Map<String, Object>> data = JsonToIslandEventTransformer.transformMessage(Feeds);
				
		CountDownLatch cdl = new CountDownLatch(10000000); //An elevate number of events that it is not expected to be meet
		
		// Create a new EsperRuntimeFacade that is used as abstraction for using Esper.
	
		EsperRuntimeFacade erf = new EsperRuntimeFacade();
	
		// Define the raw event input event stream based on a sample.
		// This is required for feeding the events into the CEP engine later on.
	
		erf.addEventType("IslandEvent", data.get(0));
		//erf.addEventType("IslandEvent", JavaTools.getFlatMapSample());

		
		// Once the raw event type and stream is defined, based on this raw event stream,
		// event patterns can be used to infer new events.
		// Note the 'FROM "sniffer.header.parsed.flat"' clause which is used to read the
		// events of the above defined event type from the equally named event stream.
		
		erf.createStatement(
				"insert into PotentialFire " + 
				"select 4 as alertLevel, " +
				"e.islandId as islandId, " +
				"e.islandName as islandName," + 
				"e.timestamp as timestamp,  " +
				"e.temperature as temperature," + 
				"e.blocked as blocked  " + 
				"from pattern [every e = IslandEvent(" +
				"   temperature >= 0)].win:time(1 sec)"
		        );
		erf.createStatement(
		        "insert into HalfFilledContainer " +
		        "select 1 as alertLevel," + 
		        "e.islandId as islandId," +
		        "e.islandName as islandName," +
		        "e.timestamp as timestamp," + 
		        "e.temperature as temperature," + 
		        "e.blocked as blocked," +
		        "50 as filledPercentage " + 
		        "from pattern [every e = IslandEvent(" + 
		        "  (e.sensor_L1_N1 = 1) or " +
		        "  (e.sensor_L0_N0 = 1 and e.sensor_L0_N2 = 1) or" + 
		        "  (e.sensor_L0_N1 = 1 and e.sensor_L0_N2 = 1) or" + 
		        "  (e.sensor_L0_N0 = 1 and e.sensor_L0_N1 = 1)" +  
				"  )].win:time(1 min)"
		        );
		erf.createStatement(
				"insert into BlockedContainerMouth " +
				"select 3 as alertLevel, " +
				"e.islandId as islandId, " +
				"e.islandName as islandName, " + 
				"e.timestamp as timestamp, " +
				"e.temperature as temperature, " +
				"e.blocked as blocked  " +
				"from pattern [every e = IslandEvent(" +
				"   blocked = 1)].win:time(1 min)"
		        );
		erf.createStatement(
				"insert into AlmostFilledContainer " +
				"select 2 as alertLevel, " +
				"e.islandId as islandId, " +
				"e.islandName as islandName, " +
				"e.timestamp as timestamp, " +
				"e.temperature as temperature, " +
				"e.blocked as blocked, " +
				"90 as filledPercentage " +
				"from pattern [every e = IslandEvent( " +
				"   (e.sensor_L1_N0 = 1 and e.sensor_L1_N2 = 1) or " +
				"   (e.sensor_L1_N1 = 1 and e.sensor_L1_N2 = 1) or " +
				"   (e.sensor_L1_N0 = 1 and e.sensor_L1_N1 = 1)  " +
				"   )].win:time(1 min)" 
				);
		
		// The above example shows how event patterns in the form of Java strings are used.

		CustomAwareListener genericListener = new CustomAwareListener();
		
		//genericListener.openFiles();
		
		EPStatement stmtbc = erf.createStatement("SELECT * FROM BlockedContainerMouth");
		stmtbc.addListener(genericListener);
		EPStatement stmtf = erf.createStatement("SELECT * FROM PotentialFire");
		stmtf.addListener(genericListener);
		EPStatement stmthc = erf.createStatement("SELECT * FROM HalfFilledContainer");
		stmthc.addListener(genericListener);
		EPStatement stmtac = erf.createStatement("SELECT * FROM AlmostFilledContainer");
		stmtac.addListener(genericListener);
		
		// Send the raw events into the CEP engine.
				
		for (Map<?, ?> event : data) {
			erf.send("IslandEvent", event);
			Thread.sleep(100);
		}

		while (System.currentTimeMillis() - genericListener.getLastTimeEvent() < 10000){ // 10 seconds since the last event
			cdl.await(1000, TimeUnit.MILLISECONDS);
		}

	}
}

