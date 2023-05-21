package uc.seng301.cardbattler.asg4.cards;

import uc.seng301.cardbattler.asg4.model.Card;

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

    abstract public Card toCard();
}
