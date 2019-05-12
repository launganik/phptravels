Feature: Home Page

Scenario Outline: Hotel dates search result scenario

Given user is already on Home Page
When user enters checkin & checkout date
Then user tries to book the first hotel
Then user closes the browser

Examples:
	| username | password |
	|  test     | test  | 