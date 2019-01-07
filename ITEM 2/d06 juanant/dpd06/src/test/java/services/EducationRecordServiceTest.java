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
import domain.EducationRecord;

import utilities.AbstractTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})

@Transactional
public class EducationRecordServiceTest  extends AbstractTest{
	@Autowired
	private EducationRecordService educationRecordService;
	@Test 
	public void testSaveActors(){
		//Actor
		Collection<EducationRecord> educationRecords = new ArrayList<>();
		
		EducationRecord guardando=educationRecordService.findOne(super.getEntityId("educationRecord3"));
		educationRecordService.save(guardando);
		educationRecords.add(guardando);
		Assert.isTrue(educationRecords.contains(guardando));
		
	}
	@Test
	public void  testDeleteActor(){
		EducationRecord borrando= educationRecordService.findOne(super.getEntityId("educationRecord3"));
			educationRecordService.delete(borrando);
			Assert.isNull(educationRecordService.findOne(borrando.getId()));
	}
	@Test
	public void  findOneOk(){
		EducationRecord find = educationRecordService.findOne(super.getEntityId("educationRecord3"));
		 int  findId= find.getId();
		 Assert.notNull(educationRecordService.findOne(findId));
	}
	@Test
	public void FindAll(){
		Collection<EducationRecord> educationRecords;
		educationRecords = this.educationRecordService.findAll();
		Assert.isTrue(!educationRecords.isEmpty());
		
	}
	@Test
	public void CreateTest(){
		EducationRecord  create= educationRecordService.create();
		Assert.notNull(create);
		
	}
}
