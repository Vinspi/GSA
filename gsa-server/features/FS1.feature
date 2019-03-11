Feature: add a new aliquote

  @smoke
  Scenario Outline: Add a new aliquote into database

    When I open browser and enter valid "rosa@univ-amu.fr" and valid "pantoufle"
    Then user should be able to connect successfully
    And I add new aliquote with "<NumLot>" "<price>" "<providerName>" "<QtyVisibleStock>" "<QtyHiddenStock>" and "<ProductName>"
    Then I logout

    Examples:
      | NumLot | price | providerName | QtyVisibleStock | QtyHiddenStock | ProductName      |
      | 1262   | 0.993 | Provider X   | 3               |                | WOLF_ANTI_MONKEY |
      | 2304   | 0.242 | Provider Y   |                 | 6              | WOLF_ANTI_MONKEY |
      | 9382   | 0.468 | Provider Z   | 13              | 5              | WOLF_ANTI_SHARK  |

