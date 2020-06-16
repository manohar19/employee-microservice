package com.sts.employeems.resource;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sts.employeems.binding.Employees;
import com.sts.employeems.entity.Employee;
import com.sts.employeems.exception.EmployeeNotFoundException;
import com.sts.employeems.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/employee")
@Api(description = "It will contains all employee endpoint", value = "employee resource")
public class EmployeeResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeResource.class);

	private static final String MESSAGE = "message";

	@Autowired
	private EmployeeService employeeService;

	@GetMapping(value = "/health")
	public String healthCheck() {
		return "Application is Up and running";
	}

	@GetMapping(produces = { "application/json", "application/xml" })
	@ApiOperation(value = "Return all employee information", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrived all employees"),
			@ApiResponse(code = 404, message = "No Employees Found"),
			@ApiResponse(code = 500, message = "Server error") })
	public ResponseEntity<Employees> getAllEmployee() {

		List<Employee> employeeList = null;

		Employees employees = null;
		HttpStatus status = null;
		HttpHeaders headers = new HttpHeaders();

		try {
			LOGGER.info("Start of getAllEmployee()");

			employees = new Employees();

			employeeList = employeeService.getAll();

			employees.setEmployees(employeeList);

			headers.set(MESSAGE, "Successfully Employees Found");
			status = HttpStatus.OK;

			LOGGER.info("End of getAllEmployee()");

		} catch (EmployeeNotFoundException e) {
			LOGGER.error("Unable to find the Employess", e);

			headers.set(MESSAGE, "No Employees Found");
			status = HttpStatus.NOT_FOUND;

		} catch (Exception e) {
			LOGGER.error("Error while performing the getAllEmployee()", e);

			headers.set(MESSAGE, "Internal Server error");
			status = HttpStatus.INTERNAL_SERVER_ERROR;

		}
		return new ResponseEntity<Employees>(employees, headers, status);

	}

	@GetMapping(value = "/{empId}", produces = { "application/json", "application/xml" })
	@ApiOperation(value = "Return employee information with given empId", response = Employee.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully able to get the employee data with given empId"),
			@ApiResponse(code = 404, message = "Employee not found with given empId"),
			@ApiResponse(code = 500, message = "Server error") })
	public ResponseEntity<Employee> getByEmpId(@PathVariable("empId") int empId) {

		Employee employee = null;

		HttpStatus status = null;
		HttpHeaders headers = new HttpHeaders();

		try {

			LOGGER.info("Start of getByEmpId(...) with empId={}", empId);

			employee = employeeService.findByEmpId(empId);

			headers.add(MESSAGE, "Successfully able to get the employee data with empId=" + empId);
			status = HttpStatus.OK;

			LOGGER.info("End of getByEmpId(...) with empId={}", empId);

		} catch (EmployeeNotFoundException e) {

			LOGGER.error("Employee not foud with empId={} and error={}", empId, e);

			headers.add(MESSAGE, "Employee not found with empId=" + empId);
			status = HttpStatus.NOT_FOUND;

		} catch (Exception e) {

			LOGGER.error("Error while getting the Employee information with empId={} and error={}", empId, e);

			headers.add(MESSAGE, "Internal Server Error and empId=" + empId);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Employee>(employee, headers, status);
	}

	@PostMapping(consumes = { "application/json", "application/xml" }, produces = { "application/json",
			"application/xml" })
	@ApiOperation(value = "Save employee information at server end", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully saved the Employee"),
			@ApiResponse(code = 500, message = "Failed to save the Employee") })
	public ResponseEntity<Employee> save(@RequestBody Employee employee) {

		Employee emp = null;

		HttpStatus status = null;
		HttpHeaders headers = new HttpHeaders();

		try {

			LOGGER.info("End of save(...)");

			emp = employeeService.saveEmployee(employee);

			headers.set(MESSAGE, "Successfully able to save the Employee");
			status = HttpStatus.OK;

			LOGGER.info("End of save(...)");

		} catch (Exception e) {
			LOGGER.error("Error while performing the save(...)", e);

			headers.set(MESSAGE, "Failed to save the Employee");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Employee>(emp, headers, status);
	}

	@DeleteMapping(value = "/{empId}")
	@ApiOperation(value = "Delete the employee information with given empId", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully saved the Employee"),
			@ApiResponse(code = 404, message = "Record in not there with empI"),
			@ApiResponse(code = 500, message = "Failed to delete the Record with empId") })
	public ResponseEntity<String> delete(@PathVariable("empId") int empId) {

		HttpStatus status = null;
		HttpHeaders headers = new HttpHeaders();

		int count = 0;

		try {

			LOGGER.info("Start of delete(...) with empId={}", empId);

			headers = new HttpHeaders();
			count = employeeService.deleteEmployee(empId);

			if (count == 0) {
				headers.set(MESSAGE, "Record in not there with empId=" + empId);
				status = HttpStatus.NOT_FOUND;
			} else {
				headers.set(MESSAGE, "Successfully deleted the record with empId=" + empId);
				status = HttpStatus.OK;
			}

			LOGGER.info("End of delete(...) with empId={}", empId);

		} catch (Exception e) {

			LOGGER.error("Error while deleting the employee object with empId={} ", empId, e);
			headers.set(MESSAGE, "Failed to delete the Record with empId=" + empId);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<String>(headers, status);
	}

	@PutMapping(consumes = { "application/json", "application/xml" }, produces = { "application/json",
			"application/xml" })
	@ApiOperation(value = "Update the employee information with given data", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated the Employee data"),
			@ApiResponse(code = 404, message = "Record in not there with empI"),
			@ApiResponse(code = 500, message = "Unable to update Employee") })
	public ResponseEntity<Employee> updateEmployee(
			@ApiParam(value = "employee object") @RequestBody Employee employee) {

		int empId = 0;
		Employee updatedEmployee = null;

		HttpStatus status = null;
		HttpHeaders headers = new HttpHeaders();

		try {
			empId = employee.getEmpId();

			LOGGER.info("Start of updateEmployee(...) with empId={}", empId);

			updatedEmployee = employeeService.update(employee);

			if (ObjectUtils.isEmpty(updatedEmployee)) {

				headers.set(MESSAGE, "Unable to update Employee");
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			} else {
				headers.set(MESSAGE, "Successfully updated Employee");
				status = HttpStatus.OK;
			}

			LOGGER.info("End of updateEmployee(...) with empId={}", empId);

		} catch (Exception e) {

			LOGGER.error("Error while updating the employee object with empId={} ", empId, e);
			headers.set(MESSAGE, "Internal Server Error");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Employee>(updatedEmployee, headers, status);
	}

}
