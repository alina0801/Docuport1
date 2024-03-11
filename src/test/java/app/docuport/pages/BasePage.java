package app.docuport.pages;

import app.docuport.utilities.BrowserUtilities;
import app.docuport.utilities.ConfigurationReader;
import app.docuport.utilities.DocuportConstants;
import app.docuport.utilities.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

    public class BasePage extends LoginPage {

        private static final Logger LOG = LogManager.getLogger();

        @FindBy(xpath = "//button[@type='submit']//span")
        public WebElement continueButton;

        @FindBy(xpath = "//span[.='Log out']/../..")
        public WebElement logoutButton;

        @FindBy(xpath = "//div[@class='v-data-footer__pagination']")
        public WebElement totalResultCount; //1-10 of 869

        @FindBy(xpath = "//div[@class='v-avatar primary']/following-sibling::span")
        public WebElement userIcon; //First and Last name from upper right

        @FindBy(xpath = "//a[@href='/profile']")
        public WebElement profileButton; //First and Last name from upper right

        @FindBy(xpath = "//span[.='First name']/..//following-sibling::div//span")
        public WebElement firstName; //First and Last name from upper right

        @FindBy(xpath = "//span[.='Last name']/..//following-sibling::div//span")
        public WebElement lastname; //First and Last name from upper right

        @FindBy(xpath = "//span[.='Search']")
        public WebElement searchButton;

        @FindBy(xpath = "//span[.='Download']")
        public WebElement downloadButton;

        @FindBy(xpath = "//label[.='All']")
        public WebElement allRadioButton;

        @FindBy(xpath = "//span[.=' Search ']")
        public WebElement updateSearchButton;

        @FindBy(xpath = "//label[.='Phone number']//following-sibling::input")
        public WebElement searchPhoneNumber;

        @FindBy(xpath = "//label[.='Email address']//following-sibling::input")
        public WebElement searchEmailAddress;

        @FindBy(xpath = "//div[@class='v-select__selection v-select__selection--comma']")
        public WebElement rowsPerPage;

        @FindBy(xpath = "//div[@class='v-input__icon v-input__icon--append']")
        public WebElement rowsPerPageDropDown;


        public List<WebElement> getNavigationItems() {
            return Driver.getDriver().findElements(By.xpath("//div[@role='listbox']//a"));
        }


        public WebElement getSearchButton() {
            return Driver.getDriver().findElement(By.xpath("//span[.='Search']"));
        }

        public WebElement getDownloadButton() {
            return Driver.getDriver().findElement(By.xpath("//span[.='Download']"));
        }

        public void navigateItem(String itemName) {
            openLeftNavigationPanel();
            for (WebElement each : Driver.getDriver().findElements(By.xpath("//div[@role='listbox']//a"))) {
                if (each.getText().equals(itemName)) {
                    each.click();
                }
            }
            if (itemName.equals("Home")) {
                closeLeftNavigationPanel();
            }
        }

        public void openLeftNavigationPanel() {
            WebElement hamburgerButton = Driver.getDriver().findElement(By.xpath("//i[@class='v-icon notranslate mdi mdi-menu theme--light']/../.."));
            BrowserUtilities.clickWithJS(hamburgerButton);
            BrowserUtilities.waitFor(1);
        }

        public void closeLeftNavigationPanel() {
            WebElement hamburgerButton = Driver.getDriver().findElement(By.xpath("//i[@class='v-icon notranslate mdi mdi-menu theme--light']/../.."));
            BrowserUtilities.clickWithJS(hamburgerButton);
            BrowserUtilities.waitFor(1 / 2);
        }

        public int getResultCount() { //1-10 of 869 ----> 869
            String expectedCount = totalResultCount.getText().substring(totalResultCount.getText().lastIndexOf(" ") + 1);
            return Integer.parseInt(expectedCount);
        }
        //logout from all userTypes
        public void logout() {
            BrowserUtilities.waitForVisibility(userIcon, DocuportConstants.small);
            BrowserUtilities.clickWithJS(userIcon);
            BrowserUtilities.waitForVisibility(logoutButton, DocuportConstants.small);
            BrowserUtilities.clickWithJS(logoutButton);
            BrowserUtilities.waitForVisibility(username, DocuportConstants.small);
            Assert.assertEquals(Driver.getDriver().getCurrentUrl(), ConfigurationReader.getProperty("url") + "/login");
        }

        public String getElementText(String text) {
            String xpath = "//*[normalize-space()='" + text + "']";
            return Driver.getDriver().findElement(By.xpath(xpath)).getText();
        }

        public WebElement getElement(String text) {
            String xpath = "//*[normalize-space()='" + text + "']";
            return Driver.getDriver().findElement(By.xpath(xpath));
        }

        public String getPageHeaderText() {
            return Driver.getDriver().findElement(By.xpath("//div[@class='d-page__header']//h1")).getText();
        }

        public void clickButton(String button) {
            switch (button.toLowerCase()) {
                case "login":
                    BrowserUtilities.waitForClickable(Driver.getDriver().findElement(By.xpath("//span[.=' Log in ']/..")), DocuportConstants.small).click();
                    break;
                case "logout":
                    BrowserUtilities.waitForVisibility(Driver.getDriver().findElement(By.xpath("//span[.='Log out']/../..")), DocuportConstants.small).sendKeys(Keys.ENTER);
                    break;
                case "user icon":
                    BrowserUtilities.waitForVisibility(Driver.getDriver().findElement(By.xpath("//span[@class='body-1 white--text']")), DocuportConstants.small).click();
                    break;
                case "continue":
                    BrowserUtilities.waitForClickable(Driver.getDriver().findElement(By.xpath("//button[@type='submit']//span")), DocuportConstants.large);
                    BrowserUtilities.clickWithJS(Driver.getDriver().findElement(By.xpath("//button[@type='submit']//span")));
                    BrowserUtilities.waitFor(1 / 2);
                    break;

                case "home":
                    navigateItem("Home");
                    break;
                case "invitations":
                    navigateItem("Invitations");
                    break;
                case "received docs":
                    navigateItem("Received docs");
                    break;
                case "my uploads":
                    navigateItem("My uploads");
                    break;
                case "clients":
                    navigateItem("Clients");
                    break;
                case "leads":
                    navigateItem("Leads");
                    break;
                case "users":
                    navigateItem("Users");
                    break;
                case "1099 form":
                    navigateItem("1099 Form");
                    break;
                default:
                    LOG.error("No such " + button + "exists");
                    throw new IllegalArgumentException();
            }
            BrowserUtilities.waitFor(1);
        }

        public String getRowsPerPage (){
            return Driver.getDriver().findElement(By.xpath("//div[@class='v-select__selection v-select__selection--comma']")).getText();
        }

        public void changeRowsPerPage (String rowCount){
            //click drop down
            BrowserUtilities.waitForClickable(Driver.getDriver().findElement(By.xpath("//div[@class='v-input__icon v-input__icon--append']")),DocuportConstants.small).click();
            //select number
            BrowserUtilities.waitForClickable(Driver.getDriver().findElement(By.xpath("//div[.='"+rowCount+"']//div//div")),DocuportConstants.small).click();

        }


    }

