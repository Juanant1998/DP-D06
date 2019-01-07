
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.FixUpTaskRepository;
import security.LoginService;
import domain.Customer;
import domain.FixUpTask;

@Service
@Transactional
public class FixUpTaskService {

	@Autowired
	private FixUpTaskRepository		fixUpTaskRepository;

	@Autowired
	private AdministratorService	as;

	@Autowired
	private UserAccountService		uas;


	public FixUpTask create() {

		final FixUpTask f = new FixUpTask();
		f.setMoment(new Date());
		f.setTicker(this.as.generateTicker());
		return f;
	}
	public Collection<FixUpTask> findAll() {
		return this.fixUpTaskRepository.findAll();
	}
	public FixUpTask findOne(final int fixUpTaskId) {
		return this.fixUpTaskRepository.findOne(fixUpTaskId);
	}
	public FixUpTask save(final FixUpTask fixUpTask) {
		final Customer actual = this.uas.getCustomerByUserAccount(LoginService.getPrincipal());

		final FixUpTask result = this.fixUpTaskRepository.save(fixUpTask);
		final Collection<FixUpTask> futs = actual.getFixUpTasks();
		if (!futs.contains(result)) {
			futs.add(result);
			actual.setFixUpTasks(futs);
		}
		return result;
	}
	public void delete(final FixUpTask fixUpTask) {
		this.fixUpTaskRepository.delete(fixUpTask);
	}
}
