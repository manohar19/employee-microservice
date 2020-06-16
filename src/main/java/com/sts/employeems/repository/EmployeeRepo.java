package com.sts.employeems.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sts.employeems.entity.Employee;

@Repository
@Transactional
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

	@Query(value = "from Employee")
	Optional<List<Employee>> findAllEmployee();

	@Query(value = "select MAX(EMP_ID) from Employee", nativeQuery = true)
	Optional<Integer> getMaxEmpId();

	Optional<Employee> findByEmpId(int empId);

	@Modifying
	@Query(value = "delete Employee e where e.empId=:empId")
	int deleteByEmpId(@Param("empId") int empId);

	@Query(value = "select e from Employee e where e.isActive=:isActive")
	List<Employee> findByIsActive(@Param("isActive") String isActive);

}
