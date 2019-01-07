
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import services.PhaseService;
import domain.Application;
import domain.FixUpTask;
import domain.Phase;

@Controller
@RequestMapping("/phases/handyworker")
public class HandyworkerPhaseController extends AbstractController {

	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private PhaseService		phaseService;

	@Autowired
	private FixUpTaskService	futService;

	@Autowired
	private ApplicationService	as;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int applicationId) {
		ModelAndView result;

		final Application actual = this.as.findOne(applicationId);
		final FixUpTask fut = this.handyWorkerService.getFixUpTaskByApplication(actual);
		Assert.notNull(fut);
		result = new ModelAndView("phases/list");
		result.addObject("phases", fut.getPhases());
		result.addObject("applicationId", applicationId);

		result.addObject("requestURI", "applications/handyworker/list.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int applicationId) {

		ModelAndView result;
		Phase phase;

		phase = this.phaseService.create();
		phase.setId(applicationId);
		result = this.createCreateModelAndView(phase);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int phaseId) {
		ModelAndView result;
		Phase phase;

		phase = this.phaseService.findOne(phaseId);
		Assert.notNull(phase);

		result = this.createEditModelAndView(phase);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Phase phase, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(phase);
		else
			try {
				this.phaseService.save(phase);
				result = new ModelAndView("redirect:list.do?applicationId=1517");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(phase, "phase.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveNew(@Valid final Phase phase, final BindingResult binding) {
		ModelAndView result;
		final int applicationId = phase.getId();
		phase.setId(0);
		final Application application = this.as.findOne(applicationId);
		final FixUpTask f = this.handyWorkerService.getFixUpTaskByApplication(application);
		if (binding.hasErrors())
			result = this.createCreateModelAndView(phase);
		else
			try {
				final Phase guardar = this.phaseService.save(phase);
				this.futService.addPhasesToFUT(f, guardar);
				result = new ModelAndView("redirect:list.do?applicationId=1517");
			} catch (final Throwable oops) {
				result = this.createCreateModelAndView(phase, "phase.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Phase phase, final BindingResult binding) {

		ModelAndView result;

		try {
			this.futService.deletePhasesToFUT(phase);
			this.phaseService.delete(phase);
			result = new ModelAndView("redirect:list.do?applicationId=1517");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(phase, "phase.commit.error");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Phase phase) {
		ModelAndView result;

		result = this.createEditModelAndView(phase, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Phase phase, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("phases/edit");
		result.addObject("phase", phase);

		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createCreateModelAndView(final Phase phase) {
		ModelAndView result;

		result = this.createCreateModelAndView(phase, null);

		return result;
	}

	protected ModelAndView createCreateModelAndView(final Phase phase, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("phases/create");
		result.addObject("phase", phase);

		result.addObject("message", messageCode);

		return result;
	}

}
