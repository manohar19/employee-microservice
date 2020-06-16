package com.sts.employeems.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.sts.employeems.entity.Employee;
import com.sts.employeems.exception.EmployeeNotFoundException;
import com.sts.employeems.exception.FailedToDeleteEmployee;
import com.sts.employeems.exception.FailedToSaveEmployeeException;
import com.sts.employeems.repository.EmployeeRepo;

@Service
public class EmployeeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

	private static final String EMPLOYEE_NOT_FOUND = "No employees found in database";

	@Autowired
	private EmployeeRepo employeeRepo;

	public List<Employee> getAll() throws EmployeeNotFoundException {

		List<Employee> employeesList = null;
		Optional<List<Employee>> optionalEmployeesList = null;

		try {

			LOGGER.info("Start of getAll()");

			optionalEmployeesList = employeeRepo.findAllEmployee();

			if (ObjectUtils.isEmpty(optionalEmployeesList)) {// employeesList==null && employeesList.size==null
				throw new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND);
			} else {
				if (optionalEmployeesList.isPresent()) {
					employeesList = optionalEmployeesList.get();
				}

			}

			LOGGER.info("End of getAll()");

		} catch (EmployeeNotFoundException e) {
			LOGGER.error(EMPLOYEE_NOT_FOUND, e);
			throw new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND);
		}

		catch (Exception e) {
			LOGGER.error("Error while getting the Employee list from the database", e);
		}

		return employeesList;
	}

	public Employee findByEmpId(int empId) throws EmployeeNotFoundException {

		Employee employee = null;
		Optional<Employee> optionalEmployee = null;

		try {
			LOGGER.info("Start of findByEmpId(...) with empId={}", empId);

			optionalEmployee = employeeRepo.findByEmpId(empId);

			if (ObjectUtils.isEmpty(optionalEmployee)) {// employee == null && Optional.isEmpty()
				LOGGER.info("Employee with empId={} is not found in database", empId);
				throw new EmployeeNotFoundException("Employee Not Found");
			} else {
				if (optionalEmployee.isPresent()) {
					employee = optionalEmployee.get();
				}
			}

			LOGGER.info("Start of findByEmpId(...) with empId={}", empId);

		} catch (EmployeeNotFoundException e) {

			LOGGER.error("Error while finding the Employee information from database with empId={} and error={}", empId,
					e);
			throw new EmployeeNotFoundException("Employee Not Found");
		} catch (Exception e) {

			LOGGER.error("Error while finding the Employee information from database with empId={} and error={}", empId,
					e);
		}

		return employee;

	}

	public int deleteEmployee(int empId) throws FailedToDeleteEmployee {

		int count = 0;

		try {

			LOGGER.info("Start of delete(...) with empId={}", empId);

			count = employeeRepo.deleteByEmpId(empId);

			if (count > 0) {
				LOGGER.info("Employee record is deleted from database with empId={}", empId);
			} else {

				LOGGER.info("Unable to delete the Employee record from database with empId={}", empId);
			}

			LOGGER.info("End of delete(...) with empId={}", empId);

		} catch (Exception e) {
			LOGGER.error("Error while deleting the Employee information from database with empId={}", empId, e);
			throw new FailedToDeleteEmployee("Failed to delete the employee");
		}

		return count;

	}

	public Employee saveEmployee(Employee employee) throws FailedToSaveEmployeeException {

		int empId = 0;
		float experience = 0.0f;

		Employee emp = null;
		Integer maxEmpId = null;
		Optional<Integer> optionalMaxEmpId = null;

		try {

			LOGGER.info("Start of save(...) ");
			optionalMaxEmpId = employeeRepo.getMaxEmpId();

			experience = employee.getExperience();

			if (ObjectUtils.isEmpty(optionalMaxEmpId)) {// maxEmpId == null || Optional.empty()
				empId = 1421;
				LOGGER.info("This is First Employee Object. So We are stating with empId as = {}", empId);

			} else {

				maxEmpId = optionalMaxEmpId.get();

				empId = maxEmpId + 2;
				LOGGER.info("This is not First Employee Object. So We are incrementing with 2 :{} ", empId);
			}

			if (!StringUtils.isEmpty(experience)) { // null && ""

				if (experience <= 2.0f) {
					employee.setDesignation("Programmer Analyst Trainee");
				}

				if (experience > 2.0f && experience < 4.0f) {
					employee.setDesignation("Programmer Analyst");
				}

				if (experience > 4.0f && experience < 6.0f) {
					employee.setDesignation("Assosiate");
				}

				if (experience > 6.0f && experience < 10.0f) {
					employee.setDesignation("Senior Assosiate");
				}

				if (experience > 10.0f) {
					employee.setDesignation("Manager");
				}
			}

			if (StringUtils.isEmpty(employee.getIsActive())) {
				employee.setIsActive("true");
			}

			employee.setEmpId(empId);
			emp = employeeRepo.save(employee);

			LOGGER.info("End of save(...) with with empId= {}", emp.getEmpId());

		} catch (Exception e) {
			LOGGER.error("Error while saveing the Employee information into the database", e);
			throw new FailedToSaveEmployeeException("Unable to Save the Employee into Database");
		}

		return emp;
	}

	public Employee update(Employee employee) {

		int empId = 0;

		Employee updatedEmployee = null;
		Employee existingEmployee = null;
		Optional<Employee> optionalExistingEmployee = null;

		try {

			empId = employee.getEmpId();
			LOGGER.info("End of delete(...) with empId={}", empId);

			optionalExistingEmployee = employeeRepo.findByEmpId(empId);

			existingEmployee = optionalExistingEmployee
					.orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

			if (employee.getName() != null && !employee.getName().equals("")) {
				existingEmployee.setName(employee.getName());
			} else {
				LOGGER.info("Got Empty name field from service with empId={}", empId);
			}

			if (employee.getEmail() != null && !employee.getEmail().equals("")) {
				existingEmployee.setEmail(employee.getEmail());
			} else {
				LOGGER.info("Got Empty Email field from service with empId={}", empId);
			}

			if (employee.getDateOfBirth() != null) {
				existingEmployee.setDateOfBirth(employee.getDateOfBirth());
			} else {
				LOGGER.info("Got Empty DateOfBirth() field from service with empId={}", empId);
			}

			updatedEmployee = employeeRepo.save(existingEmployee);

			LOGGER.info("End of delete(...) with empId={}", empId);

		} catch (Exception e) {
			LOGGER.error("Error while updating the Employee information with empId={}", empId, e);
		}

		return updatedEmployee;
	}

}
