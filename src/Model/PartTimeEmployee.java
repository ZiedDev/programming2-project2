package Model;

public class PartTimeEmployee extends Employee {
    private static final long serialVersionUID = 1L;

    private double hourlyRate;
    private int hoursPerMonth;

    public PartTimeEmployee(
            String fullName,
            Department department,
            int age,
            String position,
            double hourlyRate,
            int hoursPerMonth) {
        super(fullName, department, age, position);
        this.hourlyRate = hourlyRate;
        this.hoursPerMonth = hoursPerMonth;
    }

    public double getMonthlySalary() {
        return this.hourlyRate * this.hoursPerMonth;
    }

    public String getEmployeeType() {
        return "Part-Time";
    }

    public double getHourlyRate() {
        return this.hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public int getHoursPerMonth() {
        return this.hoursPerMonth;
    }

    public void setHoursPerMonth(int hoursPerMonth) {
        this.hoursPerMonth = hoursPerMonth;
    }
}
