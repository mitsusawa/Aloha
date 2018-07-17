package com.product.aloha;

import com.product.aloha.Data.Data;
import com.product.aloha.Data.Lesson;
import com.product.aloha.repositories.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
		if (!loginUserSession.isLoggedIn()) {
			model.addAttribute("loggedInName", "ゲスト");
			return "index";
		} else {
			model.addAttribute("loggedInName", loginUserSession.getLoggedInName());
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
	public String login(Model model) {
		if (!loginUserSession.isLoggedIn()) {
			return "login";
		} else {
			model.addAttribute("loggedInName", loginUserSession.getLoggedInName());
			return "index";
		}
	}
	
	@RequestMapping(value = {"/logout"})
	public String logout() {
		loginUserSession = new LoginUserSession();
		return "redirect:/index";
	}
	
	@RequestMapping(value = {"/signup"}, method = RequestMethod.GET)
	public String signUp(Model model) {
		if (!loginUserSession.isLoggedIn()) {
			return "signup";
		} else {
			model.addAttribute("loggedInName", loginUserSession.getLoggedInName());
			return "index";
		}
	}
	
	@RequestMapping(value = {"/signup"}, method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public String signUpExec(Model model, @ModelAttribute("signUpForm") @Validated SignUpForm signUpForm, BindingResult result) {
		String safeSignUpUserName = HtmlUtils.htmlEscape(signUpForm.getSignUpUserName());
		String safeSignUpPassword = HtmlUtils.htmlEscape(signUpForm.getSignUpPassword());
		List<Data> searchedData = repository.findByUserName(safeSignUpUserName);
		if (searchedData.size() == 0) {
			Data data = new Data();
			data.setUserName(safeSignUpUserName);
			data.setPassword(passwordEncoder.encode(safeSignUpPassword));
			repository.saveAndFlush(data);
			if (loginAuth(safeSignUpUserName, safeSignUpPassword)) {
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
				loginUserSession.setData(repository.findOneByUserName(loginUserSession.loggedInName, Data.class));
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	@RequestMapping(value = {"/makeup"}, method = RequestMethod.GET)
	public String makeUp() {
		if (loginUserSession.isLoggedIn()) {
			return "makeup";
		} else {
			return "redirect:/index";
		}
	}
	
	@RequestMapping(value = {"/table"}, method = RequestMethod.GET)
	public String table(Model model, @RequestParam("requirednum") int requirdNum) {
		if (loginUserSession.isLoggedIn()) {
			if (Objects.isNull(loginUserSession.getData())) {
				loginUserSession.setData(repository.findOneByUserName(loginUserSession.getLoggedInName(), Data.class));
			}
			Data data = loginUserSession.getData();
			try {
				model.addAttribute("getTable", data.getTimeTableArray().get(requirdNum).getLessonArray());
				model.addAttribute("tableName", data.getTimeTableArray().get(requirdNum).getTableName());
			} catch (Exception e) {
				return "redirect:/index";
			}
			return "table";
		} else {
			return "redirect:/index";
		}
	}
	
	@RequestMapping(value = {"/makeup"}, method = RequestMethod.POST)
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public String makeUpExec(Model model, @ModelAttribute("makeUpForm") @Validated MakeUpForm makeUpForm, BindingResult result) {
		if (loginUserSession.isLoggedIn()) {
			String safeMakeUpTableName = HtmlUtils.htmlEscape(makeUpForm.getMakeUpTableName());
			loginUserSession.setData(repository.findOneByUserName(loginUserSession.getLoggedInName(), Data.class));
			Data data = loginUserSession.getData();
			int tableNum;
			if (Objects.isNull(data.getTimeTableArray())) {
				data.setTimeTableArray(new ArrayList<>(0));
			}
			tableNum = data.getTimeTableArray().size();
			data.addTimeTable();
			data.getTimeTableArray().get(tableNum).setDividedNum(Byte.parseByte(makeUpForm.getDivideNum()));
			data.getTimeTableArray().get(tableNum).setTableName(safeMakeUpTableName);
			data.getTimeTableArray().get(tableNum).setLessonArray(new ArrayList<List<Lesson>>());
			for (int i = 0; i < data.getTimeTableArray().get(tableNum).getDividedNum(); i++) {
				data.getTimeTableArray().get(tableNum).addLessonArray();
			}
			for (int i = 0; i < data.getTimeTableArray().get(tableNum).getLessonArray().size(); i++) {
				for (int j = 0; j < 7; j++) {
					data.getTimeTableArray().get(tableNum).addLessonArray();
				}
			}
			repository.saveAndFlush(data);
			return "redirect:/table?requirednum=" + tableNum;
		} else {
			return "makeup";
		}
	}
	
	@ModelAttribute("signUpForm")
	public SignUpForm signUpForm() {
		SignUpForm signUpForm = new SignUpForm();
		return signUpForm;
	}
	
	@ModelAttribute("loginForm")
	public LoginForm loginForm() {
		LoginForm loginForm = new LoginForm();
		return loginForm;
	}
	
	@ModelAttribute("makeUpForm")
	public MakeUpForm makeUpForm() {
		MakeUpForm makeUpForm = new MakeUpForm();
		return makeUpForm;
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
}