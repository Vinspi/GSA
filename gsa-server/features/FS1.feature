Feature: add a new aliquote

  @smoke
  Scenario: Add a new aliquote to database
    When I open browser and enter valid "rosa@univ-amu.fr" and valid "pantoufle"
    Then user should be able to connect successfully