public class BasePlusCommissionEmployeeTest {
    public static void main(String[] args) {
        // CommissionEmployee
        CommissionEmployee commissionEmployee = new CommissionEmployee(
                "Bob", "Lewis", "333-33-3333", 5000, 0.04);

        //  BasePlusCommissionEmployee 
        BasePlusCommissionEmployee employee = new BasePlusCommissionEmployee(commissionEmployee, 300);

        // Get base-salaried commission employee data
        System.out.println("Employee information obtained by get methods:\n");
        System.out.printf("%s %s%n", "First name is", employee.getCommissionEmployee().getFirstName());
        System.out.printf("%s %s%n", "Last name is", employee.getCommissionEmployee().getLastName());
        System.out.printf("%s %s%n", "Social security number is", employee.getCommissionEmployee().getSocialSecurityNumber());
        System.out.printf("%s %.2f%n", "Gross sales is", employee.getCommissionEmployee().getGrossSales());
        System.out.printf("%s %.2f%n", "Commission rate is", employee.getCommissionEmployee().getCommissionRate());
        System.out.printf("%s %.2f%n", "Base salary is", employee.getBaseSalary());

      
        employee.setBaseSalary(1000);

      
        System.out.printf("%n%s:%n%n%s%n", "Updated employee information obtained by toString", employee.toString());
    }
}
