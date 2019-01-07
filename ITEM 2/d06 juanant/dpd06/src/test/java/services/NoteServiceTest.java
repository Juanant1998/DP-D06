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
import domain.Note;

import utilities.AbstractTest;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})

@Transactional
public class NoteServiceTest extends AbstractTest {
	@Autowired
	private NoteService noteService;
	@Test 
	public void testSaveNotes(){
		//Actor
		Collection<Note> notes = new ArrayList<>();
		Note guardando=noteService.findOne(super.getEntityId("note3"));
		noteService.save(guardando);
		notes.add(guardando);
		Assert.isTrue(notes.contains(guardando));
		
	}
	@Test
	public void  testDeleteNote(){
		Note borrando= noteService.findOne(super.getEntityId("note3"));
			noteService.delete(borrando);
			Assert.isNull(noteService.findOne(borrando.getId()));
	}
	@Test
	public void  findOneOk(){
		Note  find = noteService.findOne(super.getEntityId("note3"));
		 int  findId= find.getId();
		 Assert.notNull(noteService.findOne(findId));
	}
	@Test
	public void FindAll(){
		Collection<Note> notes;
		notes = this.noteService.findAll();
		Assert.isTrue(!notes.isEmpty());
		
	}
	@Test
	public void CreateTest(){
		Note  create= noteService.create();
		Assert.notNull(create);
		
	}
}
