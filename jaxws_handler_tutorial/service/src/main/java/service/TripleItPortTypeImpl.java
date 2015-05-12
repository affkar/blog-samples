package service;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import org.example.contract.tripleit.TripleItPortType;
import org.example.schema.tripleit.TripleItRequest;
import org.example.schema.tripleit.TripleItResponse;

@HandlerChain(file = "/handlers.xml")
public class TripleItPortTypeImpl implements TripleItPortType {

	@Resource
	private WebServiceContext context;
	
	@Override
	public TripleItResponse tripleIt(TripleItRequest parameters) {
		TripleItResponse tripleItResponse = new TripleItResponse();
		tripleItResponse.setTripledNumber(parameters.getNumberToTriple()*3);
		return tripleItResponse;
	}

}