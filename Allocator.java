import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;
/**
 * Utility class to determine allocations of units to transporters
 * based on characteristics of suppliers.
 * Also includes utility functions associated with the domain.
 * 
 * Different allocator methods may address different goal functions.
 * For example, one goal may be achieving the least expensive allocation;
 * whereas another goal may be robust to potential underestimates of demand.
 * 
 * Allocations must account for all constraints, such as
 * maximum transport capacity and maximum storage capacity.
 *
 * @author CS4050
 * @author Dr. Jody Paul
 * @version 20231114
 */
public class Allocator {

    /**
     * Allocate units to transporters to satisfy distributor
     * demands at the lowest transportation cost.
     * This functionality is the highest priority.
     * If either or both parameters is null, returns a collection of size 0.
     * @param suppliers the suppliers
     * @param transporters the transporters
     * @return the transporters with allocations that provide the lowest cost

     */
    public static Collection<Transporter> allocateForLowestTransporterCost(
                                              Collection<Supplier> suppliers,
                                              Collection<Transporter> transporters) {
        Collection<Transporter> allocation = new HashSet<Transporter>();

        allocation = transporters; // Dummy code; replace with allocation code.
        
        return allocation;
    }

    /**
     * Allocate units to transporters to satisfy distributor
     * demands at the lowest total cost where any units above the demand
     * passing through a supplier incur the associated storage costs.
     * This functionality is of secondary priority.
     * If either or both parameters is null, returns a collection of size 0.
     * @param suppliers the suppliers
     * @param transporters the transporters
     * @return the transporters with allocations that provide the lowest total cost
     */
    public static Collection<Transporter> allocateForLowestTotalCost(
                                              Collection<Supplier> suppliers,
                                              Collection<Transporter> transporters) {
        Collection<Transporter> allocation = new HashSet<Transporter>();

        allocation = transporters; // Dummy code; replace with allocation code.
        
        return allocation;
    }

    /**
     * Utility that determines the total demand of a collection of suppliers.
     * @param suppliers the suppliers
     * @return the sum of the demands of the suppliers
     */
    public static int totalDemand(Collection<Supplier> suppliers) {
        int sum = 0;
        for(Supplier s : suppliers) {
            sum += s.demand();
        }
        return sum;
    }

    /**
     * Utility that determines the total amount shipped by transporters.
     * @param transporters the transporters
     * @return the sum of the allocations of the transporters
     */
    public static int totalAmountShipped(Collection<Transporter> transporters) {
        int sum = 0;
        for(Transporter t : transporters) {
            sum += t.allocation();
        }
        return sum;
    }

    /**
     * Utility that determines the total transportation cost of all allocated shipments.
     * @param transporters the transporters
     * @return the sum of the transportation costs based on allocations of the transporters
     */
    public static int totalTransporterCost(Collection<Transporter> transporters) {
        return transporters.stream()
                           .map(t -> t.allocation() * t.costPerUnit())
                           .reduce(0, Integer::sum);
        
        // Explicit loop version
        // int sum = 0;
        // for(Transporter t : transporters) {
            // sum += t.allocation() * t.costPerUnit();
        // }
        // return sum;
    }

    /**
     * Generate a printable string with a line for each
     * transporter showing its name, allocation, and cost per unit.
     * @param transporters the transporters
     * @return a displayable string showing allocation and unit-cost for each transporter
     */
    public static String displayAllocations(Collection<Transporter> transporters) {
        return transporters.stream()
                           .map(t -> String.format("%s: %s $%s", t.name(), t.allocation(), t.costPerUnit()))
                           .collect(Collectors.joining("\n"));
        // Explicit loop version
        // String str = "";
        // for(Transporter t : transporters) {
            // str += String.format("%s: %s $%s\n", t.name(), t.allocation(), t.costPerUnit());
        // }
        // return str;
    }

    /** Hide constructor of this utility class. */
    private Allocator() { }
}