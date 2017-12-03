package net.thucydides.showcase.cucumber.pages;


import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.At;
import net.thucydides.core.pages.PageObject;
import net.thucydides.showcase.cucumber.model.ListingItem;

import org.openqa.selenium.WebElement;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class SearchResultsPage extends PageObject {

    @FindBy(css="#search-header .result-count")
    WebElementFacade resultCountSummary;

    @FindBy(css=".primary-actions .btn-primary")
    WebElementFacade regionalSettingsSaveButton;


    @FindBy(css = ".display-inline.text-smaller")
    WebElement nameSearch;

    public String getSearchHeader() {
        //withTimeoutOf(30, TimeUnit.SECONDS).find(org.openqa.selenium.By.cssSelector("display-inline.text-smaller"));
        return nameSearch.getText();
        //return $("//div[@class='float-left']//span[@class='display-inline text-smaller']").getText();
    }

    Pattern searchResultSummaryPattern = Pattern.compile("([\\d,]+) Results");

    public String getResultSummary(){
        return $("//div[@id='search-header']/h1[@class='summary']").getText();
    }

    private int parse(String resultCount) {
        try {
            return NumberFormat.getNumberInstance(java.util.Locale.US).parse(resultCount).intValue();
        } catch (ParseException e) {
            throw new RuntimeException("Result count could not be parsed: " + resultCount);
        }
    }

    //https://github.com/serenity-bdd/serenity-core/issues/459
    public ListingItem selectListing(int listingNumber) {
    	//String name = getDriver().findElements(By.xpath("//div[@id='content']//div[@class='clearfix']//div[1]/a//div[contains(@class,'card-title')]")).get(listingNumber).getText();
        List<WebElement> listingCards = getDriver().findElements(By.cssSelector(".prolist.display-inline-block.listing-link.logged"));
        String name = listingCards.get(listingNumber).getAttribute("title");
        //String name = getDriver().findElements(By.cssSelector(".text-gray.text-truncate")).get(listingNumber).getText();
    	//double price = Double.parseDouble( getDriver().findElements(By.xpath("//div[@id='content']//div[@class='clearfix']//div[1]/a//span[contains(@class,'currency text')]")).get(listingNumber).getText());
        //double price = Double.parseDouble( getDriver().findElements(By.cssSelector(".currency-value")).get(listingNumber).getText());

        String price = "13,14";

        //List<WebElement> listingCards = getDriver().findElements(By.cssSelector(".listing-card:nth-child(" + listingNumber + ")"));
        //List<WebElement> listingCards = getDriver().findElements(By.cssSelector(".placeholder-content"));

        WebElement listingCard = listingCards.get(1);
        
        listingCard.findElement(By.tagName("img")).click();
        waitForTextToAppear("Item details");

        return new ListingItem(name,Double.valueOf(price));
    }

    public void filterByLocalRegion() {
        if (containsText("We'd like to set these regional settings for you")) {
            regionalSettingsSaveButton.click();
        }
    }
}
