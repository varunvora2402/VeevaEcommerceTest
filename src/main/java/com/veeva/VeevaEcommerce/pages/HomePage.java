package com.veeva.VeevaEcommerce.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

public class HomePage {
	private static final Logger logger = Logger.getLogger(HomePage.class.getName());
    WebDriver driver;

    By searchBox = By.id("twotabsearchtextbox");
    By searchButton = By.id("nav-search-submit-button");
    By results = By.cssSelector(".s-main-slot");
    By sortOptions = By.cssSelector("#s-result-sort-select option");
    By sortDropdownButton = By.xpath("//span[contains(text(),'Sort by:')]");
    By noResultsMessage = By.xpath("//span[contains(text(), 'No results for')]");
    By suggestions = By.cssSelector(".autocomplete-results-container");
    By paginationLinks = By.cssSelector(".s-pagination-strip a");
    By currentPage = By.cssSelector(".s-pagination-item.s-pagination-selected");
    By specificBook = By.xpath("//*[contains(text(), 'Hands-On Selenium WebDriver with Java: A Deep Dive into the Development of End-to-End Tests')]");
    By priceElements = By.cssSelector(".s-main-slot .a-price-whole");
    

    
    public By getSearchBox() {
		return searchBox;
	}

	public void setSearchBox(By searchBox) {
		this.searchBox = searchBox;
	}

	public By getSearchButton() {
		return searchButton;
	}

	public void setSearchButton(By searchButton) {
		this.searchButton = searchButton;
	}

	public By getResults() {
		return results;
	}

	public void setResults(By results) {
		this.results = results;
	}

	public By getSortOptions() {
		return sortOptions;
	}

	public void setSortOptions(By sortOptions) {
		this.sortOptions = sortOptions;
	}

	public By getSortDropdownButton() {
		return sortDropdownButton;
	}

	public void setSortDropdownButton(By sortDropdownButton) {
		this.sortDropdownButton = sortDropdownButton;
	}

	public By getNoResultsMessage() {
		return noResultsMessage;
	}

	public void setNoResultsMessage(By noResultsMessage) {
		this.noResultsMessage = noResultsMessage;
	}

	public By getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(By suggestions) {
		this.suggestions = suggestions;
	}

	public By getPaginationLinks() {
		return paginationLinks;
	}

	public void setPaginationLinks(By paginationLinks) {
		this.paginationLinks = paginationLinks;
	}

	public By getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(By currentPage) {
		this.currentPage = currentPage;
	}

	public By getSpecificBook() {
		return specificBook;
	}

	public void setSpecificBook(By specificBook) {
		this.specificBook = specificBook;
	}

	public By getPriceElements() {
		return priceElements;
	}

	public void setPriceElements(By priceElements) {
		this.priceElements = priceElements;
	}

	public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void searchFor(String term) {
        driver.findElement(searchBox).sendKeys(term);
        driver.findElement(searchButton).click();
    }

    public void typeSearchTerm(String term) {
        driver.findElement(searchBox).sendKeys(term);
    }

    public boolean resultsAreDisplayed() {
        return driver.findElement(results).isDisplayed();
    }

    public boolean noResultsMessageIsDisplayed() {
        return driver.findElement(noResultsMessage).isDisplayed();
    }

    public boolean suggestionsAreDisplayed() {
        return driver.findElement(suggestions).isDisplayed();
    }

    public boolean paginationIsDisplayed() {
    	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
         wait.until(ExpectedConditions.visibilityOfElementLocated(paginationLinks));
         return driver.findElement(paginationLinks).isDisplayed();
    }
    
    public void navigateToNextPage() {
        List<WebElement> paginationLinksList = driver.findElements(paginationLinks);
        for (WebElement pageLink : paginationLinksList) {
            if (pageLink.getText().equalsIgnoreCase("Next")) {
                pageLink.click();
                break;
            }
        }
    }
    
    public int getCurrentPageNumber() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(currentPage));
        String pageNumberText = driver.findElement(currentPage).getText();
        return Integer.parseInt(pageNumberText);
    }

    public boolean isNextPageButtonPresent() {
        List<WebElement> paginationLinksList = driver.findElements(paginationLinks);
        for (WebElement pageLink : paginationLinksList) {
            if (pageLink.getText().equalsIgnoreCase("Next")) {
                return true;
            }
        }
        return false;
    }
    
    public void submitEmptySearch() {
        driver.findElement(searchButton).click();
    }
    
    public boolean isSpecificBookDisplayed() {
        return driver.findElement(specificBook).isDisplayed();
    }
    
    public boolean resultsContainTerm(String term) {
    	  if (driver.getPageSource().toLowerCase().contains(term.toLowerCase())) {
    		  logger.info("Found term '" + term + "' in page.");
    	  }
        return driver.getPageSource().toLowerCase().contains(term.toLowerCase());
    }
    
    public void sortBy(String sortOption) {
        driver.findElement(sortDropdownButton).click();

        // Wait for the options to be visible and then select the desired option
        List<WebElement> options = driver.findElements(sortOptions);
        for (WebElement option : options) {
            if (option.getText().contains(sortOption)) {
                option.click();
                logger.info("Selected sort option: " + sortOption);
                break;
            }
        }
    }
    
    
    public boolean isSortedByPriceHighToLow() {
    	 List<WebElement> priceElementsList = driver.findElements(priceElements);
         for (int i = 0; i < priceElementsList.size() - 1; i++) {
             double price1 = Double.parseDouble(priceElementsList.get(i).getText().replace(",", ""));
             double price2 = Double.parseDouble(priceElementsList.get(i + 1).getText().replace(",", ""));
             if (price1 > price2) {
            	 logger.warning("Prices are not sorted correctly: " + price1 + " > " + price2);
                 return false;
             }
         }
         logger.info("Prices are sorted correctly");
         return true;
    }
    
    public void applyBrandFilter(String brand) {
        WebElement brandFilterElement = driver.findElement(By.xpath("//li[@aria-label='" + brand + "']//i"));
        brandFilterElement.click();
        logger.info("Brand filter applied for: " + brand);
    }
    
    public boolean resultsContainBrand(String brand) {
        List<WebElement> resultElements = driver.findElements(By.cssSelector(".s-main-slot .s-title-instructions-style"));
        for (WebElement element : resultElements) {
            if (!element.getText().toLowerCase().contains(brand.toLowerCase())) {
                logger.warning("Found a result that does not contain the brand '" + brand + "': " + element.getText());
                return false;
            }
        }
        logger.info("All results contain the brand '" + brand + "'.");
        return true;
    }
}

