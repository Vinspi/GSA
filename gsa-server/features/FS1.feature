Feature: add a new aliquote

  Scenario: Add a new aliquote to database
    When I open browser and tap "http://localhost:4200/"
    Then i should see login page and login with "rosa@univ-amu.fr" and "pantoufle"