package ru.miron.func;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.LinkedList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyMathTest {
    @ParameterizedTest(name = "asin({0})")
    @DisplayName("Check dots from [-0.75; 0.75] with step 0.05")
    @MethodSource("genNotSoBigAnswerTestSource")
    public void notSoBigAnswerTest(Double arg) {
        assertEquals(Math.asin(arg), MyMath.asin(arg, 10), 0.001);
    }

    @ParameterizedTest(name = "asin({0})")
    @DisplayName("Check dots from [-1; -0.75] && [0.75; 1] with step 0.05")
    @MethodSource("genSoBigAnswerTestSource")
    public void soBigAnswerTest(Double arg) {
        assertEquals(Math.asin(arg), MyMath.asin(arg, 10000), 0.01);
    }

    static Stream<Double> genSoBigAnswerTestSource() {
        double from = -1;
        double to = -0.75;
        double step = 0.05;
        int count = (int) Math.round((to - from) / step);
        var leftValuesStream = ParamsFactory.genDotsInRangeSameDistanceBetween(from, to, count);
        from = 0.75;
        to = 1;
        var rightValuesStream = ParamsFactory.genDotsInRangeSameDistanceBetween(from, to, count);
        return Stream.concat(leftValuesStream, rightValuesStream);
    }

    static Stream<Double> genNotSoBigAnswerTestSource() {
        double from = -0.75;
        double to = 0.75;
        double step = 0.05;
        int count = (int) Math.round((to - from) / step);
        return ParamsFactory.genDotsInRangeSameDistanceBetween(from, to, count);
    }

    class ParamsFactory {
        static Stream<Double> genDotsInRangeSameDistanceBetween(double from, double to, int count) {
            var values = new LinkedList<Double>();
            var step = (to - from) / (count - 1);
            var current = from;
            for (double i = 0; i < count; i++, current += step) {
                values.add(current);
            }
            return values.stream();
        }
    }
}
