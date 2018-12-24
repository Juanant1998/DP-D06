package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import domain.HandyWorker;
import domain.Sponsor;
import domain.Tutorial;

import utilities.AbstractTest;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})

@Transactional
public class SponsorServiceTest  extends AbstractTest{
	@Autowired
	private SponsorService sponsorService;

	@Test 
	public void testSaveSponsors(){
		//Actor
		Collection<Sponsor> sponsors = new ArrayList<>();
		Sponsor guardando=sponsorService.findOne(super.getEntityId("sponsor3"));
		sponsorService.save(guardando);
		sponsors.add(guardando);
		Assert.isTrue(sponsors.contains(guardando));
		
	}
	@Test
	public void  testDeleteSponsor(){
		Sponsor borrando= sponsorService.findOne(super.getEntityId("sponsor3"));
			sponsorService.delete(borrando);
			Assert.isNull(sponsorService.findOne(borrando.getId()));
	}
	@Test
	public void  findOneOk(){
		Sponsor  find = sponsorService.findOne(super.getEntityId("sponsor3"));
		 int  findId= find.getId();
		 Assert.notNull(sponsorService.findOne(findId));
	}
	@Test
	public void FindAll(){
		Collection<Sponsor> sponsors;
		sponsors = this.sponsorService.findAll();
		Assert.isTrue(!sponsors.isEmpty());
		
	}
	@Test
	public void CreateTest(){
		Sponsor  create= sponsorService.create();
		Assert.notNull(create);
		
	}
	
	public void findTutorialByHandyWorkerTest(){
		Map<HandyWorker, Collection<Tutorial>> res = sponsorService.findTutorialsByHandyWorker();
		Assert.isTrue(!res.isEmpty());
	}
}
