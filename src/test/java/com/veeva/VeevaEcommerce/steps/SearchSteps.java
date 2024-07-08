package com.veeva.VeevaEcommerce.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.junit.Assert;

import com.veeva.VeevaEcommerce.pages.HomePage;
import java.time.Duration;

public class SearchSteps {

    WebDriver driver;
    HomePage homePage;
    WebDriverWait wait;
    
    
    private void pauseAndQuit() {
        try {
            Thread.sleep(2000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }
    @Given("the user is on the Amazon home")
    public void the_user_is_on_the_home() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.amazon.ca");
    }

    @When("the user enters a valid search term")
    public void the_user_enters_a_valid_search_term() {
        homePage.searchFor("laptop");
    }

    @Then("search results related to the term are displayed")
    public void search_results_related_to_the_term_are_displayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search")));
        Assert.assertTrue(homePage.resultsAreDisplayed());
        pauseAndQuit();
    }

    @When("the user enters an invalid search term")
    public void the_user_enters_an_invalid_search_term() {
        homePage.searchFor("asdkjfhaskdjf46tff5nfdu77'cvgrhdvtg");
    }

    @Then("a message indicating no results found is displayed")
    public void a_message_indicating_no_results_found_is_displayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePage.getNoResultsMessage()));
        Assert.assertTrue(homePage.noResultsMessageIsDisplayed());
        pauseAndQuit();
    }

    @When("the user starts typing a search term")
    public void the_user_starts_typing_a_search_term() {
        homePage.typeSearchTerm("lap");
    }

    @Then("search suggestions are displayed")
    public void search_suggestions_are_displayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePage.getSuggestions()));
        Assert.assertTrue(homePage.suggestionsAreDisplayed());
        pauseAndQuit();
    }

    @When("the user enters a search term that returns multiple pages of results")
    public void the_user_enters_a_search_term_that_returns_multiple_pages_of_results() {
        homePage.searchFor("laptop");
    }

    @Then("the user can navigate through the search results pages")
    public void the_user_can_navigate_through_the_search_results_pages() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(homePage.getPaginationLinks()));
        Assert.assertTrue(homePage.paginationIsDisplayed());

        int initialPageNumber = homePage.getCurrentPageNumber();

        if (homePage.isNextPageButtonPresent()) {
            homePage.navigateToNextPage();
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(homePage.getCurrentPage()));
            
            int newPageNumber = homePage.getCurrentPageNumber();

            Assert.assertTrue(newPageNumber > initialPageNumber);

            try {
                Thread.sleep(2000);  
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        pauseAndQuit();
    }
    
    @When("the user submits an empty search term")
    public void the_user_submits_an_empty_search_term() {
        homePage.submitEmptySearch();
    }

    @Then("the page is refreshed and stays on the same page")
    public void the_page_is_refreshed_and_stays_on_the_same_page() {
        String initialUrl = driver.getCurrentUrl();
        homePage.submitEmptySearch();
        String refreshedUrl = driver.getCurrentUrl();
        Assert.assertEquals(initialUrl, refreshedUrl);
        pauseAndQuit();
    }
    

    @When("the user enters an ISBN 13 number")
    public void the_user_enters_an_isbn_13_number() {
        homePage.searchFor("978-1098110000");
    }

    @Then("the specific book related to the ISBN 13 number is displayed")
    public void the_specific_book_related_to_the_isbn_13_number_is_displayed() {
    	 wait.until(ExpectedConditions.visibilityOfElementLocated(homePage.getSpecificBook()));
        Assert.assertTrue(homePage.isSpecificBookDisplayed());
        pauseAndQuit();
    }
    
    @When("the user enters {string}")
    public void the_user_enters(String searchTerm) {
        homePage.searchFor(searchTerm);
    }

    @Then("search results related to {string} are displayed")
    public void search_results_related_to_are_displayed(String expectedTerm) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search")));
        Assert.assertTrue(homePage.resultsContainTerm(expectedTerm));
        pauseAndQuit();
    }
    
    @When("the user sorts the results by {string}")
    public void i_sort_the_results_by(String sortOption) {
        homePage.sortBy(sortOption);
    }
    
    @Then("the user should see search results for {string} sorted by price in ascending order")
    public void i_should_see_search_results_sorted_by_price_in_ascending_order(String searchTerm) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search")));
        Assert.assertTrue(homePage.resultsContainTerm(searchTerm));
        Assert.assertTrue(homePage.isSortedByPriceHighToLow());
        pauseAndQuit();
    }
    
    @When("the user applies a brand filter for {string}")
    public void the_user_applies_a_brand_filter_for(String brand) {
        homePage.applyBrandFilter(brand);
    }
    
    @Then("the user should see search results for {string} that are by {string}")
    public void i_should_see_search_results_for_that_are_by(String searchTerm, String brand) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search")));
        Assert.assertTrue(homePage.resultsContainTerm(searchTerm));
        Assert.assertTrue(homePage.resultsContainBrand(brand));
        pauseAndQuit();
    }
}

