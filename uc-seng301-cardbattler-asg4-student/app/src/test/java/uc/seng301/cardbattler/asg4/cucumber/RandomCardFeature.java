package uc.seng301.cardbattler.asg4.cucumber;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import uc.seng301.cardbattler.asg4.cards.CardService;
import uc.seng301.cardbattler.asg4.cli.CommandLineInterface;
import uc.seng301.cardbattler.asg4.game.BattleDeckCreator;
import uc.seng301.cardbattler.asg4.game.GameInterface;
import uc.seng301.cardbattler.asg4.game.RandomSingleton;
import uc.seng301.cardbattler.asg4.model.*;
import uc.seng301.cardbattler.asg4.model.abilities.CanTargetSelf;
import uc.seng301.cardbattler.asg4.model.abilities.OnlyIfPlayState;
import uc.seng301.cardbattler.asg4.model.abilities.OnlyOnType;

import java.util.*;

public class RandomCardFeature {

    private GameInterface gameInterface;
    private BattleDeckCreator battleDeckCreator;

    final private int[] counts =  {3,2,2,2,2,2,2,1,1,1};
    final private int[] chooses = {0,0,1,2,0,1,2,0,1,2};
    final private int[] nexts   = {0,0,0,0,1,1,1,0,0,0};

    private int pos = -1;

    @Before
    public void setup() {
        CardService offlineCardGenerator = Mockito.spy(new CardService());


        CommandLineInterface cli = Mockito.mock(CommandLineInterface.class);
        battleDeckCreator = new BattleDeckCreator(offlineCardGenerator);
        gameInterface = new GameInterface(offlineCardGenerator, cli);

        /*
         * randomizer is mocked to make sure that tests are not "flakey" (fail at random times).
         * this makes sure that if under any expected values for the out put of random that are meaning full then the
         * ability follow the constracts.
         * */
        Random random = Mockito.mock(Random.class);
        Mockito.doAnswer(invocation -> {
            pos++;
            if (pos >= counts.length) {
                pos = 0;
            }
            return counts[pos];
        }).when(random).nextInt(1,4);
        Mockito.doAnswer(invocation -> chooses[pos]).when(random).nextInt(0,3);
        Mockito.doAnswer(invocation -> (nexts[pos]==1)).when(random).nextBoolean();
        RandomSingleton.random = random;
    }
    private Deck deck;


    final private static String playerName = "name";
    @Given("I have a deck")
    public void iHaveADeck() {
        Player player = gameInterface.getPlayerAccessor().createPlayer(playerName, "basic");
        Assertions.assertNotNull(player);
        deck = gameInterface.getDeckAccessor().createDeck(String.format("%ss_deck", playerName), player,
                new ArrayList<>());
        gameInterface.getPlayerAccessor().persistPlayer(player);
        Assertions.assertEquals(player, gameInterface.getPlayerAccessor().getPlayerByName(playerName));
        Assertions.assertNotNull(gameInterface.getPlayerAccessor().getPlayerByName(playerName).getDeck());
    }




    @When("I populate a battle deck")
    public void iPopulateABattleDeck() {


        battleDeckCreator.populateRandomBattleDeck(deck);
    }


    @Then("Each card has at least {int} abilities And at most {int} abilities")
    public void eachCardHasAtLeastAbilitiesAndAtMostAbilities(int min, int max) {
        for (Card card: deck.getCards()) {
            Assertions.assertTrue(card.getAbilities().size() >= min);
            Assertions.assertTrue(card.getAbilities().size() <= max);
        }
    }

    private void noDuplicateAbilities(Card card, int count) {
        for (int i = 0; i< count; i++) {
            for (int j = 0; j< count; j++) {
                if (i == j) {
                    continue;
                }
                if (card.getAbilities().get(i) instanceof CanTargetSelf) {
                    Assertions.assertFalse(card.getAbilities().get(j) instanceof CanTargetSelf);
                } else if (card.getAbilities().get(i) instanceof OnlyOnType) {
                    Assertions.assertFalse(card.getAbilities().get(j) instanceof OnlyOnType);
                } else if (card.getAbilities().get(i) instanceof OnlyIfPlayState) {
                    Assertions.assertFalse(card.getAbilities().get(j) instanceof OnlyIfPlayState);
                }
            }
        }
    }



    @Then("A card’s abilities are assigned at random")
    public void aCardSAbilitiesAreAssignedAtRandom() {
        int i = -1;
        for (Card card: deck.getCards()) {
            i++;
            if (i >= counts.length) {
                i = 0;
            }
            int count = counts[i];
            int choose = chooses[i];
            boolean next = nexts[i] == 1;

            Assertions.assertEquals(count,card.getAbilities().size());
            noDuplicateAbilities(card, count);
            if (choose == 0) {
                Assertions.assertTrue(card.getAbilities().stream().anyMatch((ability -> ability instanceof CanTargetSelf)));
            } else if (choose == 1) {
                Assertions.assertTrue(card.getAbilities().stream().anyMatch((ability -> ability instanceof OnlyOnType)));
            } else {
                Assertions.assertTrue(card.getAbilities().stream().anyMatch((ability -> ability instanceof OnlyIfPlayState)));
            }
            if (count == 2) {
                if ( (choose == 0 && next) || (choose == 2 && !next) ) {
                    Assertions.assertTrue(card.getAbilities().stream().anyMatch((ability -> ability instanceof OnlyOnType)));
                } else if ( (choose == 1 && next) || (choose == 0) ) {
                    Assertions.assertTrue(card.getAbilities().stream().anyMatch((ability -> ability instanceof OnlyIfPlayState)));
                } else {
                    Assertions.assertTrue(card.getAbilities().stream().anyMatch((ability -> ability instanceof CanTargetSelf)));
                }
            }
        }
    }



}
