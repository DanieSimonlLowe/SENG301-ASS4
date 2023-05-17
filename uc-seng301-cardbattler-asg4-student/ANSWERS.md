# SENG301 Assignment 4 (2023) - Student answers

**YOUR NAME**

## Task 1 - Identify the patterns in the code

### EXAMPLE PATTERN (this pattern is given as an example)

#### What pattern is it?

Proxy

#### What is its goal in the code?

This proxy pattern is used in the Yu-Gi-Oh app to:

- obtain real cards from an external system (Yu-Gi-Oh API), i.e. access control to cards supplied by API;
- create cards on demand, pruning what is not needed from the retrieved cards before passing them.

#### Name of UML Class diagram attached

./diagrams/yugioh-domain.png

#### Mapping to GoF pattern elements

| GoF element | Code element        |
| ----------- | ------------------- |
| Client      | BattleDeckCreator   |
| Subject     | CardGenerator       |
| Proxy       | CardProxy           |
| RealSubject | CardService         |
| request()   | getRandomCard()     |
| request()   | getRandomCardOfType |

### Pattern 1

#### What pattern is it?

**YOUR ANSWER**

#### What is its goal in the code?

**YOUR ANSWER**

#### Name of UML Class diagram attached

**YOUR ANSWER**

#### Mapping to GoF pattern elements

| GoF element | Code element |
| ----------- | ------------ |
|             |              |

### Pattern 2

#### What pattern is it?

**YOUR ANSWER**

#### What is its goal in the code?

**YOUR ANSWER**

#### Name of UML Class diagram attached

**YOUR ANSWER**

#### Mapping to GoF pattern elements

| GoF element | Code element |
| ----------- | ------------ |
|             |              |

## Task 2 - Full UML Class diagram

### Name of file of full UML Class diagram attached

**YOUR ANSWER**

## Task 3 - Implement new feature

### What pattern fulfils the need for the feature?

**YOUR ANSWER**

### What is its goal and why is it needed here?

**YOUR ANSWER**

### Name of UML Class diagram attached

**YOUR ANSWER**

### Mapping to GoF pattern elements

| GoF element | Code element |
| ----------- | ------------ |
|             |              |

## Task 4 - BONUS - Acceptance tests for Task 4

### Feature file (Cucumber Scenarios)

**NAME OF FEATURE FILE**

### Java class implementing the acceptance tests

**NAME OF JAVA FILE**
