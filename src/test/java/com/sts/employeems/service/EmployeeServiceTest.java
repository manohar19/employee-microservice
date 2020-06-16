package com.sts.employeems.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;

import com.sts.employeems.entity.Employee;
import com.sts.employeems.exception.EmployeeNotFoundException;
import com.sts.employeems.exception.FailedToDeleteEmployee;
import com.sts.employeems.exception.FailedToSaveEmployeeException;
import com.sts.employeems.repository.EmployeeRepo;

@RunWith(SpringRunner.class)
public class EmployeeServiceTest {

	@Spy
	@InjectMocks
	private EmployeeService employeeService;

	@Mock
	private EmployeeRepo employeeRepo;

	private static Employee employee = null;

	@BeforeClass
	public static void init() {
		employee = new Employee();
		employee.setEmpId(123);
		employee.setName("John");

		employee.toString();
	}

	@Test
	public void getAllTest() throws EmployeeNotFoundException {

		when(employeeRepo.findAllEmployee()).thenReturn(Optional.of(Arrays.asList(employee)));
		List<Employee> all = employeeService.getAll();

		assertEquals(14, all.get(0).getEmpId());
	}

	@Test(expected = EmployeeNotFoundException.class)
	public void getAll_If_Test() throws EmployeeNotFoundException {

		when(employeeRepo.findAllEmployee()).thenReturn(null);
		employeeService.getAll();

	}

	@Test
	public void getAll_Catch_Test() throws EmployeeNotFoundException {

		when(employeeRepo.findAllEmployee()).thenThrow(RuntimeException.class);
		employeeService.getAll();

	}

	@Test
	public void findByEmpIdTest() throws EmployeeNotFoundException {

		when(employeeRepo.findByEmpId(123)).thenReturn(Optional.of(employee));
		Employee employee2 = employeeService.findByEmpId(123);
		assertEquals("John", employee2.getName());
	}

	@Test(expected = EmployeeNotFoundException.class)
	public void findByEmpId_If_Test() throws EmployeeNotFoundException {

		when(employeeRepo.findByEmpId(123)).thenReturn(null);
		employeeService.findByEmpId(123);

	}

	@Test
	public void findByEmpId_Catch_Test() throws EmployeeNotFoundException {

		when(employeeRepo.findByEmpId(123)).thenThrow(RuntimeException.class);
		employeeService.findByEmpId(123);
	}

	@Test
	public void deleteEmployeeTest() throws FailedToDeleteEmployee {

		when(employeeRepo.deleteByEmpId(123)).thenReturn(1);
		employeeService.deleteEmployee(123);
	}

	@Test
	public void deleteEmployee_Else_Test() throws FailedToDeleteEmployee {

		when(employeeRepo.deleteByEmpId(123)).thenReturn(0);
		employeeService.deleteEmployee(123);
	}

	@Test(expected = FailedToDeleteEmployee.class)
	public void deleteEmployee_Catch_Test() throws FailedToDeleteEmployee {

		when(employeeRepo.deleteByEmpId(123)).thenThrow(RuntimeException.class);
		employeeService.deleteEmployee(123);
	}

	@Test
	public void saveEmployee_PAT_Test() throws FailedToSaveEmployeeException {

		employee.setExperience(1.2f);
		when(employeeRepo.getMaxEmpId()).thenReturn(Optional.of(12));
		when(employeeRepo.save(employee)).thenReturn(employee);
		employeeService.saveEmployee(employee);
	}

	@Test
	public void saveEmployee_PA_Test() throws FailedToSaveEmployeeException {

		employee.setExperience(3.0f);
		when(employeeRepo.getMaxEmpId()).thenReturn(null);
		when(employeeRepo.save(employee)).thenReturn(employee);
		employeeService.saveEmployee(employee);
	}

	@Test
	public void saveEmployee_A_Test() throws FailedToSaveEmployeeException {

		employee.setExperience(5.5f);
		when(employeeRepo.getMaxEmpId()).thenReturn(null);
		when(employeeRepo.save(employee)).thenReturn(employee);
		employeeService.saveEmployee(employee);
	}

	@Test
	public void saveEmployee_SA_Test() throws FailedToSaveEmployeeException {

		employee.setExperience(7.0f);
		when(employeeRepo.getMaxEmpId()).thenReturn(null);
		when(employeeRepo.save(employee)).thenReturn(employee);
		employeeService.saveEmployee(employee);
	}

	@Test
	public void saveEmployee_M_Test() throws FailedToSaveEmployeeException {

		employee.setExperience(11.0f);
		when(employeeRepo.getMaxEmpId()).thenReturn(null);
		when(employeeRepo.save(employee)).thenReturn(employee);
		employeeService.saveEmployee(employee);
	}

	@Ignore
	@Test(expected = FailedToSaveEmployeeException.class)
	public void saveEmployee_Catch_Test() throws FailedToSaveEmployeeException {

		when(employeeRepo.getMaxEmpId()).thenReturn(Optional.of(null));
		employeeService.saveEmployee(employee);
	}

	@Test
	public void updateNameTest() {

		employee.setName("Ben");
		when(employeeRepo.findByEmpId(123)).thenReturn(Optional.of(employee));
		employeeService.update(employee);
	}

	@Test
	public void updateNameElseTest() {
		Employee emp = employee;
		emp.setName(null);
		when(employeeRepo.findByEmpId(emp.getEmpId())).thenReturn(Optional.of(emp));
		employeeService.update(emp);
	}

	@Test
	public void updateEmailTest() {
		employee.setEmail("Ben@gmail.com");
		when(employeeRepo.findByEmpId(123)).thenReturn(Optional.of(employee));
		employeeService.update(employee);
	}

	@Test
	public void updateEmailElseTest() {
		Employee emp = employee;
		emp.setEmail("");
		when(employeeRepo.findByEmpId(123)).thenReturn(Optional.of(emp));
		employeeService.update(emp);
	}

	@Test
	public void updateDOBTest() {
		employee.setDateOfBirth(new Date());
		when(employeeRepo.findByEmpId(123)).thenReturn(Optional.of(employee));
		employeeService.update(employee);
	}

	@Test
	public void updateDOBElseTest() {
		Employee emp = employee;
		emp.setDateOfBirth(null);
		when(employeeRepo.findByEmpId(123)).thenReturn(Optional.of(emp));
		employeeService.update(emp);
	}

	@org.junit.Ignore
	@Test(expected = EmployeeNotFoundException.class)
	public void updateENFTest() {
		when(employeeRepo.findByEmpId(123)).thenThrow(RuntimeException.class);
		employeeService.update(employee);
	}

}
