
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import services.CustomerService;
import domain.Complaint;

@Controller
@RequestMapping("/complaints")
public class ComplaintController extends AbstractController {

	@Autowired
	private ComplaintService	compservice;

	@Autowired
	private CustomerService		cs;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;

		final Collection<Complaint> col = this.cs.getMyComplaints();

		result = new ModelAndView("complaints/list");

		System.out.println(col);
		result.addObject("complaints", col);

		result.addObject("requestURI", "/complaints/list.do");

		return result;

	}
}
