package Service;

import Model.Department;
import Model.Employee;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class DepartmentService {
    private final List<Department> departments;

    public DepartmentService() {
        this.departments = FileHandler.loadDepartments();

        if (this.departments.isEmpty()) {
            this.departments.add(new Department("IT"));
            this.departments.add(new Department("Human Resources"));
            this.departments.add(new Department("Finance"));
            this.departments.add(new Department("Marketing"));
            this.departments.add(new Department("Sales"));
            save();
        }
    }

    public List<Department> getAllDepartments() {
        return Collections.unmodifiableList(this.departments);
    }

    public Department getDepartmentById(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        String trimmedId = id.trim();
        for (Department dept : this.departments) {
            if (dept.getID() != null && dept.getID().equals(trimmedId)) {
                return dept;
            }
        }
        return null;
    }

    public Department getDepartmentByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        String trimmedName = name.trim();
        for (Department dept : this.departments) {
            if (dept.getName() != null && dept.getName().equalsIgnoreCase(trimmedName)) {
                return dept;
            }
        }
        return null;
    }

    public Department addDepartment(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be empty.");
        }

        String trimmedName = name.trim();

        if (getDepartmentByName(trimmedName) != null) {
            throw new IllegalArgumentException("Department '" + trimmedName + "' already exists.");
        }

        Department newDept = new Department(trimmedName);

        while (getDepartmentById(newDept.getID()) != null) {
            newDept = new Department(trimmedName);
        }

        this.departments.add(newDept);
        save();
        return newDept;
    }


    public void updateDepartment(String id, String newName, EmployeeService employeeService) {
        Department existing = getDepartmentById(id);
        if (existing == null) {
            throw new IllegalArgumentException("Department with ID '" + id + "' not found.");
        }

        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be empty.");
        }

        String trimmedName = newName.trim();

        Department byName = getDepartmentByName(trimmedName);
        if (byName != null && !byName.getID().equals(existing.getID())) {
            throw new IllegalArgumentException("Another department with name '" + trimmedName + "' already exists.");
        }

        existing.setName(trimmedName);
        save();
    }

    public void deleteDepartment(String id, EmployeeService employeeService) {
        Department existing = getDepartmentById(id);
        if (existing == null) {
            throw new IllegalArgumentException("Department with ID '" + id + "' not found.");
        }

        if (employeeService != null) {
            for (Employee emp : employeeService.getAllEmployees()) {
                if (emp.getDepartment() != null && existing.getID().equals(emp.getDepartment().getID())) {
                    throw new IllegalArgumentException(
                            "Cannot delete department '" + existing.getName() + "' because employees are assigned to it.");
                }
            }
        }

        this.departments.remove(existing);
        save();
    }

    public void save() {
        try {
            FileHandler.saveDepartments(this.departments);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save departments to file: " + e.getMessage(), e);
        }
    }
}