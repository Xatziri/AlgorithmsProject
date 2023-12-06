import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
/**
 * Test scaffolding for class Allocator.
 * @author  Dr. Jody Paul
 * @version 20231114
 */
public class AllocatorTest {
    // Data for tests.
    public Supplier s0, s1, s2;
    public Transporter tM, tA, tB, tC;
    public Collection<Supplier> sColl;
    public Collection<Transporter> tColl;

    /**
     * Constructor generates and saves serialized test data.
     */
    public AllocatorTest() {
        // Generate test data.
        Supplier manufacturer = new Supplier("Manufacturer", 0, 0, 400, 999);
        Supplier dist1 = new Supplier("Distributor 1", 3, 55, 0, 147);
        Supplier dist2 = new Supplier("Distributor 2", 5, 40, 0, 157);
        Transporter manuShip = new Transporter("Shipping",
                                              manufacturer,
                                              dist1,
                                              0,
                                              Transporter.MAX_UNITS,
                                              0);
        Transporter transpA = new Transporter("Transporter A",
                                              dist1,
                                              dist2,
                                              35,
                                              30,
                                              0);
        Transporter transpB = new Transporter("Transporter B",
                                              dist1,
                                              dist2,
                                              40,
                                              90,
                                              0);
        Transporter transpC = new Transporter("Transporter C",
                                              dist2,
                                              dist1,
                                              25,
                                              20,
                                              0);
        
        try {
            manufacturer.save("sup0.ser");
            dist1.save("sup1.ser");
            dist2.save("sup2.ser");
            manuShip.save("trnM.ser");
            transpA.save("trnA.ser");
            transpB.save("trnB.ser");
            transpC.save("trnC.ser");
        } catch (Exception e) {
            System.err.println("Exception when saving serialized data. " + e);
        }
        
    }

    /**
     * Sets up the test fixture by restoring
     * serialized suppliers and transporters.
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp() {
        s0 = new Supplier(); // Manufacturer
        s1 = new Supplier(); // Distributor 1
        s2 = new Supplier(); // Distributor 2
        tM = new Transporter(); // Shipping from manufacturer
        tA = new Transporter();
        tB = new Transporter();
        tC = new Transporter();
        try {
            s0.restore("sup0.ser");
            s1.restore("sup1.ser");
            s2.restore("sup2.ser");
            tM.restore("trnM.ser");
            tA.restore("trnA.ser");
            tB.restore("trnB.ser");
            tC.restore("trnC.ser");
        } catch (Exception e) {
            System.err.println("Exception when restoring test data. " + e);
        }
        // Validate at least one field of one restored object.
        if (!tB.name().equals("Transporter B")) {
            System.err.println("Restored test data corrupted.");
            System.exit(1);
        }
        sColl = new HashSet<Supplier>();
        sColl.add(s0);
        sColl.add(s1);
        sColl.add(s2);
        tColl = new HashSet<Transporter>();
        tColl.add(tM);
        tColl.add(tA);
        tColl.add(tB);
        tColl.add(tC);
    }

    /**
     * Tears down the test fixture, enabling garbage collection.
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown() {
        s0 = s1 = s2 = null;
        tM = tA = tB = tC = null;
    }

    /**
     * Test the totalDemand method.
     */
    @Test
    public void totalDemandTest() {
        assertEquals(95, Allocator.totalDemand(sColl));    
    }
    
    /**
     * Test that a null parameter passed into allocateForLowestTransporterCost
     * returns a collection of size 0.
     */
    @Test
    public void allocatorXportNullParamReturnsEmptyResultTest() {
        Collection<Transporter> result = Allocator.allocateForLowestTransporterCost(null, tColl);
        if (result == null) fail();
        assertTrue(0 == result.size());
        
        result = Allocator.allocateForLowestTransporterCost(sColl, null);
        if (result == null) fail();
        assertTrue(0 == result.size());

        result = Allocator.allocateForLowestTransporterCost(null, null);
        if (result == null) fail();
        assertTrue(0 == result.size());
    }

    /**
     * Demonstrate printing of string produced by displayAllocations.
     * Cannot test for full string because order is not specified.
     */
    @Test
    public void displayAllocationsTest() {
        Collection<Transporter> transporters = new HashSet<Transporter>();
        tA.setAllocation(11);
        tB.setAllocation(22);
        tC.setAllocation(50); // Actual allocation is 20 due to maxCapacity limit
        transporters.add(tM);
        transporters.add(tA);
        transporters.add(tB);
        transporters.add(tC);
        String display = Allocator.displayAllocations(transporters);
        assertTrue(display.contains("Shipping"));
        assertTrue(display.contains("Transporter A"));
        assertTrue(display.contains("35"));
        assertTrue(display.contains("Transporter B"));
        assertTrue(display.contains("22"));
        assertTrue(display.contains("Transporter C"));
        assertTrue(display.contains("25"));
        // Just show the display on the console!
        System.out.println("displayAllocations returns...\n" + display);
     }
}