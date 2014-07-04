package ru.sergg.samples.jpa;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmployerService {
	
	private Logger logger = LoggerFactory.getLogger(EmployerService.class);
	
	@Autowired
	private EmployerRepository projectJpaRepository;
	
	private AtomicInteger i = new AtomicInteger(0);
	
//	@Transactional(rollbackFor={IllegalAccessError.class})
//	@Transactional
	public Employer findOrCreateRestrictonBased(String name) {
		int num = i.incrementAndGet();
		logger.info("findOrCreateRestrictonBased({}), num={}", name, num);
		Employer employer = null;
		synchronized(projectJpaRepository) {
			employer = projectJpaRepository.findOne(name);
			logger.info("Employer with name '{}' found: {}", name, employer);
			if(employer == null) { 
				employer = new Employer();
				employer.setName(name);
				employer.setAddress(""+num);
				employer = projectJpaRepository.saveAndFlush(employer);
				logger.info("Employer with name '{}' created: {}", name, employer);
			}
		}
		
		employer = projectJpaRepository.findOne(name);
		logger.info("Employer with name '{}' extracted: {}", name, employer);
		return employer;
	}
}
