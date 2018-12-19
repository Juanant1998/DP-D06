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

import domain.Actor;
import domain.Curriculum;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})

@Transactional

public class CurriculumServiceTest extends AbstractTest {
	@Autowired
	private CurriculumService curriculumService;
	
	
	@Test 
	public void testSaveActors(){
		//Actor
		Collection<Curriculum> curriculums = new ArrayList<>();
		Curriculum guardando=curriculumService.findOne(super.getEntityId("curriculum3"));
		curriculumService.save(guardando);
		curriculums.add(guardando);
		Assert.isTrue(curriculums.contains(guardando));
		
	}
	@Test
	public void  testDeleteCurriculum(){
		Curriculum borrando= curriculumService.findOne(super.getEntityId("curriculum3"));
			curriculumService.delete(borrando);
			Assert.isNull(curriculumService.findOne(borrando.getId()));
	}
	@Test
	public void  findOneOk(){
		Curriculum  find = curriculumService.findOne(super.getEntityId("curriculum3"));
		 int  findId= find.getId();
		 Assert.notNull(curriculumService.findOne(findId));
	}
	@Test
	public void FindAll(){
		Collection<Curriculum> curriculums;
		curriculums = this.curriculumService.findAll();
		Assert.isTrue(!curriculums.isEmpty());
		
	}
	@Test
	public void CreateTest(){
		Curriculum  create= curriculumService.create();
		Assert.notNull(create);
		
	}
}
