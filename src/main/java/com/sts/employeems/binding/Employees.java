package com.sts.employeems.binding;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.sts.employeems.entity.Employee;

@XmlRootElement(name = "Employees")
@XmlAccessorType(XmlAccessType.FIELD)
public class Employees {

	@XmlElement(name = "Employee")
	private List<Employee> employees;

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return "Employees [employees=" + employees + "]";
	}

}
