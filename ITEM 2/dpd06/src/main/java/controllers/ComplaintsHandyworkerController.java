
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import services.HandyWorkerService;
import domain.Complaint;

@Controller
@RequestMapping("/complaints/handyworker")
public class ComplaintsHandyworkerController extends AbstractController {

	@Autowired
	private ComplaintService	cs;

	@Autowired
	private HandyWorkerService	hs;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;

		final Collection<Complaint> col = this.hs.getHandyworkerComplaints();

		result = new ModelAndView("complaints/list");

		System.out.println(col);
		result.addObject("complaints", col);

		result.addObject("requestURI", "/complaints/handyworker/list.do");

		return result;

	}
}
