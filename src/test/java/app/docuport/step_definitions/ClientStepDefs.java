package app.docuport.step_definitions;

import app.docuport.pages.ClientPage;
import app.docuport.utilities.BrowserUtilities;
import com.github.javafaker.Faker;
import io.cucumber.java.en.When;

import java.util.List;

public class ClientStepDefs {

    ClientPage clientPage = new ClientPage();
    Faker faker = new Faker();
    
    public static List<String> list;
    @When("create a personal client")
    public void create_a_personal_client(List<String>list) {
        ClientStepDefs.list = list;
        clientPage.createPersonalClient(list.get(0),list.get(1),list.get(2),list.get(3),list.get(4));
        BrowserUtilities.waitFor(1);

    }

    @When("create a business client")
    public void create_a_business_client(List<String>list) {
        ClientStepDefs.list = list;
        clientPage.createBusinessClient(list.get(0),list.get(1),list.get(2),list.get(3),list.get(4),list.get(5));
        BrowserUtilities.waitFor(1);
    }
}



