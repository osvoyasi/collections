package collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Test validation suite for ListPerfTest
 */
class ListPerfTestTest {
    
    private ListPerfTest perfTest;
    
    @BeforeEach
    void setUp() {
        perfTest = new ListPerfTest(100, 1000);
    }
    
    @Test
    void testPerfTestInitialization() {
        assertNotNull(perfTest);
        assertTrue(perfTest.getResults().isEmpty());
    }
    
    @Test
    void testRunAllTests() {
        assertDoesNotThrow(() -> perfTest.runAllTests());
        
        List<ListPerfTest.TestResult> results = perfTest.getResults();
        assertFalse(results.isEmpty());
        
        long arrayListResults = results.stream()
            .filter(r -> r.getListType().equals("ArrayList"))
            .count();
        long linkedListResults = results.stream()
            .filter(r -> r.getListType().equals("LinkedList"))
            .count();
            
        assertTrue(arrayListResults > 0);
        assertTrue(linkedListResults > 0);
    }
    
    @Test
    void testTestResultCreation() {
        ListPerfTest.TestResult result = new ListPerfTest.TestResult(
            "test", "ArrayList", 1000, 5000000L);
            
        assertEquals("test", result.getOperation());
        assertEquals("ArrayList", result.getListType());
        assertEquals(1000, result.getIterations());
        assertEquals(5000000L, result.getTimeNanos());
        assertEquals(5000L, result.getTimeMicros());
    }
    
    @Test
    void testClearResults() {
        perfTest.runAllTests();
        assertFalse(perfTest.getResults().isEmpty());
        
        perfTest.clearResults();
        assertTrue(perfTest.getResults().isEmpty());
    }
}