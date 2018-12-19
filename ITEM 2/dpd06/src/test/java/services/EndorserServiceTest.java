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

import utilities.AbstractTest;

import domain.Endorser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/dataSource.xml",
		"classpath:spring/config/packages.xml"
	})


@Transactional
public class EndorserServiceTest extends AbstractTest {
	@Autowired
	private EndorserService endorserService;
	
	@Test
	public void testSaveEndorsers(){
		Endorser saved = this.endorserService.findOne(super.getEntityId("handyWorker1"));
		Collection<Endorser> endorsers = new ArrayList<>();
		
		this.endorserService.save(saved);
		endorsers.add(saved);
		
		Assert.isTrue(endorsers.contains(saved));
		
	}
	@Test
	public void deleteTest(){
		Endorser saved = this.endorserService.findOne(super.getEntityId("handyWorker1"));
		
		this.endorserService.delete(saved);
		Assert.isNull(this.endorserService.findOne(saved.getId()));
	}

	@Test
	public void testFindAll() {
		Collection<Endorser> endorsers;
		endorsers = this.endorserService.findAll();
		Assert.isTrue(!endorsers.isEmpty());
	}
	
	@Test
	public void testFindOne(){
		Endorser e;
		e = this.endorserService.findOne(super.getEntityId("handyWorker2"));
		int findId = e.getId();
		Assert.notNull(this.endorserService.findOne(findId));
		
	}

	@Test
	public void testCreate(){
		Endorser e = this.endorserService.create();
		Assert.notNull(e);
	}

}

