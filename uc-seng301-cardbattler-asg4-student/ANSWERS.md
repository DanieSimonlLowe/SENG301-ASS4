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
|-------------|---------------------|
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

- so that when ever an action is taken in the game the cards that have been played in that game can react to the action. meaning that cards can react to game stat and do things on certain conditions in the game state.

#### observer.png 
class Game {
+listenForActions(card: Card)
+stopListeningForActions(card: Card)
+actionTrigger(playState: PlayState, actor: Card, target: Card)
}

abstract class Card {
+reactToAction(playState: PlayState, actor: Card, target: Card, actorIsAlly: boolean, targetIsAlly: boolean): boolean
}

Card "observes"-> Game
Game "notifies"-> Card

![observer patten diagram](diagrams/observer.png)
#### Mapping to GoF pattern elements


| GoF element      | Code element                                                                                                                                                   |
|------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Subject          | Game                                                                                                                                                           |
| ConcreteSubject  | Game	(no separation of concrete and non-concrete subjects)                                                                                                     | 
 | subjectState     | NONE                                                                                                                                                           |
| attach           | listenForActions                                                                                                                                               |
| detach           | stopListeningForActions                                                                                                                                        |
| notify           | actionTrigger                                                                                                                                                  |
| Observer         | Card                                                                                                                                                           |
| ConcreteObserver | Card   (although Card is an abstract class the implementation of the reactToAction is defined in card and non of it's children overnight that implementation.) |
| update           | reactToAction                                                                                                                                                  |
| observerState    | NONE                                                                                                                                                           |

### Pattern 2

#### What pattern is it?

**Decorator**

#### What is its goal in the code?
- to make it so that Ability's can be easily made with a verity of conditions and effects. meaning that some effect that card dose can have any arbitrary combination of preconditions for it to fire.


#### Decorator.png
Interface Ability {
execute(abilityCard: Card, playState: PlayState, actor: Card, target: Card, actorIsAlly: boolean,targetIsAlly: boolean)
}

abstract class AbstractAbility {
#ability: Ability
}

AbstractAbility -|> Ability
Ability -* AbstractAbility

class BasicAbility {
+execute(abilityCard: Card, playState: PlayState, actor: Card, target: Card, actorIsAlly: boolean,targetIsAlly: boolean)
}

BasicAbility -|> Ability

class TotalTimes {
-numberOfTimesToTrigger: int
+execute(abilityCard: Card, playState: PlayState, actor: Card, target: Card, actorIsAlly: boolean,targetIsAlly: boolean)
+getNumberOfTimesToTrigger()
}

TotalTimes -u|> AbstractAbility

![Decorator patten diagram](diagrams/Decorator.png)
only includes one of the many concrete decorators for readability.

#### Mapping to GoF pattern elements

| GoF element        | Code element              |
|--------------------|---------------------------|
| Component          | Ability                   |
| ConcreteComponent  | BasicAbility              |
| operation          | execute                   |
| Decorator          | AbstractAbility           |
| ConcreteDecoratorA | TotalTimes                |
| addedStateA        | numberOfTimesToTrigger    |
| addedBehaviorA     | getNumberOfTimesToTrigger |
| ConcreteDecoratorB | ActorIsAllyOrEnemy        |
| addedStateB        | isAlly                    |
| ConcreteDecoratorC | CanTargetSelf             |
| addedStateC        | canTriggerOnSelf          |
| ConcreteDecoratorD | OnlyIfPlayState           |
| addedStateD        | playStates                |
| ConcreteDecoratorE | OnlyOnType                |
| addedStateE        | classes                   |
| ConcreteDecoratorF | TargetActor               |
| ConcreteDecoratorG | TargetIsAllyOrEnemy       |
| addedStateG        | isAlly                    |

## Task 2 - Full UML Class diagram

### retroDocument.png

![retro Document](diagrams/retroDocument.png)

## Task 3.1 - Implement new feature (CardResponse)

### What pattern fulfils the need for the feature?

**Factory Method**

### What is its goal and why is it needed here?

**So that the code for creating different types of card can be separated**

### factoryMethod.png
left to right direction
skinparam classAttributeIconSize 0

abstract class Card {}
Monster -u|> Card
abstract class CardCreator {
+toCard()
}
MonsterCreator -u|> CardCreator

MonsterCreator "makes"--d> Monster


![retro Document](diagrams/factoryMethod.png)

only includes monster and monster creator. this is also implemented for spell and trap cards.
### Mapping to GoF pattern elements

| GoF element      | Code element   |
|------------------|----------------|
| Product          | Card           |
| Creator          | CardCreator    |
| FactoryMethod    | toCard         | 
| ConcreteProductA | Monster        |
| ConcreteCreatorA | MonsterCreator |
| ConcreteProductB | Spell          |
| ConcreteCreatorB | SpellCreator   |
| ConcreteProductC | Trap           |
| ConcreteCreatorC | TrapCreator    |

## Task 3.2 - Implement new feature (PlayStyle)

### What pattern fulfils the need for the feature?

**strategy**

### What is its goal and why is it needed here?

- so that multiple types of AI's can exist each with different behaviours.
- so that the different AI's all have to follow the same rules which is defined in the game.

### strategy.png

left to right direction

skinparam classAttributeIconSize 0


interface PlayerAIOperation {
+execute(allyBoard: Board, enemyBoard: Board, numCardsPlayed: int): Action
}

class Player {

}

PlayerAIOperation -u* Player

basicAI -u|> PlayerAIOperation
monsterFavouringAI -u|> PlayerAIOperation
setupFavouringAI -u|> PlayerAIOperation
recklessAI -u|> PlayerAIOperation

![Strategy patten diagram](diagrams/strategy.png)
### Mapping to GoF pattern elements

| GoF element       | Code element       |
|-------------------|--------------------|
| Strategy          | PlayerAIOperation  |
| execute           | execute            |
| Context           | Player             |
| ConcreteStrategyA | basicAI            |
| ConcreteStrategyB | monsterFavouringAI |
| ConcreteStrategyC | setupFavouringAI   |
| ConcreteStrategyD | recklessAI         |

## Task 4 - BONUS - Acceptance tests for Task 4

### Feature file (Cucumber Scenarios)

**u4-random-card-abilities.feature**

### Java class implementing the acceptance tests

**RandomCardFeature.java**
