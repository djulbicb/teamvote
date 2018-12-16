package com.bojan.teamvote.util;

import com.bojan.teamvote.model.Question;
/**
 * Utility class for sending emails.
 * 
 * @author Bojan
 *
 */
public interface EmailUtil {
	/**
	 * Send user to email voting links. User picks one link
	 * @param q
	 * @param email
	 */
	void sendQuestionEmail(Question q, String email);
	
	@Deprecated
	/**
	 * Used for testing
	 * @param toAddress - where to send
	 * @param subject - title of email
	 * @param body - email content, just text
	 */
	void sendEmail(String toAddress, String subject, String body);
		
	//	UTILITY
	//////////////////////////////////////////////////////////////////
	
	/**
	 * Utility funcionality to create html code for the email
	 * @param title - title of email
	 * @param content - html content
	 * @return
	 */
	String wrapHtml(String title, String content);
	
	/**
	 * Converts question into html table code that is sent to user. Email holds links for user to vote
	 * @param q - supplies information about votes
	 * @param email - user email where the email is being sent
	 * @return html code string
	 */
	String wrapQuestionIntoTable(Question q, String email);
	
	/**
	 * Utility functionality to wrap content into <tr><td></td></tr>
	 * @param content - inner html content
	 * @return html code string
	 */
	String wrapCelIntoRow(String content);
	
	/**
	 * Utility functionality to wrap content into <tr></tr>
	 * @param content - inner html content
	 * @return html code string
	 */
	String wrapRow(String content);
}
