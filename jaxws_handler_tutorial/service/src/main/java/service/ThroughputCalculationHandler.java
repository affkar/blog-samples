package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;

public class ThroughputCalculationHandler implements
		javax.xml.ws.handler.LogicalHandler<LogicalMessageContext>, Controllable {

	private static final String REQUEST_ID = "RequestId";
	private static final String RESPONDED_TIME = "responded-time";
	private static final String REQUESTED_TIME = "requested-time";
	
	private Map<Integer, AtomicInteger> requestsIn=new HashMap<Integer, AtomicInteger>();
	private Map<Integer, AtomicInteger> responsesOut=new HashMap<Integer, AtomicInteger>();
	private Map<Integer, AtomicInteger> faultout=new HashMap<Integer, AtomicInteger>();
	private Map<Integer,List<Long>> responseTimesInMs=new HashMap<Integer, List<Long>>();
	private QName serviceName;
	private QName operationName;
	
	private static long startTime;
	private CollatorDaemon collatorDaemon=CollatorDaemon.getInstance();
	private static AtomicInteger messageId=new AtomicInteger(1);
	
	@Override
	public void close(MessageContext mc) {
	}

	@Override
	public boolean handleFault(LogicalMessageContext messagecontext) {
		int bucket = getCurrentBucket();
		faultout.get(bucket).incrementAndGet();
		return true;
	}

	@Override
	public boolean handleMessage(LogicalMessageContext mc) {
		int bucket = getCurrentBucket();
		if(serviceName==null && operationName==null){
			serviceName=(QName) mc.get(MessageContext.WSDL_SERVICE);
			operationName=(QName) mc.get(MessageContext.WSDL_OPERATION);
			
			collatorDaemon.attach(this);
		}
		if (Boolean.FALSE.equals(mc
				.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY))) {
			mc.put(REQUESTED_TIME, new Date());
			mc.put(REQUEST_ID, messageId.getAndIncrement());
			
			if(requestsIn.get(bucket)==null){
				requestsIn.put(bucket, new AtomicInteger(1));
			}else{
				requestsIn.get(bucket).incrementAndGet();
			}
		} else {
			mc.put(RESPONDED_TIME, new Date());
			
			if(responseTimesInMs.get(bucket)==null){
				responseTimesInMs.put(bucket, new ArrayList<Long>()); 
			}
			responseTimesInMs.get(bucket).add(getTimeDifference((Date) mc.get(REQUESTED_TIME),
					(Date) mc.get(RESPONDED_TIME)));
			if(responsesOut.get(bucket)==null){
				responsesOut.put(bucket, new AtomicInteger(1));
			}else{
				responsesOut.get(bucket).incrementAndGet();
			}
		}
		return true;
	}


	private int getCurrentBucket() {
		initialiseStartTimeIfNotInitialised();
		long current_time = System.currentTimeMillis();
		return (int) (current_time - startTime) / 10000;
	}

	private void initialiseStartTimeIfNotInitialised() {
		if (startTime == 0) {
			startTime = System.currentTimeMillis();
		}
	}

	public void reset() {
		startTime=0;
		requestsIn=new HashMap<Integer, AtomicInteger>();
		responsesOut=new HashMap<Integer, AtomicInteger>();
		faultout=new HashMap<Integer, AtomicInteger>();
		serviceName=null;
		operationName=null;
	}

	@Override
	public Statistics getStatistics() {
		return new Statistics(serviceName, operationName, requestsIn, responsesOut, faultout, responseTimesInMs);
	}
	
	private Long getTimeDifference(Date date1, Date date2) {
		return (date2.getTime() - date1.getTime());
	}

}