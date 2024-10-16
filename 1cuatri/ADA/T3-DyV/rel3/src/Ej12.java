//
//
//int[][] concatenar(int[][] a, int[][] b){
//    set(a,b,b,a); //por cuadrantes
//} 
//
//int[][] matriz (int dim, int el){
//    if(dim==1){	
//        int [][] m;
//        m[0][0]=el; //n^2
//        return m;
//    }else{
//        int[][] a =matriz(dim/2, el);
//        int[][] b = matriz(dim/2, el+(dim/2));
//        concatenar(a,b); 
//    }
//}
//
//
//int[][] planificacion(int n){
//    //precondición: n=2^i, i natural positivo != 0
//
//    return matriz(n,1);
//
//}
//
