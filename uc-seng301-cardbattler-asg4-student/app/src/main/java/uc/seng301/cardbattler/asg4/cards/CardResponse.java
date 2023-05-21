package uc.seng301.cardbattler.asg4.cards;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import uc.seng301.cardbattler.asg4.model.Card;
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
     * For Task 3.1 refactor this implementation to use a design pattern
     * 
     * @return Card representation of json deserialized response
     */
    public Card toCard() {
        Card card;
        if (type.toLowerCase().contains("monster")) {
            card = new MonsterCreator(name,race,attack,defence,level,type,attribute,description).toCard();
        } else if (type.toLowerCase().contains("spell")) {
            card = new SpellCreator(name,race,attack,defence,level,type,attribute,description).toCard();
        } else if (type.toLowerCase().contains("trap")) {
            card = new TrapCreator(name,race,attack,defence,level,type,attribute,description).toCard();
        } else {
            // invalid card found (shouldn't happen)
            return null;
        }


        return card;
    }

}
