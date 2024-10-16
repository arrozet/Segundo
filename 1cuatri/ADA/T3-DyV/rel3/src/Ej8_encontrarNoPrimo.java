
public class Ej8_encontrarNoPrimo {
	// CHAT GPT
	private static int morePrimes(int[] A, int i, int j, int r, int s) {
        int primeCount1 = countPrimes(A, i, j);
        int primeCount2 = countPrimes(A, r, s);
        
        if (primeCount1 > primeCount2) {
            return 1;
        } else if (primeCount1 < primeCount2) {
            return -1;
        } else {
            return 0;
        }
    }

    private static int countPrimes(int[] A, int start, int end) {
        int count = 0;
        for (int k = start; k <= end; k++) {
            if (isPrime(A[k])) {
                count++;
            }
        }
        return count;
    }

    private static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
	// CHAT GPT
    
	public static int encontrarNoPrimo(int[] a) {
		return encontrarNoPrimo(a, 0, a.length-1);
	}
	private static int encontrarNoPrimo(int[] a, int start, int end) {
		if(end-start == 0) {
			return a[start];
		}
		else {
			int mid = (start + end) / 2;
			if((end+1-start) % 2 == 1) {
				int comparacionPrimosIzqDer = morePrimes(a,start,mid-1,mid+1,end);
				
				if(comparacionPrimosIzqDer == 1) {	// no puedo poner +1, pq si el array es impar una mitad tendrá más primos
					return encontrarNoPrimo(a, mid+1, end);
				}
				else if (comparacionPrimosIzqDer == -1) {
					return encontrarNoPrimo(a, start, mid-1);
				}
				else {	// como solo hay un elemento discrepante
					return a[mid];
				}
			}
			else{
				int comparacionPrimosIzqDer = morePrimes(a,start,mid-1,mid,end);
				
				
				if(comparacionPrimosIzqDer == 1) {	// no puedo poner +1, pq si el array es impar una mitad tendrá más primos
					return encontrarNoPrimo(a, mid, end);
				}
				else {
					return encontrarNoPrimo(a, start, mid-1);
				}
			}
		}
	}
	
	public static int encontrarDiscrepante(int[] a) {
		return encontrarDiscrepante(a, 0, a.length-1);
	}
	private static int encontrarDiscrepante(int[] a, int start, int end) {
		if(end-start == 0) {
			return a[start];
		}
		else {
			int valor_retorno = Integer.MIN_VALUE;
			int mid = (start + end)/2;
			int fin1 = mid; int start2 = mid;
			
			if(mid-start > end-mid) {
				fin1 = mid-1;
			}
			
			int masPrimos = morePrimes(a, start, fin1, start2, end);
			
			if(masPrimos == 1) {
				valor_retorno = encontrarDiscrepante(a, start2, end);	// supongo que el discrepante es el primo
			}
			else if (masPrimos == -1) {
				valor_retorno = encontrarDiscrepante(a,start,fin1);
			}
			else {
				valor_retorno = Integer.MIN_VALUE;
			}
			
			if(valor_retorno == Integer.MIN_VALUE) {	// no era primo el valor discrepante
				if(masPrimos == 1) {
					valor_retorno = encontrarDiscrepante(a,start,fin1);
				}
				else if (masPrimos == -1) {
					valor_retorno = encontrarDiscrepante(a, start2, end);
				} 
			}
			
			return valor_retorno;
		}
	}
	
	
	
	public static void main (String[] args) {
		// precondicion: hay un elemento discrepante
		int[] a = {3,5,7,88,11,13,17};
		System.out.println(encontrarNoPrimo(a));
		
		// NO HECHO
		int[] b = {2,4,8,16,82,42,98,7};
		System.out.println(encontrarDiscrepante(b));
	}
}
