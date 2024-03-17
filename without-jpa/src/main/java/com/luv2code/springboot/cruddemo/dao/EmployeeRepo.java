package com.luv2code.springboot.cruddemo.dao;

import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.util.DatabaseUtil;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class EmployeeRepo {

    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();

        // SQL query to select all employees
        String sql = "SELECT * FROM employee";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    long id = rs.getLong("id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String email = rs.getString("email");

                    // Create Employee object and add it to the list
                    Employee employee = new Employee(firstName, lastName, email);
                    employees.add(employee);
                }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
        return employees;
    }

    public void save(Employee emp) {

        String sql = "INSERT INTO employee (first_name, last_name, email) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set parameters for the PreparedStatement
            pstmt.setString(1, emp.getFirstName());
            pstmt.setString(2, emp.getLastName());
            pstmt.setString(3, emp.getEmail());

            // Execute the SQL INSERT statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Employee findById(long id) {
        Employee employee = null;

        // SQL query to select employee by id
        String sql = "SELECT * FROM employee WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id); // Set the parameter using setLong method
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String email = rs.getString("email");

                    employee = new Employee(firstName, lastName, email);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
        return employee;
    }

    public void deleteById(int id) {
        // SQL query to delete employee by id
        String sql = "DELETE FROM employee WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection(); // Establish a database connection
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Create a prepared statement for the SQL query

            stmt.setLong(1, id); // Set the parameter (employee ID) in the prepared statement
            int rowsAffected = stmt.executeUpdate(); // Execute the delete operation

            // Check if any rows were affected (employee deleted successfully)

        } catch (SQLException e) { // Catch any SQL exceptions
            e.printStackTrace(); // Print stack trace for debugging (not recommended for production)
            // Handle SQL exception as needed in your application
        }
    }
}








