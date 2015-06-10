package metrics;

import java.util.HashMap;
import java.util.Map;

public class SubMetric {
	/**
	 * Tipo da medição efetuada para a métrica.
	 */
	private Map<String, Integer> metricSubType;	
	
	public SubMetric() {
		this.metricSubType = new HashMap<String, Integer>();
	}
	
	/**
	 * Insere submétrica com valor
	 * @param subMetric submétrica da métrica.
	 */
	public void addValue(String subMetric, Integer value) {
		metricSubType.put(subMetric, value);		
	}
	
	public Map<String, Integer> getValues() {
		return this.metricSubType; 
	}
		
}
