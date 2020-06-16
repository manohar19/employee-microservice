package com.sts.employeems.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "EMPLOYEE")
@ApiModel(description = "Contains all Employee data")
@XmlRootElement(name = "Employee")
@XmlAccessorType(XmlAccessType.FIELD)
public class Employee implements Serializable {

	private static final long serialVersionUID = -3107896371768277450L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "It is auto increment primary key value")
	private int id;

	@Column(name = "EMP_ID")
	@ApiModelProperty(value = "EmpId is auto system generated value")
	private int empId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "SALARY")
	private float salary;

	@Column(name = "MOBILE_NO")
	private long mobileNo;

	@Column(name = "EXPERIENCE")
	private float experience;

	@Column(name = "SKILLS")
	private String skills;

	@Column(name = "CONTACT_PREFERENCE")
	private String contactPreference;

	@Column(name = "DEPARTMENT", nullable = false)
	private String department;

	@Column(name = "DOB")
	private Date dateOfBirth;

	@Column(name = "IS_ACTIVE")
	@ApiModelProperty(value = "Default is avtive")
	private String isActive; // true or false

	@Column(name = "PHOTO_PATH")
	private String photoPath;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "DESIGNATION")
	@ApiModelProperty(value = "It is auto generated field by application based on experience")
	private String designation;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	public long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public float getExperience() {
		return experience;
	}

	public void setExperience(float experience) {
		this.experience = experience;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getContactPreference() {
		return contactPreference;
	}

	public void setContactPreference(String contactPreference) {
		this.contactPreference = contactPreference;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((contactPreference == null) ? 0 : contactPreference.hashCode());
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((department == null) ? 0 : department.hashCode());
		result = prime * result + ((designation == null) ? 0 : designation.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + empId;
		result = prime * result + Float.floatToIntBits(experience);
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((isActive == null) ? 0 : isActive.hashCode());
		result = prime * result + (int) (mobileNo ^ (mobileNo >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((photoPath == null) ? 0 : photoPath.hashCode());
		result = prime * result + Float.floatToIntBits(salary);
		result = prime * result + ((skills == null) ? 0 : skills.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (contactPreference == null) {
			if (other.contactPreference != null)
				return false;
		} else if (!contactPreference.equals(other.contactPreference))
			return false;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (department == null) {
			if (other.department != null)
				return false;
		} else if (!department.equals(other.department))
			return false;
		if (designation == null) {
			if (other.designation != null)
				return false;
		} else if (!designation.equals(other.designation))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (empId != other.empId)
			return false;
		if (Float.floatToIntBits(experience) != Float.floatToIntBits(other.experience))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (isActive == null) {
			if (other.isActive != null)
				return false;
		} else if (!isActive.equals(other.isActive))
			return false;
		if (mobileNo != other.mobileNo)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (photoPath == null) {
			if (other.photoPath != null)
				return false;
		} else if (!photoPath.equals(other.photoPath))
			return false;
		if (Float.floatToIntBits(salary) != Float.floatToIntBits(other.salary))
			return false;
		if (skills == null) {
			if (other.skills != null)
				return false;
		} else if (!skills.equals(other.skills))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", empId=" + empId + ", name=" + name + ", email=" + email + ", gender=" + gender
				+ ", salary=" + salary + ", mobileNo=" + mobileNo + ", experience=" + experience + ", skills=" + skills
				+ ", contactPreference=" + contactPreference + ", department=" + department + ", dateOfBirth="
				+ dateOfBirth + ", isActive=" + isActive + ", photoPath=" + photoPath + ", address=" + address
				+ ", designation=" + designation + "]";
	}

}
