# dock-app-testautomation

- Clone the Repo

- Open it in IntelliJ Idea as a Gradle project

- Install the Java 15 or higher

- Execute the command on MAC `./gradlew` or on windows as `'gradle'`


- You can run the test either direct or by configuring the runner https://www.jetbrains.com/help/idea/work-with-tests-in-gradle.html

- Following command can also be used:

- MAC: `./gradlew test -DsuitXmlFile=mainSuite.xml` 

- Windows: `gradle test -DsuitXmlFile=mainSuite.xml`

- Appium should be installed on your system to run the tests locally on your phone

- Run the command `adb devices` and your phone information should be visible like
  `2341cb190e017ece	device`

- The displayed number is your udid which is required to interact with your phone

- open local.properties file and update follwoing information about your phone

`androidPhoneName=YOUR_PHONE_NAME`
`androidUdid=2341cb190e017ece, that you got from above command`
`androidVersion=YOUR_PHONE_VERSION`
