# TeamVote - Web App for public or team voting on different topics
# Currently under construction
Java, SpringBoot, Thymeleaf, Bootstrap, Hibernate, MySQL, JQuery | Editor used: Spring Boot Suit

## Description
A prototype of a team voting web app. Define a question and assign it to different teams who can vote on it via email or on website.

## Current TODO list
* Finish CRUD Question, Option, Team, Vote, User. Create is finished
* Double check if teams are selected while creating a question
* Add archive question functionality
* Add exception handling (Finish views)
* Add Email Service to send a email to users when questions is created
* Privacy setting

## Done TODO list
* Show voting results
* Show assigned questions
* Show public questions on index page
* Create question, Create option, Create Team
* Login/register page, Create User, profile page
* Spring Security
* Creating model entities
* JUnit test for creating user repository
* Limit fetching of teams and users to make up faster. 
* Define public/private questions
* Add basic voting functionality
* Redirect if user already voted for a question to showResults.html
* Clean user lists after creating a vote
* Adjust Lazy Eager loading of entity models

## Optional
* Set filter search on keyup event, instead on blur event. Check performance
