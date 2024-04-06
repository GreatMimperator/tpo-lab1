package ru.miron.story;

import org.junit.jupiter.api.*;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StoryTest {

    @Nested
    class ThoughtTest {
        @Test
        @DisplayName("Thought different state defining test")
        public void stateDefiningTest() {
            Thought thought = new Thought("", null, null);
            assertEquals(thought.getState(), Thought.State.NOT_EVEN_BEGAN);
            thought = new Thought("", Instant.now(), null);
            assertEquals(thought.getState(), Thought.State.IN_PROGRESS);
            thought = new Thought("", Instant.now(), Instant.now());
            assertEquals(thought.getState(), Thought.State.ENDED);
        }

        @Test
        @DisplayName("Bad thought time test")
        public void badThoughtTimeTest() {
            assertThrows(IllegalStateException.class, () -> new Thought("", null, Instant.now()));
            var notStartedThought = new Thought("", null, null);
            assertThrows(IllegalStateException.class, () -> notStartedThought.setEnded(Instant.now()));
            Instant began = Instant.now();
            Instant badEnded = Instant.now().minus(Duration.ofHours(1));
            assertThrows(IllegalStateException.class, () -> new Thought("", began, badEnded));
            var startedThought = new Thought("", began, null);
            assertThrows(IllegalStateException.class, () -> startedThought.setEnded(badEnded));
        }
    }

    @Nested
    class PlaceTest {

        @Test
        @DisplayName("Bad human addition test")
        public void badHumanAdditionTest() {
            Place place = new Place("");
            place.addHuman(new Human("Vasya"));
            place.addHuman(new Human("Petya"));
            assertThrows(IllegalStateException.class, () -> place.addHuman(new Human("Vasya")));
        }
    }

    @Nested
    class LocationSetTest {

        private LocationSet parentLocationSet;
        private LocationSet childLocationSet;
        private Place childLocationSetPlace;
        private Place parentLocationSetPlace;
        private Human parentPlaceHuman;
        private Human childPlaceHuman;

        @BeforeEach
        void init() {
            parentLocationSet = new LocationSet("Parent");
            childLocationSet = new LocationSet("Child");
            childLocationSetPlace = new Place("Child.Place");
            parentLocationSetPlace = new Place("Parent.Place");
//
            // insert
            parentLocationSet.setLocationSets(new HashSet<>(List.of(childLocationSet)));

            parentPlaceHuman = new Human("Parent.Place.Human");
            parentLocationSetPlace.addHuman(parentPlaceHuman);

            childPlaceHuman = new Human("Child.Place.Human");
            childLocationSetPlace.addHuman(childPlaceHuman);

            parentLocationSet.setPlacesInside(new HashSet<>(List.of(parentLocationSetPlace)));
            childLocationSet.setPlacesInside(new HashSet<>(List.of(childLocationSetPlace)));
        }

        @Test
        @DisplayName("Humans inside receiving and contains test")
        public void humansInsideAndContainsTest() {
            // checks
            assertTrue(parentLocationSet.contains(parentLocationSetPlace));
            assertTrue(parentLocationSet.contains(childLocationSet));
            assertTrue(childLocationSet.contains(childLocationSetPlace));
            assertTrue(parentLocationSet.contains(childLocationSetPlace));
            assertEquals(parentLocationSet.getHumansInside(),
                    new HashSet<>(List.of(parentPlaceHuman, childPlaceHuman)));
            assertFalse(parentLocationSet.contains(parentLocationSet));
            assertFalse(parentLocationSet.contains(new LocationSet("")));
            assertFalse(childLocationSet.contains(parentLocationSet));
            assertFalse(parentLocationSet.contains(new Place("")));
            assertFalse(childLocationSet.contains(parentLocationSetPlace));
        }

        @Test
        @DisplayName("Remove-add-set test")
        public void removeAddSetTest() {
            parentLocationSet.removeLocationSet(childLocationSet);
            assertFalse(parentLocationSet.contains(childLocationSet));
            assertFalse(parentLocationSet.contains(childLocationSetPlace));
            parentLocationSet.addLocationSet(childLocationSet);
            assertTrue(parentLocationSet.contains(childLocationSet));
            assertTrue(parentLocationSet.contains(childLocationSetPlace));

            assertThrows(IllegalStateException.class,
                    () -> parentLocationSet.removeLocationSet(parentLocationSet));
            assertThrows(IllegalStateException.class,
                    () -> parentLocationSet.addLocationSet(parentLocationSet));

            var tempLocationSet = new LocationSet("tempLS");
            childLocationSet.addLocationSet(tempLocationSet);
            assertTrue(parentLocationSet.contains(tempLocationSet));
            assertThrows(IllegalStateException.class,
                    () -> parentLocationSet.addLocationSet(tempLocationSet));
            assertTrue(parentLocationSet.removeLocationSet(tempLocationSet));
            assertFalse(parentLocationSet.contains(tempLocationSet));
            assertTrue(parentLocationSet.removeLocationSet(childLocationSet));
            assertFalse(parentLocationSet.removeLocationSet(childLocationSet));
            parentLocationSet.addLocationSet(childLocationSet);

            var anotherLocationSetContainingChild = new LocationSet("another");
            anotherLocationSetContainingChild.addPlace(parentLocationSetPlace);
            anotherLocationSetContainingChild.setLocationSets(new HashSet<>(List.of(childLocationSet)));
            assertThrows(IllegalStateException.class,
                    () -> parentLocationSet.setLocationSets(new HashSet<>(List.of(anotherLocationSetContainingChild))));
            assertThrows(IllegalStateException.class,
                    () -> parentLocationSet.setLocationSets(new HashSet<>(List.of(parentLocationSet))));

            assertThrows(IllegalStateException.class,
                    () -> parentLocationSet.setPlacesInside(new HashSet<>(List.of(childLocationSetPlace))));

            assertThrows(IllegalStateException.class,
                    () -> parentLocationSet.addPlace(childLocationSetPlace));
        }
    }
}
