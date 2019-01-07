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

import domain.PersonalRecord;

import utilities.AbstractTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/dataSource.xml",
		"classpath:spring/config/packages.xml"
	})


@Transactional
public class PersonalRecordServiceTest extends AbstractTest {

	@Autowired
	private PersonalRecordService personalRecordService;
	
	@Test
	public void testSavepersonalRecords(){
		PersonalRecord saved = this.personalRecordService.findOne(super.getEntityId("personalRecord1"));
		Collection<PersonalRecord> personalRecords = new ArrayList<>();
		
		this.personalRecordService.save(saved);
		personalRecords.add(saved);
		
		Assert.isTrue(personalRecords.contains(saved));
		
	}
	@Test
	public void deleteTest(){
		PersonalRecord saved = this.personalRecordService.findOne(super.getEntityId("personalRecord1"));
		this.personalRecordService.delete(saved);
		Assert.isNull(this.personalRecordService.findOne(saved.getId()));
	}

	@Test
	public void testFindAll() {
		Collection<PersonalRecord> personalRecords;
		personalRecords = this.personalRecordService.findAll();
		Assert.isTrue(!personalRecords.isEmpty());
	}
	
	@Test
	public void testFindOne(){
		PersonalRecord pr;
		pr = this.personalRecordService.findOne(super.getEntityId("personalRecord2"));
		int findId = pr.getId();
		Assert.notNull(this.personalRecordService.findOne(findId));
		
	}

	@Test
	public void testCreate(){
		PersonalRecord pr = this.personalRecordService.create();
		Assert.notNull(pr);
	}

}



