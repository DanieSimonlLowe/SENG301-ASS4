package uc.seng301.cardbattler.asg4.cards;

import uc.seng301.cardbattler.asg4.game.PlayState;
import uc.seng301.cardbattler.asg4.model.*;
import uc.seng301.cardbattler.asg4.model.abilities.*;

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
        Ability ability = new BasicAbility(Card::damageCard, "attack", attack);
        ability = new TargetIsAllyOrEnemy(ability, false);
        ability = new OnlyIfPlayState(ability, PlayState.ON_PLAY);
        ability = new OnlyOnType(ability, Monster.class);
        ability = new CanTargetSelf(ability, false);
        card.addAbility(ability);
        card.setAttack(attack);
        card.setDefence(defence);
        card.setLife(0);
        card.setCardPosition(CardPosition.ATTACK);
        card.setName(name);
        card.setDescription(description);

        return card;
    }
}
