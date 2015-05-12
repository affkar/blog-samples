package service;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import org.example.contract.doubleit.DoubleItPortType;
import org.example.schema.doubleit.DoubleItRequest;
import org.example.schema.doubleit.DoubleItResponse;

@HandlerChain(file = "/handlers.xml")
public class DoubleItPortTypeImpl implements DoubleItPortType {

   @Resource
   private WebServiceContext context;

	@Override
	public DoubleItResponse doubleIt(DoubleItRequest parameters) {
		DoubleItResponse doubleItResponse = new DoubleItResponse();
		doubleItResponse.setDoubledNumber(parameters.getNumberToDouble()*2);
		return doubleItResponse;
	}
}

