
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import repositories.HandyWorkerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Category;
import domain.Complaint;
import domain.Customer;
import domain.Endorsement;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.MessageBox;
import domain.Note;
import domain.Phase;
import domain.Profile;
import domain.Report;
import domain.Tutorial;
import domain.Warranty;

@Service
@Transactional
public class HandyWorkerService {

	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;

	@Autowired
	private MessageBoxService		mbs;

	@Autowired
	private FixUpTaskService		futs;

	@Autowired
	private CustomerRepository		cr;

	@Autowired
	private FinderService			fs;

	@Autowired
	private UserAccountService		uas;

	@Autowired
	private ApplicationService		as;

	@Autowired
	private PhaseService			ps;

	@Autowired
	private ActorService			actorservice;

	@Autowired
	private ReportService			rs;

	@Autowired
	private NoteService				ns;

	@Autowired
	private TutorialService			ts;

	@Autowired
	private EndorsementService		endorsementService;

	@Autowired
	private CustomerService			customerService;


	public HandyWorker create() {//revisar
		final HandyWorker result = new HandyWorker();
		final UserAccount user = new UserAccount();
		result.setProfiles(new ArrayList<Profile>());
		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);
		final Collection<Authority> r = new ArrayList<Authority>();
		r.add(a);
		user.setAuthorities(r);
		result.setUserAccount(user);

		return result;
	}

	public void checkAuthority() {
		UserAccount ua;
		ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		final Collection<Authority> auth = ua.getAuthorities();
		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(auth.contains(a));
	}

	public HandyWorker register(final HandyWorker hw) {

		Assert.notNull(hw.getUserAccount());
		Assert.notNull(hw);
		Assert.notEmpty(hw.getProfiles());
		Assert.notEmpty(hw.getApplications());
		Assert.notNull(hw.getFinder());
		Assert.notNull(hw.getCurriculum());

		final HandyWorker result = this.create();
		result.setAddress(hw.getAddress());
		result.setApplications(new ArrayList<Application>());
		result.setCurriculum(hw.getCurriculum());
		result.setEmail(hw.getEmail());
		result.setFinder(hw.getFinder());
		result.setMake(hw.getMake());
		result.setMiddleName(hw.getMiddleName());
		result.setName(hw.getName());
		result.setPhone(hw.getPhone());
		result.setPhoto(hw.getPhoto());
		result.setProfiles(hw.getProfiles());
		result.getUserAccount().setPassword(hw.getUserAccount().getPassword());
		result.getUserAccount().setUsername(hw.getUserAccount().getUsername());
		result.setIsBanned(hw.getIsBanned());
		result.setIsSuspicious(hw.getIsSuspicious());

		final MessageBox in = this.actorservice.createNewMessageBox(hw.getUserAccount().getUsername(), "-in");
		final MessageBox out = this.actorservice.createNewMessageBox(hw.getUserAccount().getUsername(), "-out");
		final MessageBox trash = this.actorservice.createNewMessageBox(hw.getUserAccount().getUsername(), "-trash");
		final MessageBox spam = this.actorservice.createNewMessageBox(hw.getUserAccount().getUsername(), "-spam");

		final Collection<MessageBox> msboxes = new ArrayList<MessageBox>();
		msboxes.add(in);
		msboxes.add(out);
		msboxes.add(trash);
		msboxes.add(spam);

		result.setMessageBoxes(msboxes);
		final HandyWorker x = this.save(result);

		return x;

	}

	public Collection<Complaint> getHandyworkerComplaints() {
		final Collection<FixUpTask> futs = this.handyWorkerRepository.getFixUpTaskByHandyWorker(LoginService.getPrincipal());
		final Collection<Complaint> res = new ArrayList<Complaint>();
		for (final FixUpTask f : futs)
			if (!f.getComplaints().isEmpty())
				res.addAll(f.getComplaints());

		return res;

	}

	public Collection<FixUpTask> getAllFixUpTasks() {
		Assert.isTrue(!this.actorservice.isActualActorBanned());
		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);

		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(a));

		final Collection<FixUpTask> result = this.futs.findAll();

		return result;

	}

	public Customer getFixUpTaskCustomer(final FixUpTask fut) {
		Assert.isTrue(!this.actorservice.isActualActorBanned());

		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);

		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(a));

		final Customer c = this.cr.findCustomerByFixUpTask(fut);

		return c;

	}
	public Collection<FixUpTask> filterFixUpTasksByKeyword(final String keyword) {
		Assert.isTrue(!this.actorservice.isActualActorBanned());
		final Finder finder = this.fs.createFinderByKeyword(keyword);

		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());

		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actual.getUserAccount().getAuthorities().contains(a));

		final Finder f = actual.getFinder();
		f.setCategories(finder.getCategories());
		f.setEndDate(finder.getEndDate());
		f.setFixUpTasks(finder.getFixUpTasks());
		f.setMaxPrice(finder.getMaxPrice());
		f.setMinPrice(finder.getMinPrice());
		f.setStartDate(finder.getStartDate());
		f.setWarranties(finder.getWarranties());

		actual.setFinder(f);

		final HandyWorker h = this.handyWorkerRepository.save(actual);

		return f.getFixUpTasks();
	}

	public Collection<FixUpTask> filterFixUpTasksByDateRange(final Date start, final Date end) {
		Assert.isTrue(this.actorservice.isActualActorBanned());
		final Finder finder = this.fs.createFinderByDateRange(start, end);

		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());

		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actual.getUserAccount().getAuthorities().contains(a));

		final Finder f = actual.getFinder();
		f.setCategories(finder.getCategories());
		f.setEndDate(finder.getEndDate());
		f.setFixUpTasks(finder.getFixUpTasks());
		f.setMaxPrice(finder.getMaxPrice());
		f.setMinPrice(finder.getMinPrice());
		f.setStartDate(finder.getStartDate());
		f.setWarranties(finder.getWarranties());

		actual.setFinder(f);

		final HandyWorker h = this.handyWorkerRepository.save(actual);

		return f.getFixUpTasks();
	}
	public Collection<FixUpTask> filterFixUpTasksByPriceRange(final Double min, final Double max) {

		Assert.isTrue(this.actorservice.isActualActorBanned());
		final Finder finder = this.fs.createFinderByPriceRange(min, max);

		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());

		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actual.getUserAccount().getAuthorities().contains(a));
		final Finder f = actual.getFinder();
		f.setCategories(finder.getCategories());
		f.setEndDate(finder.getEndDate());
		f.setFixUpTasks(finder.getFixUpTasks());
		f.setMaxPrice(finder.getMaxPrice());
		f.setMinPrice(finder.getMinPrice());
		f.setStartDate(finder.getStartDate());
		f.setWarranties(finder.getWarranties());

		actual.setFinder(f);

		final HandyWorker h = this.handyWorkerRepository.save(actual);

		return f.getFixUpTasks();
	}

	public Collection<FixUpTask> filterFixUpTasksByWarranty(final Warranty w) {

		Assert.isTrue(this.actorservice.isActualActorBanned());
		final Finder finder = this.fs.createFinderByWarranty(w);

		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());

		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actual.getUserAccount().getAuthorities().contains(a));
		final Finder f = actual.getFinder();
		f.setCategories(finder.getCategories());
		f.setEndDate(finder.getEndDate());
		f.setFixUpTasks(finder.getFixUpTasks());
		f.setMaxPrice(finder.getMaxPrice());
		f.setMinPrice(finder.getMinPrice());
		f.setStartDate(finder.getStartDate());
		f.setWarranties(finder.getWarranties());

		actual.setFinder(f);

		final HandyWorker h = this.handyWorkerRepository.save(actual);

		return f.getFixUpTasks();
	}

	public Collection<FixUpTask> filterFixUpTasksByCategory(final Category w) {
		Assert.isTrue(this.actorservice.isActualActorBanned());

		final Finder finder = this.fs.createFinderByCategory(w);

		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());

		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actual.getUserAccount().getAuthorities().contains(a));
		final Finder f = actual.getFinder();
		f.setCategories(finder.getCategories());
		f.setEndDate(finder.getEndDate());
		f.setFixUpTasks(finder.getFixUpTasks());
		f.setMaxPrice(finder.getMaxPrice());
		f.setMinPrice(finder.getMinPrice());
		f.setStartDate(finder.getStartDate());
		f.setWarranties(finder.getWarranties());

		actual.setFinder(f);

		final HandyWorker h = this.handyWorkerRepository.save(actual);

		return f.getFixUpTasks();
	}

	public Collection<Application> listApplications() {

		Assert.isTrue(!this.actorservice.isActualActorBanned());
		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());
		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actual.getUserAccount().getAuthorities().contains(a));
		return actual.getApplications();
	}

	public Application showApplication(final int applicationId) {
		Assert.isTrue(!this.actorservice.isActualActorBanned());
		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());

		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actual.getUserAccount().getAuthorities().contains(a));

		final Application application = this.as.findOne(applicationId);

		Assert.isTrue(actual.getApplications().contains(application));

		return application;
	}

	public Application createApplication(final Application app) {

		Assert.isTrue(!this.actorservice.isActualActorBanned());
		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());

		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actual.getUserAccount().getAuthorities().contains(a));

		final Application res = new Application();
		res.setComments(app.getComments());
		res.setCreditCard(app.getCreditCard());
		res.setMoment((LocalDate.now().toDate()));
		res.setOfferedPrice(app.getOfferedPrice());
		res.setStatus("PENDING");

		final Application result = this.as.save(res);

		return result;
	}

	public Phase addPhase(final Application a, final Phase phase, final FixUpTask fut) {

		Assert.isTrue(!this.actorservice.isActualActorBanned());
		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());

		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actual.getUserAccount().getAuthorities().contains(authority));

		Assert.isTrue(actual.getApplications().contains(a));
		Assert.isTrue(a.getStatus().contentEquals("ACCEPTED"));
		Assert.notNull(phase);
		final Phase p = new Phase();
		p.setTitle(phase.getTitle());
		p.setDescription(phase.getDescription());
		p.setStartMoment(phase.getStartMoment());
		p.setEndMoment(phase.getEndMoment());

		final Collection<Phase> roto2 = fut.getPhases();
		roto2.add(p);
		fut.setPhases(roto2);

		final Phase result = this.ps.save(p);

		return result;

	}

	public Collection<Phase> getFixUpTaskPhases(final FixUpTask f) {

		Assert.isTrue(!this.actorservice.isActualActorBanned());
		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());

		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actual.getUserAccount().getAuthorities().contains(a));
		//preguntar si hay que comprobar que el handy worker es el propietario de la application aceptada.
		return f.getPhases();

	}

	public Phase updatePhase(final Phase p) {
		Assert.isTrue(!this.actorservice.isActualActorBanned());
		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());

		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actual.getUserAccount().getAuthorities().contains(a));
		Assert.notNull(p);

		final Phase result = this.ps.save(p);

		return result;

	}

	public void deletePhase(final Phase p) {
		Assert.isTrue(!this.actorservice.isActualActorBanned());
		this.ps.delete(p);
	}

	public Collection<FixUpTask> getFinderFixUpTasks() {
		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());
		Assert.isTrue(!actual.getIsBanned());
		return actual.getFinder().getFixUpTasks();
	}

	public Collection<FixUpTask> getFixUpTasksUserInvolved() {
		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());
		Assert.isTrue(!actual.getIsBanned());
		final Collection<FixUpTask> result = this.handyWorkerRepository.getFixUpTaskByHandyWorker(LoginService.getPrincipal());

		return result;
	}

	public Note createNoteInReport(final Note n, final Report r) {
		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());
		Assert.isTrue(!actual.getIsBanned());
		final Collection<Note> x = r.getNotes();
		x.add(n);

		r.setNotes(x);

		final Note result = this.ns.save(n);
		final Report opt = this.rs.save(r);

		return result;

	}

	public Note writeCommentOnNote(final Note n, final String c) {
		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());
		Assert.isTrue(!actual.getIsBanned());
		this.actorservice.checkSpammer(c);
		n.setComment(n.getComment() + "\n Comentario del usuario " + LoginService.getPrincipal().getUsername() + ": " + c);
		final Note result = this.ns.save(n);

		return result;

	}

	public Collection<HandyWorker> findAll() {
		return this.handyWorkerRepository.findAll();
	}
	public HandyWorker findOne(final int handyWorkerId) {
		return this.handyWorkerRepository.findOne(handyWorkerId);
	}
	public HandyWorker save(final HandyWorker handyWorker) {
		return this.handyWorkerRepository.save(handyWorker);
	}
	public void delete(final HandyWorker handyWorker) {
		this.handyWorkerRepository.delete(handyWorker);
	}

	public Collection<Tutorial> getTutorials(final UserAccount u) {
		Assert.isTrue(!this.actorservice.isActualActorBanned());
		this.checkAuthority();
		final Collection<Tutorial> t = this.handyWorkerRepository.getTutorialsByHandyWorker(u);

		return t;

	}
	public Collection<Tutorial> listTutorials() {
		Assert.isTrue(!this.actorservice.isActualActorBanned());
		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());
		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actual.getUserAccount().getAuthorities().contains(a));
		return this.getTutorials(actual.getUserAccount());
	}

	public Tutorial showTutorial(final int tutorialId) {
		Assert.isTrue(!this.actorservice.isActualActorBanned());
		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());

		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actual.getUserAccount().getAuthorities().contains(a));

		final Tutorial tutorial = this.ts.findOne(tutorialId);

		Assert.isTrue(this.getTutorials(actual.getUserAccount()).contains(tutorial));

		return tutorial;
	}

	public Tutorial createTutorial(final Tutorial t) {
		Assert.isTrue(!this.actorservice.isActualActorBanned());
		this.checkAuthority();

		final Tutorial res = new Tutorial();
		res.setHandyworker(t.getHandyworker());
		res.setLastUpdate(LocalDate.now().toDate());
		res.setPicture(t.getPicture());
		res.setSections(t.getSections());
		res.setSummary(t.getSummary());
		res.setTitle(t.getTitle());

		final Tutorial result = this.ts.save(res);

		return result;
	}

	public Tutorial updateTutorial(final Tutorial t) {
		Assert.isTrue(!this.actorservice.isActualActorBanned());
		this.checkAuthority();
		Assert.notNull(t);

		final Tutorial result = this.ts.save(t);

		return result;

	}

	public void deleteTutorial(final Tutorial t) {
		Assert.isTrue(!this.actorservice.isActualActorBanned());
		this.checkAuthority();
		Assert.notNull(t);
		this.ts.delete(t);
	}

	public Collection<Endorsement> listEndorsements() {
		Assert.isTrue(!this.actorservice.isActualActorBanned());
		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());
		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actual.getUserAccount().getAuthorities().contains(a));

		final Collection<Endorsement> res = new ArrayList<>();
		for (final Endorsement endor : this.endorsementService.findAll())
			if ((endor.getEndorser().equals(actual)) || (endor.getSender().equals(actual)))
				res.add(endor);

		return res;

	}

	public Endorsement showEndorsement(final int id) {

		Assert.isTrue(!this.actorservice.isActualActorBanned());
		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());
		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actual.getUserAccount().getAuthorities().contains(a));

		Endorsement res = null;

		for (final Endorsement endor : this.endorsementService.findAll())
			if ((endor.getEndorser().equals(actual)) || (endor.getSender().equals(actual)) && endor.getId() == id)
				res = endor;
		return res;
	}

	public Endorsement createEndorsement(final Customer customer, final Endorsement endor, final FixUpTask n) {

		Assert.isTrue(!this.actorservice.isActualActorBanned());
		this.checkAuthority();
		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());
		final Endorsement res = new Endorsement();

		if (customer.getFixUpTasks().contains(n)) {
			res.setComments(endor.getComments());
			res.setEndorser(customer);
			res.setMoment(LocalDate.now().toDate());
			res.setSender(actual);

		}
		final Endorsement result = this.endorsementService.save(res);
		return result;

	}
	public Endorsement updateEndorsement(final Endorsement endor) {
		Assert.isTrue(!this.actorservice.isActualActorBanned());
		this.checkAuthority();
		Assert.notNull(endor);
		final Endorsement result = this.endorsementService.save(endor);
		return result;
	}
	public void deleteEndorsement(final Endorsement endor) {
		Assert.isTrue(!this.actorservice.isActualActorBanned());
		this.checkAuthority();
		this.endorsementService.delete(endor);
	}

}
