package com.product.aloha;

import com.product.aloha.repositories.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;

@SpringBootApplication
@Controller

public class AlohaApplication {
	@Autowired
	DataRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(AlohaApplication.class, args);
	}
	
	@RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("loginName", "ゲスト");
		return "index";
	}
	
	
	@RequestMapping(value = {"/bootstrap"})
	public String bootstrap() {
		return "bootstrap";
	}
	
	@RequestMapping(value = {"/login"}, method = RequestMethod.POST)
	public String loginExec(Model model, @ModelAttribute("loginForm") LoginForm loginForm) {
		model.addAttribute("loginName", loginForm.getLoginUserName());
		return "index";
	}
	
	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = {"/signup"}, method = RequestMethod.GET)
	public String signUp() {
		return "signup";
	}
	
	@RequestMapping(value = {"/signup"}, method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public String signupExec(Model model, @ModelAttribute("signupForm") SignupForm signupForm, ModelAndView mav) {
		Data data = new Data();
		data.setUserName(signupForm.getSighupUserName());
		data.setPassword(signupForm.getSighupPassword());
		repository.saveAndFlush(data);
		model.addAttribute("loginName", data.getUserName());
		return "index";
	}
	
	@PostConstruct
	public void init() {
		Data data = new Data();
		data.setUserName("guest");
		data.setPassword("dummy");
		repository.saveAndFlush(data);
	}
}

@RestController
class DbTest {
	@RequestMapping(value = {"/db"})
	public String db(Data data) {
		return (data.getId().toString());
	}
}
