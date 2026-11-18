package Model;

import java.io.Serializable;
import java.util.UUID;

public abstract class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private String ID; // NOTE: Discuss if needs to be int and can be manual
    private String fullName;
    private Department department;
    private int age;
    private String position;

    public Employee(
            String fullName,
            Department department,
            int age,
            String position) {
        this.ID = UUID.randomUUID().toString();
        this.fullName = fullName;
        this.department = department;
        this.age = age;
        this.position = position;
    }

    public abstract double getMonthlySalary();

    public abstract String getEmployeeType();

    public String getID() {
        return this.ID;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return """
                ID: %s
                Name: %s
                Department: %s
                Position: %s
                Age: %d
                Monthly Salary: %.2f""".formatted(
                this.ID, this.fullName, this.department, this.position, this.age, this.getMonthlySalary());
    }
}
