package ru.miron.story;

import lombok.*;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

@Getter
@Setter
public class Human extends StoryObject {
    private Sex sex;
    private Emotion emotion;
    // Strength of emotion is the second element in Pair
    private Map<Human, Pair<Emotion, Integer>> humanEmotionRelations;
    private StoryObject focusedOn;
    private Thought currentThought;


    public Human(String name) {
        super(name);
    }

    public Human(String name,
                 Sex sex,
                 Emotion emotion,
                 Map<Human, Pair<Emotion, Integer>> humanEmotionRelations,
                 StoryObject focusedOn,
                 Thought currentThought) {
        super(name);
        this.sex = sex;
        this.emotion = emotion;
        this.humanEmotionRelations = humanEmotionRelations;
        this.focusedOn = focusedOn;
        this.currentThought = currentThought;
    }


    public enum Sex {
        MALE,
        FEMALE
    }
}
