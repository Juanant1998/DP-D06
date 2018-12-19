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
import domain.Tutorial;

import utilities.AbstractTest;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})

@Transactional
public class TutorialServiceTest  extends AbstractTest{
	@Autowired
	private TutorialService tutorialService;

	@Test 
	public void testSaveTutorials(){
		//Actor
		Collection<Tutorial> tutorials = new ArrayList<>();
		Tutorial guardando=tutorialService.findOne(super.getEntityId("tutorial3"));
		tutorialService.save(guardando);
		tutorials.add(guardando);
		Assert.isTrue(tutorials.contains(guardando));
		
	}
	@Test
	public void  testDeleteTutorials(){
		Tutorial borrando= tutorialService.findOne(super.getEntityId("tutorial3"));
			tutorialService.delete(borrando);
			Assert.isNull(tutorialService.findOne(borrando.getId()));
	}
	@Test
	public void  findOneOk(){
		Tutorial  find = tutorialService.findOne(super.getEntityId("tutorial3"));
		 int  findId= find.getId();
		 Assert.notNull(tutorialService.findOne(findId));
	}
	@Test
	public void FindAll(){
		Collection<Tutorial> tutorials;
		tutorials = this.tutorialService.findAll();
		Assert.isTrue(!tutorials.isEmpty());
		
	}
	@Test
	public void CreateTest(){
		Tutorial  create= tutorialService.create();
		Assert.notNull(create);
		
	}
}
