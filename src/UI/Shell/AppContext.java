package UI.Shell;

import Service.DepartmentService;
import Service.EmployeeService;

import java.util.function.Consumer;

public class AppContext {
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;
    private final Consumer<Route> navigator;

    public AppContext(
            DepartmentService departmentService,
            EmployeeService employeeService,
            Consumer<Route> navigator) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
        this.navigator = navigator;
    }

    public DepartmentService departments() {
        return departmentService;
    }

    public EmployeeService employees() {
        return employeeService;
    }

    public void navigate(Route route) {
        navigator.accept(route);
    }
}
