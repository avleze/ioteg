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

package jEsperToolkit;

import jEsperToolkit.util.MapEventHelper;

import java.util.List;
import java.util.Map;

import clj_net_pcap.PacketHeaderDataBean;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.ConfigurationOperations;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.core.service.EPStatementImpl;

/**
 * 
 * This class provides an abstraction to {@link EPServiceProvider} and
 * {@link EPRuntime}. The intent is to ease the usage of Esper in other code.
 * 
 * @author Ruediger Gad
 * 
 */
public class EsperRuntimeFacade {

	private EPServiceProvider provider;
	private EPRuntime runtime;

	/**
	 * Default constructor with (hopefully) sensible defaults.
	 */
	public EsperRuntimeFacade() {
		Configuration config = new Configuration();
		config.getEngineDefaults().getThreading().setThreadPoolInbound(true);
		config.getEngineDefaults().getThreading()
				.setThreadPoolInboundNumThreads(2);
		config.getEngineDefaults().getThreading().setThreadPoolOutbound(true);
		config.getEngineDefaults().getThreading()
				.setThreadPoolOutboundNumThreads(2);
		config.getEngineDefaults().getThreading().setThreadPoolRouteExec(true);
		config.getEngineDefaults().getThreading()
				.setThreadPoolRouteExecNumThreads(2);
		config.getEngineDefaults().getThreading().setThreadPoolTimerExec(true);
		config.getEngineDefaults().getThreading()
				.setThreadPoolTimerExecNumThreads(2);
		provider = EPServiceProviderManager.getDefaultProvider(config);
		runtime = provider.getEPRuntime();
	}

	/**
	 * Must be called when no further processing is about to take place. This
	 * means that this must be typically called on shutdown.
	 */
	public void destroy() {
		provider.destroy();
		runtime = null;
	}

	public boolean isDestroyed() {
		return provider.isDestroyed();
	}

	/**
	 * Send the given event to the {@link EPRuntime} instance for processing.
	 * 
	 * @param evt
	 */
	public void send(Object evt) {
		if (evt instanceof Map) {
			throw new RuntimeException(
					"The single parameter send method is not intended "
							+ "for sending Map-based events! "
							+ "Please use send(type, event) instead.");
		}

		runtime.sendEvent(evt);
	}

	/**
	 * Create an {@link EPStatementImpl} from the given EPL Pattern.
	 * 
	 * @param pattern
	 * @return
	 */
	public EPStatementImpl createStatement(String pattern) {
		EPStatementImpl statement = (EPStatementImpl) provider
				.getEPAdministrator().createEPL(pattern);
		return statement;
	}

	/**
	 * Add the given class as event type to the CEP engine. This is makes the
	 * class known to the CEP engine such that it can be used as event type in
	 * EPL patterns.
	 * 
	 * @param clazz
	 */
	public void addEventType(Class<?> clazz) {
		ConfigurationOperations config = provider.getEPAdministrator()
				.getConfiguration();
		config.addEventType(clazz);
	}

	/**
	 * Add a Map-based event type to the CEP engine. eventSample must be a
	 * {@link Map} with a sample of an event with all attributes being set that
	 * shall be used later on as non-dynamic properties.
	 * 
	 * @param name
	 * @param eventSample
	 */
	public void addEventType(String name, Map<String, Object> eventSample) {
		ConfigurationOperations config = provider.getEPAdministrator()
				.getConfiguration();

		config.addEventType(name,
				MapEventHelper.getEventDescritption(eventSample, config));
	}

	/**
	 * Send all events in the given {@link List} to the CEP engine.
	 * 
	 * @param events
	 */
	public void sendAll(List<?> events) {
		for (Object obj : events) {
			send(obj);
		}
	}

	/**
	 * Send all map-based events of a single type.
	 * 
	 * @param mapEventTypeName
	 * @param events
	 */
	public void sendAll(String mapEventTypeName, List<Object> events) {
		for (Object o : events) {
			if (o instanceof Map<?, ?>) {
				send(mapEventTypeName, (Map<?, ?>) o);
			}
		}
	}

	/**
	 * Send all map-based events. This method is capable of sending events of
	 * different types. It expects the actual event to be contained in a map
	 * itself. The event has to be the value the key is the name/type of the
	 * event as string.
	 * 
	 * @param events
	 */
	public void sendAllMap(List<?> events) {
		for (Object obj : events) {
			if (obj instanceof Map<?, ?>) {
				Map<?, ?> m = (Map<?, ?>) obj;

				for (Object key : m.keySet()) {
					Object value = m.get(key);

					if (key instanceof String && value instanceof Map<?, ?>) {
						send((String) key, (Map<?, ?>) value);
					}
				}
			}
		}
	}

	/**
	 * Send a single map event.
	 * 
	 * @param mapEventTypeName
	 * @param map
	 */
	public void send(String mapEventTypeName, Map<?, ?> map) {
		runtime.sendEvent(map, mapEventTypeName);
	}

	public void addEventType(String name, Class<PacketHeaderDataBean> eventClass) {
		ConfigurationOperations config = provider.getEPAdministrator()
				.getConfiguration();

		config.addEventType(name, eventClass);		
	}

}
