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
import domain.Application;

import utilities.AbstractTest;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ApplicationServiceTest extends AbstractTest {
	@Autowired
	private ApplicationService applicationService;
	@Test 
	public void testSaveActors(){
		//Actor
		Collection<Application> applications = new ArrayList<>();
		Application guardando=applicationService.findOne(super.getEntityId("application1"));
		applicationService.save(guardando);
		applications.add(guardando);
		Assert.isTrue(applications.contains(guardando));
		
	}
	@Test
	public void  testDeleteActor(){
		Application borrando= applicationService.findOne(super.getEntityId("application1"));
		applicationService.delete(borrando);
			Assert.isNull(applicationService.findOne(borrando.getId()));
	}
	@Test
	public void  findOneOk(){
		Application find = applicationService.findOne(super.getEntityId("application1"));
		 int  findId= find.getId();
		 Assert.notNull(applicationService.findOne(findId));
	}
	@Test
	public void FindAll(){
		Collection<Application> applications;
		applications = this.applicationService.findAll();
		Assert.isTrue(!applications.isEmpty());
		
	}
	@Test
	public void CreateTest(){
		Application  create= applicationService.create();
		Assert.notNull(create);
		
	}
	

}
