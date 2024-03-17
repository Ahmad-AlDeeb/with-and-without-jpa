package com.luv2code.springboot.cruddemo.service;

import com.luv2code.springboot.cruddemo.dao.EmployeeRepo;
import com.luv2code.springboot.cruddemo.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Service layer between Controller & DAO (Façade Design Pattern) which delegates all functionalities to DAO
public interface EmployeeService {

    List<Employee> findAll();

    Employee findById(int theId);

    void save(Employee theEmployee);

    void deleteById(int theId);

}

@Service
class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }

    @Override
    public Employee findById(int theId) {
        return employeeRepo.findById(theId);
    }

    @Transactional
    @Override
    public void save(Employee theEmployee) {
        employeeRepo.save(theEmployee);
    }

    @Transactional
    @Override
    public void deleteById(int theId) {
        employeeRepo.deleteById(theId);
    }
}