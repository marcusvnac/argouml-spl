package foo;

import util.Log;
import metrics.GatherMetrics;


public class Main {

	/**
	 * Inicializa o sistema.
	 * @param args parâmetros de execução. 
	 * 				1º parâmetro: diretório raiz 
	 * 				2º parâmetro: arquivo de saída. 
	 * 				3º parâmetro: Apenas métricas básicas
	 */
	public static void main(String[] args) {
		Log.info("Starting gattering metrics");
		if (args.length > 1) {
			GatherMetrics rd;
			if (args.length == 2) {
				rd = new GatherMetrics(args[0]);
			} else {
				rd = new GatherMetrics(args[0], Boolean.parseBoolean(args[2]));
			}
			rd.gatherMetrics(args[1]);
			Log.info("End of gattering metrics. File " + args[1] + " generated.");
		} else {
			Log.info("No args found.");
		}		
	}
}