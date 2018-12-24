
package services;

import java.util.ArrayList;
import java.util.Collection;

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
import domain.Complaint;
import domain.Customer;
import domain.Endorsement;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.MessageBox;
import domain.Note;
import domain.Report;

@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerRepository		customerRepository;

	@Autowired
	private NoteService				ns;

	@Autowired
	private UserAccountService		uas;

	@Autowired
	private ApplicationService		as;

	@Autowired
	private ActorService			actorservice;

	@Autowired
	private ReportService			rs;
	@Autowired
	private ComplaintService		complaintService;
	@Autowired
	private EndorsementService		endorsementService;
	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;


	public Customer create() {
		return new Customer();
	}
	public Collection<Customer> findAll() {
		return this.customerRepository.findAll();
	}
	public Customer findOne(final int customerId) {
		return this.customerRepository.findOne(customerId);
	}
	public Customer save(final Customer customer) {
		return this.customerRepository.save(customer);
	}
	public void delete(final Customer customer) {
		this.customerRepository.delete(customer);
	}
	public void checkAuthority() {

		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.notNull(user);
		final Collection<Authority> authority = user.getAuthorities();
		Assert.notNull(authority);
		final Authority a1 = new Authority();
		a1.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(authority.contains(a1));

	}


	@Autowired
	private MessageBoxService	mbs;


	public Customer register(final Customer c1) {
		Assert.notNull(c1);

		final Collection<UserAccount> uss = this.uas.findAll();

		//Assert.isTrue(!(uss.contains(c1.getUserAccount())));
		final Customer c2 = this.create();
		c2.setAddress(c1.getAddress());
		c2.setEmail(c1.getEmail());
		c2.setFixUpTasks(new ArrayList<FixUpTask>());
		c2.setMiddleName(c1.getMiddleName());
		c2.setName(c1.getName());
		c2.setPhone(c1.getPhone());
		c2.setPhoto(c1.getPhoto());
		c2.setIsSuspicious(c1.getIsSuspicious());
		c2.setIsBanned(c1.getIsBanned());

		final MessageBox in = this.actorservice.createNewMessageBox(c1.getUserAccount().getUsername(), "-in");
		final MessageBox out = this.actorservice.createNewMessageBox(c1.getUserAccount().getUsername(), "-out");
		final MessageBox trash = this.actorservice.createNewMessageBox(c1.getUserAccount().getUsername(), "-trash");
		final MessageBox spam = this.actorservice.createNewMessageBox(c1.getUserAccount().getUsername(), "-spam");

		final Collection<MessageBox> msboxes = new ArrayList<MessageBox>();
		msboxes.add(in);
		msboxes.add(out);
		msboxes.add(trash);
		msboxes.add(spam);

		c2.setMessageBoxes(msboxes);
		final Customer x = this.save(c2);

		return x;

	}

	public Collection<FixUpTask> getMyFixUpTasks() {
		Assert.isTrue(!this.actorservice.isActualActorBanned());
		final UserAccount actual = LoginService.getPrincipal();
		Assert.notNull(actual);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(actual.getAuthorities().contains(auth));

		//is account banned?

		final Customer c = this.uas.getCustomerByUserAccount(actual);

		return c.getFixUpTasks();

	}

	public Collection<Application> getApplicationsForFUT(final FixUpTask fut) {
		Assert.isTrue(!this.actorservice.isActualActorBanned());
		final Collection<Application> result = fut.getApplications();

		return result;
	}

	public Application updateApplication(final Application app, final String status, final String comments) {
		Assert.isTrue(!this.actorservice.isActualActorBanned());
		final Application appcopia = app;
		app.setStatus(status);

		if (status == "ACCEPTED" && app.getCreditCard().equals(null))
			app.setComments(appcopia.getComments() + "\n you must provide a valid credit card number in order to perform the task.");
		final Application result = this.as.save(app);

		if (comments != null)
			app.setComments(appcopia.getComments() + "\n Customer comments : " + comments);

		return result;
	}

	public Collection<Complaint> getMyComplaints() {

		final Customer actual = this.uas.getCustomerByUserAccount(LoginService.getPrincipal());
		System.out.println(actual);
		System.out.println(actual.getIsBanned());
		Assert.isTrue(true);
		final Collection<FixUpTask> fixuptasks = actual.getFixUpTasks();
		final Collection<Complaint> result = new ArrayList<Complaint>();
		for (final FixUpTask f : fixuptasks) {
			final Collection<Complaint> c = f.getComplaints();
			for (final Complaint x : c)
				result.add(x);
		}
		return result;
	}

	public Note writeNoteForReport(final Note n, final Report r) {
		final Customer actual = this.uas.getCustomerByUserAccount(LoginService.getPrincipal());
		Assert.isTrue(!actual.getIsBanned());
		final Collection<Note> notes = r.getNotes();
		notes.add(n);
		r.setNotes(notes);
		final Note result = this.ns.save(n);
		this.rs.save(r);

		return result;
	}
	public void createComplaintByCustomer(final Complaint t, final FixUpTask fut) {

		UserAccount user;
		user = LoginService.getPrincipal();

		final Customer kast = this.uas.getCustomerByUserAccount(user);
		final Collection<FixUpTask> futs = kast.getFixUpTasks();
		if (futs.contains(fut)) {
			final Collection<Complaint> complaints = fut.getComplaints();
			complaints.add(t);

			fut.setComplaints(complaints);

		}

	}

	public Note writeCommentOnNote(final Note n, final String comments) {

		final Customer actual = this.uas.getCustomerByUserAccount(LoginService.getPrincipal());
		Assert.isTrue(!actual.getIsBanned());

		n.setComment(n.getComment() + "Comentario de " + LoginService.getPrincipal().getUsername() + ": " + comments);
		final Note result = this.ns.save(n);

		return result;

	}

	///A
	public Collection<Endorsement> getEndorsments() {
		Assert.isTrue(!this.actorservice.isActualActorBanned());

		final Collection<Endorsement> res = new ArrayList<>();
		final Customer actual = this.uas.getCustomerByUserAccount(LoginService.getPrincipal());
		Assert.isTrue(!actual.getIsBanned());

		final Collection<Endorsement> prueba = this.endorsementService.findAll();
		for (final Endorsement a : this.endorsementService.findAll())
			if ((a.getEndorser().equals(actual)) || (a.getSender().equals(actual)))
				res.add(a);

		return res;
	}
	public Endorsement getEndorsement(final int id) {
		Assert.isTrue(!this.actorservice.isActualActorBanned());
		final Customer actual = this.uas.getCustomerByUserAccount(LoginService.getPrincipal());

		Endorsement res = null;

		for (final Endorsement a : this.endorsementService.findAll())
			if ((a.getEndorser().equals(actual)) || (a.getSender().equals(actual)) && a.getId() == id)
				res = a;
		return res;
	}
	public Endorsement createEndorsement(final HandyWorker handy, final Endorsement endor, final FixUpTask n) {
		Assert.isTrue(!this.actorservice.isActualActorBanned());

		final Customer actual = this.uas.getCustomerByUserAccount(LoginService.getPrincipal());
		this.checkAuthority();
		final Endorsement res = new Endorsement();

		if (this.handyWorkerRepository.getFixUpTaskByHandyWorker(handy.getUserAccount()).contains(n)) {
			res.setComments(endor.getComments());
			res.setEndorser(handy);
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
		this.endorsementService.delete(endor);
	}

}
