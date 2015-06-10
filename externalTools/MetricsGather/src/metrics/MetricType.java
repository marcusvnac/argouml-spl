package metrics;

/**
 * Representa os tipos de métricas a serem contabilizadas.
 * @author Marcus
 *
 */
public enum MetricType {
	GRANULARITY("GranularityType"), LOCALIZATION("Localization"), OR("OR"), AND("AND"), SD("SD"), 
	CLASS_NUMBER("#Classes"), PACKAGE_NUMBER("#Pacotes"), LOC("LOC");	
	
	 /** Origem da venda.*/
    private final String identifier;
    
    /**
     * Construtor protegido.
     * @param saleOrigin origem da venda.
     */
    private MetricType(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Retorna o identificador da Métrica.
     * @return Identificador da métrica.
     */
    public String getIdentifier() {
        return this.identifier;
    }
    
    public static MetricType getByIdentifier(String identifier) {
    	for (int i=0; i<MetricType.values().length; i++) {    		
    		MetricType metricType = MetricType.values()[i]; 
    		if (metricType.getIdentifier().equals(identifier)) {
    			return metricType;
    		}
    	}
    	return null;
    }
}
