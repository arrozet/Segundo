public class Interval implements Comparable<Interval>{
    private int high, low;
    public Interval(int bajo, int alto){
        low = bajo;
        high = alto;
    }
    public int high(){
        return high;
    }
    public int low(){
        return low;
    }
    public Interval union(Interval k){
        return new
                Interval(Math.min(k.low,low),Math.max(k.high,high));
    }
    public boolean overlap(Interval k) {
        return k!=null && low<= k.high && high>=k.low;
    }
    public int compareTo(Interval o){
        return low - o.low;
    }
    @Override
    public String toString() {
        return "[" + low + "," + " " + high + "]";
    }
}
