# Automated functional tests

## Setting tests environnement

Automated functionnal tests are made with Fluentlenium, which is *a website automation framework which extends Selenium to write reliable and resilient UI functional tests.* Read more about it [here](http://fluentlenium.org/).

Dependencies are already set in the pom.xml file.

Fluentlenium is configured to work with Chrome. To run the tests it is required to download the [Chrome driver](http://chromedriver.chromium.org/) and place it in the wp1/application folder.

Fluentlenium tests are located under wp1/src/test/java/ch.heigvd.amt.wp1/fluentlenium :

* Wp1FluentScenario1Test.java contains a UI test scenario

* *Wp1FluentTest.java* contains small UI tests
* the subdirectory *pages* contains usefull constants and functions related to each page of the website, helping tests management over time.



## Available tests

#### Statements

* Tests modify the database and change its sate
* Database is wiped each time it is restarted
* It may be required to restart the database to execute certain tests again
* To verify whether a user exists or not, the amount of registered should not exceed 100 (otherwise the verification should include users table browsing)



#### Test scenario 1

`devAppShouldRegisterCreateAppsBrowseAndLogOut`

* App developer registers and logs in 

  * *User heads to the login page*



![login](md_img/login.png)

​	assertion : current page is login*

  * *user clicks "here" to register*

    assertion : current page is register

  * *user fills personal information and registers*

    ![register](md_img/register.png)

    *assertion : after clicking "Register" button, the current page is home (logged in)*

* App developer creates 25 applications

  * user clicks on "Applications"

  ![applications](md_img/applications.png)

  *assertion : current page is applications*

  * user creates apps one by one by :
    * clicking "Create a new application" to open the drawer
    * entering app information 
    * clicking "Add" button

  ![app_creation](md_img/app_creation.png)

  ![app created](md_img/app_creation_done.png)

  *assertion : alert message "Application has been successfully created." is shown*

* App developer browses to the last application table page

  * user bowses by clicking "Next" button under the table

  *assertion : there are 10 applications in the table before clicking "Next" button*

  *assertion : there are 10 applications in the table  before clicking "Next" for the second time*

  *assertion : there are 5 applications in the table*

* App developer logs out

  * user clicks "Home" before logging out and sees the amount of applications owned

  ![logout](md_img/logout.png)

  *assertion : number of application displayed is 25*

  *assertion : after clicking logout, current page is login*

* App developer tries to go back to the applications page but is redirected to login page

  * user browses to applications page while logged out

  *assertion : current page is login*



### Other tests

##### Registration / login

`itShouldNotBePossibleToRegisterWithoutInformation` 

* go to login and hit "here" to register
* click "Register" without filling information

*assertion : current page is login before clicking "here" link*

*assertion : current page is register after clicking "here" link*

*assertion : current page is still register after clicking "Register" button*



`successfulRegistrationShouldBringUserToHomePage`

* go to login and hit "here" to register
* enter non-existing, valid information

*assertion : current page is login before clicking "here" link*

*assertion : current page is register after clicking "here" link*

*assertion : current page is home after filling information and clicking "Register" button*



`itShouldNotBePossibleToRegisterWithTwoDifferentPassword`

* go to login and hit "here" to register
* enter non-existing, information but 2 different passwords

*assertion : current page is login before clicking "here" link*

*assertion : current page is register after clicking "here" link*

*assertion : current page is still register after filling information and clicking "Register" button*



`itShouldNotBePossibleToRegisterWithAnExistingEmail`

- go to login and hit "here" to register
- enter valid information but an existing e-mail  (default in database)

*assertion : current page is login before clicking "here" link*

*assertion : current page is register after clicking "here" link*

*assertion : current page is still register after filling information and clicking "Register" button*



`itShouldNotBePossibleToSigninWithAnInvalidEmail`

- go to login and enter bad credentials

  *assertion : current page is login before clicking "Login" button*

  *assertion : current page is login after clicking "Login" button*



`successfulSigninShouldBringUserToHomePage`

- go to login and enter valid credentials (default admin in database)

*assertion : current page is login before clicking "Login" button*

*assertion : current page is home after clicking "Login" button*



##### Users administration

The following tests use `successfulSigninShouldBringUserToHomePage` code to login with default admin and browse to the users page.



`adminShouldBeAbleToCreateAUser`

* create app developer user

*assertion : alert message "User created." is shown*



`adminShouldNotBeAbleToCreateAUserWithoutEmail`

* fill add user form but without an email address

*assertion : user in not in users table* : relevant if < 10 users



`adminShouldNotBeAbleToCreateDuplicateUser`

* fill add user form with an existing email address

*assertion : alert message "This email address already exist." shown*

*assertion : the user appears only once in the table*



`adminShouldBeAbleToModifyUser`

* modify second user last name (default user) and presses "Save"

*assertion : alert message "User has been successfully updated." shown*

*assertion : the user appears once in the table*



`adminShouldBeAbleToResetUser`

- modify user state to RESET ("Reset")

*assertion : alert message "Password reset, email sent." shown*



`adminShouldBeAbleToDeleteUser`

* delete 4th user (default user)

*assertion : alert message "User has been successfully deleted." shown*

*assertion : default user 4 not in the users table*



##### Applications administration

The following tests use `successfulSigninShouldBringUserToHomePage` code to login with default admin and browse to the applications page.



`adminShouldBeAbleToCreateApplication`

* create an app

*assertion :  alert message "Application has been successfully created." shown*

*assertion :  app name found once in the apps table*



`adminShouldNotBeAbleToCreateDuplicateApplication`

* create an app
* create a second app with same fields

*assertion :  alert message "Another application has the same name. Please change." shown*

*assertion :  app name found only once in the apps table*



`adminShouldBeAbleToCreateAndModifyApplication`

* create an app
* modify the previously created app name and description

*assertion :  alert message "Application has been successfully updated." shown*

*assertion :  old app name doesn't appear in the table*

*assertion :  newapp name appears  once in the table*



`adminShouldBeAbleToDeleteApplication`

- create an app if necessary
- delete first app in the table

*assertion :  alert message "Application has been successfully deleted." shown*

*assertion :  there is one app less at the end*



##### APP DEVELOPER ACTIONS

`resetUserMustSetAValidPassword`

- log in with default reset app developer
- try reaching applications page
- change for empty password
- change for valid password
- try reaching applications page

*assertion : first page after login is profile page*

assertion : page stays profile while password not set

assertion : danger message "Password can't be empty." shown when trying to set with empty string

assertion : success message "Profile has been successfully updated." shown when setting with valid string

assertion : user is on apps page after clicking "Applications" (and password)



`appDevelopperShouldNotSeeUsers`

* login as default app developer
* try reaching users page

*assertion : page stays the home page*

*assertion :  alert message "You do not have the rights to access this page." shown*



`appDevelopperShouldBeAbleToCreateModifyAndDeleteIt`

* login as default app developer
* create app
* modify app name and description
* delete app

*assertion : correct alert message after each action*

*assertion :  new app appears in the table*

*assertion :  modified app name appears in the table*

*assertion :  old app name doesn't appear in the table anymore*

*assertion :  deleted app doesn't appear in the table anymore*



##### Navigation

`itShouldbePossibleToNavigateBetweenLoginAndRegisterWithoutError`

* go to login
* move to register

*assertion : first page is login*

*assertion : second page is register*

*assertion : third page is login again*



`itShouldBePossibleToBrowseToUsersFrom2Links`

* go to users by clicking in the home "users" card
* go back to home page
* go to users by clicking on the side panel

*assertion : page is users after clicking in the card*

*assertion : page is also users after clicking on the panel*



`userShouldNotBeAbleToAccessHomeAfterLogout`

* login as default admin
* log out
* try reaching home page

*assertion : current page is home after logging in*

*assertion : current page is login after logging out*

*assertion : current page remains login when trying to reach home page*











