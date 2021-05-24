Feature: Shopping

  Scenario: I want to pick a product
    Given A product Shoe with price 100.00 in catalog
    And the product is available in warehouse in 20 units
    And the cart is empty
    When I pick 2 Shoes
    Then The cart should have size 1 and total cost 200.00

  Scenario: I want to remove a product
    Given A product Shoe with price 100.00 in catalog
    And the product is available in warehouse in 20 units
    And the cart is empty
    When I pick 2 Shoes
    And I remove the Shoes from the cart
    Then The cart should have size 0 and total cost 0.00