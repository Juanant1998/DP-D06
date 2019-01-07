package controllers.handyWorker;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FixUpTaskService;
import services.HandyWorkerService;
import services.PhaseService;

import controllers.AbstractController;

import domain.FixUpTask;
import domain.Phase;

@Controller
@RequestMapping("/phase/handyworker")
public class PhaseHandyWorkerController extends AbstractController{
	
	@Autowired
	private HandyWorkerService handyWorkerService;
	
	@Autowired
	private PhaseService phaseService;
	
	@Autowired
	private FixUpTaskService futService;
	
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list(@RequestParam int futId){
		ModelAndView result;
		Collection<Phase> phases;
		FixUpTask fixUpTask; //Obtener la fut correspondiente a esta lista de phases
		
		fixUpTask = this.futService.findOne(futId);
		phases = handyWorkerService.findPhases(fixUpTask);
		
		result = new ModelAndView("phases/list");
		result.addObject("phases", phases);
		result.addObject("requestURI", "application/handyworker/list.do");
		
		return result;
	}
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView create(){
		
		ModelAndView result;
		Phase phase;
		
		phase = this.phaseService.create();
		result = this.createEditModelAndView(phase);
		
		return result;
	}
	

	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam int phaseId){
		ModelAndView result;
		Phase phase;
		
		phase = this.phaseService.findOne(phaseId);
		Assert.notNull(phase);
		
		result = this.createEditModelAndView(phase);
		
		return result;
	}

	
	
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Phase phase, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = this.createEditModelAndView(phase);
		} else{
			try {
				this.phaseService.save(phase);
				result = new ModelAndView("redirect:list.do");
			} catch(Throwable oops){
				result = this.createEditModelAndView(phase, "phase.commit.error");
			}
		}
		
		
		return result;
	}
	
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params = "delete")
	public ModelAndView delete(Phase phase, BindingResult binding){
		
		ModelAndView result;
		
		try{
			phaseService.delete(phase);
			result = new ModelAndView("redirect:list.do");
		} catch (Throwable oops){
			result = this.createEditModelAndView(phase, "phase.commit.error");
		}
		
		
		return result;
	}
	
	
	

	protected ModelAndView createEditModelAndView(Phase phase) {
		ModelAndView result;
		
		result = this.createEditModelAndView(phase, null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Phase phase, String messageCode) {
		ModelAndView result;
		
		
		result = new ModelAndView("phase/edit");
		result.addObject("phase", phase);
		
		result.addObject("message", messageCode);
		

		return result;
	}
	
	

}
