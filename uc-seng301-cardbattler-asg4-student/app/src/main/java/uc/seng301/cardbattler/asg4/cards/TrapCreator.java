package uc.seng301.cardbattler.asg4.cards;

import uc.seng301.cardbattler.asg4.game.PlayState;
import uc.seng301.cardbattler.asg4.model.Card;
import uc.seng301.cardbattler.asg4.model.Monster;
import uc.seng301.cardbattler.asg4.model.Spell;
import uc.seng301.cardbattler.asg4.model.Trap;
import uc.seng301.cardbattler.asg4.model.abilities.*;

public class TrapCreator extends CardCreator {
    public TrapCreator(String name, String race, int attack, int defence, int level, String type, String attribute, String description) {
        super(name,race,attack,defence,level,type,attribute,description);
    }

    @Override
    public Card toCard() {
        Card card = new Trap();
        Ability ability = new BasicAbility(Card::blockNextAbilities, "delay", 1);
        ability = new TargetActor(ability);
        ability = new TotalTimes(ability, 1);
        ability = new ActorIsAllyOrEnemy(ability, false);
        ability = new OnlyOnType(ability, Spell.class, Monster.class);
        ability = new TargetActor(ability);
        ability = new OnlyIfPlayState(ability, PlayState.BEFORE_PLAY);
        card.addAbility(ability);

        card.setName(name);
        card.setDescription(description);
        return card;
    }
}
