package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.FixUpTask;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/dataSource.xml",
		"classpath:spring/config/packages.xml"
	})


@Transactional
public class FixUpTaskServiceTest extends AbstractTest {

	@Autowired
	private FixUpTaskService fixUpTaskService;
	
	@Test
	public void testSavefixUpTasks(){
		FixUpTask saved = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask1"));
		Collection<FixUpTask> fixUpTasks = new ArrayList<>();
		
		this.fixUpTaskService.save(saved);
		fixUpTasks.add(saved);
		
		Assert.isTrue(fixUpTasks.contains(saved));
		
	}
	@Test
	public void deleteTest(){
		FixUpTask saved = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask1"));
		
		this.fixUpTaskService.delete(saved);
		Assert.isNull(this.fixUpTaskService.findOne(saved.getId()));
	}

	@Test
	public void testFindAll() {
		Collection<FixUpTask> fixUpTasks;
		fixUpTasks = this.fixUpTaskService.findAll();
		Assert.isTrue(!fixUpTasks.isEmpty());
	}
	
	@Test
	public void testFindOne(){
		FixUpTask f;
		f = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask2"));
		int findId = f.getId();
		Assert.notNull(this.fixUpTaskService.findOne(findId));
		
	}

	@Test
	public void testCreate(){
		FixUpTask f = this.fixUpTaskService.create();
		Assert.notNull(f);
	}

}
