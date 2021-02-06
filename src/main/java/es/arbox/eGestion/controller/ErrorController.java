package es.arbox.eGestion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController extends BaseController{

	@GetMapping("/")
	public String login(Model model) {
	    return "error";
	}
}
