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
