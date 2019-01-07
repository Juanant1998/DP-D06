
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ApplicationRepository;
import security.Authority;
import security.LoginService;
import domain.Application;
import domain.CreditCard;
import domain.FixUpTask;
import domain.HandyWorker;

@Service
@Transactional
public class ApplicationService {

	@Autowired
	private ApplicationRepository	applicationRepository;

	@Autowired
	private UserAccountService		uas;

	@Autowired
	private CreditCardService		ccs;

	@Autowired
	private FixUpTaskService		futs;


	public Application create() {
		final Application a = new Application();
		a.setMoment(new Date());
		return a;
	}
	public Collection<Application> findAll() {
		return this.applicationRepository.findAll();
	}
	public Application findOne(final int applicationId) {
		return this.applicationRepository.findOne(applicationId);
	}
	public Application save(final Application application) {
		final Authority auth = new Authority();
		auth.setAuthority(Authority.HANDYWORKER);

		HandyWorker actual = null;
		if (LoginService.getPrincipal().getAuthorities().contains(auth))
			actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());
		if (!this.ccs.findAll().contains(application.getCreditCard())) {
			final CreditCard newCreditCard = this.ccs.save(application.getCreditCard());
			application.setCreditCard(newCreditCard);
		}

		//GUARDA APLICACIÓN
		System.out.println("llega hasta guardar la aplicación");
		final Application a = this.applicationRepository.save(application);
		//FIN DEL GUARDADO DE LA APLICACION
		System.out.println("guarda la aplicacion");

		if (LoginService.getPrincipal().getAuthorities().contains(auth) && actual != null)
			if (!actual.getApplications().contains(a)) {
				System.out.println("llega al if de actual");

				final Collection<Application> lista = actual.getApplications();
				lista.add(a);
				actual.setApplications(lista);
			}
		System.out.println("lo pasa y retorna");
		return a;
	}
	public void delete(final Application application) {
		this.applicationRepository.delete(application);
	}
	public void relateFixUpTaskToApplication(final int fixuptaskId, final Application savedapplication) {
		final FixUpTask fixuptask = this.futs.findOne(fixuptaskId);
		final Collection<Application> applications = fixuptask.getApplications();
		applications.add(savedapplication);

	}

}
