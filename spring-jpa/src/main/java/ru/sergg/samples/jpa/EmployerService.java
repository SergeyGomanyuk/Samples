package ru.sergg.samples.jpa;

import javax.persistence.RollbackException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.transaction.annotation.Transactional;

public class EmployerService {
	@Autowired
	private EmployerRepository projectJpaRepository;
	
	@Transactional(rollbackFor={IllegalAccessError.class})
	public Employer findOrCreateRestrictonBased(String name) {
		Employer project = new Employer();
		project.setName(name);
		try {
			saveAndFlush(project);
			Thread.sleep(300);
		} catch(Exception e) {
			System.err.println("\nException:");
			e.printStackTrace();
		} 
		return projectJpaRepository.findOne(name);
	}

	@Transactional(rollbackFor={IllegalAccessError.class})
	public Employer saveAndFlush(Employer employer) {
		return projectJpaRepository.saveAndFlush(employer);
	}
}
