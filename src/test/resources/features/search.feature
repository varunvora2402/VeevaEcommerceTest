Feature: Search functionality on Amazon

  Scenario: Valid search term
    Given the user is on the Amazon home
    When the user enters a valid search term
    Then search results related to the term are displayed

  Scenario: Invalid search term
    Given the user is on the Amazon home
    When the user enters an invalid search term
    Then a message indicating no results found is displayed

  Scenario: Search suggestions
    Given the user is on the Amazon home
    When the user starts typing a search term
    Then search suggestions are displayed

  Scenario: Search results pagination
    Given the user is on the Amazon home
    When the user enters a search term that returns multiple pages of results
    Then the user can navigate through the search results pages
    
  Scenario: Empty search term
    Given the user is on the Amazon home
    When the user submits an empty search term
    Then the page is refreshed and stays on the same page
    
  Scenario: ISBN 13 search term
    Given the user is on the Amazon home
    When the user enters an ISBN 13 number
    Then the specific book related to the ISBN 13 number is displayed
    
  Scenario Outline: Search term variations
    Given the user is on the Amazon home
    When the user enters "<searchTerm>"
    Then search results related to "laptop" are displayed

    Examples:
      | searchTerm |
      | ldptop     |
      | laptol     |
      | laotop     |
      | lptop      |
      
  Scenario: Search with synonyms
    Given the user is on the Amazon home
    When the user enters "couch"
	  Then search results related to "sofa" are displayed
	
  Scenario: Search in different languages
    Given the user is on the Amazon home
    When the user enters "matka"
	  Then search results related to "clay pot" are displayed

  Scenario: Search and sort results
    Given the user is on the Amazon home
    When the user enters "camera"
    And the user sorts the results by "Price: High to low"
    Then the user should see search results for "camera" sorted by price in ascending order
    
  Scenario: Search with filters applied
    Given the user is on the Amazon home
    When the user enters "camera"
    And the user applies a brand filter for "Sony"
    Then the user should see search results for "camera" that are by "Sony"