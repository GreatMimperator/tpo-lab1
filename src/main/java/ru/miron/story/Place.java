package ru.miron.story;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Place extends StoryObject {
    private Set<Human> humanInside;

    public Place(String name) {
        this(name, new HashSet<>());
    }

    public void addHuman(Human human) {
        if (humanInside.contains(human)) {
            throw new IllegalStateException("Human is already inside the place");
        }
        humanInside.add(human);
    }

    public boolean removeHuman(Human human) {
        return humanInside.remove(human);
    }

    public boolean contains(Human human) {
        return humanInside.contains(human);
    }

    public Place(String name, Set<Human> humanInside) {
        super(name);
        this.humanInside = humanInside;
    }
}
