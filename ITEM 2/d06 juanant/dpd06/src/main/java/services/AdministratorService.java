
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import repositories.ApplicationRepository;
import repositories.CustomerRepository;
import repositories.FixUpTaskRepository;
import repositories.HandyWorkerRepository;
import repositories.ReportRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import utilities.RandomString;
import domain.Actor;
import domain.Administrator;
import domain.Category;
import domain.Customer;
import domain.Endorsement;
import domain.HandyWorker;
import domain.Message;
import domain.MessageBox;
import domain.Referee;
import domain.Score;
import domain.SystemConfig;
import domain.Warranty;

@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository	administratorRepository;

	@Autowired
	private WarrantyService			warrantyService;
	@Autowired
	private CategoryService			categoryService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private FixUpTaskRepository		futs;
	@Autowired
	private ApplicationRepository	as;
	@Autowired
	private HandyWorkerRepository	hs;
	@Autowired
	private CustomerRepository		cs;
	@Autowired
	private RefereeService			rs;
	@Autowired
	private ReportRepository		rp;
	@Autowired
	private EndorsementService		es;


	public Administrator create() {
		return new Administrator();
	}
	public Collection<Administrator> findAll() {
		return this.administratorRepository.findAll();
	}
	public Administrator findOne(final int administratorId) {
		return this.administratorRepository.findOne(administratorId);
	}
	public Administrator save(final Administrator administrator) {
		return this.administratorRepository.save(administrator);
	}
	public void delete(final Administrator administrator) {
		this.administratorRepository.delete(administrator);
	}

	public void checkAuthority() {

		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.notNull(user);
		final Collection<Authority> authority = user.getAuthorities();
		Assert.notNull(authority);
		final Authority a1 = new Authority();
		a1.setAuthority(Authority.ADMIN);
		Assert.isTrue(authority.contains(a1));

	}

	public Administrator createAdmin() {
		final Administrator result;
		this.checkAuthority();

		final UserAccount userAccount = new UserAccount();
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		userAccount.addAuthority(a);
		result = this.create();
		result.setUserAccount(userAccount);

		return result;
	}

	public List<Warranty> warrantyList() {
		this.checkAuthority();
		final List<Warranty> res = (List<Warranty>) this.warrantyService.findAll();
		return res;
	}

	public Warranty showWarranty(final int warId) {
		return this.warrantyService.findOne(warId);
	}

	public Warranty createWarranty() {
		return this.warrantyService.create();
	}

	public Warranty updateWarranty(final Warranty war) {

		Assert.isTrue(war.isDraftMode());

		final Warranty result = this.warrantyService.save(war);

		return result;
	}

	public void deleteWarranty(final Warranty war) {
		Assert.isTrue(war.isDraftMode());
		this.warrantyService.delete(war);
	}

	public List<Category> categoryList() {
		final List<Category> res = (List<Category>) this.categoryService.findAll();
		return res;
	}

	public Category showCategory(final int catId) {
		return this.categoryService.findOne(catId);
	}

	public Category createCategory() {
		return this.categoryService.create();
	}

	public Category updateCategory(final Category cat) {
		final Category result = this.categoryService.save(cat);

		return result;
	}

	public void deleteCategory(final Category category) {
		this.categoryService.delete(category);
	}

	public void broadcastMessage(final Message message) {
		final List<Actor> listaActores = (List<Actor>) this.actorService.findAll();
		for (final Actor a : listaActores)
			for (final MessageBox msb : a.getMessageBoxes())
				if (msb.isSystemBox() == true && msb.getName().endsWith("in")) {
					final List<Message> messages = (List<Message>) msb.getMessages();
					messages.add(message);
					msb.setMessages(messages);
				}
	}

	public Collection<Object> displayClevelDashboard() {
		final Integer min1 = this.futs.minApplicationsByFixUpTasks();
		final Integer max1 = this.futs.minApplicationsByFixUpTasks();
		final Double avg1 = this.futs.averageApplicationsByFixUpTasks();
		final Integer d1 = this.futs.desviationApplicationsByFixUpTasks();

		final Integer min2 = this.cs.minFixUpTaskByCustomer();
		final Integer max2 = this.cs.maxFixUpTaskByCustomer();
		final Double avg2 = this.cs.averageFixUpTaskByCustomer();
		final Integer d2 = this.cs.deviationFixUpTaskByCustomer();

		final Integer min3 = this.futs.minMaximunPriceByFixUpTasks();
		final Integer max3 = this.futs.minMaximunPriceByFixUpTasks();
		final Double avg3 = this.futs.averageMaximunPriceByFixUpTasks();
		final Integer d3 = this.futs.desviationMaximumPriceByFixUpTasks();

		final Integer min4 = this.as.minOfferedPriceByFixUpTasks();
		final Integer max4 = this.as.maxOfferedPriceByFixUpTasks();
		final Double avg4 = this.as.averageOfferedPriceByFixUpTasks();
		final Integer d4 = this.as.desviationOfferedPriceByFixUpTasks();

		final Double rpending = this.as.ratioPendingAplications();

		final Double raccepted = this.as.ratioAcceptedAplications();

		final Double rrejected = this.as.ratioRejectedAplications();

		final Double relapsed = this.as.ratioPendingApplicationsSpiret();

		final Collection<Customer> c9 = this.cs.tenPersentMoreFixUpTasks();

		final Collection<HandyWorker> c10 = this.hs.tenPercentMoreAcceptedApplications();

		final Collection<Object> res = new ArrayList<Object>();

		res.add(min1);
		res.add(min2);
		res.add(min3);
		res.add(min4);
		res.add(max1);
		res.add(max2);
		res.add(max3);
		res.add(max4);
		res.add(avg1);
		res.add(avg2);
		res.add(avg3);
		res.add(avg4);
		res.add(d1);
		res.add(d2);
		res.add(d3);
		res.add(d4);
		res.add(rpending);
		res.add(raccepted);
		res.add(rrejected);
		res.add(relapsed);
		res.add(c9);
		res.add(c10);

		return res;

	}

	public Referee createRefereeAccount(final Referee r) {
		this.checkAuthority();

		this.checkAuthority();

		final Referee result = this.rs.create();
		Assert.notNull(r);
		result.setAddress(r.getAddress());
		result.setEmail(r.getEmail());
		result.setIsBanned(r.getIsBanned());
		result.setIsSuspicious(r.getIsSuspicious());
		result.setMessageBoxes(r.getMessageBoxes());
		result.setMiddleName(r.getMiddleName());
		result.setName(r.getName());
		result.setPhone(r.getPhone());
		result.setPhoto(r.getPhoto());
		result.setProfiles(r.getProfiles());
		result.setUserAccount(r.getUserAccount());

		final Referee resultado = this.rs.save(result);

		return resultado;
	}

	public Collection<Actor> getListOfSuspiciousActors() {
		this.checkAuthority();
		final Collection<Actor> iterable = this.actorService.findAll();
		final Collection<Actor> result = new ArrayList<Actor>();
		for (final Actor a : iterable)
			if (a.getIsSuspicious())
				result.add(a);
		return result;
	}

	public Actor banActor(final Actor a) {
		this.checkAuthority();
		if (!a.getIsBanned() && a.getIsSuspicious())
			a.setIsBanned(true);
		final Actor result = this.actorService.save(a);

		return result;
	}

	public Actor unbanActor(final Actor a) {
		this.checkAuthority();
		if (a.getIsBanned())
			a.setIsBanned(false);
		final Actor result = this.actorService.save(a);

		return result;
	}

	public Collection<Object> displayBlevelDashboard() {
		final Collection<Object> res = new ArrayList<Object>();

		final Map<Integer, Integer> topCustomers = this.cs.topThreeCustomers();
		final Map<Integer, Integer> topHandyWorkers = this.hs.topThreeHandyWorkers();

		final Integer min1 = this.futs.minComplaintsByFixUpTasks();
		final Integer max1 = this.futs.maxComplaintsByFixUpTasks();
		final Double avg1 = this.futs.averageComplaintsByFixUpTasks();
		final Integer desv1 = this.futs.desviationComplaintsByFixUpTasks();
		final Integer min2 = this.rp.minNotesByReports();
		final Integer max2 = this.rp.maxNotesByReports();
		final Double avg2 = this.rp.averageNotesByReports();
		final Integer desv2 = this.rp.desviationNotesByReports();
		final Double rat3 = this.futs.ratioOneComplaintForFixUpTasks();

		res.add(min1);
		res.add(max1);
		res.add(avg1);
		res.add(desv1);
		res.add(min2);
		res.add(max2);
		res.add(avg2);
		res.add(desv2);
		res.add(rat3);
		res.add(topCustomers);
		res.add(topHandyWorkers);

		return res;
	}

	public String generateTicker() {//ARREGLAR SIGUIENDO PATRON "DDMMYY-ABCDEF"
		String ticker = "";
		final Date actualDate = new Date();
		String dd = String.valueOf(actualDate.getDay());
		String mm = String.valueOf(actualDate.getMonth() + 1);
		final String yyyy = String.valueOf(actualDate.getYear());
		final String yy = yyyy.substring(1);

		if (dd.length() == 1)
			dd = "0" + dd;
		if (mm.length() == 1)
			mm = "0" + mm;

		ticker = ticker + dd + mm + yy + "-";
		final RandomString random = new RandomString();

		final String s = random.nextString();
		final String alphanum = s.substring(0, 6);

		ticker = ticker + alphanum;

		return ticker;
	}
	public List<String> getBadWords() {
		final SystemConfig configuration = this.administratorRepository.returnSystemConfig().get(0);
		return configuration.getBadWords();
	}

	public List<String> addBadWord(final String word) {
		final SystemConfig configuration = this.administratorRepository.returnSystemConfig().get(0);
		configuration.getBadWords().add(word);
		return configuration.getBadWords();
	}

	public void deleteBadWord(final String word) {
		final SystemConfig configuration = this.administratorRepository.returnSystemConfig().get(0);
		configuration.getBadWords().remove(word);
	}

	public List<String> getGoodWords() {
		final SystemConfig configuration = this.administratorRepository.returnSystemConfig().get(0);
		return configuration.getBadWords();
	}

	public List<String> addGoodWord(final String word) {
		final SystemConfig configuration = this.administratorRepository.returnSystemConfig().get(0);
		configuration.getGoodWords().add(word);
		return configuration.getGoodWords();
	}

	public void deleteGoodWord(final String word) {
		final SystemConfig configuration = this.administratorRepository.returnSystemConfig().get(0);
		configuration.getGoodWords().remove(word);
	}

	public Map<Actor, Score> calculateAllActorsScores() { //void
		final Map<Actor, Score> allscores = new HashMap<Actor, Score>();
		final List<Endorsement> endorsements = (List<Endorsement>) this.es.findAll();

		for (final Endorsement e : endorsements) {
			final Double newscore = e.calculateScore().getNumericScore();
			if (allscores.containsKey(e.getEndorser())) {
				final Double oldscore = allscores.get(e.getEndorser()).getNumericScore();
				final Double oldplusnew = (oldscore + newscore) / 2;

				final Score putscore = new Score();
				putscore.setNumericScore(oldplusnew);

				allscores.put(e.getEndorser(), putscore);

			} else {

				final Score putscore = new Score();
				putscore.setNumericScore(newscore);

				allscores.put(e.getEndorser(), putscore);

			}
		}

		return allscores;
	}

}
