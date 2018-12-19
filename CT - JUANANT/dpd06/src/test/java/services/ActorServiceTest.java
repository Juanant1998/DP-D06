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
import domain.MessageBox;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})

@Transactional
public class ActorServiceTest extends AbstractTest  {
@Autowired
private ActorService actorService;

@Autowired
private MessageBoxService messageBoxService;

@Autowired
private MessageService messageService;

@Test 
public void testSaveActors(){
	//Actor
	Collection<Actor> actors = new ArrayList<>();
	Actor guardando=actorService.findOne(super.getEntityId("handyWorker1"));
	actorService.save(guardando);
	actors.add(guardando);
	Assert.isTrue(actors.contains(guardando));
	
}
@Test
public void  testDeleteActor(){
	Actor borrando= actorService.findOne(super.getEntityId("handyWorker1"));
		actorService.delete(borrando);
		Assert.isNull(actorService.findOne(borrando.getId()));
}
@Test
public void  findOneOk(){
	 Actor  find = actorService.findOne(super.getEntityId("handyWorker2"));
	 int  findId= find.getId();
	 Assert.notNull(actorService.findOne(findId));
}
@Test
public void FindAll(){
	Collection<Actor> actors;
	actors = this.actorService.findAll();
	Assert.isTrue(!actors.isEmpty());
	
}
@Test
public void CreateTest(){
	Actor  create= actorService.create();
	Assert.notNull(create);
	
}

@Test
public void TestActorBanned(){
	Actor  find = actorService.findOne(super.getEntityId("handyWorker3"));
	Assert.isTrue(!find.getIsBanned());
}
@Test
public void TestCreateMessageBox(){
	MessageBox create = messageBoxService.create();
	String  username = "paco";
	String  msgboxname = "caja de mensaje";
	
	create.setName(username + " " + msgboxname);
	create.setSystemBox(false);
	
	
	
	Assert.notNull(create);
}

@Test
public  void TestSendMessage(){
	Message mes = messageService.findOne(super.getEntityId("message2"));
	Collection<Actor> recipients =mes.getRecipients();
	Assert.notEmpty(recipients);
	Assert.notNull(mes.getSender());
	Message m = this.actorService.sendMessage(mes);

	recipients =m.getRecipients();

	for (Actor a : recipients){
		
		for(MessageBox mbox : a.getMessageBoxes()){
			if (mbox.isSystemBox() == true && mbox.getName().endsWith("in")) {
				Assert.isTrue((mbox.getMessages().contains(m)));
			}
		}
		
		
	}
		
}
/*}
@Test
public void editMessageBoxTest(){//Try to edit a system box
	super.authenticate("handyworker1");
	MessageBox  m = this.messageBoxService.findOne(super.getEntityId("in1"));
	String newName = "ini";
	this.actorService.editMessageBox(m, newName);
	Assert.isTrue(!m.getName().equals(newName));
	super.authenticate(null);
}

/*@Test
public void deleteMessageBoxTest(){ //Intenta borrar una caja del sistema
	super.authenticate("handyworker1");
	MessageBox  m = this.messageBoxService.findOne(super.getEntityId("in1"));
	this.actorService.deleteMessageBox(m);
	Assert.notNull(this.actorService.findOne(m.getId()));
	
	super.authenticate(null);
}*/

@Test
public void editDataTest(){
	Actor a1 = actorService.findOne(super.getEntityId("handyWorker2"));
	 
	String comprueba = a1.getEmail();
	
	a1.setEmail("g@gmail.com");
	
	Actor r = this.actorService.editMyData(a1);
	Assert.isTrue(!(comprueba.equals(r.getEmail())));
	 
}





}
