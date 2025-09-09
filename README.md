# Web Automation with Allure TestNG Integration

Web Automation training project to test web applications using Selenium WebDriver and TestNG. This project demonstrates how to integrate Allure TestNG for comprehensive test reporting in both local development and CI/CD pipeline environments.

**Test Site:** https://www.globalsqa.com/angularjs-protractor-practice-site/

## Table of Contents
- [Project Overview](#project-overview)
- [Prerequisites](#prerequisites)
- [Local Development Setup](#local-development-setup)
- [Allure TestNG Configuration](#allure-testng-configuration)
- [Running Tests Locally](#running-tests-locally)
- [Allure Report Generation](#allure-report-generation)
- [CI/CD Pipeline with GitHub Actions](#cicd-pipeline-with-github-actions)
- [Best Practices](#best-practices)
- [Troubleshooting](#troubleshooting)

## Project Overview

This automation framework includes:
- **Selenium WebDriver 4.3.0** for browser automation
- **TestNG 6.14.3** for test execution and organization
- **Allure TestNG 2.22.2** for detailed test reporting
- **Page Object Model (POM)** design pattern
- **Maven** for dependency management
- **GitHub Actions** for CI/CD pipeline

## Prerequisites

Before setting up the project, ensure you have:

- **Java 11** or higher
- **Maven 3.6+**
- **Git**
- **Allure Command Line Tool** (for local report generation)

## Local Development Setup

### 1. Clone the Repository

```bash
git clone https://github.com/luisaferco/web-automation-pom.git
cd web-automation-pom
```

### 2. Install Dependencies

```bash
mvn clean install
```

### 3. Install Allure Command Line Tool

#### Windows (using Scoop)
```bash
scoop install allure
```

#### Windows (using Chocolatey)
```bash
choco install allure
```

#### macOS (using Homebrew)
```bash
brew install allure
```

#### Linux (manual installation)
```bash
# Download and extract Allure
wget https://github.com/allure-framework/allure2/releases/download/2.24.0/allure-2.24.0.tgz
tar -zxvf allure-2.24.0.tgz
sudo mv allure-2.24.0 /opt/allure
echo 'export PATH="/opt/allure/bin:$PATH"' >> ~/.bashrc
source ~/.bashrc
```

### 4. Verify Allure Installation

```bash
allure --version
```

## Allure TestNG Configuration

### Maven Dependencies

The project includes the following Allure-related dependencies in `pom.xml`:

```xml
<dependency>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-testng</artifactId>
    <version>2.22.2</version>
</dependency>
```

### Enhanced Maven Configuration

To improve the Allure integration, add the following plugins to your `pom.xml`:

```xml
<build>
    <plugins>
        <!-- Existing plugins... -->
        
        <!-- Maven Surefire Plugin with Allure -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.3.1</version>
            <configuration>
                <suiteXmlFiles>
                    <suiteXmlFile>src/test/resources/Suite.xml</suiteXmlFile>
                </suiteXmlFiles>
                <argLine>
                    -javaagent:"${settings.localRepository}/org/aspectj/aspectjweaver/1.9.19/aspectjweaver-1.9.19.jar"
                </argLine>
                <systemProperties>
                    <property>
                        <name>allure.results.directory</name>
                        <value>${project.build.directory}/allure-results</value>
                    </property>
                </systemProperties>
            </configuration>
            <dependencies>
                <dependency>
                    <groupId>org.aspectj</groupId>
                    <artifactId>aspectjweaver</artifactId>
                    <version>1.9.19</version>
                </dependency>
            </dependencies>
        </plugin>
        
        <!-- Allure Maven Plugin -->
        <plugin>
            <groupId>io.qameta.allure</groupId>
            <artifactId>allure-maven</artifactId>
            <version>2.12.0</version>
            <configuration>
                <reportVersion>2.24.0</reportVersion>
                <resultsDirectory>${project.build.directory}/allure-results</resultsDirectory>
                <reportDirectory>${project.build.directory}/allure-report</reportDirectory>
            </configuration>
        </plugin>
    </plugins>
</build>
```

### Allure Annotations in Tests

The project uses various Allure annotations for enhanced reporting:

```java
import io.qameta.allure.*;

@Epic("User Management")
@Feature("Login Functionality")
public class RegistrationLoginTests extends BaseTest {
    
    @Test
    @Story("Failed Login Attempts")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test failed login with incorrect username")
    public void failedUserNameRegistration(String userName, String password) {
        // Test implementation
    }
}
```

### Available Allure Annotations

- `@Epic` - High-level business requirement
- `@Feature` - Feature being tested
- `@Story` - User story or scenario
- `@Severity` - Test importance level (BLOCKER, CRITICAL, NORMAL, MINOR, TRIVIAL)
- `@Description` - Detailed test description
- `@Step` - Test step description
- `@Attachment` - Attach files or screenshots
- `@Issue` - Link to issue tracker
- `@TmsLink` - Link to test management system

## Running Tests Locally

### Basic Test Execution

```bash
# Run all tests
mvn clean test

# Run specific test suite
mvn clean test -DsuiteXmlFile=src/test/resources/Suite.xml

# Run with specific browser
mvn clean test -DbrowserName=CHROME

# Run parallel tests
mvn clean test -DsuiteXmlFile=src/test/resources/ParallelSuite.xml
```

### Browser Configuration

The project supports multiple browsers:
- `CHROME` (default)
- `FIREFOX`
- `EDGE`

Set browser using system property:
```bash
mvn clean test -DbrowserName=FIREFOX
```

## Allure Report Generation

### Method 1: Quick Local View (Temporary Server)

```bash
# Generate and serve report immediately
allure serve target/allure-results
```

This command:
- Generates a temporary report
- Starts a local web server
- Opens the report in your default browser
- Report is deleted when server stops

### Method 2: Generate and Save Report

```bash
# Generate report to specific directory
allure generate target/allure-results --clean -o target/allure-report

# Open the generated report
allure open target/allure-report
```

### Method 3: Using Maven Plugin

```bash
# Generate report using Maven
mvn allure:report

# Serve report using Maven
mvn allure:serve
```

### Report Features

The Allure report provides:
- **Overview Dashboard** - Test execution summary
- **Test Suites** - Organized test results
- **Graphs** - Visual representation of test trends
- **Timeline** - Test execution timeline
- **Behaviors** - BDD-style test organization
- **Packages** - Test organization by package structure
- **Categories** - Failed test categorization

## CI/CD Pipeline with GitHub Actions

### Current Workflow Configuration

The project includes a GitHub Actions workflow (`.github/workflows/run-CI.yml`) that:

1. **Triggers on:**
   - Push to `main` branch
   - Pull requests to `main` branch
   - Manual workflow dispatch with browser selection

2. **Execution Steps:**
   - Sets up Java 11 environment
   - Builds project with Maven
   - Runs tests with specified browser
   - Generates Allure report
   - Deploys report to GitHub Pages

### Enhanced Workflow Configuration

For better CI/CD integration, consider this enhanced workflow:

```yaml
name: Continuous-Integration-Enhanced

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]
  workflow_dispatch:
    inputs:
      browserName:
        description: 'Browser type'
        default: 'CHROME'
        required: false
        type: choice
        options:
          - CHROME
          - FIREFOX
          - EDGE
      testSuite:
        description: 'Test Suite'
        default: 'Suite.xml'
        required: false
        type: choice
        options:
          - Suite.xml
          - ParallelSuite.xml

jobs:
  test:
    runs-on: ubuntu-latest
    
    steps:
      - name: Checkout code
        uses: actions/checkout@v4

      - name: Set up JDK 11
        uses: actions/setup-java@v4
        with:
          java-version: '11'
          distribution: 'temurin'
          cache: maven

      - name: Install dependencies
        run: mvn clean compile test-compile

      - name: Run tests
        run: |
          mvn clean test \
            -DbrowserName=${{ inputs.browserName || 'CHROME' }} \
            -DsuiteXmlFile=src/test/resources/${{ inputs.testSuite || 'Suite.xml' }}
        continue-on-error: true

      - name: Get Allure history
        uses: actions/checkout@v4
        if: always()
        continue-on-error: true
        with:
          ref: gh-pages
          path: gh-pages

      - name: Generate Allure Report
        uses: simple-elf/allure-report-action@master
        if: always()
        with:
          allure_results: target/allure-results
          allure_report: allure-report
          gh_pages: gh-pages
          allure_history: allure-history
          keep_reports: 20

      - name: Deploy to GitHub Pages
        uses: peaceiris/actions-gh-pages@v3
        if: always()
        with:
          github_token: ${{ secrets.GITHUB_TOKEN }}
          publish_dir: allure-history
          publish_branch: gh-pages

      - name: Upload Allure Results
        uses: actions/upload-artifact@v4
        if: always()
        with:
          name: allure-results
          path: target/allure-results
          retention-days: 30
```

### Setting Up GitHub Pages

1. Go to your repository **Settings**
2. Navigate to **Pages** section
3. Set **Source** to "Deploy from a branch"
4. Select **gh-pages** branch
5. Set folder to **/ (root)**
6. Save the configuration

Your Allure reports will be available at: `https://yourusername.github.io/your-repository-name`

### Environment Variables

Configure these repository secrets/variables:

- `GITHUB_TOKEN` - Automatically provided by GitHub
- `ALLURE_RESULTS_PATH` - Custom path for allure results (optional)

## Best Practices

### 1. Test Organization

```java
@Epic("E-Commerce Platform")
@Feature("User Authentication")
public class LoginTests extends BaseTest {
    
    @Test
    @Story("Successful Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User should be able to login with valid credentials")
    public void successfulLogin() {
        // Test implementation
    }
}
```

### 2. Step Annotations

```java
@Step("Navigate to login page")
public LoginPage navigateToLogin() {
    // Implementation
    return new LoginPage(driver);
}

@Step("Enter credentials: username={0}, password=***")
public void enterCredentials(String username, String password) {
    // Implementation
}
```

### 3. Attachments

```java
@Attachment(value = "Screenshot", type = "image/png")
public byte[] takeScreenshot() {
    return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
}

@Attachment(value = "Page Source", type = "text/html")
public String getPageSource() {
    return driver.getPageSource();
}
```

### 4. Test Data Management

```java
@DataProvider(name = "loginData")
public Object[][] getLoginData() {
    return new Object[][] {
        {"validUser", "validPass", true},
        {"invalidUser", "validPass", false},
        {"validUser", "invalidPass", false}
    };
}
```

### 5. Parallel Execution

Configure TestNG for parallel execution:

```xml
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="ParallelSuite" parallel="methods" thread-count="3">
    <test name="ParallelTest">
        <classes>
            <class name="co.com.training.web.tests.RegistrationLoginTests"/>
        </classes>
    </test>
</suite>
```

## Troubleshooting

### Common Issues and Solutions

#### 1. Allure Results Not Generated

**Problem:** No allure-results directory created after test execution.

**Solution:**
- Ensure AspectJ weaver is properly configured
- Check system property `allure.results.directory`
- Verify Allure TestNG dependency is included

#### 2. Empty Allure Report

**Problem:** Report generates but shows no tests.

**Solution:**
- Check if tests are actually running
- Verify allure-results directory contains JSON files
- Ensure proper Allure annotations are used

#### 3. GitHub Actions Deployment Fails

**Problem:** Report not deploying to GitHub Pages.

**Solution:**
- Verify GitHub Pages is enabled
- Check repository permissions
- Ensure gh-pages branch exists
- Verify GITHUB_TOKEN permissions

#### 4. Browser Driver Issues

**Problem:** WebDriver not found or incompatible version.

**Solution:**
- Update WebDriverManager version
- Check browser version compatibility
- Ensure proper browser is installed in CI environment

#### 5. Maven Build Failures

**Problem:** Build fails with dependency issues.

**Solution:**
```bash
# Clear Maven cache
mvn dependency:purge-local-repository

# Reinstall dependencies
mvn clean install -U
```

### Debug Commands

```bash
# Verbose Maven execution
mvn clean test -X

# Skip tests but generate report from existing results
mvn allure:report -Dmaven.test.skip=true

# Check Allure installation
allure --version

# Validate TestNG suite
java -cp "target/test-classes:target/classes" org.testng.TestNG src/test/resources/Suite.xml
```

## Additional Resources

- [Allure TestNG Documentation](https://docs.qameta.io/allure/#_testng)
- [TestNG Documentation](https://testng.org/doc/)
- [Selenium WebDriver Documentation](https://selenium-python.readthedocs.io/)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Maven Surefire Plugin](https://maven.apache.org/surefire/maven-surefire-plugin/)

---

**Note:** This project is configured to run using `Suite.xml` file by default. For parallel execution, use `ParallelSuite.xml` which is configured for REMOTE browser mode to run test cases in parallel.
