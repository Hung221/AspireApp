# AspireApp
QA Development Challenge
# Structure of the project 
1. Java Commons 
a. AbstractPage contains common method, for example: sendKey, click , waitToElemnetVisible ...
b. AbstractTest is used to init driver, support multible browser (Chrome, Firefox, Egde)
2. PageObject
Contains method is used on each page
3. PageUI
Contains locator is used on each page
4. ReportConfig
Contains report file >in this case I'm using Allure report
5. Utils
Is used to defined constant such as username, password, longtime, shorttime ...
6. And finally TCs is created in test.java.TCs 
# How to run and get the report for this project?
Can use one of 2 ways:
Clone project from github and import it as a maven project in your IDE
1. run by testRunner.xml 
2. run by cmd "mvn clean test"
# The report will be extracted in target/allure-json
use the cmd "allure serve allure-json" to visualize the report
Please refer to the attachment below for the test report:
![image](https://user-images.githubusercontent.com/74232108/162586029-5e91ced0-ab63-4ec1-b78e-66c354da33b2.png)
![image](https://user-images.githubusercontent.com/74232108/162586053-f835e86f-7922-4858-83c2-70bd107661d5.png)
