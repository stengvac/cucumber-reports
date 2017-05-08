Feature:
  Background:
    Given I have personal data
      # phone number is in format +62 (xxx) xxxx-xxxx
      | id | firstName   |
      | p1 | Jana |

    Given I have another personal data
      | id | secondName |
      | p2 | Jano  |

  Scenario Outline: Test is run
    Given I started my calculation
    When I execute operation "<operation>" on "<firstNum>" and "<secNum>"

    Examples: ddd
    fff
      | firstNum | secNum | operation |
      | 64       | 55     | lcm       |
