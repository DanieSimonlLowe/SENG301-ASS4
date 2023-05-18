package uc.seng301.cardbattler.asg4.game;

import uc.seng301.cardbattler.asg4.model.Card;
import uc.seng301.cardbattler.asg4.model.Monster;
import uc.seng301.cardbattler.asg4.model.Spell;
import uc.seng301.cardbattler.asg4.model.Trap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RecklessAI implements PlayerAIOperation {
    /**
     * Number of turns a reckless AI will skip at the start of the game
     */
    private static int turnsToWait = 5;
    /**
     * A Reckless play style that has no limit on cards played each turn.
     * The play style will wait for a set number of turns and only play cards after
     * the <b>turnsToWait</b> count has
     * reached 0 in hopes of overwhelming the enemy
     *
     * @param allyBoard      current player's board
     * @param enemyBoard     enemy's board
     * @param numCardsPlayed number of cards played so far in the turn
     * @return The action the play style chooses to execute
     */
    @Override
    public Action execute(Board allyBoard, Board enemyBoard, int numCardsPlayed) {
        if (turnsToWait > 0) {
            turnsToWait--;
            return null;
        }
        if (!allyBoard.getHand().isEmpty()) {
            List<Card> cardsInTypeOrder = new ArrayList<>();
            cardsInTypeOrder.addAll(allyBoard.getHand().stream().filter(Spell.class::isInstance).toList());
            cardsInTypeOrder.addAll(allyBoard.getHand().stream().filter(Monster.class::isInstance).toList());
            cardsInTypeOrder.addAll(allyBoard.getHand().stream().filter(Trap.class::isInstance).toList());
            Card cardToPlay = cardsInTypeOrder.get(0);
            Card target = null;
            if (cardToPlay instanceof Monster && !enemyBoard.getMonsterSlots().isEmpty()) {
                target = enemyBoard.getMonsterSlots().stream()
                        .sorted(Comparator.comparingInt(Monster::getLife).reversed()).toList().get(0);
            }
            return new Action(cardToPlay, target);
        }
        return null;
    }
}
