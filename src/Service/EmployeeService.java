package Service;

import Model.Department;
import Model.Employee;
import Model.FullTimeEmployee;
import Model.PartTimeEmployee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmployeeService {
    private final List<Employee> employees;
    public EmployeeService() {
        this.employees = FileHandler.loadEmployees();
    }
    
    public List<Employee> getAllEmployees() {
        return Collections.unmodifiableList(this.employees);
    }

    public Employee searchById(String id) {
        for (Employee emp : this.employees) {
            if (emp.getID() == id) {
                return emp;
            }
        }
        return null;
    }

    public List<Employee> searchByName(String nameQuery) {
        List<Employee> result = new ArrayList<>();
        if (nameQuery == null || nameQuery.trim().isEmpty()) {
            return result;
        }
        String normalizedQuery = nameQuery.trim().toLowerCase();
        for (Employee emp : this.employees) {
            if (emp.getFullName() != null && emp.getFullName().toLowerCase().contains(normalizedQuery)) {
                result.add(emp);
            }
        }
        return result;
    }

    public List<Employee> filterByDepartment(String departmentName) {
        List<Employee> result = new ArrayList<>();
        if (departmentName == null || departmentName.trim().isEmpty()) {
            return result;
        }
        String normalizedDept = departmentName.trim().toLowerCase();
        for (Employee emp : this.employees) {
            if (emp.getDepartment() != null && emp.getDepartment().getName() != null) {
                if (emp.getDepartment().getName().toLowerCase().contains(normalizedDept)) {
                    result.add(emp);
                }
            }
        }
        return result;
    }

    public void addEmployee(Employee employee) {
        validateCommonFields(employee);
        if (searchById(employee.getID()) != null) {
            throw new IllegalArgumentException("Employee with ID '" + employee.getID() + "' already exists.");
        }
        employee.validateTypeSpecificFields();// check depending on the type of employee
        this.employees.add(employee);
        save();
    }

    public FullTimeEmployee addFullTimeEmployee(String fullName, Department dept, int age, String position, double basicSalary, double bonus) {
        FullTimeEmployee emp = new FullTimeEmployee(fullName, dept, age, position, basicSalary, bonus);
        addEmployee(emp);
        return emp;
    }

    public PartTimeEmployee addPartTimeEmployee(String fullName, Department dept, int age, String position, double hourlyRate, int hoursPerMonth) {
        PartTimeEmployee emp = new PartTimeEmployee(fullName, dept, age, position, hourlyRate, hoursPerMonth);
        addEmployee(emp);
        return emp;
    }
    
    public void updateEmployee(String id, String fullName, Department dept, int age, String position, double basicSalary, double bonus) {
        Employee existing = searchById(id);
        if (existing == null) {
            throw new IllegalArgumentException("Employee with ID '" + id + "' not found.");
        }
        if (!(existing instanceof FullTimeEmployee fullTimeEmp)) {
            throw new IllegalArgumentException("Employee with ID '" + id + "' is not a Full-Time employee.");
        }
        validateCommonParameters(fullName, dept, age, position);
        if (basicSalary < 0) {
            throw new IllegalArgumentException("Basic monthly salary cannot be negative.");
        }
        if (bonus < 0) {
            throw new IllegalArgumentException("Monthly bonus cannot be negative.");
        }
        fullTimeEmp.setFullName(fullName.trim());
        fullTimeEmp.setDepartment(dept);
        fullTimeEmp.setAge(age);
        fullTimeEmp.setPosition(position.trim());
        fullTimeEmp.setBasicSalary(basicSalary);
        fullTimeEmp.setBonus(bonus);
        save();
    }

    public void updateEmployee( String id, String fullName, Department dept, int age, String position, double hourlyRate, int hoursPerMonth) {
        Employee existing = searchById(id);
        if (existing == null) {
            throw new IllegalArgumentException("Employee with ID '" + id + "' not found.");
        }
        if (!(existing instanceof PartTimeEmployee partTimeEmp)) {
            throw new IllegalArgumentException("Employee with ID '" + id + "' is not a Part-Time employee.");
        }
        validateCommonParameters(fullName, dept, age, position);
        if (hourlyRate < 0) {
            throw new IllegalArgumentException("Hourly rate cannot be negative.");
        }
        if (hoursPerMonth < 0) {
            throw new IllegalArgumentException("Working hours per month cannot be negative.");
        }
        partTimeEmp.setFullName(fullName.trim());
        partTimeEmp.setDepartment(dept);
        partTimeEmp.setAge(age);
        partTimeEmp.setPosition(position.trim());
        partTimeEmp.setHourlyRate(hourlyRate);
        partTimeEmp.setHoursPerMonth(hoursPerMonth);
        save();
    }
    
    public void deleteEmployee(String id) {
        int index = findIndexById(id);
        if (index == -1) {
            throw new IllegalArgumentException("Employee with ID '" + id + "' not found.");
        }
        this.employees.remove(index);
        save();
    }

    private void validateCommonFields(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null.");
        }
        if (employee.getID() == null || employee.getID().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee ID cannot be empty.");
        }
        if (employee.getFullName() == null || employee.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee full name is required.");
        }
        if (employee.getDepartment() == null) {
            throw new IllegalArgumentException("Please select a valid department.");
        }
        if (employee.getAge() <= 0) {
            throw new IllegalArgumentException("Employee age must be a positive integer.");
        }
        if (employee.getPosition() == null || employee.getPosition().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee position is required.");
        }
    }

    private void validateCommonParameters(String fullName, Department dept, int age, String position) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee full name is required.");
        }
        if (dept == null) {
            throw new IllegalArgumentException("Please select a valid department.");
        }
        if (age <= 0) {
            throw new IllegalArgumentException("Employee age must be a positive integer.");
        }
        if (position == null || position.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee position is required.");
        }
    }

    private int findIndexById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return -1;
        }
        String trimmedId = id.trim();
        for (int i = 0; i < this.employees.size(); i++) {
            Employee emp = this.employees.get(i);
            if (emp.getID() != null && emp.getID().equals(trimmedId)) {
                return i;
            }
        }
        return -1;
    }
    public void save() {
        try {
            FileHandler.saveEmployees(this.employees);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save employees to file: " + e.getMessage(), e);
        }
    }
}
