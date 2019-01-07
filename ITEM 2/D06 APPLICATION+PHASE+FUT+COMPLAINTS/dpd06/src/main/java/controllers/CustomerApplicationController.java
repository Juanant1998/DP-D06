
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ApplicationService;
import services.FixUpTaskService;
import services.UserAccountService;
import domain.Application;
import domain.CreditCard;
import domain.Customer;
import domain.FixUpTask;

@Controller
@RequestMapping("applications/customer")
public class CustomerApplicationController {

	@Autowired
	private UserAccountService	uas;

	@Autowired
	private FixUpTaskService	futs;

	@Autowired
	private ApplicationService	as;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int futId) {

		ModelAndView result;

		final Customer customer = this.uas.getCustomerByUserAccount(LoginService.getPrincipal());

		final FixUpTask fut = this.futs.findOne(futId);

		Assert.isTrue(customer.getFixUpTasks().contains(fut));

		final Collection<Application> col = fut.getApplications();

		result = new ModelAndView("applications/list");

		System.out.println(col);
		result.addObject("applications", col);

		result.addObject("requestURI", "/applications/customer/list.do");

		return result;

	}

	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam final int applicationId) {
		ModelAndView result;

		final Application a = this.as.findOne(applicationId);

		a.setStatus("REJECTED");

		this.as.save(a);

		result = new ModelAndView("fixuptask/list");

		return result;
	}

	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;

		application = this.as.findOne(applicationId);
		Assert.notNull(application);
		application.setCreditCard(new CreditCard());
		application.setStatus("ACCEPTED");

		result = this.createEditModelAndView(application);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Application app, final BindingResult binding) {
		ModelAndView result;
		System.out.println("==============TESTING====================");
		System.out.println(app.getMoment());
		System.out.println(app.getStatus());
		System.out.println(app.getCreditCard());
		System.out.println(app.getOfferedPrice());
		System.out.println(app.getId());
		System.out.println(app.getVersion());

		System.out.println("==============TESTING====================");

		if (binding.hasErrors()) {
			System.out.println("form has errors");

			result = this.createEditModelAndView(app);
		} else
			try {
				this.as.save(app);
				System.out.println("ha pasado el save");
				result = new ModelAndView("fixuptask/list");
			} catch (final Throwable oops) {
				System.out.println("hay una excepcion");
				result = this.createEditModelAndView(app, "application.commit.error");
			}
		return result;
	}
	protected ModelAndView createEditModelAndView(final Application c) {
		ModelAndView result;
		result = this.createEditModelAndView(c, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Application c, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("applications/edit");
		result.addObject("application", c);

		result.addObject("message", messageCode);

		return result;

	}
}
