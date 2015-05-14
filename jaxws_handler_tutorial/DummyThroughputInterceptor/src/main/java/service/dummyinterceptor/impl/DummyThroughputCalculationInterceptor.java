package service.dummyinterceptor.impl;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

import service.interceptor.api.ThroughputPhaseInterceptor;

public class DummyThroughputCalculationInterceptor  extends
		AbstractPhaseInterceptor<Message> implements ThroughputPhaseInterceptor<Message> {

	
	public DummyThroughputCalculationInterceptor() {
		super(Phase.USER_LOGICAL,true);
	}
	@Override
	public void handleMessage(Message message) throws Fault {
		//do nothing
	}
	
}
