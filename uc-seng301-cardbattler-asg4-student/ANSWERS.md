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

#### What is its goal in the code?

- so that when ever a action is taken in the game the cards of that game can react to the action.
- allows cards to activate there ability's.

#### observer.png 
class Game {
+listenForActions(card: Card)
+stopListeningForActions(card: Card)
+actionTrigger(playState: PlayState, actor: Card, target: Card)
}

abstract class Card {
+reactToAction(playState: PlayState, actor: Card, target: Card, actorIsAlly: boolean, targetIsAlly: boolean): boolean
}

Card -d* Game

![observer patten diagram](diagrams/observer.png)

#### Mapping to GoF pattern elements

| GoF element      | Code element                                                                                                                                          |
|------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------|
| Subject          | Game                                                                                                                                                  |
| ConcreteSubject  | Game	(no seprate of concreate and non-concreate)                                                                                                      | 
 | subjectState     | action (not stored long term)                                                                                                                         |
| attach           | listenForActions                                                                                                                                      |
| detach           | stopListeningForActions                                                                                                                               |
| notify           | actionTrigger                                                                                                                                         |
| Observer         | Card                                                                                                                                                  |
| ConcreteObserver | Card   (allthough Card is an abstract the implentation of the reactToAction is defined in card and non of it's children overright that implentation.) |
| update           | reactToAction                                                                                                                                         |
| observerState    |                                                                                                                                                       |

### Pattern 2

#### What pattern is it?

**Strategy**

#### What is its goal in the code?

-so that matiple types of AI's can exist each with diffrent behavours.
-so that the diffrent AI's all have to follow the same rules which is defined in the game.

#### strategy.png

@startuml

skinparam classAttributeIconSize 0


interface PlayerAIOperation {
+execute(allyBoard: Board, enemyBoard: Board, numCardsPlayed: int)
}

class Player {

}

PlayerAIOperation "1"---"1" Player

PlayStyles::recklessAI -|> PlayerAIOperation

@enduml
![Strategy patten diagram](diagrams/strategy.png)
(only includes recklessAI there are 3 others as in table below.)

#### Mapping to GoF pattern elements

| GoF element       | Code element                   |
|-------------------|--------------------------------|
| Strategy          | PlayerAIOperation              |
| execute           | execute                        |
| Context           | Player                         |
| ConcreteStrategyA | PlayStyles::basicAI            |
| ConcreteStrategyB | PlayStyles::monsterFavouringAI |
| ConcreteStrategyC | PlayStyles::setupFavouringAI   |
| ConcreteStrategyD | PlayStyles::recklessAI         |

instead of the ConcreteStrategy being explicitly defined as classes they are only implicitly defined as classes. as in java a interface with only one function can be set to a function with the same signature as the interfaces function. this means that the functions in PlayStyles are implicitly implements PlayerAIOperation as they have the same method signature, and are used as the values for the PlayerAIOperation object inside the Player Object.

## Task 2 - Full UML Class diagram

### retroDoucument.svg

![retro Document](diagrams/retroDoucument.svg)

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
