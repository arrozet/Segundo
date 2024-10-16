package parciales;

public class Parcial2020_2 {

	public static char antesRecesion(double[] a, double[] b) {
		if(buscarInicio(a) < buscarInicio(b)) {
			return 'A';
		}
		else if(buscarInicio(b) < buscarInicio(a)) {
			return 'B';
		}
		else {
			return 'I';
		}
	}
	
	public static int buscarInicio(double[] a) {
		return buscarInicio(a,0,a.length-1);
	}
	
	public static int buscarInicio(double[] a, int ini, int fin) {
		int mid = (ini+fin)/2;
		if(a[mid]<a[mid-1]) {
			return mid;
		}
		else if (a[mid]>0) {
			return buscarInicio(a,mid,fin);
		}
		else {
			return buscarInicio(a,ini,mid);
		}
	}
	
	
	public static void main(String[] args) {
		double[] a = {5,4.5,3,1,-0.25,-0.5,-3,-10};
		double[] b = {5,4.5,3,145,45,-0.5,-3,-10};
//		double[] b =  {7, 2, 0.35, 0.35, 0, 0,-1, -3.5};
		System.out.println(antesRecesion(a,b));

	}

}
