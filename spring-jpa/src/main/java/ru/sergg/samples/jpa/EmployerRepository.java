package ru.sergg.samples.jpa;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.transaction.annotation.Transactional;

public interface EmployerRepository extends JpaRepository<Employer, String> {

}
