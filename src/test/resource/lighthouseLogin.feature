Feature: LightHouse Login

  @Login
  Scenario Outline: Login to Portal (test case: 107213)
    Given I browse to login page
    When I enter <username> and <password> first time
    Then validate login pass
    And User log out

    Examples: 
      | username                | password  |
      | orantest@mailinator.com | 1q2w3e4r$ |

  @Login
  Scenario Outline: Login negetive (test cases: 107214, 107217, 107215, 107218, 107258)
    Given I browse to login page
    When I enter <username> and <password> first time
    Then validate warning message <message>

    Examples: 
      | username                | password  | message                             |
      | ronen.yurik@perion.com  | 123456    | The email or password is incorrect  |
      | orantest@mailinator.com |           | Please enter your password.         |
      |                         | 1q2w3e4r$ | Please enter an email.              |
      | orantest@mailinator.com | 123456    | The email or password is incorrect. |
      | ronen.yurik@perion.com  | 1q2w3e4r$ | The email or password is incorrect. |
      |                         |           | Please enter an email.              |

  @Login
  Scenario Outline: : Remember me check box (Test Case 107249)
    Given I browse to login page
    And I enter <username> and <password> and checkbox
    When User log out
    And click Login
    Then validate login Fail

    Examples: 
      | username                | password  |
      | orantest@mailinator.com | 1q2w3e4r$ |
