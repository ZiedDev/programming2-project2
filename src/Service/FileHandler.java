package Service;

import Model.Department;
import Model.Employee;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private static final String DATA_DIRECTORY = "data";
    private static final String EMPLOYEES_FILE = DATA_DIRECTORY + File.separator + "employees.dat";
    private static final String DEPARTMENTS_FILE = DATA_DIRECTORY + File.separator + "departments.dat";

    public static <T extends Serializable> void saveData(String filePath, List<T> data) throws IOException {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(data);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> List<T> loadData(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                return (List<T>) obj;
            }
            return new ArrayList<>();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Warning: Failed to load data from " + filePath + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    //////////////////

    public static void saveEmployees(List<Employee> employees) throws IOException {
        saveData(EMPLOYEES_FILE, employees);
    }

    public static List<Employee> loadEmployees() {
        return loadData(EMPLOYEES_FILE);
    }

    public static void saveDepartments(List<Department> departments) throws IOException {
        saveData(DEPARTMENTS_FILE, departments);
    }

    public static List<Department> loadDepartments() {
        return loadData(DEPARTMENTS_FILE);
    }
}
