package service.performance;

public interface PerformanceInput {

	void incrementRequestsCount();

	void incrementResponsesCount();

	void incrementFaultsCount();

	void addResponseTime(Long reponseTimes);

	void addResponseTimeForFault(Long reponseTimes);

	void addRequestPayloadSize(Long payloadSize);

	void addResponsePayloadSize(Long payloadSize);

	void addFaultPayloadSize(Long payloadSize);
}
