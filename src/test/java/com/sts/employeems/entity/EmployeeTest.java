package com.sts.employeems.entity;

import org.junit.Test;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

import nl.jqno.equalsverifier.EqualsVerifier;

public class EmployeeTest {

	@Test
	public void employee_Test() {
		Validator validator = ValidatorBuilder.create().with(new SetterTester()).with(new GetterTester()).build();

		PojoClass pojoClass = PojoClassFactory.getPojoClass(Employee.class);
		validator.validate(pojoClass);

	
		EqualsVerifier.forClass(Employee.class).usingGetClass().verify();
	}
}
