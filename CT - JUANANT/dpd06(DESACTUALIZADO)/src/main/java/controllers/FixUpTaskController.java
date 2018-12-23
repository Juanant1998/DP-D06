
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FixUpTaskService;
import domain.FixUpTask;

@Controller
@RequestMapping("/fixuptask")
public class FixUpTaskController extends AbstractController {

	@Autowired
	private FixUpTaskService	futs;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;

		final Collection<FixUpTask> col = this.futs.findAll();

		result = new ModelAndView("fixuptask/list");

		System.out.println(col);
		result.addObject("fixuptasks", col);

		result.addObject("requestURI", "/fixuptask/handyworker/list.do");

		return result;

	}
}
