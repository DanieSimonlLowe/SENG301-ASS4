package uc.seng301.cardbattler.asg4.game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import uc.seng301.cardbattler.asg4.model.Card;
import uc.seng301.cardbattler.asg4.model.Monster;
import uc.seng301.cardbattler.asg4.model.Player;
import uc.seng301.cardbattler.asg4.model.Spell;
import uc.seng301.cardbattler.asg4.model.Trap;

/**
 * A class that includes several different play styles as methods.
 * These static methods are used as method references to represent a
 * {@link Player}s AI
 * TODO: Clearly this implementation is not ideal and due to its static nature
 * has side-effects that we don't want; For Task 3.2 refactor this
 * implementation to use a design pattern
 */
public class PlayStyles {
    /**
     * Number of turns a setup favouring AI will skip if an optimal (non-monster
     * card) is not available to play
     */
    private static int turnsToSetup = 5;
    /**
     * Number of turns a reckless AI will skip at the start of the game
     */
    private static int turnsToWait = 5;

    /**
     * Default private constructor to prevent instantiation of static-only class
     */
    private PlayStyles() {
    }

    /**
     * Basic play style that simply plays the first card in the hand against the
     * first monster in the enemies board if target-able
     *
     * @param allyBoard      current player's board
     * @param enemyBoard     enemy's board
     * @param numCardsPlayed number of cards played so far in the turn
     * @return The action the play style chooses to execute
     */
    public static Action basicAI(Board allyBoard, Board enemyBoard, int numCardsPlayed) {
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
    public static Action monsterFavouringAI(Board allyBoard, Board enemyBoard, int numCardsPlayed) {
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
    public static Action setupFavouringAI(Board allyBoard, Board enemyBoard, int numCardsPlayed) {
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
    public static Action recklessAI(Board allyBoard, Board enemyBoard, int numCardsPlayed) {
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
