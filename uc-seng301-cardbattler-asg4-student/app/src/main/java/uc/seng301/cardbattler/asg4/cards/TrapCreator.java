package uc.seng301.cardbattler.asg4.cards;

import uc.seng301.cardbattler.asg4.model.Card;
import uc.seng301.cardbattler.asg4.model.Trap;

public class TrapCreator extends CardCreator {
    public TrapCreator(String name, String race, int attack, int defence, int level, String type, String attribute, String description) {
        super(name,race,attack,defence,level,type,attribute,description);
    }

    @Override
    public Card toCard() {
        Card card = new Trap();
        addAbilities(card);
        card.setName(name);
        card.setDescription(description);
        return card;
    }
}
