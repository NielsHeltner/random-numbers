package randomnumbers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomNumbersDriver {
    
    public final static int SEED = 123456789;
    public final static int AMOUNT = 10000;
    
    public static void main(String[] args) {
        RandomGenerator rng = new RandomGenerator(101427, 321, (int) Math.pow(2, 16), SEED);
        RandomGenerator randu = new RandomGenerator(65539, 0, (int) Math.pow(2, 31), SEED);
        Random stock = new Random(SEED);
        
        List<Double> rngs = gatherNumbers(rng, AMOUNT);
        List<Double> randus = gatherNumbers(randu, AMOUNT);
        List<Double> stocks = gatherNumbers(stock, AMOUNT);
        
        /*System.out.println("Is rng uniform? " + testUniformity(rngs));
        System.out.println("Is randus uniform? " + testUniformity(randus));
        System.out.println("Is stock uniform? " + testUniformity(stocks));*/
        
        System.out.println("Is rng independent? " + testIndependence(rngs));
        System.out.println("Is randus independent? " + testIndependence(randus));
        System.out.println("Is stock independent? " + testIndependence(stocks));
    }
    
    public static boolean testIndependence(List<Double> result) {
        
        //Map<Double, Integer> exp = getExpected(result);
        //getObserved(result);
        System.out.println("hi");
        
        return false;
    }
    
    private static Map<Double, Integer> getExpected(List<Double> numbers) {
        Map<Double, Integer> lengthOfRuns = new HashMap();
        for (int i = 1; i < numbers.size(); i++) {
            double expectedSize = (2 / factorial(i + 3)) * (numbers.size() * (Math.pow(i, 2) + 3 * i + 1) - (Math.pow(i, 3) + 3 * Math.pow(i, 2) - i - 4));
            lengthOfRuns.put(expectedSize, lengthOfRuns.getOrDefault(expectedSize, 0) + 1);
        }
        
        return lengthOfRuns;
    }
    
    private static Map<Integer, Integer> getObserved(List<Double> numbers) {
        Map<Integer, Integer> lengthOfRuns = new HashMap(); // key = length of run, value = amount of runs of key length
        
        int length = 0;
        boolean previous = false;
        for (int i = 1; i < numbers.size(); i++) { //the previous and current boolean denotes: false: <=, true: >
            boolean current = numbers.get(i) > numbers.get(i - 1);
            
            if (current != previous) { // new sequence
                updateMap(lengthOfRuns, length); // update map with previous sequence result
                length = 0; // reset since it's a new sequence
            }
            
            length++;
            previous = current;
        }
        updateMap(lengthOfRuns, length); // update map with last sequence
        
        return lengthOfRuns;
    }
    
    private static void updateMap(Map<Integer, Integer> map, int length) {
        map.put(length, map.getOrDefault(length, 0) + 1);
    }
    
    private static long factorial(int number) {
        long result = 1;
        
        for (int factor = 2; factor <= number; factor++) {
            result *= factor;
        }
        
        return result;
    }
    
    public static boolean testUniformity(List<Double> result) {
        List<Double> numbers = result.subList(0, 100);
        Collections.sort(numbers);
        
        List<Double> dPlusList = new ArrayList();
        for (int i = 1; i <= numbers.size(); i++) {
            dPlusList.add((i / (double) numbers.size()) - numbers.get(i - 1));
        }
        double dPlus = Collections.max(dPlusList);
        
        List<Double> dMinusList = new ArrayList();
        for (int i = 1; i <= numbers.size(); i++) {
            dMinusList.add(Math.abs(numbers.get(i - 1) - ((i - 1) / (double) numbers.size())));
        }
        double dMinus = Collections.max(dMinusList);
        
        
        double d = Math.max(dPlus, dMinus);
        double dAlpha = 1.35810 / Math.sqrt(numbers.size());
        
        return d <= dAlpha;
    }
    
    public static List<Double> gatherNumbers(RandomGenerator rng, int count) {
        List<Double> result = new ArrayList();
        for (int i = 0; i < count; i++) {
            result.add(rng.next());
        }
        return result;
    }
    
    public static List<Double> gatherNumbers(Random rng, int count) {
        List<Double> result = new ArrayList();
        for (int i = 0; i < count; i++) {
            result.add(rng.nextDouble());
        }
        return result;
    }
    
}
