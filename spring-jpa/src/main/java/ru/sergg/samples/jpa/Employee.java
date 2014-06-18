package ru.sergg.samples.jpa;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class Employee extends NamedEntity {
	
    @ManyToOne(fetch=FetchType.EAGER)
	private Employer employer;

	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer project) {
		this.employer = project;
	}

	@Override
	public String toString() {
		return "Employee [" + super.toString() + 
				", employer=" + employer.getName() +
				"]";
	}
}
