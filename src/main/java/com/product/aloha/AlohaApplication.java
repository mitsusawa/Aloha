package com.product.aloha;

import com.product.aloha.Data.Data;
import com.product.aloha.Data.Friend;
import com.product.aloha.Data.Lesson;
import com.product.aloha.Data.LessonArrayWrap;
import com.product.aloha.Data.ToDo;
import com.product.aloha.repositories.DataRepository;
import com.product.aloha.repositories.FriendRepository;
import com.product.aloha.repositories.LessonArrayWrapRepository;
import com.product.aloha.repositories.LessonRepository;
import com.product.aloha.repositories.TimeTableRepository;
import com.product.aloha.repositories.ToDoRepository;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.util.HtmlUtils;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
@Controller
@EnableAutoConfiguration
@SessionAttributes("scopedTarget.loginUserSession")
public class AlohaApplication {
	
	@Autowired
	LoginUserSession loginUserSession;
	
	private final DataRepository repository;
	
	private final TimeTableRepository timeTableRepository;
	
	private final LessonArrayWrapRepository lessonArrayWrapRepository;
	
	private final LessonRepository lessonRepository;
	
	private final ToDoRepository toDoRepository;
	
	private final FriendRepository friendRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	public AlohaApplication(DataRepository repository, TimeTableRepository timeTableRepository, LessonArrayWrapRepository lessonArrayWrapRepository, LessonRepository lessonRepository, ToDoRepository toDoRepository, FriendRepository friendRepository) {
		this.repository = repository;
		this.timeTableRepository = timeTableRepository;
		this.lessonArrayWrapRepository = lessonArrayWrapRepository;
		this.lessonRepository = lessonRepository;
		this.toDoRepository = toDoRepository;
		this.friendRepository = friendRepository;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(AlohaApplication.class, args);
	}
	
	@RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public String index(Model model) {
		model.addAttribute("isLoggedIn", loginUserSession.isLoggedIn());
		if (!loginUserSession.isLoggedIn()) {
			model.addAttribute("loggedInName", "ゲスト");
			return "index";
		} else {
			model.addAttribute("loggedInName", loginUserSession.getLoggedInName());
			if (Objects.isNull(loginUserSession.getData())) {
				loginUserSession.setData(repository.findOneByUserName(loginUserSession.getLoggedInName(), Data.class));
			}
			Data data = loginUserSession.getData();
			data.setToDoList(repository.findOneByUserName(loginUserSession.getLoggedInName(), Data.class).getToDoList());
			data.setFriendList(repository.findOneByUserName(loginUserSession.getLoggedInName(), Data.class).getFriendList());
			if (Objects.isNull(data.getTimeTableArray())) {
				data.setTimeTableArray(new ArrayList<>());
				repository.saveAndFlush(data);
			}
			if (Objects.isNull(data.getToDoList())) {
				data.setToDoList(new ArrayList<>());
				repository.saveAndFlush(data);
			}
			int acceptableCount = 0;
			int friendCount = 0;
			for (Friend friend : data.getFriendList()) {
				if (friend.getAcceptable()) {
					acceptableCount++;
				} else if (friend.getStatus()) {
					friendCount++;
				}
			}
			repository.saveAndFlush(data);
			model.addAttribute("acceptableCount", acceptableCount);
			model.addAttribute("friendCount", friendCount);
			model.addAttribute("friendList", data.getFriendList());
			model.addAttribute("timeTableArray", data.getTimeTableArray());
			model.addAttribute("toDoList", data.getToDoList());
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
			return "redirect:/index";
		}
	}
	
	@RequestMapping(value = {"/logout"})
	public String logout(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return "redirect:/index";
	}
	
	@RequestMapping(value = {"/signup"}, method = RequestMethod.GET)
	public String signUp(Model model) {
		if (!loginUserSession.isLoggedIn()) {
			return "signup";
		} else {
			model.addAttribute("loggedInName", loginUserSession.getLoggedInName());
			return "redirect:/index";
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
				try {
					loginUserSession.getData().getToDoList();
				} catch (Exception e) {
				
				}
				try {
					loginUserSession.getData().getFriendList();
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
	
	@RequestMapping(value = {"/todo"}, method = RequestMethod.POST)
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public String makeUpToDoExec(Model model, @ModelAttribute("makeUpToDoForm") @Validated MakeUpToDoForm makeUpToDoForm, BindingResult result) {
		if (loginUserSession.isLoggedIn()) {
			String safeMakeUpToDoName = HtmlUtils.htmlEscape(makeUpToDoForm.getNewToDoName());
			String safeMakeUpToDoInfo = HtmlUtils.htmlEscape(makeUpToDoForm.getNewToDoInfo());
			Data data = repository.findOneByUserName(loginUserSession.getLoggedInName(), Data.class);
			if (Objects.isNull(data.getToDoList())) {
				data.setToDoList(new ArrayList<>());
			}
			int toDoNum = data.getToDoList().size();
			data.getToDoList().add(new ToDo());
			data.getToDoList().get(toDoNum).setInfo(safeMakeUpToDoInfo);
			data.getToDoList().get(toDoNum).setName(safeMakeUpToDoName);
			toDoRepository.save(data.getToDoList().get(toDoNum));
			repository.saveAndFlush(data);
			return "redirect:/index";
		} else {
			return "redirect:/index";
		}
	}
	
	@RequestMapping(value = {"/todo_del"}, method = RequestMethod.GET)
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public String deleteToDoExec(Model model, @RequestParam("deleteNum") @Validated @Min(0) int deleteNum) {
		if (loginUserSession.isLoggedIn()) {
			Data data = repository.findOneByUserName(loginUserSession.getLoggedInName(), Data.class);
			if (Objects.isNull(data.getToDoList())) {
				data.setToDoList(new ArrayList<>());
			}
			data.getToDoList().remove(deleteNum);
			repository.saveAndFlush(data);
			return "redirect:/index";
		} else {
			return "redirect:/index";
		}
	}
	
	@RequestMapping(value = {"/friend"}, method = RequestMethod.POST)
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public String friendRequest(Model model, @ModelAttribute("friendRequestForm") @Validated FriendRequestForm friendRequestForm, BindingResult result) {
		if (loginUserSession.isLoggedIn()) {
			String safeFriendRequestName = HtmlUtils.htmlEscape(friendRequestForm.getFriendRequestName());
			Data data = repository.findOneByUserName(loginUserSession.getLoggedInName(), Data.class);
			if (Objects.isNull(data.getFriendList())) {
				data.setFriendList(new ArrayList<>());
			}
			int friendNum = data.getFriendList().size();
			Data reciveData;
			int reciveNum;
			boolean flag = false;
			for (Friend friend : data.getFriendList()) {
				if (friend.getName().equals(safeFriendRequestName)) {
					flag = true;
				}
			}
			if (repository.findByUserName(safeFriendRequestName).size() > 0 && !flag && !safeFriendRequestName.equals(loginUserSession.getLoggedInName())) {
				data.getFriendList().add(new Friend());
				data.getFriendList().get(friendNum).setName(safeFriendRequestName);
				reciveData = repository.findOneByUserName(safeFriendRequestName, Data.class);
				if (Objects.isNull(reciveData.getFriendList())) {
					reciveData.setFriendList(new ArrayList<>());
				}
				reciveNum = reciveData.getFriendList().size();
				reciveData.getFriendList().add(new Friend());
				reciveData.getFriendList().get(reciveNum).setName(data.getUserName());
				reciveData.getFriendList().get(reciveNum).setAcceptable(true);
				friendRepository.save(data.getFriendList().get(friendNum));
				friendRepository.save(reciveData.getFriendList().get(reciveNum));
				repository.saveAndFlush(data);
			}
			return "redirect:/index";
		} else {
			return "redirect:/index";
		}
	}
	
	@RequestMapping(value = {"/accept"}, method = RequestMethod.POST)
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public String acceptOrRefuse(Model model, @ModelAttribute("friendRequestForm") @Validated AcceptForm acceptForm, BindingResult result) {
		if (loginUserSession.isLoggedIn()) {
			boolean acceptFlag = Boolean.parseBoolean(HtmlUtils.htmlEscape(acceptForm.getAcceptFlag()));
			int friendAcceptNum = Integer.parseInt(HtmlUtils.htmlEscape(acceptForm.getFriendAcceptNum()));
			Data data = repository.findOneByUserName(loginUserSession.getLoggedInName(), Data.class);
			if (Objects.isNull(data.getFriendList())) {
				data.setToDoList(new ArrayList<>());
			}
			try {
				if (acceptFlag) {
					if (repository.findByUserName(data.getFriendList().get(Integer.parseInt(HtmlUtils.htmlEscape(acceptForm.getFriendAcceptNum()))).getName()).size() > 0) {
						data.getFriendList().get(friendAcceptNum).setStatus(true);
						data.getFriendList().get(friendAcceptNum).setAcceptable(false);
						Data anotherData = repository.findOneByUserName(data.getFriendList().get(Integer.parseInt(HtmlUtils.htmlEscape(acceptForm.getFriendAcceptNum()))).getName(), Data.class);
						Friend anotherFriend = null;
						for (int i = 0; i < anotherData.getFriendList().size(); i++) {
							if (anotherData.getFriendList().get(i).getName().equals(loginUserSession.getLoggedInName())) {
								anotherFriend = anotherData.getFriendList().get(i);
								break;
							}
						}
						if (!Objects.isNull(anotherFriend)) {
							anotherFriend.setAcceptable(false);
							anotherFriend.setStatus(true);
						}
						friendRepository.save(data.getFriendList().get(friendAcceptNum));
						friendRepository.save(anotherFriend);
						repository.saveAndFlush(data);
					}
				} else {
					data.getFriendList().remove(friendAcceptNum);
					Data anotherData = repository.findOneByUserName(data.getFriendList().get(friendAcceptNum).getName(), Data.class);
					for (int i = 0; i < anotherData.getFriendList().size(); i++) {
						if (anotherData.getFriendList().get(i).getName().equals(loginUserSession.getLoggedInName())) {
							anotherData.getFriendList().remove(i);
							break;
						}
					}
				}
			} catch (Exception e) {
			
			}
			repository.saveAndFlush(data);
			return "redirect:/index";
		} else {
			return "redirect:/index";
		}
	}
	
	@RequestMapping(value = {"/send"}, method = RequestMethod.POST)
	@Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public String copySentTable(Model model, @ModelAttribute("sendForm") @Validated SendForm sendForm, BindingResult result) {
		if (loginUserSession.isLoggedIn()) {
			int safeFriendNum = Integer.parseInt(HtmlUtils.htmlEscape(sendForm.getSendFriendNum()));
			int safeTableNum = Integer.parseInt(HtmlUtils.htmlEscape(sendForm.getSendTableNum()));
			int index;
			Data data = repository.findOneByUserName(loginUserSession.getLoggedInName(), Data.class);
			if (repository.findByUserName(data.getFriendList().get(safeFriendNum).getName()).size() > 0) {
				Data anotherData = repository.findOneByUserName(data.getFriendList().get(safeFriendNum).getName(), Data.class);
				if (Objects.isNull(anotherData.getTimeTableArray())) {
					anotherData.setTimeTableArray(new ArrayList<>());
					repository.saveAndFlush(anotherData);
				}
				index = anotherData.getTimeTableArray().size();
				anotherData.getTimeTableArray().add(data.getTimeTableArray().get(safeTableNum).clone());
				for (LessonArrayWrap lessonArrayWrap : anotherData.getTimeTableArray().get(index).getLessonArrayWrap()) {
					lessonArrayWrapRepository.save(lessonArrayWrap);
					for (Lesson lesson : lessonArrayWrap.getArray()) {
						lessonRepository.save(lesson);
					}
				}
				timeTableRepository.save(anotherData.getTimeTableArray().get(index));
				repository.saveAndFlush(anotherData);
			}
			return "redirect:/index";
		} else
		
		{
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
	
	@ModelAttribute("makeUpToDoForm")
	public MakeUpToDoForm makeUpToDoForm() {
		MakeUpToDoForm makeUpToDoForm = new MakeUpToDoForm();
		return makeUpToDoForm;
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