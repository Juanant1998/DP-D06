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

import domain.MiscellaneousRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/dataSource.xml",
		"classpath:spring/config/packages.xml"
	})


@Transactional
public class MiscellaneousRecordServiceTest extends AbstractTest {
	@Autowired
	private MiscellaneousRecordService miscellaneousRecordService;
	
	@Test
	public void testSaveMiscellaneousRecords(){
		MiscellaneousRecord saved = this.miscellaneousRecordService.findOne(super.getEntityId("miscellaneousRecord1"));
		Collection<MiscellaneousRecord> miscellaneousRecords = new ArrayList<>();
		
		this.miscellaneousRecordService.save(saved);
		miscellaneousRecords.add(saved);
		
		Assert.isTrue(miscellaneousRecords.contains(saved));
		
	}
	@Test
	public void deleteTest(){
		MiscellaneousRecord saved = this.miscellaneousRecordService.findOne(super.getEntityId("miscellaneousRecord1"));
		
		this.miscellaneousRecordService.delete(saved);
		Assert.isNull(this.miscellaneousRecordService.findOne(saved.getId()));
	}

	@Test
	public void testFindAll() {
		Collection<MiscellaneousRecord> miscellaneousRecords;
		miscellaneousRecords = this.miscellaneousRecordService.findAll();
		Assert.isTrue(!miscellaneousRecords.isEmpty());
	}
	
	@Test
	public void testFindOne(){
		MiscellaneousRecord mr;
		mr = this.miscellaneousRecordService.findOne(super.getEntityId("miscellaneousRecord2"));
		int findId = mr.getId();
		Assert.notNull(this.miscellaneousRecordService.findOne(findId));
		
	}

	@Test
	public void testCreate(){
		MiscellaneousRecord mr = this.miscellaneousRecordService.create();
		Assert.notNull(mr);
	}

}


