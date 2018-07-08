package com.product.aloha;

import com.product.aloha.repositories.DataRepository;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
@Controller

public class AlohaApplication {
	
	@Autowired
	LoginUserSession loginUserSession;
	
	final DataRepository repository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	public AlohaApplication(DataRepository repository) {
		this.repository = repository;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(AlohaApplication.class, args);
	}
	
	@RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
	public String index(Model model) {
		if(!loginUserSession.isLoggedIn()) {
			model.addAttribute("loggedinName", "ゲスト");
			return "index";
		}else{
			model.addAttribute("loggedinName", loginUserSession.getLoggedInName());
			return "index";
		}
	}
	
	
	@RequestMapping(value = {"/bootstrap"})
	public String bootstrap() {
		return "bootstrap";
	}
	
	@RequestMapping(value = {"/login"}, method = RequestMethod.POST)
	public String loginExec(Model model, @ModelAttribute("loginForm") @Validated LoginForm loginForm, BindingResult result) {
		String safeLoginUserName = HtmlUtils.htmlEscape(loginForm.getLoginUserName());
		String safeLoginPassword = HtmlUtils.htmlEscape(loginForm.getLoginPassword());
		if (!result.hasErrors()) {
			if (loginAuth(safeLoginUserName, safeLoginPassword)) {
				return "redirect:/index";
			} else {
				return "login";
			}
		} else {
			return "login";
		}
	}
	
	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	@RequestMapping(value = {"/logout"})
	public String logout() {
		loginUserSession = new LoginUserSession();
		return "redirect:/index";
	}
	
	@RequestMapping(value = {"/signup"}, method = RequestMethod.GET)
	public String signup() {
		return "signup";
	}
	
	@RequestMapping(value = {"/signup"}, method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public String signupExec(Model model, @ModelAttribute("signupForm") @Validated SignupForm signupForm, BindingResult result) {
		String safeSignupUserName = HtmlUtils.htmlEscape(signupForm.getSignupUserName());
		String safeSignupPassword = HtmlUtils.htmlEscape(signupForm.getSignupPassword());
		List<Data> searchedData = repository.findByUserName(safeSignupUserName);
		if (searchedData.size() == 0) {
			Data data = new Data();
			data.setUserName(safeSignupUserName);
			data.setPassword(passwordEncoder.encode(safeSignupPassword));
			repository.saveAndFlush(data);
			if (loginAuth(safeSignupUserName, safeSignupPassword)) {
				return "redirect:/index";
			} else {
				return "signup";
			}
		} else {
			return "signup";
		}
	}
	
	public boolean loginAuth(String loginUserName, String loginPassword) {
		List<Data> data = repository.findByUserName(loginUserName);
		if (data.size() > 0) {
			if (passwordEncoder.matches((loginPassword), data.get(0).getPassword())) {
				loginUserSession.setLoginUserSession(loginUserSession);
				loginUserSession.setLoggedIn(true);
				loginUserSession.setLoggedInName(loginUserName);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/*
	@PostConstruct
	public void init() {
		Data data = new Data();
		data.setUserName("guest");
		data.setPassword("dummy");
		repository.saveAndFlush(data);
	}
	*/
	
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
