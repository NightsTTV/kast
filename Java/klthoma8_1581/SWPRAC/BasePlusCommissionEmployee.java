
public class BasePlusCommissionEmployee {
    private final CommissionEmployee commissionEmployee;
    private double baseSalary;

    public BasePlusCommissionEmployee(CommissionEmployee commissionEmployee, double baseSalary) {
        if (baseSalary < 0.0) {
            throw new IllegalArgumentException("Base salary must be >= 0.0");
        }

        this.commissionEmployee = commissionEmployee;
        this.baseSalary = baseSalary;
    }


    public double getBaseSalary() { return baseSalary; }


    public void setBaseSalary(double baseSalary) {
        if (baseSalary < 0.0) {
            throw new IllegalArgumentException("Base salary must be >= 0.0");
        }
        this.baseSalary = baseSalary;
    }

 
    public CommissionEmployee getCommissionEmployee() {
        return commissionEmployee;
    }

  
    public double earnings() {
        return baseSalary + commissionEmployee.earnings();
    }

 
    @Override
    public String toString() {
        return String.format("%s %s%n%s: %.2f","Base-salaried", commissionEmployee.toString(),"Base salary", getBaseSalary());
    }
}
