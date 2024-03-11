package app.docuport.pages.docuport_pages;


import app.docuport.step_definitions.docuport_stepdefs.ClientStepDefs;
import app.docuport.utilities.BrowserUtilities;
import app.docuport.utilities.ConfigurationReader;
import app.docuport.utilities.DocuportConstants;
import app.docuport.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class LoginPage {

    public LoginPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//label[.='Username or email']//following-sibling::input")
    public WebElement username;

    @FindBy(xpath = "//label[.='Password']//following-sibling::input")
    public WebElement password;

    @FindBy(xpath = "//span[.=' Log in ']/..")
    public WebElement loginButton;

    public void loginDocuport(String usertype) {

        //get test env
        Driver.getDriver().get(ConfigurationReader.getProperty("url"));

        //wait for username to be visible
        BrowserUtilities.waitForVisibility(username, DocuportConstants.small);

        //clear username field
        username.clear();

        //assigns temp to the email string for each userType
        String temp = switch (usertype) {
            case "client" -> DocuportConstants.USERNAME_CLIENT;
            case "employee" -> DocuportConstants.USERNAME_EMPLOYEE;
            case "supervisor" -> DocuportConstants.USERNAME_SUPERVISOR;
            case "advisor" -> DocuportConstants.USERNAME_ADVISOR;
            case "newly created client" -> ClientStepDefs.list.get(2);
            default -> {
                try {
                    throw new Exception("Invalid Role");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        //Sends username keys
        username.sendKeys(temp);

        //clear and send password
        password.clear();
        password.sendKeys(DocuportConstants.getPassword());

        //wait for login button to be clickable and click
        BrowserUtilities.waitForClickable(loginButton, DocuportConstants.small).click();
        BrowserUtilities.waitFor(1 );


        //if client, click continue
        if (usertype.equals("client")) {
            BrowserUtilities.waitForVisibility(Driver.getDriver().findElement(By.xpath("//button[@type='submit']//span")), DocuportConstants.large);
            BrowserUtilities.clickWithJS(Driver.getDriver().findElement(By.xpath("//button[@type='submit']//span")));
            BrowserUtilities.waitFor(1 / 2);
        } else {
            //closes left navigation items so landing page is visible
            Driver.getDriver().findElement(By.xpath("//button[@class='pa-0 v-btn v-btn--rounded v-btn--text theme--light v-size--default gray--text']")).sendKeys(Keys.ENTER, Keys.ENTER);
        }
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public void loginDocuport(String username, String password) {
        Driver.getDriver().get(ConfigurationReader.getProperty("url"));
        BrowserUtilities.waitForVisibility(this.username, DocuportConstants.small);
        this.username.clear();
        this.username.sendKeys(username);
        this.password.clear();
        this.password.sendKeys(password);
        BrowserUtilities.waitForClickable(loginButton, DocuportConstants.small);
        loginButton.click();
        BrowserUtilities.waitFor(1 / 2);
    }
}
