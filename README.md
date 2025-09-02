# Web Automation 

Web Automation training project to test web, using Selenium Webdriver and TestNG to create the test cases. The course is oriented to people with previous objected-oriented programming that want to delve in

Page: https://www.globalsqa.com/angularjs-protractor-practice-site/

 the automation project is configured to run using Suite.xml file to run the Suite by default 
 Some parameters you might configure:
 - browser  
   - Should be set as : CHROME, FIREFOX OR EDGE.   

if you want to run using selenium grid, please modify run the ParallelSuite.xml file which is configured as REMOTE browser in order tu our test cases in parallel mode.

## Execute allure report locally


### Installation 

1. Follow steps according to your operating system in [allure-report-install](https://allurereport.org/docs/install/) instructions.
2. Verify the installation by running allure --version.

### Generate report
1. For a quick local view, this command generates a temporary report and automatically starts a local web server to display it in your default web browser.

```
allure serve
```

2. Using allure `generate` and allure `open` (For saving and viewing):
```
allure generate --clean -o allure-report allure-results
```

The command will generate and specify the output directory

3. Open allure report with
```
allure open allure-report
```
   