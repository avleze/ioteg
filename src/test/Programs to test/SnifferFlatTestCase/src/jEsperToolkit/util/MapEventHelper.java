/*  
 *  Copyright (C) 2015, Frankfurt University of Applied Sciences
 *
 *  This program is free software; you can redistribute it and/or modify it under the terms of the
 *  GNU General Public License as published by the Free Software Foundation; either version 2 of the License,
 *  or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along with this program;
 *  if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 *
 *  Linking this library statically or dynamically with other modules is making a combined work based on this library.
 *  Thus, the terms and conditions of the GNU General Public License cover the whole combination.
 *
 *  As a special exception, the copyright holders of this library give you permission to link this library with
 *  independent modules to produce an executable, regardless of the license terms of these independent modules,
 *  and to copy and distribute the resulting executable under terms of your choice, provided that you also meet,
 *  for each linked independent module, the terms and conditions of the license of that module. An independent module
 *  is a module which is not derived from or based on this library. If you modify this library, you may extend
 *  this exception to your version of the library, but you are not obligated to do so. If you do not wish to do so,
 *  delete this exception statement from your version.
 * 
 *
 *
 *  In addition, this software is also licensed under the terms of the Eclipse Public License 
 *  (EPL) 1.0. You can find a copy of the EPL at: 
 *  http://opensource.org/licenses/eclipse-1.0.php
 */

package jEsperToolkit.util;

import java.util.HashMap;
import java.util.Map;

import com.espertech.esper.client.ConfigurationOperations;

/**
 * 
 * @author Ruediger Gad
 * 
 *         Assorted helper functionality for handling events in Map format.
 * 
 */
public class MapEventHelper {

    /**
     * 
     * Creates a description for a passed sample event. The description is
     * required for adding the Map-based event type to the CEP engine.
     * 
     * @param eventSample
     * @return Map with description of event
     */
    public static Map<String, Object> getEventDescritption(
            Map<String, Object> eventSample, ConfigurationOperations config) {
        Map<String, Object> desc = new HashMap<String, Object>();

        for (String key : eventSample.keySet()) {
        	Object obj = eventSample.get(key);
            
            if (obj instanceof Map) {
            	String nestedTypeName = key + "Type";
            	System.out.println("Found nested type: " + nestedTypeName);
            	
            	@SuppressWarnings("unchecked")
				Map<String, Object> nestedDescription = getEventDescritption((Map<String, Object>) obj, config);
            	config.addEventType(nestedTypeName, nestedDescription);
            	
            	desc.put(key, nestedTypeName);
            } else {
            	desc.put(key, obj.getClass());	
            }
        }

        return desc;
    }

}
