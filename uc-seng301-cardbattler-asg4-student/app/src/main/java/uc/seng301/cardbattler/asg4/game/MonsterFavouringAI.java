package uc.seng301.cardbattler.asg4.game;

import uc.seng301.cardbattler.asg4.model.Card;
import uc.seng301.cardbattler.asg4.model.Monster;

import java.util.Comparator;
import java.util.List;

public class MonsterFavouringAI implements PlayerAIOperation {
    /**
     * A Monster favouring play style that simply plays the first {@link Monster}
     * card in the hand against the
     * lowest life monster on the enemies board if target-able
     *
     * @param allyBoard      current player's board
     * @param enemyBoard     enemy's board
     * @param numCardsPlayed number of cards played so far in the turn
     * @return The action the play style chooses to execute
     */
    @Override
    public Action execute(Board allyBoard, Board enemyBoard, int numCardsPlayed) {
        if (numCardsPlayed == 1) // only play 1 card per turn
            return null;
        if (!allyBoard.getHand().isEmpty()) {
            Card cardToPlay;
            List<Card> monstersInHand = allyBoard.getHand().stream().filter(Monster.class::isInstance).toList();
            if (!monstersInHand.isEmpty())
                cardToPlay = monstersInHand.get(0);
            else
                cardToPlay = allyBoard.getHand().get(0);

            Card target = null;
            if (cardToPlay instanceof Monster && !enemyBoard.getMonsterSlots().isEmpty()) {
                target = enemyBoard.getMonsterSlots().stream().sorted(Comparator.comparingInt(Monster::getLife))
                        .toList().get(0);
            }
            return new Action(cardToPlay, target);
        }
        return null;
    }
}
