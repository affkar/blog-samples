package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.namespace.QName;

public class Statistics {

	private Map<Integer, AtomicInteger> requestsIn = new HashMap<Integer, AtomicInteger>();
	private Map<Integer, AtomicInteger> responsesOut = new HashMap<Integer, AtomicInteger>();
	private Map<Integer, AtomicInteger> faultout = new HashMap<Integer, AtomicInteger>();
	private QName serviceName;
	private QName operationName;
	private Map<Integer, List<Long>> responseTimesInMs;
	
	
	
	public Statistics(QName serviceName, QName operationName, Map<Integer, AtomicInteger> requestsIn,
			Map<Integer, AtomicInteger> responsesOut,
			Map<Integer, AtomicInteger> faultout, Map<Integer, List<Long>> responseTimesInMs) {
		super();
		this.serviceName=serviceName;
		this.operationName=operationName;
		this.requestsIn = requestsIn;
		this.responsesOut = responsesOut;
		this.faultout = faultout;
		this.responseTimesInMs=responseTimesInMs;
	}

	public Map<Integer, AtomicInteger> getRequestsIn() {
		return requestsIn;
	}

	public Map<Integer, AtomicInteger> getResponsesOut() {
		return responsesOut;
	}

	public Map<Integer, AtomicInteger> getFaultout() {
		return faultout;
	}

	public QName getServiceName() {
		return serviceName;
	}

	public QName getOperationName() {
		return operationName;
	}

	public Map<Integer, List<Long>> getResponseTimesInMs() {
		return responseTimesInMs;
	}

	@Override
	public String toString() {
		return "Statistics For operationName =[" + operationName + "] serviceName = [" + serviceName + "]" + "[requestsIn=" + requestsIn + ", responsesOut="
				+ responsesOut + ", faultout=" + faultout + ", responseTimesInMs=" + responseTimesInMs + "]";
	}
	

	
	
	
}
