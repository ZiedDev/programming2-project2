package Model;

public class FullTimeEmployee extends Employee {
    private double basicSalary;
    private double bonus;

    public FullTimeEmployee(
            String fullName,
            Department department,
            int age,
            String position,
            double basicSalary,
            double bonus) {
        super(fullName, department, age, position);
        this.basicSalary = basicSalary;
        this.bonus = bonus;
    }

    public double getMonthlySalary() {
        return this.basicSalary + this.bonus;
    }

    public String getEmployeeType() {
        return "Full-Time";
    }

    public double getBasicSalary() {
        return this.basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public double getBonus() {
        return this.bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }
}
