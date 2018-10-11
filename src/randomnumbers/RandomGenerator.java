package randomnumbers;

public class RandomGenerator {
    
    private long a;
    private long c;
    private long m;
    private long seed;
    private long previousX;
    
    public RandomGenerator(long a, long c, long m, long seed) {
        this.a = a;
        this.c = c;
        this.m = m;
        previousX = this.seed = seed;
    }
    
    public double next() {
        return (lcg() / (double) m);
    }
    
    private long lcg() {
        long result = (a * previousX + c) % m;
        previousX = result;
        return result;
    }
    
}
