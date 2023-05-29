Feature: U4 - As Alex, I want to have cards with a diverse range of abilities.

  Scenario Outline: AC1 - A card must have at least 1 ability and at most 3 abilities
    Given I have a deck
    And Random ability count of <count> chose ability <ability_number> and <next>
    When I populate a battle deck
    Then Each card has at least 1 abilities And at most 3 abilities
    Examples:
      | count | ability_number |  next |
      | 3     | 0              | false |
      | 2     | 0              | false |
      | 2     | 1              | false |
      | 2     | 2              | false |
      | 2     | 0              | true  |
      | 2     | 1              | true  |
      | 2     | 2              | true  |
      | 1     | 0              | false |
      | 1     | 1              | false |
      | 1     | 2              | false |

  Scenario Outline: AC2 - A card’s abilities are assigned at random
    Given I have a deck
    And Random ability count of <count> chose ability <ability_number> and <next>
    When I populate a battle deck
    Then A card’s abilities are assigned at random
    Examples:
    | count | ability_number |  next |
    | 3     | 0              | false |
    | 2     | 0              | false |
    | 2     | 1              | false |
    | 2     | 2              | false |
    | 2     | 0              | true  |
    | 2     | 1              | true  |
    | 2     | 2              | true  |
    | 1     | 0              | false |
    | 1     | 1              | false |
    | 1     | 2              | false |