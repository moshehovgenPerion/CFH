Feature: Codefuel Manage Placements

  Background: Login to portal and add an app
    Given User logged into the portal enter "nofardi1@mailinator.com" and "1q2w3e4r$"
    When User select App tab and click on Add app button
    And Enter App "Auto" select "1" Enter packageID "com.google.app"
    And Click Add button
    Then validate App created

  # And delete apps
  @Placement @Regression
  Scenario: Create a new app and verify default placement (test case: 107401)
    And verify default placement exists


  @Placement @Regression @Sanity
  Scenario Outline: Add new placement (regular, special char, long string, and with space) (Test cases: 107402, 107493, 107494)
    And Add new placement with <placename>
    And click save placement
    And validate placement created with <placename>

    Examples: 
      | username                | password  | name | platform | packageID      | placename                   |
      | nofardi1@mailinator.com | 1q2w3e4r$ | Auto | 1        | com.google.app | place                       |
      | nofardi1@mailinator.com | 1q2w3e4r$ | Auto | 1        | com.google.app | place name                  |
      | nofardi1@mailinator.com | 1q2w3e4r$ | Auto | 1        | com.google.app | place!@$%                   |
      | nofardi1@mailinator.com | 1q2w3e4r$ | Auto | 1        | com.google.app | placenameplacenameplacename |

  @Placement @Regression @Sanity
  Scenario: Edit existing placement (Test case: 107404)
    And Add new placement with "placename"
    And click save placement
    And edit place to "newPlace"
    And click save placement edit
    And validate placement created with "newPlace"

  @Placement @Sanity
  Scenario: De-Activate existing placement (Test case: 107423)
    And Add new placement with "placename"
    And click save placement
    And verify active placement
    And click deactive placement
    And verify deactive placement

  @Test
  Scenario: Check app list
    And check app list
