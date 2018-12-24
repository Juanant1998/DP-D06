
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

import services.ComplaintService;
import domain.Complaint;

@Controller
@RequestMapping("complaints/customer")
public class CustomerComplaintController {

	@Autowired
	private ComplaintService	cs;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int complaintId) {
		ModelAndView result;
		Complaint complaint;

		complaint = this.cs.findOne(complaintId);
		Assert.notNull(complaint);

		result = this.createEditModelAndView(complaint);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Complaint complaint, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(complaint);
		else
			try {
				this.cs.save(complaint);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(complaint, "complaint.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int complaintId) {
		ModelAndView result;
		Complaint complaint;

		complaint = this.cs.findOne(complaintId);
		Assert.notNull(complaint);

		result = this.createDisplayModelAndView(complaint);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Complaint c) {
		ModelAndView result;
		result = this.createEditModelAndView(c, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Complaint c, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("complaints/edit");
		result.addObject("complaint", c);

		result.addObject("message", messageCode);

		return result;

	}

	protected ModelAndView createDisplayModelAndView(final Complaint c) {
		ModelAndView result;
		result = this.createDisplayModelAndView(c, null);

		return result;
	}

	protected ModelAndView createDisplayModelAndView(final Complaint c, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("complaints/display");
		result.addObject("complaint", c);

		result.addObject("message", messageCode);

		return result;

	}
}
