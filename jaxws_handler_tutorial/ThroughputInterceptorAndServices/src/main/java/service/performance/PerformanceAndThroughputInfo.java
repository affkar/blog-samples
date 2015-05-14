package service.performance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class PerformanceAndThroughputInfo {

	private ServiceAndOperation serviceAndOperation;
	private Map<Integer, AtomicInteger> requestsIn;
	private Map<Integer, AtomicInteger> responsesOut;
	private Map<Integer, AtomicInteger> faultout;
	private Map<Integer, List<Long>> responseTimesInMs;
	private Map<Integer, List<Long>> faultOutTimesInMs;
	
	public PerformanceAndThroughputInfo(ServiceAndOperation serviceAndOperation) {
		super();
		this.serviceAndOperation=serviceAndOperation;
		this.requestsIn = new HashMap<Integer, AtomicInteger>();
		this.responsesOut = new HashMap<Integer, AtomicInteger>();
		this.faultout = new HashMap<Integer, AtomicInteger>();
		this.responseTimesInMs=new HashMap<Integer, List<Long>>();
		this.faultOutTimesInMs=new HashMap<Integer, List<Long>>();
	}

	

	@Override
	public String toString() {
		return "PerformanceAndThroughputInfo For operationName =[" + serviceAndOperation.getOperation() + "] serviceName = [" + serviceAndOperation.getService() + "]" + "[requestsIn=" + requestsIn + ", responsesOut="
				+ responsesOut + ", faultout=" + faultout + ", responseTimesInMs=" + responseTimesInMs + ", faultOutTimesInMs=" + faultOutTimesInMs + "]";
	}

	public void incrementRequestCount(int bucket) {
		initialiseBucketAndIncrementCountInMap(bucket, requestsIn);
	}
	
	public void incrementResponseCount(int bucket) {
		initialiseBucketAndIncrementCountInMap(bucket, responsesOut);
	}
	
	public void incrementFaultCount(int bucket) {
		initialiseBucketAndIncrementCountInMap(bucket, faultout);
	}
	
	private void initialiseBucketAndIncrementCountInMap(int bucket, Map<Integer, AtomicInteger> countMap) {
		if(countMap.get(bucket)==null){
			countMap.put(bucket, new AtomicInteger(1));
		}else{
			countMap.get(bucket).incrementAndGet();
		}
	}



	public void addFaultResponseTimes(int bucket, Long timeDifference) {
		initialiseBucketAndAddResponseTimesInMap(bucket, timeDifference, faultOutTimesInMs);
	}



	public void addResponseTimes(int bucket, Long timeDifference) {
		initialiseBucketAndAddResponseTimesInMap(bucket, timeDifference, responseTimesInMs);
	}
	
	private void initialiseBucketAndAddResponseTimesInMap(int bucket, Long timeDifference, Map<Integer, List<Long>> returnTimeMap) {
		if(returnTimeMap.get(bucket)==null){
			returnTimeMap.put(bucket, new ArrayList<Long>()); 
		}
		returnTimeMap.get(bucket).add(timeDifference);
	}
	
}
