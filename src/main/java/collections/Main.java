package collections;

import java.util.*;

/**
 * Results visualization and main entry point
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("================================================================");
        System.out.println("           ArrayList vs LinkedList Performance Test         ");
        System.out.println("================================================================");
        System.out.println();
        
        // Create and run performance tests
        ListPerfTest perfTest = new ListPerfTest(1000, 10000);
        perfTest.runAllTests();
        
        // Display results
        displayResults(perfTest.getResults());
        
        // Display summary
        displaySummary(perfTest.getResults());
        
        System.out.println("================================================================");
        System.out.println("                          TEST COMPLETED                        ");
        System.out.println("================================================================");
    }
    
    private static void displayResults(List<ListPerfTest.TestResult> results) {
        System.out.println();
        System.out.println("PERFORMANCE RESULTS (in microseconds)");
        System.out.println("==========================================");
        System.out.printf("%-20s | %-10s | %-12s | %-12s%n", 
            "Operation", "Iterations", "ArrayList", "LinkedList");
        System.out.println("---------------------|------------|--------------|--------------");
        
        // Group results by operation
        Map<String, List<ListPerfTest.TestResult>> groupedResults = new LinkedHashMap<>();
        for (ListPerfTest.TestResult result : results) {
            groupedResults
                .computeIfAbsent(result.getOperation(), k -> new ArrayList<>())
                .add(result);
        }
        
        // Display grouped results
        for (Map.Entry<String, List<ListPerfTest.TestResult>> entry : groupedResults.entrySet()) {
            String operation = entry.getKey();
            List<ListPerfTest.TestResult> operationResults = entry.getValue();
            
            ListPerfTest.TestResult arrayListResult = operationResults.stream()
                .filter(r -> r.getListType().equals("ArrayList"))
                .findFirst()
                .orElse(null);
                
            ListPerfTest.TestResult linkedListResult = operationResults.stream()
                .filter(r -> r.getListType().equals("LinkedList"))
                .findFirst()
                .orElse(null);
            
            if (arrayListResult != null && linkedListResult != null) {
                System.out.printf("%-20s | %-10d | %-12d | %-12d%n",
                    operation,
                    arrayListResult.getIterations(),
                    arrayListResult.getTimeMicros(),
                    linkedListResult.getTimeMicros());
            }
        }
    }
    
    private static void displaySummary(List<ListPerfTest.TestResult> results) {
        System.out.println();
        System.out.println("PERFORMANCE SUMMARY");
        System.out.println("======================");
        
        Map<String, Integer> wins = new HashMap<>();
        wins.put("ArrayList", 0);
        wins.put("LinkedList", 0);
        
        // Group and analyze results
        Map<String, List<ListPerfTest.TestResult>> groupedResults = new LinkedHashMap<>();
        for (ListPerfTest.TestResult result : results) {
            groupedResults
                .computeIfAbsent(result.getOperation(), k -> new ArrayList<>())
                .add(result);
        }
        
        System.out.println("\nWINNER BY OPERATION:");
        System.out.println("----------------------");
        
        for (Map.Entry<String, List<ListPerfTest.TestResult>> entry : groupedResults.entrySet()) {
            String operation = entry.getKey();
            List<ListPerfTest.TestResult> operationResults = entry.getValue();
            
            ListPerfTest.TestResult arrayListResult = operationResults.stream()
                .filter(r -> r.getListType().equals("ArrayList"))
                .findFirst()
                .orElse(null);
                
            ListPerfTest.TestResult linkedListResult = operationResults.stream()
                .filter(r -> r.getListType().equals("LinkedList"))
                .findFirst()
                .orElse(null);
            
            if (arrayListResult != null && linkedListResult != null) {
                String winner = arrayListResult.getTimeNanos() < linkedListResult.getTimeNanos() 
                    ? "ArrayList" : "LinkedList";
                wins.put(winner, wins.get(winner) + 1);
                
                System.out.printf("• %-18s: %s%n", operation, winner);
            }
        }
        
        System.out.println();
        System.out.println("FINAL SCORE:");
        System.out.println("ArrayList: " + wins.get("ArrayList") + " wins");
        System.out.println("LinkedList: " + wins.get("LinkedList") + " wins");
        
        System.out.println();
        System.out.println("PRACTICAL RECOMMENDATIONS:");
        System.out.println("• Use ArrayList for: random access, iteration, add/remove at end");
        System.out.println("• Use LinkedList for: frequent insertions/deletions at beginning/middle");
        System.out.println("• Default choice: ArrayList (better memory locality, cache-friendly)");
    }
}