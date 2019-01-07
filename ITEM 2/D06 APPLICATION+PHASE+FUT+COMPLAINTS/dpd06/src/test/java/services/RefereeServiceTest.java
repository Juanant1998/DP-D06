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
import domain.Referee;

import utilities.AbstractTest;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})

@Transactional
public class RefereeServiceTest  extends AbstractTest{
	@Autowired
	private RefereeService refereeService;

	@Test 
	public void testSaveActors(){
		//Actor
		Collection<Referee> referees = new ArrayList<>();
		Referee guardando=refereeService.findOne(super.getEntityId("referee3"));
		refereeService.save(guardando);
		referees.add(guardando);
		Assert.isTrue(referees.contains(guardando));
		
	}
	@Test
	public void  testDeleteActor(){
		Referee borrando= refereeService.findOne(super.getEntityId("referee3"));
			refereeService.delete(borrando);
			Assert.isNull(refereeService.findOne(borrando.getId()));
	}
	@Test
	public void  findOneOk(){
		Referee  find = refereeService.findOne(super.getEntityId("referee3"));
		 int  findId= find.getId();
		 Assert.notNull(refereeService.findOne(findId));
	}
	@Test
	public void FindAll(){
		Collection<Referee> referees;
		referees = this.refereeService.findAll();
		Assert.isTrue(!referees.isEmpty());
		
	}
	@Test
	public void CreateTest(){
		Referee  create= refereeService.create();
		Assert.notNull(create);
		
	}
}
