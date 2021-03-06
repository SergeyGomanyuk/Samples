package ru.sergg.samples.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Employer extends NamedEntity {
	
    private String address;
	
	@OneToMany(mappedBy = "employer", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Employee> staff = new ArrayList<>();

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Employee> getStaff() {
		return staff;
	}

	public void setStaff(List<Employee> staff) {
		this.staff = staff;
	}

	public void addStaff(Employee... staff) {
		for(Employee employee : staff) {
			this.staff.add(employee);
			employee.setEmployer(this);
		}
	}

	@Override
	public String toString() {
		return "Employer [" + super.toString() + 
				", address=" + address +
				", staff=" + staff +
				"]";
	}
}
