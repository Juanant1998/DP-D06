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
import domain.EndorserRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})

@Transactional
public class EndorserRecordServiceTest extends AbstractTest {

	@Autowired
	private EndorserRecordService endorserRecordService;
	
	
	@Test 
	public void testSaveActors(){
		//Actor
		Collection<EndorserRecord> endorserRecords = new ArrayList<>();
		EndorserRecord guardando=endorserRecordService.findOne(super.getEntityId("endorserRecord3"));
		endorserRecordService.save(guardando);
		endorserRecords.add(guardando);
		Assert.isTrue(endorserRecords.contains(guardando));
		
	}
	@Test
	public void  testDeleteActor(){
		EndorserRecord borrando= endorserRecordService.findOne(super.getEntityId("endorserRecord3"));
			endorserRecordService.delete(borrando);
			Assert.isNull(endorserRecordService.findOne(borrando.getId()));
	}
	@Test
	public void  findOneOk(){
		 EndorserRecord  find = endorserRecordService.findOne(super.getEntityId("endorserRecord3"));
		 int  findId= find.getId();
		 Assert.notNull(endorserRecordService.findOne(findId));
	}
	@Test
	public void FindAll(){
		Collection<EndorserRecord> endorserRecords;
		endorserRecords = this.endorserRecordService.findAll();
		Assert.isTrue(!endorserRecords.isEmpty());
		
	}
	@Test
	public void CreateTest(){
		EndorserRecord  create= endorserRecordService.create();
		Assert.notNull(create);
		
	}
}
