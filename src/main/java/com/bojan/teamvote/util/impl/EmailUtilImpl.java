package com.bojan.teamvote.util.impl;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam;

import java.util.List;

import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.bojan.teamvote.model.Opinion;
import com.bojan.teamvote.model.Question;
import com.bojan.teamvote.service.QuestionService;
import com.bojan.teamvote.util.EmailUtil;

@Component
public class EmailUtilImpl implements EmailUtil {

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private QuestionService questionService;
	
	@Override
	public void sendQuestionEmail(Question q, String email) {	
		String body = wrapQuestionIntoTable(q, email);
		System.out.println(body);
		sendEmail(email, "New question", body);
	}
	
	@Override
	public void sendEmail(String toAddress, String subject, String body) {
		MimeMessage mimeMessage = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		try {
			helper.setTo(toAddress);
			//helper.setText(body);
			helper.setSubject(subject);
			mimeMessage.setContent(body, "text/html");
			sender.send(mimeMessage);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public String wrapHtml(String title, String content) {
		StringBuilder body = new StringBuilder();
		body.append("<html>");
		
		body.append("<head>");
		body.append("<title>"+title+"</title>");
		body.append("</head>");
		
		body.append("<body>");
		body.append(content);
		body.append("</body>");
		
		body.append("</html>");
		return null;
	}

	@Override
	public String wrapQuestionIntoTable(Question q, String email) {
		StringBuilder body = new StringBuilder();
		body.append("<table>");
		body.append(wrapCelIntoRow("<h1>" + q.getText() + "/<h1>"));

		for (Opinion op : q.getOpinions()) {
			body.append(wrapCelIntoRow("<a href='http://localhost:8080/email/vote/"+ email + "/" + q.getQuestionId() +"/" + op.getOpinionId() + "'>"+op.getText()+"</a>"));
		}
		body.append("</table>");
		return body.toString();
	}
	@Override
	public String wrapCelIntoRow(String content) {
		return wrapRow("<td>" + content + "</td>");
	}
	@Override
	public String wrapRow(String content) {
		return "<tr>"+ content + "</tr>";
	}


	
	
}



