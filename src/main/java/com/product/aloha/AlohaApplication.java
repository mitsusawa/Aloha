package com.product.aloha;

import com.product.aloha.Data.Data;
import com.product.aloha.Data.Lesson;
import com.product.aloha.Data.LessonArrayWrap;
import com.product.aloha.repositories.DataRepository;
import com.product.aloha.repositories.LessonArrayWrapRepository;
import com.product.aloha.repositories.LessonRepository;
import com.product.aloha.repositories.TimeTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
@Controller
@EnableAutoConfiguration
public class AlohaApplication {
	
	@Autowired
	LoginUserSession loginUserSession;
	
	private final DataRepository repository;
	
	private final TimeTableRepository timeTableRepository;
	
	private final LessonArrayWrapRepository lessonArrayWrapRepository;
	
	private final LessonRepository lessonRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	public AlohaApplication(DataRepository repository, TimeTableRepository timeTableRepository, LessonArrayWrapRepository lessonArrayWrapRepository, LessonRepository lessonRepository) {
		this.repository = repository;
		this.timeTableRepository = timeTableRepository;
		this.lessonArrayWrapRepository = lessonArrayWrapRepository;
		this.lessonRepository = lessonRepository;
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
	
	@Transactional
	public boolean loginAuth(String loginUserName, String loginPassword) {
		List<Data> data = repository.findByUserName(loginUserName);
		if (data.size() > 0) {
			if (passwordEncoder.matches((loginPassword), data.get(0).getPassword())) {
				loginUserSession.setLoginUserSession(loginUserSession);
				loginUserSession.setLoggedIn(true);
				loginUserSession.setLoggedInName(loginUserName);
				loginUserSession.setData(repository.findOneByUserName(loginUserSession.loggedInName, Data.class));
				try {
					loginUserSession.getData().getTimeTableArray();
				} catch (Exception e) {
				}
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
	
	@RequestMapping(value = {"/table"}, method = RequestMethod.GET, params = "requirednum")
	@Transactional
	public String table(Model model, @RequestParam("requirednum") int requirdNum) {
		if (loginUserSession.isLoggedIn()) {
			loginUserSession.setData(repository.findOneByUserName(loginUserSession.getLoggedInName(), Data.class));
			Data data = loginUserSession.getData();
			try {
				model.addAttribute("getTable", data.getTimeTableArray().get(requirdNum).getLessonArrayWrap());
				model.addAttribute("tableNum", requirdNum);
				model.addAttribute("tableName", data.getTimeTableArray().get(requirdNum).getTableName());
			} catch (Exception e) {
				return "redirect:/index";
			}
			return "table";
		} else {
			return "redirect:/index";
		}
	}
	
	@RequestMapping(value = {"/table"}, method = RequestMethod.GET)
	public String tableWrongAccess(Model model) {
		return "redirect:/index";
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
			data.getTimeTableArray().get(tableNum).setTableName(safeMakeUpTableName);
			int tmpNum = Integer.parseInt(HtmlUtils.htmlEscape(makeUpForm.getDivideNum()));
			if (Objects.isNull(data.getTimeTableArray().get(tableNum).getLessonArrayWrap())) {
				data.getTimeTableArray().get(tableNum).setLessonArrayWrap(new ArrayList<LessonArrayWrap>());
			}
			for (int i = 0; i < tmpNum; i++) {
				data.getTimeTableArray().get(tableNum).getLessonArrayWrap().add(new LessonArrayWrap());
			}
			for (LessonArrayWrap lnwpArray : data.getTimeTableArray().get(tableNum).getLessonArrayWrap()) {
				if (Objects.isNull(lnwpArray.getArray())) {
					lnwpArray.setArray(new ArrayList<>());
				}
				lessonArrayWrapRepository.save(lnwpArray);
				for (int i = 0; i < 7; i++) {
					lnwpArray.getArray().add(new Lesson());
					lessonRepository.save(lnwpArray.getArray().get(i));
				}
			}
			timeTableRepository.save(data.getTimeTableArray().get(tableNum));
			repository.saveAndFlush(data);
			return "redirect:/table?requirednum=" + tableNum;
		} else {
			return "makeup";
		}
	}
	
	@RequestMapping(value = {"/edit"}, method = RequestMethod.GET, params = {"wrapNum", "editLessonNum"})
	public String edit(Model model, @RequestParam("wrapNum") int wrapNum, @RequestParam("editLessonNum") int editLessonNum, @RequestParam("tableNum") int tableNum) {
		if (loginUserSession.isLoggedIn()) {
			model.addAttribute("tableNum", tableNum);
			model.addAttribute("wrapNum", wrapNum);
			model.addAttribute("editLessonNum", editLessonNum);
			return "edit";
		} else {
			return "redirect:/index";
		}
	}
	
	@RequestMapping(value = {"/edit"}, method = RequestMethod.GET)
	public String editWrongAccess(Model model) {
		return "redirect:/index";
	}
	
	@RequestMapping(value = {"/edit"}, method = RequestMethod.POST)
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public String editExec(Model model, @ModelAttribute("editForm") EditForm editForm) {
		if (loginUserSession.isLoggedIn()) {
			Data data = repository.findOneByUserName(loginUserSession.getLoggedInName(), Data.class);
			String safeEditLessonName = HtmlUtils.htmlEscape(editForm.getEditLessonName());
			int wrapNum = Integer.parseInt(HtmlUtils.htmlEscape(editForm.getWrapNum()));
			int editLessonNum = Integer.parseInt(HtmlUtils.htmlEscape(editForm.getEditLessonNum()));
			int tableNum = Integer.parseInt(HtmlUtils.htmlEscape(editForm.getTableNum()));
			data.getTimeTableArray().get(tableNum).getLessonArrayWrap().get(wrapNum).getArray().get(editLessonNum).setName(safeEditLessonName);
			lessonRepository.save(data.getTimeTableArray().get(tableNum).getLessonArrayWrap().get(wrapNum).getArray().get(editLessonNum));
			lessonArrayWrapRepository.save(data.getTimeTableArray().get(tableNum).getLessonArrayWrap().get(wrapNum));
			timeTableRepository.save(data.getTimeTableArray().get(tableNum));
			repository.saveAndFlush(data);
			loginUserSession.setData(repository.findOneByUserName(loginUserSession.getLoggedInName(), Data.class));
			return "redirect:/table?requirednum=" + tableNum;
		} else {
			return "redirect:/index";
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