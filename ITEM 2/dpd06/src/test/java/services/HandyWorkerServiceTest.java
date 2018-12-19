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

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Application;
import domain.Customer;
import domain.Endorsement;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.MessageBox;
import domain.Note;
import domain.Phase;
import domain.Report;
import domain.Tutorial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class HandyWorkerServiceTest extends AbstractTest {

	@Autowired
	private HandyWorkerService handyWorkerService;
	
	@Autowired
	private FixUpTaskService fixUpTaskService;
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private PhaseService phaseService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private NoteService noteService;
	
	@Autowired
	private TutorialService tutorialService;
	
	@Autowired
	private EndorsementService endorsementService;
	
	@Autowired
	private CustomerService customerService;

	@Test 
	public void testSaveActors(){
		//Actor
		Collection<HandyWorker> actors = new ArrayList<>();
		HandyWorker guardando=handyWorkerService.findOne(super.getEntityId("handyWorker3"));
		handyWorkerService.save(guardando);
		actors.add(guardando);
		Assert.isTrue(actors.contains(guardando));
		
	}
	@Test
	public void  testDeleteHandy(){
		HandyWorker borrando= handyWorkerService.findOne(super.getEntityId("handyWorker3"));
			handyWorkerService.delete(borrando);
			Assert.isNull(handyWorkerService.findOne(borrando.getId()));
	}
	@Test
	public void  findOneOk(){
		HandyWorker  find = handyWorkerService.findOne(super.getEntityId("handyWorker3"));
		 int  findId= find.getId();
		 Assert.notNull(handyWorkerService.findOne(findId));
	}
	@Test
	public void FindAll(){
		Collection<HandyWorker> actors;
		actors = this.handyWorkerService.findAll();
		Assert.isTrue(!actors.isEmpty());
		
	}
	@Test
	public void createTest(){
		HandyWorker  create= handyWorkerService.create();
		Assert.notNull(create);
		
	}
	@Test
	public void createNewMessageBox(){
		super.authenticate("handyWorker1");
		String username = "paquito";
		String msgboxname= "dejajajas";
		MessageBox box = this.actorService.createNewMessageBox(username, msgboxname);
		Assert.notNull(box);
		super.authenticate(null);
		
	}
	@Test
	public void registerTest(){
		super.authenticate("handyWorker1");
		HandyWorker h;
		h = this.handyWorkerService.findOne(super.getEntityId("handyWorker1"));
		UserAccount  n  = new UserAccount();
		Authority  s  = new Authority();
		n.setUsername("paquito");
		n.setPassword("21232f297a57a5a743894a0e4a801fc2");
		s.setAuthority(Authority.HANDYWORKER);
		Collection<Authority> authorities = new ArrayList<>();
		authorities.add(s);
		n.setAuthorities(authorities);
		h.setUserAccount(n);
		
		HandyWorker  registrado = this.handyWorkerService.register(h);
		registrado.setUserAccount(n);
		Assert.isTrue(registrado.getIsBanned().equals(h.getIsBanned()));
		super.authenticate(null);
	}
	@Test
	public void getAllFixUpTasksTest(){
		super.authenticate("handyWorker1");
		Collection<FixUpTask> fs =this.handyWorkerService.getAllFixUpTasks();
		Assert.isTrue(!fs.isEmpty());
		super.authenticate(null);
		
	}
	@Test
	public void getFixUpTaskCustomer(){
		super.authenticate("handyWorker1");
		FixUpTask f=this.fixUpTaskService.findOne(super.getEntityId("fixUpTask1"));
		
		Customer c = this.handyWorkerService.getFixUpTaskCustomer(f);
		Assert.notNull(c);
		Assert.isTrue(c.getFixUpTasks().contains(f));
		super.authenticate(null);
	}
	@Test
	public void filterFixUpTasksByKeywordTest(){
		super.authenticate("handyWorker1");
		String keyword = "description";
		Collection<FixUpTask> fs = this.handyWorkerService.filterFixUpTasksByKeyword(keyword);
		Assert.isTrue(!fs.isEmpty());
		super.authenticate("handyworker1");
	}
	/*@Test
	public void filterFixUpTasksByDateRangeTest(){
		super.authenticate("handyworker1");
		Date date = Date.UTC(year, month, date, hrs, min, sec)
		Collection<FixUpTask> fs = this.handyWorkerService.filterFixUpTasksByKeyword(keyword);
		Assert.isTrue(!fs.isEmpty());
		super.authenticate(null);
	}*/
	@Test
	public void listApplicationsTest(){
		super.authenticate("handyWorker1");
		Collection<Application> apps = this.handyWorkerService.listApplications();
		Assert.isTrue(!apps.isEmpty());
		super.authenticate("handyworker1");
	}
	@Test
	public void showApplicationTest(){
		super.authenticate("handyWorker1");
		Application app=this.applicationService.findOne(super.getEntityId("application1"));
		Assert.notNull(this.handyWorkerService.showApplication(app.getId()));
		super.authenticate(null);
	}
	@Test
	public void createApplicationTest(){
		super.authenticate("handyWorker1");
		Application app=this.applicationService.findOne(super.getEntityId("application1"));
		Application app2 = this.handyWorkerService.createApplication(app);
		app.setComments("hhh");
		Assert.notNull(app2);
		Assert.isTrue(!app.getComments().equals(app2.getComments()));
		super.authenticate(null);
	}

	@Test
	public void addPhaseTest(){
		super.authenticate("handyWorker5");
		Application app=this.applicationService.findOne(super.getEntityId("application7"));
		FixUpTask fut = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask1"));
		Phase ph = this.phaseService.findOne(super.getEntityId("phase1"));
		Phase res = this.handyWorkerService.addPhase(app, ph, fut);
		ph.setDescription("jiji");
		Assert.isTrue(!ph.getDescription().equals(res.getDescription()));
		super.authenticate(null);
	}
	@Test
	public void getFixUpTaskPhasesTest(){
		super.authenticate("handyWorker1");
		FixUpTask fut = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask1"));
		Collection<Phase> futs = this.handyWorkerService.getFixUpTaskPhases(fut);
		Assert.isTrue(!futs.isEmpty());
		super.authenticate(null);
	}
	@Test
	public void updatePhase(){
		super.authenticate("handyWorker1");
		Phase ph = this.phaseService.findOne(super.getEntityId("phase1"));
		Phase phneutro= this.handyWorkerService.updatePhase(ph);
		Assert.notNull(this.phaseService.findOne(phneutro.getId()));
		super.authenticate(null);
	}
	@Test
	public void deletePhase(){
		super.authenticate("handyWorker1");
		Phase ph = this.phaseService.findOne(super.getEntityId("phase1"));
		this.handyWorkerService.deletePhase(ph);
		Assert.isNull(this.phaseService.findOne(ph.getId()));
		super.authenticate(null);
	}
	@Test
	public void getFinderFixUpTasksTest(){
		super.authenticate("handyWorker1");
		Collection<FixUpTask> fs = this.handyWorkerService.getFinderFixUpTasks();
		Assert.isTrue(!fs.isEmpty());
		super.authenticate(null);
	}
	@Test
	public void getFixUpTasksUserInvolved(){
		super.authenticate("handyWorker5");
		Collection<FixUpTask> fs = this.handyWorkerService.getFixUpTasksUserInvolved();
		Assert.isTrue(!fs.isEmpty());
		super.authenticate(null);
	}
	@Test
	public void createNoteInReport(){
		super.authenticate("handyWorker1");
		Note n = this.noteService.findOne(super.getEntityId("note1"));
		Report r = this.reportService.findOne(super.getEntityId("report1"));
		Note res = this.handyWorkerService.createNoteInReport(n, r);
		Collection<Note> notes = r.getNotes();
		Assert.isTrue(notes.contains(n));
		Assert.notNull(res);
		super.authenticate(null);
	}
	@Test
	public void writeCommentOnNote(){
		super.authenticate("handyWorker1");
		Note n = this.noteService.findOne(super.getEntityId("note1"));
		String c = "Hola";
		Note nota = this.handyWorkerService.writeCommentOnNote(n, c);
		Assert.isTrue(nota.getComment().contains(c));
	}

	@Test
	public void listTutorials(){
		super.authenticate("handyWorker1");
		Collection<Tutorial> tuts = this.handyWorkerService.listTutorials();
		Assert.isTrue(!tuts.isEmpty());
		super.authenticate(null);
	}
	@Test
	public void getTutorials(){
		super.authenticate("handyWorker1");
		HandyWorker h = this.handyWorkerService.findOne(super.getEntityId("handyWorker1"));
		Collection<Tutorial> ts = this.handyWorkerService.getTutorials(h.getUserAccount());
		Assert.isTrue(!ts.isEmpty());
		super.authenticate(null);
	}
	@Test
	public void showTutorial(){
		super.authenticate("handyWorker1");
		Tutorial t = this.tutorialService.findOne(super.getEntityId("tutorial1"));
		Tutorial res = this.handyWorkerService.showTutorial(t.getId());
		Assert.notNull(res);
		super.authenticate(null);
	}
	@Test
	public void createTutorial(){
		super.authenticate("handyWorker1");
		Tutorial t = this.tutorialService.findOne(super.getEntityId("tutorial1"));
		Tutorial res = this.handyWorkerService.createTutorial(t);
		t.setTitle("jdjd");
		Assert.notNull(res);
		Assert.isTrue(!res.getTitle().equals(t.getTitle()));
		super.authenticate(null);
	}
	@Test
	public void updateTutorial(){
		super.authenticate("handyWorker1");
		Tutorial t = this.tutorialService.findOne(super.getEntityId("tutorial1"));
		Tutorial res= this.handyWorkerService.updateTutorial(t);
		Assert.notNull(this.tutorialService.findOne(res.getId()));
	}
	
	@Test
	public void deleteTutorial(){
		super.authenticate("handyWorker1");
		Tutorial t = this.tutorialService.findOne(super.getEntityId("tutorial1"));
		this.handyWorkerService.deleteTutorial(t);
		Assert.isNull(this.tutorialService.findOne(t.getId()));
		
	}

	@Test
	public void listEndorsements(){
		super.authenticate("handyWorker5");
		Collection<Endorsement> en = this.handyWorkerService.listEndorsements();
		Assert.isTrue(!en.isEmpty());
		super.authenticate(null);
	}
	@Test
	public void showEndorsement(){
		super.authenticate("handyWorker5");
		Endorsement en = this.endorsementService.findOne(super.getEntityId("endorsement1"));
		Endorsement res = this.handyWorkerService.showEndorsement(en.getId());
		Assert.notNull(res);
		super.authenticate(null);
	}
	@Test
	public void createEndorsement(){
		super.authenticate("handyWorker5");
		Endorsement en = this.endorsementService.findOne(super.getEntityId("endorsement1"));
		Customer c = this.customerService.findOne(super.getEntityId("customer1"));
		FixUpTask    fut = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask1"));
		
		Endorsement res = this.handyWorkerService.createEndorsement(c, en,fut);
		en.setComments("jdjdjs");
		Assert.isTrue(!res.getComments().equals(en.getComments()));
		Assert.notNull(res);
		super.authenticate(null);
	}
	@Test
	public void updateEndorsement(){
		super.authenticate("handyWorker1");
		Endorsement en = this.endorsementService.findOne(super.getEntityId("endorsement1"));
		Endorsement res= this.handyWorkerService.updateEndorsement(en);
		Assert.notNull(this.endorsementService.findOne(res.getId()));
	}
	
	@Test
	public void deleteEndorsement(){
		super.authenticate("handyWorker1");
		Endorsement en = this.endorsementService.findOne(super.getEntityId("endorsement1"));
		this.handyWorkerService.deleteEndorsement(en);
		Assert.isNull(this.endorsementService.findOne(en.getId()));
		
	}


}