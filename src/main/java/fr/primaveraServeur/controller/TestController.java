package fr.primaveraServeur.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController
{
	
	@RequestMapping(value = "/accueilRiverain", method = RequestMethod.GET)
	public ModelAndView accueilRiverain() {
		
		ModelAndView model = new ModelAndView();
        model.addObject("title", "Accueil Riverain");
        model.addObject("message", "Cette page est accessible seulement par les riverains!");
        model.setViewName("accueilRiverain");
        
        return model;
	}
	
	@RequestMapping(value = "/accueilInspecteur", method = RequestMethod.GET)
	public ModelAndView accueilInspecteur() {
		
		ModelAndView model = new ModelAndView();
        model.addObject("title", "Accueil Riverain");
        model.addObject("message", "Cette page est accessible seulement par les riverains!");
        model.setViewName("accueilInspecteur");
        
        return model;
	}
	
	@RequestMapping(value = "/accueilBTP", method = RequestMethod.GET)
	public ModelAndView accueilBTP() {
		
		ModelAndView model = new ModelAndView();
        model.addObject("title", "Accueil Riverain");
        model.addObject("message", "Cette page est accessible seulement par les riverains!");
        model.setViewName("accueilBTP"); 
        
        return model;
	}
}
