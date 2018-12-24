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
import domain.Section;

import utilities.AbstractTest;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SectionServiceTest extends AbstractTest {
	@Autowired
	private SectionService sectionService;

	@Test 
	public void testSaveSections(){
		//Actor
		Collection<Section> sections = new ArrayList<>();
		Section guardando=sectionService.findOne(super.getEntityId("section2"));
		sectionService.save(guardando);
		sections.add(guardando);
		Assert.isTrue(sections.contains(guardando));
		
	}
	@Test
	public void  testDeleteSection(){
		Section  create= sectionService.create();
		Assert.notNull(create);			
		Section s = sectionService.save(create);
sectionService.delete(s);
			Assert.isNull(sectionService.findOne(create.getId()));
	}
	@Test
	public void  findOneOk(){
		Section  find = sectionService.findOne(super.getEntityId("section2"));
		 int  findId= find.getId();
		 Assert.notNull(sectionService.findOne(findId));
	}
	@Test
	public void FindAll(){
		Collection<Section> sections;
		sections = this.sectionService.findAll();
		Assert.isTrue(!sections.isEmpty());
		
	}
	@Test
	public void CreateTest(){
		Section  create= sectionService.create();
		Assert.notNull(create);
		
	}
}

