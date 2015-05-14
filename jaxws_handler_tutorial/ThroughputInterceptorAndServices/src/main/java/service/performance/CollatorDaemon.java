package service.performance;

import java.util.ArrayList;
import java.util.List;

public class CollatorDaemon {

	private List<Controllable> handlers=new ArrayList<Controllable>();
	
	public void attach(Controllable handler){
		handlers.add(handler);
	}
	
	public List<String> log(){
		List<String> logs=new ArrayList<String>();
		for(Controllable handler:handlers){
			for(PerformanceAndThroughputInfo info: handler.getStatistics().getAllPerformanceAndThroughputInfo()){
				logs.add(info.toString());
			}
		}
		return logs;
	}
	
	public void reset(){
		for(Controllable handler:handlers){
			handler.reset();
		}
	}
	
	private static CollatorDaemon collatorDaemon=new CollatorDaemon();
	
	private CollatorDaemon(){}
	
	public static CollatorDaemon getInstance() {
		return collatorDaemon;
	}

}
