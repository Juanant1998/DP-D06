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

import domain.Endorsement;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/dataSource.xml",
		"classpath:spring/config/packages.xml"
	})


@Transactional
public class EndorsementServiceTest extends AbstractTest {

	@Autowired
	private EndorsementService endorsementService;
	
	@Test
	public void testSaveEndorsements(){
		Endorsement saved = this.endorsementService.findOne(super.getEntityId("endorsement1"));
		Collection<Endorsement> endorsements = new ArrayList<>();

		this.endorsementService.save(saved);
		endorsements.add(saved);
		
		Assert.isTrue(endorsements.contains(saved));
		
	}
	@Test
	public void deleteTest(){
		Endorsement saved = this.endorsementService.findOne(super.getEntityId("endorsement1"));
		
		this.endorsementService.delete(saved);
		Assert.isNull(this.endorsementService.findOne(saved.getId()));
	}

	/*@Test
	public void testFindAll() {
		Collection<Endorsement> endorsements;
		endorsements = this.endorsementService.findAll();
		Assert.notNull(endorsements);
		Assert.notEmpty(endorsements);
	}*/
	
	@Test
	public void testFindOne(){
		Endorsement e;
		e = this.endorsementService.findOne(super.getEntityId("endorsement2"));
		int findId = e.getId();
		Assert.notNull(this.endorsementService.findOne(findId));
		
	}

	@Test
	public void testCreate(){
		Endorsement e = this.endorsementService.create();
		Assert.notNull(e);
	}

}



