package service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;

public class ResponseTimesLogicalHandler implements
		javax.xml.ws.handler.LogicalHandler<LogicalMessageContext> {

	private static final String RESPONDED_TIME = "responded-time";
	private static final String REQUESTED_TIME = "requested-time";

	private AtomicInteger messageId=new AtomicInteger(1); 
	
	@Override
	public void close(MessageContext mc) {
	}

	@Override
	public boolean handleFault(LogicalMessageContext messagecontext) {
		return true;
	}

	@Override
	public boolean handleMessage(LogicalMessageContext mc) {
		if (Boolean.FALSE.equals(mc
				.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY))) {
			mc.put(REQUESTED_TIME, new Date());
			mc.put("RequestId", messageId.getAndIncrement());
		} else {
			mc.put(RESPONDED_TIME, new Date());
			Map<String, Object> propertyMap = mc;
			System.out.println("responsetimeinms<"
					+ getTimeDifference((Date) mc.get(REQUESTED_TIME),
							(Date) mc.get(RESPONDED_TIME)) + ">WSDL Interface<"
					+ (QName) propertyMap.get(MessageContext.WSDL_INTERFACE)
					+ ">WSDL Operation<"
					+ (QName) propertyMap.get(MessageContext.WSDL_OPERATION)
					+ ">WSDL Port<"
					+ (QName) propertyMap.get(MessageContext.WSDL_PORT)
					+ ">WSDL Service<"
					+ (QName) propertyMap.get(MessageContext.WSDL_SERVICE));
		}
		return true;
	}

	private Long getTimeDifference(Date date1, Date date2) {
		return (date2.getTime() - date1.getTime());
	}

}
