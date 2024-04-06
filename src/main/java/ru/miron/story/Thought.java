package ru.miron.story;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@Getter
public class Thought {
    private String value;
    private Instant began;
    private Instant ended;

    public Thought(String value, Instant began, Instant ended) {
        checkInstants(began, ended);
        this.value = value;
        this.began = began;
        this.ended = ended;
    }

    private static void checkInstants(Instant began, Instant ended) {
        if (began == null) {
            if (ended != null) {
                throw new IllegalStateException("If thought has end it should have begin");
            }
        } else {
            if (ended != null && began.isAfter(ended)) {
                throw new IllegalStateException("Thought can't start before its end");
            }
        }
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setBegan(Instant began) {
        checkInstants(began, ended);
        this.began = began;
    }

    public void setEnded(Instant ended) {
        checkInstants(began, ended);
        this.ended = ended;
    }

    public State getState() {
        if (began == null) {
            return State.NOT_EVEN_BEGAN;
        } else if (ended == null) {
            return State.IN_PROGRESS;
        } else {
            return State.ENDED;
        }
    }


    public enum State {
        NOT_EVEN_BEGAN,
        IN_PROGRESS,
        ENDED
    }
}
