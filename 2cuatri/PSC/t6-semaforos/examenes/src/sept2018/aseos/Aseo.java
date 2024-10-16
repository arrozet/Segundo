package sept2018.aseos;


import java.util.concurrent.Semaphore;

public class Aseo {

	private int mujeres=0;
	private int hombres=0;

	private Semaphore hombreOcupado = new Semaphore(1);
	private Semaphore mujerOcupado = new Semaphore(1);
	private Semaphore ocupadoPorOtroSexo = new Semaphore(1);
	/**
	 * El hombre id quiere entrar en el aseo. 
	 * Espera si no es posible, es decir, si hay alguna mujer en ese
	 * momento en el aseo
	 */
	public void llegaHombre(int id) throws InterruptedException{
		hombreOcupado.acquire();
		if(hombres==0){	// si es el primer hombre
			ocupadoPorOtroSexo.acquire();
		}
		hombres++;
		System.out.println("Hombre: entra hombre " + id + ". Quedan " + hombres);
		hombreOcupado.release();
	}
	/**
	 * La mujer id quiere entrar en el aseo. 
	 * Espera si no es posible, es decir, si hay algun hombre en ese
	 * momento en el aseo
	 */
	public void llegaMujer(int id) throws InterruptedException{
		mujerOcupado.acquire();
		if(mujeres==0){	// si es la primera mujer
			ocupadoPorOtroSexo.acquire();
		}
		mujeres++;
		System.out.println("Mujer: entra mujer " + id+ ". Quedan " + mujeres);
		mujerOcupado.release();
	}
	/**
	 * El hombre id, que estaba en el aseo, sale
	 */
	public void saleHombre(int id)throws InterruptedException{
		hombreOcupado.acquire();
		hombres--;
		System.out.println("	Hombre: sale hombre " + id + ". Quedan " + hombres);
		if(hombres==0){
			ocupadoPorOtroSexo.release();
		}
		hombreOcupado.release();
	}
	
	public void saleMujer(int id)throws InterruptedException{
		mujerOcupado.acquire();
		mujeres--;
		System.out.println("	Mujer: sale mujer " + id + ". Quedan " + mujeres);
		if(mujeres==0){
			ocupadoPorOtroSexo.release();
		}
		mujerOcupado.release();
	}
}
