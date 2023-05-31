Feature: U4 - As Alex, I want to have cards with a diverse range of abilities.

  Scenario: AC1 - A card must have at least 1 ability and at most 3 abilities
    Given I have a deck
    When I populate a battle deck
    Then Each card has at least 1 abilities And at most 3 abilities

  Scenario: AC2 - A card’s abilities are assigned at random
    Given I have a deck
    When I populate a battle deck
    Then A card’s abilities are assigned at random