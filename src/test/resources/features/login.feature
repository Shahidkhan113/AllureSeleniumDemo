@LoginTest
Feature: ThriftPlan Login Testing

  Scenario Outline: Login Scenarios
    Given User is on the login page
    When User enters "<email>" and "<password>"
    Then User should see the result "<caseType>"

    Examples:
      | email                            | password | caseType         |
      | wrong@veroke.com                 | 123456   | Invalid Email    |
      | muhammad.shakeel@veroke.sa      | wrong123 | Invalid Password |
      | muhammad.shakeel@veroke.sa      | 123123   | Valid Login      |
