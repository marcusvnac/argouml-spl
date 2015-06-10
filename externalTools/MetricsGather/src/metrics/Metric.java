package metrics;

/**
 * Representa uma métrica, contendo suas submétricas e valores
 * @author Marcus
 */
public class Metric {
	
	/**
	 * Nome da feature a qual se refere a metrica;
	 */
	private final String feature;
	
	/**
	 * Array contendo o tipo de métrica: Granularidade ou Localização.
	 */
	private SubMetric[] subMetrics;	
	
	/**
	 * Retorna o nome da feature
	 * @return Nome da feature.
	 */
	public String getFeature() {
		return feature;
	}

	/**
	 * Construtor padrão.
	 * @param feature nome da Feature
	 */
	public Metric(String feature) {
		this.subMetrics = new SubMetric[MetricType.values().length-1];
		for (int i=0; i<this.subMetrics.length; i++) {
			this.subMetrics[i] = new SubMetric();
		}
		this.feature = feature;
	}
	
	/**
	 * Retorna a submétrica.
	 * @param typeEnum tipo da métrica a retornar.
	 * @return submétroca encontada.
	 */
	public SubMetric getSubMetric(MetricType typeEnum) {
		return this.subMetrics[typeEnum.ordinal()];
	}	
	
	/**
	 * Armazena uma submétrica para a métrica.
	 * @param metricType Tipo da métrica.
	 * @param subMetric Tipo da submétrica.
	 * @param value Valor da submétrica.
	 */
	public void storeMetric(MetricType metricType, String subMetric, Integer value) {
		this.subMetrics[metricType.ordinal()].addValue(subMetric, value);
		
	}
}
