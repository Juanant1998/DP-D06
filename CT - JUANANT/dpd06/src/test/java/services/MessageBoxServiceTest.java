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

import domain.MessageBox;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/dataSource.xml",
		"classpath:spring/config/packages.xml"
	})


@Transactional
public class MessageBoxServiceTest extends AbstractTest {
	@Autowired
	private MessageBoxService messageBoxService;
	
	@Test
	public void testSaveMessageBoxes(){
		MessageBox saved = this.messageBoxService.findOne(super.getEntityId("in1"));
		Collection<MessageBox> messageBoxes = new ArrayList<>();
		
		this.messageBoxService.save(saved);
		messageBoxes.add(saved);
		
		Assert.isTrue(messageBoxes.contains(saved));
		
	}
	@Test
	public void deleteTest(){
		MessageBox saved = this.messageBoxService.findOne(super.getEntityId("in1"));
		
		this.messageBoxService.delete(saved);
		Assert.isNull(this.messageBoxService.findOne(saved.getId()));
	}

	@Test
	public void testFindAll() {
		Collection<MessageBox> messageBoxes;
		messageBoxes = this.messageBoxService.findAll();
		Assert.isTrue(!messageBoxes.isEmpty());
	}
	
	@Test
	public void testFindOne(){
		MessageBox mgb;
		mgb = this.messageBoxService.findOne(super.getEntityId("in2"));
		int findId = mgb.getId();
		Assert.notNull(this.messageBoxService.findOne(findId));
		
	}

	@Test
	public void testCreate(){
		MessageBox mgb = this.messageBoxService.create();
		Assert.notNull(mgb);
	}

}

