package uc.seng301.cardbattler.asg4.cucumber;

import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
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

    private int count;
    private int choose;
    private boolean next;

    @Before
    public void setup() {
        CardService offlineCardGenerator = Mockito.spy(new CardService());


        CommandLineInterface cli = Mockito.mock(CommandLineInterface.class);
        battleDeckCreator = new BattleDeckCreator(offlineCardGenerator);
        gameInterface = new GameInterface(offlineCardGenerator, cli);
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

    private void noDuplicateAbilities(Card card) {
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
        for (Card card: deck.getCards()) {
            Assertions.assertEquals(count,card.getAbilities().size());
            noDuplicateAbilities(card);
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

    @ParameterType(value = "true|True|TRUE|false|False|FALSE")
    public Boolean booleanValue(String value) {
        return Boolean.valueOf(value);
    }

    @Given("Random count of {int} chose ability {int} and {booleanValue}")
    public void randomCountOfCountChoseAbilityChooseAndNext(int count, int choose, boolean next) {
        this.choose = choose;
        this.count = count;
        this.next = next;
        Random random = Mockito.mock(Random.class);
        Mockito.when(random.nextInt(1,4)).thenReturn(count);
        Mockito.when(random.nextInt(0,3)).thenReturn(choose);
        Mockito.when(random.nextBoolean()).thenReturn(next);

        RandomSingleton.random = random;
    }
}
