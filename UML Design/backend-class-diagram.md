# Preliminary UML Class Diagram — Backend (`Model` and `Service`)

This file separates the Employee Management System (EMS) into two layers:

1. **Backend (detailed now):** `Model` and `Service`, plus the `AppContext` boundary from the frontend.
2. **Frontend (placeholder):** high-level `UI` packages to expand in the next step.

## 1. Backend Class Diagram

```mermaid
classDiagram
    direction TB

    class Serializable {
        <<interface>>
    }

    namespace Frontend_Boundary {
        class AppContext {
            -DepartmentService departmentService
            -EmployeeService employeeService
            -Consumer~Route~ navigator
            +AppContext(DepartmentService departmentService, EmployeeService employeeService, Consumer~Route~ navigator)
            +departments() DepartmentService
            +employees() EmployeeService
            +navigate(Route route) void
        }
    }

    namespace Model {
        class Department {
            -String ID
            -String name
            +Department(String name)
            +getID() String
            +getName() String
            +setName(String name) void
            +toString() String
        }

        class Employee {
            <<abstract>>
            -String ID
            -String fullName
            -Department department
            -int age
            -String position
            +Employee(String fullName, Department department, int age, String position)
            +getMonthlySalary()* double
            +getEmployeeType()* String
            +getID() String
            +getFullName() String
            +setFullName(String fullName) void
            +getDepartment() Department
            +setDepartment(Department department) void
            +getAge() int
            +setAge(int age) void
            +getPosition() String
            +setPosition(String position) void
            +toString() String
            +validateTypeSpecificFields() void
        }

        class FullTimeEmployee {
            -double basicSalary
            -double bonus
            +FullTimeEmployee(String fullName, Department department, int age, String position, double basicSalary, double bonus)
            +getMonthlySalary() double
            +getEmployeeType() String
            +getBasicSalary() double
            +setBasicSalary(double basicSalary) void
            +getBonus() double
            +setBonus(double bonus) void
        }

        class PartTimeEmployee {
            -double hourlyRate
            -int hoursPerMonth
            +PartTimeEmployee(String fullName, Department department, int age, String position, double hourlyRate, int hoursPerMonth)
            +getMonthlySalary() double
            +getEmployeeType() String
            +getHourlyRate() double
            +setHourlyRate(double hourlyRate) void
            +getHoursPerMonth() int
            +setHoursPerMonth(int hoursPerMonth) void
        }
    }

    namespace Service {
        class DepartmentService {
            -List~Department~ departments
            +DepartmentService()
            +getAllDepartments() List~Department~
            +getDepartmentById(String id) Department
            +getDepartmentByName(String name) Department
            +addDepartment(String name) Department
            +updateDepartment(String id, String newName, EmployeeService employeeService) void
            +deleteDepartment(String id, EmployeeService employeeService) void
            +save() void
        }

        class EmployeeService {
            -List~Employee~ employees
            +EmployeeService()
            +getAllEmployees() List~Employee~
            +searchById(String id) Employee
            +searchByName(String nameQuery) List~Employee~
            +filterByDepartment(String departmentName) List~Employee~
            +addEmployee(Employee employee) void
            +addFullTimeEmployee(String fullName, Department dept, int age, String position, double basicSalary, double bonus) FullTimeEmployee
            +addPartTimeEmployee(String fullName, Department dept, int age, String position, double hourlyRate, int hoursPerMonth) PartTimeEmployee
            +updateEmployee(String id, String fullName, Department dept, int age, String position, double basicSalary, double bonus) void
            +updateEmployee(String id, String fullName, Department dept, int age, String position, double hourlyRate, int hoursPerMonth) void
            +deleteEmployee(String id) void
            -validateCommonFields(Employee employee) void
            -validateCommonParameters(String fullName, Department dept, int age, String position) void
            -findIndexById(String id) int
            +save() void
            +getEmployeeTypes() String[]
        }

        class FileHandler {
            -String DATA_DIRECTORY$
            -String EMPLOYEES_FILE$
            -String DEPARTMENTS_FILE$
            +saveData(String filePath, List~T~ data)$ void
            +loadData(String filePath)$ List~T~
            +saveEmployees(List~Employee~ employees)$ void
            +loadEmployees()$ List~Employee~
            +saveDepartments(List~Department~ departments)$ void
            +loadDepartments()$ List~Department~
        }
    }

    %% Inheritance and Realization
    Serializable <|.. Department
    Serializable <|.. Employee
    Employee <|-- FullTimeEmployee
    Employee <|-- PartTimeEmployee

    %% Model Associations
    Employee "0..*" --> "1" Department : assigned to

    %% Service Aggregations
    DepartmentService "1" o-- "0..*" Department : manages
    EmployeeService "1" o-- "0..*" Employee : manages

    %% Service Dependencies
    DepartmentService ..> EmployeeService : checks assigned employees
    DepartmentService ..> FileHandler : persists via
    EmployeeService ..> FileHandler : persists via
    EmployeeService ..> FullTimeEmployee : creates and updates
    EmployeeService ..> PartTimeEmployee : creates and updates
    EmployeeService ..> Department : filters and assigns
    FileHandler ..> Employee : serializes
    FileHandler ..> Department : serializes

    %% Frontend to Backend Boundary
    AppContext "1" --> "1" DepartmentService : exposes
    AppContext "1" --> "1" EmployeeService : exposes
```

## 2. Frontend vs Backend Split (Preliminary Placeholder)

```mermaid
classDiagram
    direction LR

    namespace Frontend {
        class Main
        class MainFrame
        class AppContext
        class AppPage {
            <<abstract>>
        }
        class LoginPage
        class HomePage
        class EmployeesPage
        class DepartmentsPage
    }

    namespace Backend {
        class DepartmentService
        class EmployeeService
        class FileHandler
        class Employee {
            <<abstract>>
        }
        class Department
    }

    Main ..> MainFrame : launches
    MainFrame *-- AppContext : creates
    MainFrame *-- AppPage : hosts
    AppPage <|-- LoginPage
    AppPage <|-- HomePage
    AppPage <|-- EmployeesPage
    AppPage <|-- DepartmentsPage
    AppPage --> AppContext : reads services

    AppContext --> DepartmentService
    AppContext --> EmployeeService
    DepartmentService o-- Department
    EmployeeService o-- Employee
    DepartmentService ..> FileHandler
    EmployeeService ..> FileHandler
```

## 3. Code vs Lab 7 Notes

- `Employee.ID` and `Department.ID` use UUID `String` values in the current code. Lab 7 Section 4.1 specifies an integer employee ID.
- `Employee.validateTypeSpecificFields()` is currently an empty method in `Employee` and has no overrides in `FullTimeEmployee` or `PartTimeEmployee`.
- `EmployeeService.searchById(String id)` compares strings with `==` instead of `.equals(...)`.
