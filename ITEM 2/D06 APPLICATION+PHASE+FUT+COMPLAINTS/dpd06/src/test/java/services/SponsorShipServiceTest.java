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

import domain.Actor;
import domain.Sponsorship;

import utilities.AbstractTest;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})

@Transactional
//error
public class SponsorShipServiceTest  extends AbstractTest{
	@Autowired
	private SponsorshipService sponsorshipService;
	@Test 
	public void testSaveSponsorships(){
		//Actor
		Collection<Sponsorship> sponsorships = new ArrayList<>();
		Sponsorship guardando=sponsorshipService.findOne(super.getEntityId("sponsorship3"));
		sponsorshipService.save(guardando);
		sponsorships.add(guardando);
		Assert.isTrue(sponsorships.contains(guardando));
		
	}
	@Test
	public void  testDeleteSponsorship(){
		Sponsorship borrando= sponsorshipService.findOne(super.getEntityId("sponsorship3"));
			sponsorshipService.delete(borrando);
			Assert.isNull(sponsorshipService.findOne(borrando.getId()));
	}
	@Test
	public void  findOneOk(){
		Sponsorship  find = sponsorshipService.findOne(super.getEntityId("sponsorship3"));
		 int  findId= find.getId();
		 Assert.notNull(sponsorshipService.findOne(findId));
	}
	@Test
	public void FindAll(){
		Collection<Sponsorship> sponsorships;
		sponsorships = this.sponsorshipService.findAll();
		Assert.isTrue(!sponsorships.isEmpty());
		
	}
	@Test
	public void CreateTest(){
		Sponsorship  create= sponsorshipService.create();
		Assert.notNull(create);
		
	}
}
