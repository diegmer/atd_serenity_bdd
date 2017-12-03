package net.thucydides.showcase.cucumber.steps;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.jayway.restassured.http.ContentType;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.core.Serenity;
import net.thucydides.showcase.cucumber.model.Pet;

import static javax.swing.text.DefaultStyledDocument.ElementSpec.ContentType;
import static net.serenitybdd.rest.SerenityRest.rest;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;
import java.util.Random;

import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Response;
import org.junit.Assert;

/**
 * Created by john on 27/05/2015.
 */
public class PetStoreSteps {

    List<Pet> pets;

    @Given("I have the following pets?")
    public void i_have_the_following_pets(List<Pet> pets) {
        this.pets = Lists.newArrayList(pets);
    }

    @When("I add the pet to the store")
    public void i_add_the_pet_to_the_store() {
        for (Pet pet : pets) {
            int id = Math.abs(new Random().nextInt());

            String jsonPet = "{\n" +
                    "  \"id\": " + id + ",\n" +
                    "  \"category\": {\n" +
                    "    \"id\": 0,\n" +
                    "    \"name\": \"string\"\n" +
                    "  },\n" +
                    "  \"name\": \"" + pet.getName() + "\",\n" +
                    "  \"photoUrls\": [\n" +
                    "    \"string\"\n" +
                    "  ],\n" +
                    "  \"tags\": [\n" +
                    "    {\n" +
                    "      \"id\": 0,\n" +
                    "      \"name\": \"string\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"status\": \""
                    + pet.getStatus() + "\"}";

            Response response = rest()
                    .contentType("application/json")
                    .accept("application/json")
                    .body(jsonPet)
                    .when()
                    .post("http://petstore.swagger.io/v2/pet")
                    .then()
                    .extract()
                    .response();

            pet.setId(id);
        }
    }

    @Then("the pets? should be available in the store")
    public void pets_should_be_available() {
        for (Pet pet : pets) {
            rest().get("http://petstore.swagger.io/v2/pet/{id}", pet.getId())
                    .then().statusCode(200)
                    .and().body("name", equalTo(pet.getName()));
            Serenity.setSessionVariable("res").to(rest());
        }
    }

    @Then("^I get status code (\\d+)$")
    public void getStatusCode(int expectedStatusCode) throws Throwable {
        Response res = Serenity.sessionVariableCalled("res");
        Assert.assertEquals("Status code must be: " + expectedStatusCode, expectedStatusCode, res.getStatusCode());
    }
}
