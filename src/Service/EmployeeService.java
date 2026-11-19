package Service;

import Model.Employee;
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
}
