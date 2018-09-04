package jEsperToolkit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


/**
 * @author Juan Boubeta-Puig <juan.boubeta@uca.es>
 *
 */

public class JsonToIslandEventTransformer
{
	public synchronized static List<Map<String, Object>> transformMessage(String outputEncoding) throws JsonGenerationException, JsonMappingException, JsonParseException, IOException
			{

		// LinkedHashMap will iterate in the order in which the entries were put into the map
		List<Map<String, Object>> eventMap = new ArrayList<Map<String, Object>>();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = null;
		File eventvalues = new File(outputEncoding);
		
		try {
			
			jsonNode = mapper.readTree(eventvalues);			
			
			for (int i = 0; i < jsonNode.get("feeds").size(); i++){
			
				// Event payload is a nested map
				Map<String, Object> eventPayload = new LinkedHashMap<String, Object>();
							
				eventPayload.put("islandId", jsonNode.get("channel").get("id").asInt());
				eventPayload.put("islandName", jsonNode.get("channel").get("name").getTextValue());
				eventPayload.put("timestamp", jsonNode.get("feeds").path(i).get("created_at").getTextValue());
				eventPayload.put("sensor_L0_N0", Integer.parseInt(jsonNode.get("feeds").path(i).get("field1").getTextValue()));
				eventPayload.put("sensor_L0_N1", Integer.parseInt(jsonNode.get("feeds").path(i).get("field2").getTextValue()));
				eventPayload.put("sensor_L0_N2", Integer.parseInt(jsonNode.get("feeds").path(i).get("field3").getTextValue()));
				eventPayload.put("sensor_L1_N0", Integer.parseInt(jsonNode.get("feeds").path(i).get("field4").getTextValue()));
				eventPayload.put("sensor_L1_N1", Integer.parseInt(jsonNode.get("feeds").path(i).get("field5").getTextValue()));
				eventPayload.put("sensor_L1_N2", Integer.parseInt(jsonNode.get("feeds").path(i).get("field6").getTextValue()));
				eventPayload.put("temperature", Integer.parseInt(jsonNode.get("feeds").path(i).get("field7").getTextValue()));		
				eventPayload.put("blocked", Integer.parseInt(jsonNode.get("feeds").path(i).get("field8").getTextValue()));
	
				eventMap.add(eventPayload);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		//System.out.println("===IslandEvent created: " + eventMap);
		return eventMap;
	}
}
