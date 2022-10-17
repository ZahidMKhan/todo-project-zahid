## Project Intro
This framework is designed using Page Object Model Design Pattern and TestNG for assertions and writing test cases.

It is a Maven project, and the basic skeleton is like:
<br>src/main/java package/packages.. class/classes..
<br>src/test/java package/packages.. class/classes..

In the project we have an XML file(testSuite.xml) to manage the test suite.
<br>pom.xml manages all the required dependencies.



#### How to run these e2e tests locally

**Prerequisite**:

Make sure you have Java and Maven installed. 
To check, run the following commands on your console:  
`java -version` (to check for java)
`mvn -version` (to check for maven)

###### Clone Platform Test project:

`git clone https://github.com/ZahidMKhan/todo-project-zahid.git`

**Set up the tests:**

Set up a `config.properties` file and put it in _src.main.java.com.todo.config_ folder.
The properties file should provide the application URL. (already checked in)

The suite file controls which browser to run the tests on. Test blocks can be added in the suite file for different browser configurations. If no browser details are provided, the default settings are - 'Chrome' in maximized mode.

**How to run the tests:**

Navigate to project root directory(where the pom.xml is located) and run the following command:

`mvn clean install`

_(this takes care of all the required dependencies specified in the pom.xml file and then builds the project, runs the tests)_

**Test reporting**

Once the test execution finishes, the framework generates a report which can be found in the _test-reports_ folder(created automatically after execution through the suite file).

Also, the screenshots of failed tests(if any) can be found in _test-screenshots_ folder(created only when there are one or more failed tests).