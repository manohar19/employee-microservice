package com.sts.employeems.resource;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;

import com.sts.employeems.binding.Employees;
import com.sts.employeems.entity.Employee;
import com.sts.employeems.exception.EmployeeNotFoundException;
import com.sts.employeems.exception.FailedToSaveEmployeeException;
import com.sts.employeems.service.EmployeeService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class EmployeeResourceTest {

	@LocalServerPort
	private int port;

	@Spy
	@InjectMocks
	private EmployeeResource employeeResource;

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private EmployeeService employeeService;

	@Test
	public void healthCheckTest() throws RestClientException, MalformedURLException {
		String object = restTemplate.getForObject(new URL("http://localhost:" + port + "/employee/health").toString(),
				String.class);

		assertEquals("Application is Up and running", object);
	}

	@Test
	public void getAllEmployeeTest() throws RestClientException, MalformedURLException, EmployeeNotFoundException {

		Employee employee = new Employee();
		employee.setEmpId(123);

		when(employeeService.getAll()).thenReturn(Arrays.asList(employee));
		Employees employees = restTemplate.getForObject(new URL("http://localhost:" + port + "/employee").toString(),
				Employees.class);
		employees.toString();
		assertEquals(123, employees.getEmployees().get(0).getEmpId());
	}

	@Test
	public void getAllEmployee_ENF_Test() throws RestClientException, MalformedURLException, EmployeeNotFoundException {

		when(employeeService.getAll()).thenThrow(EmployeeNotFoundException.class);
		restTemplate.getForObject(new URL("http://localhost:" + port + "/employee").toString(), Employees.class);

	}

	@Test
	public void getAllEmployee_Catch_Test()
			throws RestClientException, MalformedURLException, EmployeeNotFoundException {

		when(employeeService.getAll()).thenThrow(RuntimeException.class);
		restTemplate.getForObject(new URL("http://localhost:" + port + "/employee").toString(), Employees.class);

	}

	@Test
	public void getByEmpIdTest() throws RestClientException, MalformedURLException, EmployeeNotFoundException {

		Employee employee = new Employee();
		employee.setEmpId(123);
		employee.setName("Merry");

		when(employeeService.findByEmpId(123)).thenReturn(employee);
		Employee employee2 = restTemplate.getForObject(new URL("http://localhost:" + port + "/employee/123").toString(),
				Employee.class);
		assertNotNull(employee2);
	}

	@Test
	public void getByEmpId_ENE_Test() throws RestClientException, MalformedURLException, EmployeeNotFoundException {

		when(employeeService.findByEmpId(123)).thenThrow(EmployeeNotFoundException.class);
		restTemplate.getForObject(new URL("http://localhost:" + port + "/employee/123").toString(), Employee.class);
	}

	@Test
	public void getByEmpId_Catch_Test() throws RestClientException, MalformedURLException, EmployeeNotFoundException {

		when(employeeService.findByEmpId(123)).thenThrow(RuntimeException.class);
		restTemplate.getForObject(new URL("http://localhost:" + port + "/employee/123").toString(), Employee.class);
	}

	@Test
	public void saveTest() throws RestClientException, MalformedURLException, FailedToSaveEmployeeException {

		Employee employee = new Employee();
		employee.setEmpId(123);
		employee.setName("Merry");

		when(employeeService.saveEmployee(employee)).thenReturn(employee);

		restTemplate.postForObject(new URL("http://localhost:" + port + "/employee").toString(), employee,
				Employee.class);

	}

	@Test
	public void save_Catch_Test() throws RestClientException, MalformedURLException, FailedToSaveEmployeeException {

		when(employeeService.saveEmployee(Mockito.any())).thenThrow(RuntimeException.class);

		restTemplate.postForObject(new URL("http://localhost:" + port + "/employee").toString(), new Employee(),
				Employee.class);

	}

}
