import java.util.Arrays;

public class MainTarifa {
	public static void main(String[] args) {
		int[] estimacion= {11, 11, 25, 22, 10, 12, 21, 8, 28, 4};
		// tarifa plana, permanencia, tarifaMegas
		TarifaTelefonica p=new TarifaTelefonica(11,4,1,estimacion);
		
		int[] megasCompleto = new int[estimacion.length];
		for(int m=0; m<estimacion.length; m++) {
			megasCompleto[m] = 2 * estimacion[m];
		}
		System.out.println(Arrays.toString(megasCompleto));
		
		System.out.println("El pago acumulado es: " + p.resolverBottomUp());
		System.out.println("El plan que el estudiante seguirá es: ");
		System.out.println(Arrays.toString(p.reconstruirSol()));
		p.imprimeVectorSolucion();
	}

}
