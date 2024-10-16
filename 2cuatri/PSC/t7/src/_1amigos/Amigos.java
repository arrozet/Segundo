package _1amigos;

public class Amigos {
	//a y b son amigos y ocupan la posición pos en la lista
	private long a;
	private long b;
	private int pos;
	
	public Amigos(long a,long b,int pos){
		this.a = a; this.b = b;
		this.pos = pos;
	}

	public String toString(){
		return pos+":("+ a + ","+ b+")";
	}
	
	public int pos(){
		return pos;
	}
}
