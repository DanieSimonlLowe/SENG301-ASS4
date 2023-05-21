package uc.seng301.cardbattler.asg4.cards;

import uc.seng301.cardbattler.asg4.game.PlayState;
import uc.seng301.cardbattler.asg4.model.Card;
import uc.seng301.cardbattler.asg4.model.Monster;
import uc.seng301.cardbattler.asg4.model.Spell;
import uc.seng301.cardbattler.asg4.model.abilities.*;

public class SpellCreator extends CardCreator {

    public SpellCreator(String name, String race, int attack, int defence, int level, String type, String attribute, String description) {
        super(name,race,attack,defence,level,type,attribute,description);
    }

    @Override
    public Card toCard() {
        Card card;
        card = new Spell();
        Ability ability = new BasicAbility(Card::healCard, "heal", 1000);
        ability = new TotalTimes(ability, 1);
        ability = new ActorIsAllyOrEnemy(ability, true);
        ability = new TargetIsAllyOrEnemy(ability, false);
        ability = new OnlyIfPlayState(ability, PlayState.AFTER_PLAY);
        ability = new OnlyOnType(ability, Monster.class);
        card.addAbility(ability);

        card.setName(name);
        card.setDescription(description);
        return card;
    }
}
