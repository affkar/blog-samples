package service;

import javax.xml.namespace.QName;

public class ServiceAndOperation {

	private QName service;
	private QName operation;
	public ServiceAndOperation(QName service, QName operation) {
		super();
		this.service = service;
		this.operation = operation;
	}
	public QName getService() {
		return service;
	}
	public QName getOperation() {
		return operation;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((operation == null) ? 0 : operation.hashCode());
		result = prime * result + ((service == null) ? 0 : service.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServiceAndOperation other = (ServiceAndOperation) obj;
		if (operation == null) {
			if (other.operation != null)
				return false;
		} else if (!operation.equals(other.operation))
			return false;
		if (service == null) {
			if (other.service != null)
				return false;
		} else if (!service.equals(other.service))
			return false;
		return true;
	}
	
	
	
	
}
