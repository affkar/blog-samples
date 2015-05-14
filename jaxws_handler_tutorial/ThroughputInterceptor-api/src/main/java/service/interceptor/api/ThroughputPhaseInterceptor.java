package service.interceptor.api;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptor;
/**
 * Marker Interface
 * @author alagu
 *
 * @param <T>
 */
public interface ThroughputPhaseInterceptor<T extends Message> extends PhaseInterceptor<T> {

}
