package com.bojan.teamvote;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bojan.teamvote.dao.RoleDao;
import com.bojan.teamvote.dao.UserDao;
import com.bojan.teamvote.model.Opinion;
import com.bojan.teamvote.model.Question;
import com.bojan.teamvote.model.Role;
import com.bojan.teamvote.model.User;
import com.bojan.teamvote.service.QuestionService;
import com.bojan.teamvote.service.RoleService;
import com.bojan.teamvote.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamvoteApplicationTests {

	@Autowired
	UserService userService;

	@Autowired
	QuestionService questionService;
	
	@Autowired
	RoleService roleService;
	
	/**
	 * Add security privileges 
	 */
	@Test
	public void initDb() {
		{
			roleService.addRole("USER");
			roleService.addRole("ADMIN");
		}
	}
	
	@Test
	@Deprecated
	public void checkIfExists() {
		boolean ifExistsByEmail = userService.ifExistsByEmail("admin@gmail.com");
		boolean ifExistsByName = userService.ifExistsByName("admin");
		
		System.out.println(ifExistsByEmail);
		System.out.println(ifExistsByName);
	}
	
	@Test
	@Deprecated
	public void addQuestion() {
		Question q = new Question();
		q.setText("This is question 1");
		User questionUser = userService.findById(1);
		q.setOwner(questionUser);

		List<Opinion> opinion = new ArrayList<>();

		Opinion op1 = new Opinion();
		op1.setText("Opinion 1");
		op1.setQuestion(q);
		op1.setQuestion(q);
		List<User> users = new ArrayList<>();
		users.add(questionUser);
		//op1.setUsers(users);

		Opinion op2 = new Opinion();
		op2.setText("Opinion 2");
		op2.setQuestion(q);
		op2.setQuestion(q);
		List<User> users2 = new ArrayList<>();
		users.add(questionUser);
		//op2.setUsers(users2);

		opinion.add(op1);
		opinion.add(op2);
		q.setOpinions(opinion);

		
		List<Question> questions = new ArrayList<>();
		questions.add(q);
		questionUser.setAskQuestions(questions );
		//userService.updateUser(questionUser);
	}
}
