package service.interceptor.impl;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

import service.interceptor.api.ThroughputPhaseInterceptor;
import service.performance.CollatorDaemon;
import service.performance.Controllable;
import service.performance.PerformanceAndThroughputInfo;
import service.performance.ServiceAndOperation;
import service.performance.Statistics;

public class ThroughputCalculationInterceptor  extends
		AbstractPhaseInterceptor<Message> implements Controllable, ThroughputPhaseInterceptor<Message> {

	private static final String REQUEST_ID = "RequestId";
	private static final String RESPONDED_TIME = "responded-time";
	private static final String REQUESTED_TIME = "requested-time";
	
	private Statistics statistics;
	
	private static long startTime;
	private CollatorDaemon collatorDaemon;
	private AtomicInteger messageId;
	
	public ThroughputCalculationInterceptor() {
		super(Phase.USER_LOGICAL,true);
		reset();
		collatorDaemon=CollatorDaemon.getInstance();
		collatorDaemon.attach(this);
	}
	
	@Override
	public void handleMessage(Message message) throws Fault {
		int bucket = getCurrentBucket();
		ServiceAndOperation serviceAndOperation = extractServiceAndOperation(message);
		PerformanceAndThroughputInfo performanceAndThroughputInfo = statistics.getPerformanceAndThroughputInfoFor(serviceAndOperation);
		if(MessageUtils.isOutbound(message)){
			addResponseHeaders(message.getExchange());
			if(MessageUtils.isFault(message)){
				performanceAndThroughputInfo.incrementFaultCount(bucket);
				performanceAndThroughputInfo.addFaultResponseTimes(bucket,getTimeDifference(message));
			}else{
				performanceAndThroughputInfo.incrementResponseCount(bucket);
				performanceAndThroughputInfo.addResponseTimes(bucket,getTimeDifference(message));
			}
		}else{
			addRequestHeaders(message.getExchange());
			performanceAndThroughputInfo.incrementRequestCount(bucket);
		}
	}
	private ServiceAndOperation extractServiceAndOperation(Message message) {
			return new ServiceAndOperation((QName) message.get(MessageContext.WSDL_SERVICE),
			(QName) message.get(MessageContext.WSDL_OPERATION));
	}
	private void addResponseHeaders(Exchange exchange) {
		exchange.put(RESPONDED_TIME, new Date());
	}
	private void addRequestHeaders(Exchange exchange) {
		exchange.put(REQUESTED_TIME, new Date());
		exchange.put(REQUEST_ID, messageId.getAndIncrement());
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
		messageId=new AtomicInteger(1);
		statistics=new Statistics();
	}

	@Override
	public Statistics getStatistics() {
		return statistics;
	}
	
	private Long getTimeDifference(Message message){
		return getTimeDifference((Date) message.getExchange().get(REQUESTED_TIME),
				(Date) message.getExchange().get(RESPONDED_TIME));
	}
	
	private Long getTimeDifference(Date date1, Date date2) {
		return (date2.getTime() - date1.getTime());
	}
}