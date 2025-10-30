public class BasePlusCommissionEmployee extends CommissionEmployee {
    private double baseSalary; // base salary per week

    // Constructor
    public BasePlusCommissionEmployee(String firstName, String lastName, String socialSecurityNumber,
                                       double grossSales, double commissionRate, double baseSalary) {
        super(firstName, lastName, socialSecurityNumber, grossSales, commissionRate);

        if (baseSalary < 0.0) {
            throw new IllegalArgumentException("Base salary must be >= 0.0");
        }

        this.baseSalary = baseSalary;
    }

    // Setter for baseSalary
    public void setBaseSalary(double baseSalary) {
        if (baseSalary < 0.0) {
            throw new IllegalArgumentException("Base salary must be >= 0.0");
        }

        this.baseSalary = baseSalary;
    }

    // Getter for baseSalary
    public double getBaseSalary() {
        return baseSalary;
    }

    // Override earnings to include base salary
    @Override
    public double earnings() {
        return getBaseSalary() + super.earnings();
    }

    // Override toString for proper representation
    @Override
    public String toString() {
        return String.format(
            "%s%n%s: %.2f", 
            super.toString(), 
            "Base Salary", getBaseSalary()
        );
    }
}
