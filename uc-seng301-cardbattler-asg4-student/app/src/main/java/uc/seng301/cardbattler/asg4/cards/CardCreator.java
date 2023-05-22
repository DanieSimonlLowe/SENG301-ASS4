package uc.seng301.cardbattler.asg4.cards;

import uc.seng301.cardbattler.asg4.game.PlayState;
import uc.seng301.cardbattler.asg4.game.RandomSingleton;
import uc.seng301.cardbattler.asg4.model.Card;
import uc.seng301.cardbattler.asg4.model.Monster;
import uc.seng301.cardbattler.asg4.model.Spell;
import uc.seng301.cardbattler.asg4.model.abilities.*;

import java.util.Random;

/**
 * {@link Card} API response JSON deserializer (Jackson)
 */
public abstract class CardCreator {


    protected String name;

    protected String race;

    protected int attack;

    protected int defence;

    protected int level;

    protected String type;

    protected String attribute;

    protected String description;


    public CardCreator(String name, String race, int attack, int defence, int level, String type, String attribute, String description) {
        this.name = name;
        this.race = race;
        this.attack = attack;
        this.defence = defence;
        this.level = level;
        this.type = type;
        this.attribute = attribute;
        this.description = description;
    }

    protected Card addAbilities(Card card) {
        Random random = RandomSingleton.getRandom();

        int count = random.nextInt(1,4);
        boolean[] has = new boolean[]{false, false, false};
        if (count == 3) {
            has[0] = true;
            has[1] = true;
            has[2] = true;
        } else if (count == 1) {
            int choose = random.nextInt(0,3);
            has[choose] = true;
        } else {
            int choose = random.nextInt(0,3);
            has[choose] = true;
            boolean next = random.nextBoolean();
            if (next) {
                has[(choose + 1) % 3] = true;
            } else {
                has[(choose + 2) % 3] = true;
            }

        }

        if (has[0]) {
            Ability ability = new BasicAbility(Card::damageCard, "attack", attack);
            ability = new TargetIsAllyOrEnemy(ability, false);
            ability = new OnlyIfPlayState(ability, PlayState.ON_PLAY);
            ability = new OnlyOnType(ability, Monster.class);
            ability = new CanTargetSelf(ability, false);
            card.addAbility(ability);
        }
        if (has[1]) {
            Ability ability = new BasicAbility(Card::healCard, "heal", 1000);
            ability = new TotalTimes(ability, 1);
            ability = new ActorIsAllyOrEnemy(ability, true);
            ability = new TargetIsAllyOrEnemy(ability, false);
            ability = new OnlyIfPlayState(ability, PlayState.AFTER_PLAY);
            ability = new OnlyOnType(ability, Monster.class);
            card.addAbility(ability);
        }
        if (has[2]) {
            Ability ability = new BasicAbility(Card::blockNextAbilities, "delay", 1);
            ability = new TargetActor(ability);
            ability = new TotalTimes(ability, 1);
            ability = new ActorIsAllyOrEnemy(ability, false);
            ability = new OnlyOnType(ability, Spell.class, Monster.class);
            ability = new TargetActor(ability);
            ability = new OnlyIfPlayState(ability, PlayState.BEFORE_PLAY);
            card.addAbility(ability);
        }

        return card;
    }

    abstract public Card toCard();
}
