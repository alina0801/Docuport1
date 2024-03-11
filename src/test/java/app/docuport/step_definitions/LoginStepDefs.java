package app.docuport.step_definitions;
import app.docuport.pages.BasePage;
import app.docuport.utilities.BrowserUtilities;
import app.docuport.utilities.ConfigurationReader;
import app.docuport.utilities.DocuportConstants;
import app.docuport.utilities.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.util.Map;

public class LoginStepDefs {

        BasePage basePage = new BasePage();

        //------------------ Applies to all
        @Given("user is on Docuport login page")
        public void user_is_on_docuport_login_page() {
            Driver.getDriver().get(ConfigurationReader.getProperty("url"));
            BrowserUtilities.takeScreenshot();
        }

        @When("user enters username for {string}")
        public void userEntersUsernameFor(String string) throws Exception {
            String temp = switch (string) {
                case "client" -> DocuportConstants.USERNAME_CLIENT;
                case "employee" -> DocuportConstants.USERNAME_EMPLOYEE;
                case "supervisor" -> DocuportConstants.USERNAME_SUPERVISOR;
                case "advisor" -> DocuportConstants.USERNAME_ADVISOR;
                default -> throw new Exception("Invalid Role");
            };
            basePage.username.sendKeys(temp);
        }

        @When("user enters password for {string}")
        public void user_enters_password_for(String string) {
            basePage.password.sendKeys(DocuportConstants.getPassword());
        }

    @Then("user clicks {string} button")
    public void user_clicks_button(String buttonText) {
        basePage.clickButton(buttonText);
        }

        @Then("user should see the home page for {string}")
        public void user_should_see_the_home_page_for(String string) {
            if (string.equals("client")) {
                BrowserUtilities.waitFor(3);
                BrowserUtilities.waitForClickable(basePage.continueButton, DocuportConstants.large).click();
            } else {
                Driver.getDriver().findElement(By.xpath("//button[@class='pa-0 v-btn v-btn--rounded v-btn--text theme--light v-size--default gray--text']")).sendKeys(Keys.ENTER, Keys.ENTER);
            }
            BrowserUtilities.validateTitle(DocuportConstants.HOME_PAGE_TITLE);
            BrowserUtilities.takeScreenshot();
        }

        //---------------- Logout for all logins
        @When("user clicks the user logo")
        public void user_clicks_the_user_logo() {
            BrowserUtilities.waitForVisibility(Driver.getDriver().findElement(By.xpath("//button[@class='pa-0 v-btn v-btn--rounded v-btn--text theme--light v-size--default gray--text']")), DocuportConstants.small).click();
        }

        @When("the user clicks Log out")
        public void the_user_clicks() {
            BrowserUtilities.waitForVisibility(Driver.getDriver().findElement(By.xpath("//span[.='Log out']/../..")), DocuportConstants.small).sendKeys(Keys.ENTER);
        }

        @Then("the user return to the login page")
        public void the_user_return_to_the_login_page() {
            BrowserUtilities.waitForVisibility(basePage.username, DocuportConstants.small);
            Assert.assertEquals(DocuportConstants.LOGIN_PAGE_URL, Driver.getDriver().getCurrentUrl());
            BrowserUtilities.takeScreenshot();
        }

        //--
        @When("user enter credentials")
        public void user_enter_credentials(Map<String, String> credentials) {
//        for (Map.Entry<String,String> entry : credentials.entrySet()){
//            String key = entry.getKey();
//            String value = entry.getValue();
//            System.out.println("key = " + key);
//            System.out.println("value = " + value);
//        }
            basePage.loginDocuport(credentials.get("username"), credentials.get("password"));
        }

        @Then("user should see the home page")
        public void user_should_see_the_home_page_for() {
            BrowserUtilities.validateTitle(DocuportConstants.HOME_PAGE_TITLE);
            BrowserUtilities.takeScreenshot();
        }

        //--

        @Given("user logs into {string}")
        public void user_logs_into(String userType) {
            basePage.loginDocuport(userType);
        }

        @Then("logout")
        public void logout_from() {
            basePage.logout();
        }

        @Then("login as newly created user")
        public void login_as_newly_created_user() {
            basePage.loginDocuport(ClientStepDefs.list.get(2), ClientStepDefs.list.get(4));
            basePage.closeLeftNavigationPanel();
        }
    }

