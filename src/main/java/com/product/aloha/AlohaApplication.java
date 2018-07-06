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

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
@Controller

public class AlohaApplication {
	final
	DataRepository repository;
	
	@Autowired
	public AlohaApplication(DataRepository repository) {
		this.repository = repository;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(AlohaApplication.class, args);
	}
	
	@RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("loggedinName", "ゲスト");
		return "index";
	}
	
	
	@RequestMapping(value = {"/bootstrap"})
	public String bootstrap() {
		return "bootstrap";
	}
	
	@RequestMapping(value = {"/login"}, method = RequestMethod.POST)
	public String loginExec(Model model, @ModelAttribute("loginForm") LoginForm loginForm) {
		try {
			List<Data> data = repository.findByUserName(loginForm.getLoginUserName());
			if (data.get(0).getPassword().equals(loginForm.getLoginPassword())) {
				model.addAttribute("loggedinName", loginForm.getLoginUserName());
				return "index";
			} else {
				return "login";
			}
		} catch (Exception e) {
			return "login";
		}
	}
	
	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = {"/signup"}, method = RequestMethod.GET)
	public String signup() {
		return "signup";
	}
	
	@RequestMapping(value = {"/signup"}, method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public String signupExec(Model model, @ModelAttribute("signupForm") SignupForm signupForm) {
		Data data = new Data();
		data.setUserName(signupForm.getSighupUserName());
		data.setPassword(signupForm.getSighupPassword());
		repository.saveAndFlush(data);
		model.addAttribute("loggedinName", signupForm.getSighupUserName());
		return "index";
	}
	
	@PostConstruct
	public void init() {
		Data data = new Data();
		data.setUserName("guest");
		data.setPassword("dummy");
		repository.saveAndFlush(data);
	}
	
	@ModelAttribute("signupForm")
	public SignupForm signupForm() {
		SignupForm signupForm = new SignupForm();
		return signupForm;
	}
	
	@ModelAttribute("loginForm")
	public LoginForm loginForm() {
		LoginForm loginForm = new LoginForm();
		return loginForm;
	}
}

@RestController
class DbTest {
	@RequestMapping(value = {"/db"})
	public String db(Data data) {
		return (data.getId().toString());
	}
}
