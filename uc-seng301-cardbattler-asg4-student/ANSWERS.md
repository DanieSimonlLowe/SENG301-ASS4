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

Observer
//TODO (redo if you have Time)
#### What is its goal in the code?

is used so that cards can react to actions in the game allowing them to execute there ablitys every turn.

#### Name of UML Class diagram attached

![observer patten diagram](diagrams/observer.png)

#### Mapping to GoF pattern elements

| GoF element       | Code element                                        |
| ----------------- |-----------------------------------------------------|
| Subject           | Game                                                |
| ConcreteSubject   | Game	(no seprate of concreate and non-concreate)    | 
 |subjectState | action (not stored)                                 |
| attach | listenForActions                                    |
|detach | stopListeningForActions                             |
|notify| actionTrigger                                       |
|Observer| Card                                                |
|update| reactToAction                                       |
|ConcreteObserver| Card   (no seprate of concreate and non-concreate)  |
|observerState|                                                     |

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
