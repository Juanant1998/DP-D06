
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
import services.CreditCardService;
import services.FixUpTaskService;
import services.UserAccountService;
import domain.Application;
import domain.CreditCard;
import domain.HandyWorker;

@Controller
@RequestMapping("applications/handyworker")
public class HandyworkerApplicationController {

	@Autowired
	private ApplicationService	as;

	@Autowired
	private UserAccountService	uas;

	@Autowired
	private CreditCardService	ccs;
	@Autowired
	private FixUpTaskService	futs;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;

		final HandyWorker handy = this.uas.getHandyByUserAccount(LoginService.getPrincipal());

		final Collection<Application> col = handy.getApplications();

		result = new ModelAndView("applications/list");

		System.out.println(col);
		result.addObject("applications", col);

		result.addObject("requestURI", "/applications/handyworker/list.do");

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;

		application = this.as.findOne(applicationId);
		Assert.notNull(application);

		result = this.createEditModelAndView(application);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int futId) {
		ModelAndView result;

		Application application;

		final CreditCard cc = this.ccs.findOne(1358);

		application = this.as.create();

		application.setStatus("PENDING");
		application.setId(futId);
		application.setCreditCard(cc);
		result = this.createCreateModelAndView(application);

		result.addObject("futId", futId);

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
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				System.out.println("hay una excepcion");
				result = this.createEditModelAndView(app, "application.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/savefut", method = RequestMethod.POST, params = "save")
	public ModelAndView savefut(@Valid final Application app, final BindingResult binding) {
		ModelAndView result;
		System.out.println("==============TESTING THE SAVEFUT====================");
		System.out.println(app.getMoment());
		System.out.println(app.getStatus());
		System.out.println(app.getCreditCard());
		System.out.println(app.getOfferedPrice());
		System.out.println(app.getId());
		System.out.println(app.getVersion());

		System.out.println("==============TESTING THE SAVEFUT====================");

		if (binding.hasErrors()) {
			System.out.println("form has errors");

			result = this.createCreateModelAndView(app);
		} else
			try {
				final int fixuptaskId = app.getId();
				app.setId(0);
				final Application savedapplication = this.as.save(app);

				this.as.relateFixUpTaskToApplication(fixuptaskId, savedapplication);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				System.out.println("hay una excepcion");
				result = this.createCreateModelAndView(app, "application.commit.error");
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

	protected ModelAndView createCreateModelAndView(final Application c) {
		ModelAndView result;
		result = this.createCreateModelAndView(c, null);

		return result;
	}

	protected ModelAndView createCreateModelAndView(final Application c, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("applications/create");
		result.addObject("application", c);

		result.addObject("message", messageCode);

		return result;

	}
}
