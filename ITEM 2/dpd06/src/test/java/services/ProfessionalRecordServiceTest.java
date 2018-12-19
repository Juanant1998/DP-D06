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

import domain.ProfessionalRecord;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ProfessionalRecordServiceTest extends AbstractTest {
	//error
	@Autowired
	private ProfessionalRecordService professionalRecordService;

	@Test 
	public void testSaveProfessionalRecords(){
		//Actor
		Collection<ProfessionalRecord> profesionalRecords = new ArrayList<>();
		ProfessionalRecord guardando=professionalRecordService.findOne(super.getEntityId("professionalRecord3"));
		professionalRecordService.save(guardando);
		profesionalRecords.add(guardando);
		Assert.isTrue(profesionalRecords.contains(guardando));
		
	}
	@Test
	public void  testDeleteProfessionalRecord(){
		ProfessionalRecord borrando= professionalRecordService.findOne(super.getEntityId("professionalRecord3"));
			professionalRecordService.delete(borrando);
			Assert.isNull(professionalRecordService.findOne(borrando.getId()));
	}
	@Test
	public void  findOneOk(){
		ProfessionalRecord  find = professionalRecordService.findOne(super.getEntityId("professionalRecord3"));
		 int  findId= find.getId();
		 Assert.notNull(professionalRecordService.findOne(findId));
	}
	@Test
	public void FindAll(){
		Collection<ProfessionalRecord> profesionalRecords;
		profesionalRecords = this.professionalRecordService.findAll();
		Assert.isTrue(!profesionalRecords.isEmpty());
		
	}
	@Test
	public void CreateTest(){
		ProfessionalRecord  create= professionalRecordService.create();
		Assert.notNull(create);
		
	}
}
