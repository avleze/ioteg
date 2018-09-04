package jEsperToolkit;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.StatementAwareUpdateListener;

public class CustomAwareListener implements StatementAwareUpdateListener {

	long lastTimeEventProcessed = System.currentTimeMillis();	
	
	@Override
	public void update(EventBean[] newComplexEvents, EventBean[] oldComplexEvents, 
			EPStatement detectedEventPattern, EPServiceProvider serviceProvider) {
		
		lastTimeEventProcessed = System.currentTimeMillis();
		if (newComplexEvents != null) {
			System.out.println("=====newComplexEvents[0].getUnderlying():" + newComplexEvents[0].getUnderlying());	
		}		
	}
	
	public long getLastTimeEvent(){
		return lastTimeEventProcessed;
	}
}
