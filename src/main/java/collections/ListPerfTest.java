package collections;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Core performance testing engine for comparing ArrayList and LinkedList operations
 */
public class ListPerfTest {
    
    private final int warmupIterations;
    private final int testIterations;
    private final List<TestResult> results;
    
    public ListPerfTest(int warmupIterations, int testIterations) {
        this.warmupIterations = warmupIterations;
        this.testIterations = testIterations;
        this.results = new ArrayList<>();
    }
    
    public ListPerfTest() {
        this(1000, 10000);
    }
    
    /**
     * Performance test result container
     */
    public static class TestResult {
        private final String operation;
        private final String listType;
        private final int iterations;
        private final long timeNanos;
        
        public TestResult(String operation, String listType, int iterations, long timeNanos) {
            this.operation = operation;
            this.listType = listType;
            this.iterations = iterations;
            this.timeNanos = timeNanos;
        }
        
        public String getOperation() { return operation; }
        public String getListType() { return listType; }
        public int getIterations() { return iterations; }
        public long getTimeNanos() { return timeNanos; }
        public long getTimeMicros() { return TimeUnit.NANOSECONDS.toMicros(timeNanos); }
    }
    
    /**
     * Run all performance tests
     */
    public void runAllTests() {
        System.out.println("🔥 Starting performance tests...");
        System.out.println("Warmup iterations: " + warmupIterations);
        System.out.println("Test iterations: " + testIterations);
        
        // Warm up JVM
        warmUp();
        
        // Run individual tests
        testAddToEnd();
        testAddToBeginning();
        testAddToMiddle();
        testGetRandom();
        testGetSequential();
        testRemoveFromEnd();
        testRemoveFromBeginning();
        testRemoveFromMiddle();
        testIteration();
        
        System.out.println("✅ All tests completed!");
    }
    
    private void warmUp() {
        System.out.print("⚡ Warming up JVM...");
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();
        
        for (int i = 0; i < warmupIterations; i++) {
            arrayList.add(i);
            linkedList.add(i);
            if (!arrayList.isEmpty()) arrayList.get(i % arrayList.size());
            if (!linkedList.isEmpty()) linkedList.get(i % linkedList.size());
        }
        
        arrayList.clear();
        linkedList.clear();
        System.out.println(" Done!");
    }
    
    private void testAddToEnd() {
        System.out.print("🧪 Testing add to end...");
        
        // ArrayList
        long startTime = System.nanoTime();
        List<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < testIterations; i++) {
            arrayList.add(i);
        }
        long arrayListTime = System.nanoTime() - startTime;
        results.add(new TestResult("add(end)", "ArrayList", testIterations, arrayListTime));
        
        // LinkedList
        startTime = System.nanoTime();
        List<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < testIterations; i++) {
            linkedList.add(i);
        }
        long linkedListTime = System.nanoTime() - startTime;
        results.add(new TestResult("add(end)", "LinkedList", testIterations, linkedListTime));
        
        System.out.println(" Done!");
    }
    
    private void testAddToBeginning() {
        System.out.print("🧪 Testing add to beginning...");
        
        // ArrayList
        long startTime = System.nanoTime();
        List<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < testIterations; i++) {
            arrayList.add(0, i);
        }
        long arrayListTime = System.nanoTime() - startTime;
        results.add(new TestResult("add(begin)", "ArrayList", testIterations, arrayListTime));
        
        // LinkedList
        startTime = System.nanoTime();
        List<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < testIterations; i++) {
            linkedList.add(0, i);
        }
        long linkedListTime = System.nanoTime() - startTime;
        results.add(new TestResult("add(begin)", "LinkedList", testIterations, linkedListTime));
        
        System.out.println(" Done!");
    }
    
    private void testAddToMiddle() {
        System.out.print("🧪 Testing add to middle...");
        int smallerTestSize = testIterations / 10;
        
        // Prepare base lists
        List<Integer> arrayListBase = new ArrayList<>();
        List<Integer> linkedListBase = new LinkedList<>();
        for (int i = 0; i < smallerTestSize; i++) {
            arrayListBase.add(i);
            linkedListBase.add(i);
        }
        
        // ArrayList
        long startTime = System.nanoTime();
        List<Integer> arrayList = new ArrayList<>(arrayListBase);
        for (int i = 0; i < smallerTestSize; i++) {
            arrayList.add(arrayList.size() / 2, i);
        }
        long arrayListTime = System.nanoTime() - startTime;
        results.add(new TestResult("add(middle)", "ArrayList", smallerTestSize, arrayListTime));
        
        // LinkedList
        startTime = System.nanoTime();
        List<Integer> linkedList = new LinkedList<>(linkedListBase);
        for (int i = 0; i < smallerTestSize; i++) {
            linkedList.add(linkedList.size() / 2, i);
        }
        long linkedListTime = System.nanoTime() - startTime;
        results.add(new TestResult("add(middle)", "LinkedList", smallerTestSize, linkedListTime));
        
        System.out.println(" Done!");
    }
    
    private void testGetRandom() {
        System.out.print("🧪 Testing random access...");
        
        // Prepare lists
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < testIterations; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }
        
        Random random = new Random(42);
        
        // ArrayList
        long startTime = System.nanoTime();
        for (int i = 0; i < testIterations; i++) {
            arrayList.get(random.nextInt(arrayList.size()));
        }
        long arrayListTime = System.nanoTime() - startTime;
        results.add(new TestResult("get(random)", "ArrayList", testIterations, arrayListTime));
        
        // LinkedList
        startTime = System.nanoTime();
        for (int i = 0; i < testIterations; i++) {
            linkedList.get(random.nextInt(linkedList.size()));
        }
        long linkedListTime = System.nanoTime() - startTime;
        results.add(new TestResult("get(random)", "LinkedList", testIterations, linkedListTime));
        
        System.out.println(" Done!");
    }
    
    private void testGetSequential() {
        System.out.print("🧪 Testing sequential access...");
        
        // Prepare lists
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < testIterations; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }
        
        // ArrayList
        long startTime = System.nanoTime();
        for (int i = 0; i < testIterations; i++) {
            arrayList.get(i);
        }
        long arrayListTime = System.nanoTime() - startTime;
        results.add(new TestResult("get(sequential)", "ArrayList", testIterations, arrayListTime));
        
        // LinkedList
        startTime = System.nanoTime();
        for (int i = 0; i < testIterations; i++) {
            linkedList.get(i);
        }
        long linkedListTime = System.nanoTime() - startTime;
        results.add(new TestResult("get(sequential)", "LinkedList", testIterations, linkedListTime));
        
        System.out.println(" Done!");
    }
    
    private void testRemoveFromEnd() {
        System.out.print("🧪 Testing remove from end...");
        
        // ArrayList
        List<Integer> arrayList = createFilledList(new ArrayList<>(), testIterations);
        long startTime = System.nanoTime();
        while (!arrayList.isEmpty()) {
            arrayList.remove(arrayList.size() - 1);
        }
        long arrayListTime = System.nanoTime() - startTime;
        results.add(new TestResult("remove(end)", "ArrayList", testIterations, arrayListTime));
        
        // LinkedList
        List<Integer> linkedList = createFilledList(new LinkedList<>(), testIterations);
        startTime = System.nanoTime();
        while (!linkedList.isEmpty()) {
            linkedList.remove(linkedList.size() - 1);
        }
        long linkedListTime = System.nanoTime() - startTime;
        results.add(new TestResult("remove(end)", "LinkedList", testIterations, linkedListTime));
        
        System.out.println(" Done!");
    }
    
    private void testRemoveFromBeginning() {
        System.out.print("🧪 Testing remove from beginning...");
        
        // ArrayList
        List<Integer> arrayList = createFilledList(new ArrayList<>(), testIterations);
        long startTime = System.nanoTime();
        while (!arrayList.isEmpty()) {
            arrayList.remove(0);
        }
        long arrayListTime = System.nanoTime() - startTime;
        results.add(new TestResult("remove(begin)", "ArrayList", testIterations, arrayListTime));
        
        // LinkedList
        List<Integer> linkedList = createFilledList(new LinkedList<>(), testIterations);
        startTime = System.nanoTime();
        while (!linkedList.isEmpty()) {
            linkedList.remove(0);
        }
        long linkedListTime = System.nanoTime() - startTime;
        results.add(new TestResult("remove(begin)", "LinkedList", testIterations, linkedListTime));
        
        System.out.println(" Done!");
    }
    
    private void testRemoveFromMiddle() {
        System.out.print("🧪 Testing remove from middle...");
        int smallerTestSize = testIterations / 10;
        
        // ArrayList
        List<Integer> arrayList = createFilledList(new ArrayList<>(), smallerTestSize);
        long startTime = System.nanoTime();
        while (arrayList.size() > 1) {
            arrayList.remove(arrayList.size() / 2);
        }
        long arrayListTime = System.nanoTime() - startTime;
        results.add(new TestResult("remove(middle)", "ArrayList", smallerTestSize, arrayListTime));
        
        // LinkedList
        List<Integer> linkedList = createFilledList(new LinkedList<>(), smallerTestSize);
        startTime = System.nanoTime();
        while (linkedList.size() > 1) {
            linkedList.remove(linkedList.size() / 2);
        }
        long linkedListTime = System.nanoTime() - startTime;
        results.add(new TestResult("remove(middle)", "LinkedList", smallerTestSize, linkedListTime));
        
        System.out.println(" Done!");
    }
    
    private void testIteration() {
        System.out.print("🧪 Testing iteration...");
        
        // Prepare lists
        List<Integer> arrayList = createFilledList(new ArrayList<>(), testIterations);
        List<Integer> linkedList = createFilledList(new LinkedList<>(), testIterations);
        
        // ArrayList iteration
        long startTime = System.nanoTime();
        int sum = 0;
        for (Integer value : arrayList) {
            sum += value;
        }
        long arrayListTime = System.nanoTime() - startTime;
        results.add(new TestResult("iteration", "ArrayList", testIterations, arrayListTime));
        
        // LinkedList iteration
        startTime = System.nanoTime();
        sum = 0;
        for (Integer value : linkedList) {
            sum += value;
        }
        long linkedListTime = System.nanoTime() - startTime;
        results.add(new TestResult("iteration", "LinkedList", testIterations, linkedListTime));
        
        System.out.println(" Done!");
    }
    
    private List<Integer> createFilledList(List<Integer> list, int size) {
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        return list;
    }
    
    /**
     * Get all test results
     */
    public List<TestResult> getResults() {
        return Collections.unmodifiableList(results);
    }
    
    /**
     * Clear previous results
     */
    public void clearResults() {
        results.clear();
    }
}