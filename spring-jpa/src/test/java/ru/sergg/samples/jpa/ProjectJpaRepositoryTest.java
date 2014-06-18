package ru.sergg.samples.jpa;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/spring-context.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProjectJpaRepositoryTest {

	@Autowired
	private EmployerRepository employerRepository;
	
	@Autowired
	private EmployerService employerService;

	private ExecutorService executionService = Executors.newCachedThreadPool();
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSimple() {
		Employee employee = new Employee();
		employee.setName("employee");
		
		Employer employer = new Employer();
		employer.setName("employer");
		employer.addStaff(employee);
		
		employerRepository.save(employer);
		
		System.out.println(""+employerRepository.findOne("employer"));
		
	}

	@Test
	public void testConcurrent() throws Exception {
		ProjectCreator pc1 = new ProjectCreator();
		ProjectCreator pc2 = new ProjectCreator();
		ProjectCreator pc3 = new ProjectCreator();
		ProjectCreator pc4 = new ProjectCreator();
		ProjectCreator pc5 = new ProjectCreator();
		ProjectCreator pc6 = new ProjectCreator();
		List<Future<Object>> futures = executionService.invokeAll(Arrays.asList(new ProjectCreator[] {pc1, pc2, pc3, pc4, pc5, pc6}));
		for(Future<Object> future : futures) {
			try {
				future.get();
			} catch(Exception e) {
				System.err.println("\nUnexpected exception:");
				e.printStackTrace();
			}
		}
	}

	private class ProjectCreator implements Callable<Object> {
		@Override
		public Object call() throws Exception {
			System.err.println("\n\n!!!!!!!!!!!!!!!! "+employerService.findOrCreateRestrictonBased("p1")+"\n\n");
			return null;
		}
	}
}
