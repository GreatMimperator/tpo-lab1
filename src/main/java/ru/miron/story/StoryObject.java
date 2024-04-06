package ru.miron.story;

import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
public class StoryObject {
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoryObject that = (StoryObject) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
