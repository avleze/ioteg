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
 * @author Lorena Gutiérrez-Madroñal <lorena.gutierrezmadronal@uca.es>
 *
 */

public class JsonToSnifferEventTransformer
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
							
				eventPayload.put("ethSrc", jsonNode.get("feeds").path(i).get("ethSrc").getTextValue());
				eventPayload.put("ethDst", jsonNode.get("feeds").path(i).get("ethDst").getTextValue());
				eventPayload.put("udpDst", jsonNode.get("feeds").path(i).get("udpDst").asInt());
				eventPayload.put("udpSrc", jsonNode.get("feeds").path(i).get("udpSrc").asInt());
				eventPayload.put("len", jsonNode.get("feeds").path(i).get("len").asInt());
				eventPayload.put("ts", jsonNode.get("feeds").path(i).get("timestamp").asLong()); 
				eventPayload.put("icmpEchoSeq", jsonNode.get("feeds").path(i).get("sequence").asInt());	
				eventPayload.put("icmpType", jsonNode.get("feeds").path(i).get("icmpType").getTextValue());
				eventPayload.put("ipTtl", jsonNode.get("feeds").path(i).get("ipTtl").asInt());
				eventPayload.put("ipVer", jsonNode.get("feeds").path(i).get("ipVer").asInt());
				eventPayload.put("ipChecksum", jsonNode.get("feeds").path(i).get("ipChecksum").asInt());
				eventPayload.put("ipId", jsonNode.get("feeds").path(i).get("ipId").asInt());
				eventPayload.put("tcpFlags", jsonNode.get("feeds").path(i).get("tcpFlags").asInt());
				eventPayload.put("tcpSrc", jsonNode.get("feeds").path(i).get("sourcePort").asInt());
				eventPayload.put("tcpDst", jsonNode.get("feeds").path(i).get("destinationPort").asInt());
				eventPayload.put("ipSrc", jsonNode.get("feeds").path(i).get("source").getTextValue());
				eventPayload.put("ipDst", jsonNode.get("feeds").path(i).get("destination").getTextValue());
				eventPayload.put("arpSourceIp", jsonNode.get("feeds").path(i).get("sourceIp").getTextValue());
				eventPayload.put("arpTargetIp", jsonNode.get("feeds").path(i).get("targetIp").getTextValue());
				eventPayload.put("arpOpDesc", jsonNode.get("feeds").path(i).get("arpOpDesc").getTextValue());
				eventPayload.put("arpSourceMac", jsonNode.get("feeds").path(i).get("sourceMac").getTextValue());
				eventPayload.put("arpTargetMac", jsonNode.get("feeds").path(i).get("targetMac").getTextValue());
				
				eventMap.add(eventPayload);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		//System.out.println("===SnifferEvent created: " + eventMap);
		return eventMap;
	}
}
