package uc.seng301.cardbattler.asg4.game;

import uc.seng301.cardbattler.asg4.model.Card;
import uc.seng301.cardbattler.asg4.model.Spell;
import uc.seng301.cardbattler.asg4.model.Trap;

public class BasicAI implements PlayerAIOperation{
    /**
     * Basic play style that simply plays the first card in the hand against the
     * first monster in the enemies board if target-able
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
            Card selectedCard = null;
            if (!enemyBoard.getMonsterSlots().isEmpty()) {
                selectedCard = enemyBoard.getMonsterSlots().get(0);
            }
            return new Action(allyBoard.getHand().get(0),
                    allyBoard.getHand().get(0) instanceof Trap || allyBoard.getHand().get(0) instanceof Spell ? null
                            : selectedCard);
        }
        return null;
    }
}
