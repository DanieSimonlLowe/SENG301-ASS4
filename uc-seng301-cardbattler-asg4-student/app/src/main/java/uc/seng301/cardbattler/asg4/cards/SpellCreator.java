package uc.seng301.cardbattler.asg4.cards;

import uc.seng301.cardbattler.asg4.model.Card;
import uc.seng301.cardbattler.asg4.model.Spell;

public class SpellCreator extends CardCreator {

    public SpellCreator(String name, String race, int attack, int defence, int level, String type, String attribute, String description) {
        super(name,race,attack,defence,level,type,attribute,description);
    }

    @Override
    public Card toCard() {
        Card card;
        card = new Spell();
        addAbilities(card);

        card.setName(name);
        card.setDescription(description);
        return card;
    }
}
