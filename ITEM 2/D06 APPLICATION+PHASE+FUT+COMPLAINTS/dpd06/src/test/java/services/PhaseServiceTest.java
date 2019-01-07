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
import domain.Phase;

import utilities.AbstractTest;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})

@Transactional
public class PhaseServiceTest extends AbstractTest {
	@Autowired
	private PhaseService phaseService;
	@Test 
	public void testSavePhases(){
		//Actor
		Collection<Phase> phases = new ArrayList<>();
		Phase guardando=phaseService.findOne(super.getEntityId("phase3"));
		phaseService.save(guardando);
		phases.add(guardando);
		Assert.isTrue(phases.contains(guardando));
		
	}
	@Test
	public void  testDeletePhase(){
		Phase borrando= phaseService.findOne(super.getEntityId("phase3"));
			phaseService.delete(borrando);
			Assert.isNull(phaseService.findOne(borrando.getId()));
	}
	@Test
	public void  findOneOk(){
		Phase  find = phaseService.findOne(super.getEntityId("phase3"));
		 int  findId= find.getId();
		 Assert.notNull(phaseService.findOne(findId));
	}
	@Test
	public void FindAll(){
		Collection<Phase> phases;
		phases = this.phaseService.findAll();
		Assert.isTrue(!phases.isEmpty());
		
	}
	@Test
	public void CreateTest(){
		Phase  create= phaseService.create();
		Assert.notNull(create);
		
	}
	
}
