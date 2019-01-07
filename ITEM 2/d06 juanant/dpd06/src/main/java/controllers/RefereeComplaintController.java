
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import domain.Complaint;

@Controller
@RequestMapping("complaints/referee")
public class RefereeComplaintController {

	@Autowired
	private ComplaintService	cs;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;

		final Collection<Complaint> col = this.cs.findAll();

		result = new ModelAndView("complaints/list");

		System.out.println(col);
		result.addObject("complaints", col);

		result.addObject("requestURI", "/complaints/referee/list.do");

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
