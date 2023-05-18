package uc.seng301.cardbattler.asg4.game;

import uc.seng301.cardbattler.asg4.model.Card;
import uc.seng301.cardbattler.asg4.model.Monster;
import uc.seng301.cardbattler.asg4.model.Spell;
import uc.seng301.cardbattler.asg4.model.Trap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SetupFavouringAI implements PlayerAIOperation {
    /**
     * Number of turns a setup favouring AI will skip if an optimal (non-monster
     * card) is not available to play
     */
    private static int turnsToSetup = 5;
    /**
     * A Set-up favouring play style that plays up to 3 cards in a turn.
     * If there are no {@link Spell}s or {@link Trap}s in the hand it will not play
     * a card unless the
     * <b>turnsToSetup</b> count has reached 0
     *
     * @param allyBoard      current player's board
     * @param enemyBoard     enemy's board
     * @param numCardsPlayed number of cards played so far in the turn
     * @return The action the play style chooses to execute
     */
    @Override
    public Action execute(Board allyBoard, Board enemyBoard, int numCardsPlayed) {
        if (numCardsPlayed == 3) {
            return null;
        }
        if (allyBoard.getHand().size() == allyBoard.getHand().stream().filter(Monster.class::isInstance).toList().size()
                && turnsToSetup > 0) {
            // only monsters in hand
            turnsToSetup--;
            return null;
        }
        if (!allyBoard.getHand().isEmpty()) {
            List<Card> cardsInTypeOrder = new ArrayList<>();
            cardsInTypeOrder.addAll(allyBoard.getHand().stream().filter(Spell.class::isInstance).toList());
            cardsInTypeOrder.addAll(allyBoard.getHand().stream().filter(Trap.class::isInstance).toList());
            cardsInTypeOrder.addAll(allyBoard.getHand().stream().filter(Monster.class::isInstance).toList());
            Card cardToPlay = cardsInTypeOrder.get(0);
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
