
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

import services.CategoryService;
import services.CustomerService;
import services.FixUpTaskService;
import services.WarrantyService;
import domain.Category;
import domain.FixUpTask;
import domain.Warranty;

@Controller
@RequestMapping("/fixuptask/customer")
public class CustomerFixUpTaskController {

	// Constructors -----------------------------------------------------------

	public CustomerFixUpTaskController() {
		super();
	}


	@Autowired
	private FixUpTaskService	futs;

	@Autowired
	private CustomerService		cs;

	@Autowired
	private WarrantyService		ws;

	@Autowired
	private CategoryService		catservice;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;

		final Collection<FixUpTask> col = this.cs.getMyFixUpTasks();

		result = new ModelAndView("fixuptask/list");

		System.out.println(col);
		result.addObject("fixuptasks", col);

		result.addObject("requestURI", "/fixuptask/customer/list.do");

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int fixuptaskId) {
		ModelAndView result;
		FixUpTask fut;

		fut = this.futs.findOne(fixuptaskId);
		Assert.notNull(fut);
		result = this.createEditModelAndView(fut);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final FixUpTask f, final BindingResult binding) {
		ModelAndView result;
		//TESTING: DELETE LINES BETWEEN
		System.out.println(f.getCategory());
		System.out.println(f.getWarranty());
		System.out.println(f.getStartDate());
		System.out.println(f.getEndDate());
		System.out.println(f.getAddress());
		System.out.println(f.getDescription());
		System.out.println(f.getTicker());
		System.out.println(f.getApplications());
		System.out.println(f.getComplaints());
		System.out.println(f.getPhases());
		System.out.println(f.getMaximumPrice());
		System.out.println(f.getMoment());

		//TESTING: DELETE LINES BETWEEN
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(f);
			System.out.println("Errores en el formulario");
		} else
			try {
				this.futs.save(f);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(f, "fixuptask.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final FixUpTask fut) {
		ModelAndView result;
		result = this.createEditModelAndView(fut, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FixUpTask fut, final String messageCode) {
		ModelAndView result;
		final Collection<Category> categories = this.catservice.findAll();
		final Collection<Warranty> warranties = this.ws.findAll();

		result = new ModelAndView("fixuptask/edit");
		result.addObject("fixuptask", fut);
		result.addObject("message", messageCode);
		result.addObject("warrantyList", warranties);
		result.addObject("categoryList", categories);

		return result;
	}
}
