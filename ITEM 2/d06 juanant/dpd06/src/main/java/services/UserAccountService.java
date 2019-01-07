
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import security.UserAccount;
import security.UserAccountRepository;
import domain.Customer;
import domain.HandyWorker;

@Service
@Transactional
public class UserAccountService {

	@Autowired
	private UserAccountRepository	uas;


	public UserAccount create() {
		return new UserAccount();
	}
	public Collection<UserAccount> findAll() {
		return this.uas.findAll();
	}

	public UserAccount findOne(final int uaId) {
		return this.uas.findOne(uaId);
	}

	public UserAccount save(final UserAccount ua) {
		return this.uas.save(ua);
	}

	public void delete(final UserAccount ua) {
		this.uas.delete(ua);
	}

	public HandyWorker getHandyByUserAccount(final UserAccount useracc) {
		String ua = useracc.getUsername();
		final HandyWorker a = this.uas.getHandyByUserAccount(ua);
		return a;
	}

	public Customer getCustomerByUserAccount(final UserAccount useracc) {
		final Customer c = this.uas.getCustomerByUserAccount(useracc.getUsername());
		return c;

	}

}
