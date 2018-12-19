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
import domain.Message;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})

@Transactional

public class MessageServiceTest  extends AbstractTest{
	@Autowired
	private MessageService messageService;
	
	@Test 
	public void testSaveActors(){
		//Actor
		Collection<Message> messages = new ArrayList<>();
		Message guardando=messageService.findOne(super.getEntityId("message3"));
		messageService.save(guardando);
		messages.add(guardando);
		Assert.isTrue(messages.contains(guardando));

		
	}
	@Test
	public void  testDeleteActor(){
		Message borrando= messageService.findOne(super.getEntityId("message3"));
			messageService.delete(borrando);
			Assert.isNull(messageService.findOne(borrando.getId()));
	}
	@Test
	public void  findOneOk(){
		Message  find = messageService.findOne(super.getEntityId("message3"));
		 int  findId= find.getId();
		 Assert.notNull(messageService.findOne(findId));
	}
	@Test
	public void FindAll(){
		Collection<Message> actors;
		actors = this.messageService.findAll();
		Assert.isTrue(!actors.isEmpty());
		
	}
	@Test
	public void CreateTest(){
		Message  create= messageService.create();
		Assert.notNull(create);
		
	}

}
