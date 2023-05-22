package uc.seng301.cardbattler.asg4.cards;

import uc.seng301.cardbattler.asg4.model.*;

public class MonsterCreator extends CardCreator {


    public MonsterCreator(String name, String race, int attack, int defence, int level, String type, String attribute, String description) {
        super(name,race,attack,defence,level,type,attribute,description);
    }

    /**
     * Converts itself to a Card including assigning a default ability for each card
     * type
     * For Task 3.1 refactor this implementation to use a design pattern
     *
     * @return Card representation of json deserialized response
     */
    @Override
    public Card toCard() {
        Monster card;
        card = new Monster();
        this.addAbilities(card);
        card.setAttack(attack);
        card.setDefence(defence);
        card.setLife(0);
        card.setCardPosition(CardPosition.ATTACK);
        card.setName(name);
        card.setDescription(description);

        return card;
    }
}
