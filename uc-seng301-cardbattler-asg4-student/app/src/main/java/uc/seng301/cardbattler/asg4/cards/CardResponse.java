package uc.seng301.cardbattler.asg4.cards;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import uc.seng301.cardbattler.asg4.game.PlayState;
import uc.seng301.cardbattler.asg4.model.Card;
import uc.seng301.cardbattler.asg4.model.CardPosition;
import uc.seng301.cardbattler.asg4.model.Monster;
import uc.seng301.cardbattler.asg4.model.Spell;
import uc.seng301.cardbattler.asg4.model.Trap;
import uc.seng301.cardbattler.asg4.model.abilities.Ability;
import uc.seng301.cardbattler.asg4.model.abilities.ActorIsAllyOrEnemy;
import uc.seng301.cardbattler.asg4.model.abilities.BasicAbility;
import uc.seng301.cardbattler.asg4.model.abilities.CanTargetSelf;
import uc.seng301.cardbattler.asg4.model.abilities.OnlyIfPlayState;
import uc.seng301.cardbattler.asg4.model.abilities.OnlyOnType;
import uc.seng301.cardbattler.asg4.model.abilities.TargetActor;
import uc.seng301.cardbattler.asg4.model.abilities.TargetIsAllyOrEnemy;
import uc.seng301.cardbattler.asg4.model.abilities.TotalTimes;

/**
 * {@link Card} API response JSON deserializer (Jackson)
 */
public class CardResponse {
    @JsonDeserialize
    @JsonProperty("name")
    private String name;

    @JsonDeserialize
    @JsonProperty("race")
    private String race;

    @JsonDeserialize
    @JsonProperty("atk")
    private int attack;

    @JsonDeserialize
    @JsonProperty("def")
    private int defence;

    @JsonDeserialize
    @JsonProperty("level")
    private int level;

    @JsonDeserialize
    @JsonProperty("type")
    private String type;

    @JsonDeserialize
    @JsonProperty("attribute")
    private String attribute;

    @JsonDeserialize
    @JsonProperty("desc")
    private String description;

    /**
     * No-args Jackson constructor
     */
    public CardResponse() {
        // no-args jackson constructor
    }

    @Override
    public String toString() {
        return "CardResponse{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", race='" + race + '\'' +
                ", attribute='" + attribute + '\'' +
                ", attack=" + attack +
                ", defence=" + defence +
                ", level=" + level +
                '}';
    }

    /**
     * Converts itself to a Card including assigning a default ability for each card
     * type
     * 
     * TODO: This method is quite verbose and doesn't follow some design principles
     * For Task 3.1 refactor this implementation to use a design pattern
     * 
     * @return Card representation of json deserialized response
     */
    public Card toCard() {
        Card card;
        if (type.toLowerCase().contains("monster")) {
            card = new Monster();
            Ability ability = new BasicAbility(Card::damageCard, "attack", attack);
            ability = new TargetIsAllyOrEnemy(ability, false);
            ability = new OnlyIfPlayState(ability, PlayState.ON_PLAY);
            ability = new OnlyOnType(ability, Monster.class);
            ability = new CanTargetSelf(ability, false);
            card.addAbility(ability);
            Monster monster = (Monster) card;
            monster.setAttack(attack);
            monster.setDefence(defence);
            monster.setLife(0);
            monster.setCardPosition(CardPosition.ATTACK);
        } else if (type.toLowerCase().contains("spell")) {
            card = new Spell();
            Ability ability = new BasicAbility(Card::healCard, "heal", 1000);
            ability = new TotalTimes(ability, 1);
            ability = new ActorIsAllyOrEnemy(ability, true);
            ability = new TargetIsAllyOrEnemy(ability, false);
            ability = new OnlyIfPlayState(ability, PlayState.AFTER_PLAY);
            ability = new OnlyOnType(ability, Monster.class);
            card.addAbility(ability);
        } else if (type.toLowerCase().contains("trap")) {
            card = new Trap();
            Ability ability = new BasicAbility(Card::blockNextAbilities, "delay", 1);
            ability = new TargetActor(ability);
            ability = new TotalTimes(ability, 1);
            ability = new ActorIsAllyOrEnemy(ability, false);
            ability = new OnlyOnType(ability, Spell.class, Monster.class);
            ability = new TargetActor(ability);
            ability = new OnlyIfPlayState(ability, PlayState.BEFORE_PLAY);
            card.addAbility(ability);
        } else {
            // invalid card found (shouldn't happen)
            return null;
        }
        card.setName(name);
        card.setDescription(description);

        return card;
    }

}
