import java.util.Arrays;

public class TarifaTelefonica {

	private int tarifaPlana, permanencia, tarifaMegas;
	private int[] estimacion;
	private int[] pago;// Pago mínimo a realizar durante los meses de i...n

	public TarifaTelefonica(int tp, int p, int tm, int[] est) {
		tarifaPlana = tp;
		permanencia = p;
		tarifaMegas = tm;
		estimacion = est;
		pago = null;
	}

	public int resolverBottomUp() {	// de arriba a abajo
		pago = new int[estimacion.length+1];// necesitamos un array de tamaño n+1
		int n = estimacion.length;	// en el ultimo hueco del array guardaremos el caso base, 0; por eso iteramos desde 0 a n-1
		
		// literalmente copiar la ecuación
		for(int i=n; i>=0; i--) {
			if(i==n) {
				pago[i]=0;
			}
			else if ((i+permanencia)>n) {
				pago[i] = tarifaMegas * estimacion[i] + pago[i+1];
			}
			else if (i<=n-1) {
				pago[i] = Math.min(tarifaPlana * permanencia + pago[i+permanencia], tarifaMegas * estimacion[i] + pago[i+1]);
			}
		}
		
		return pago[0];	// pues se rellena de derecha a izquierda
	}

	public int[] reconstruirSol() {
		if (pago == null) {
			throw new RuntimeException("Se debe resolver el problema primero");
		}
		// la solucion es un vector de 0 y 1 que indica si ese mes se coge tarifa plana
		int[] solucion = new int[estimacion.length];
		
		int i = 0;
		while(i<solucion.length) {
			// sera tarifa plana cuando sea posible ponerla (por los indices) 
			// y cuando para hallar el mes i+1, en el mes i no se haya pagado precio de megas 
			// (recuerda que pago está rellenado al revés)
			int precioMegasI = estimacion[i]*tarifaMegas;
			if(pago[i]-precioMegasI != pago[i+1]) {
				for(int j=i; j<i+permanencia; j++) {
					solucion[j] = 1;
				}
				i+=permanencia;
			}
			else {
				i++;
			}
			
//			pero para reconstruirlo reconstruyes de izquierda a derecha porque quieres ver 
//			si en el mes i+1 (que está en pago en la izquierda en lugar de en la derecha -> pago[i]) 
//			se pagó precioMegas (estimacion[i]*tarifaMegas). 
//
//			Si no se pagó precioMegas (pago[i]-precioMegas != pago[i+1], donde pago[i+1] representa al mes anterior),
//			es que pusiste tarifaPlana y tienes que cubrir "permanencia" meses
			
		}
		
		
		return solucion;
	}

	public void imprimeVectorSolucion() {
		System.out.println(Arrays.toString(pago));
	}

}