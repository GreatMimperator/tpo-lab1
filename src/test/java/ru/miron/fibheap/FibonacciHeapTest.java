package ru.miron.fibheap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FibonacciHeapTest {

    @Test
    @DisplayName("last not min inserted")
    public void lastNotMinInsertedTest() {
        var lowerElementObject = new Object();
        FibonacciHeap<Object> heap = new FibonacciHeap<>();
        var prioritiesList = List.of(4, 3, 2);
        for (var priority : prioritiesList) {
            heap.enqueue(null, priority);
        }
        heap.enqueue(lowerElementObject, 1);
        heap.enqueue(null, 2);
        assertEquals(heap.size(), prioritiesList.size() + 1 + 1);
        assertEquals(heap.dequeueMin().getValue(), lowerElementObject);
        assertEquals(heap.size(), prioritiesList.size() + 1);
    }

    @Test
    @DisplayName("Random insert then delete")
    public void randomInsertThenDeleteTest() {
        var elements = List.of(1, 5, 2, 8, 4, 6, 3, 7, 9);
        FibonacciHeap<Integer> heap = new FibonacciHeap<>();
        for (var element : elements) {
            heap.enqueue(element, element);
        }
        assertEquals(heap.size(), elements.size());
        var sorted = elements.stream().sorted().toList();
        for (var value : sorted) {
            assertEquals(heap.dequeueMin().getValue(), value);
        }
        assertEquals(heap.size(), 0);
    }

    @Test
    @DisplayName("Random insert then delete")
    public void randomInsertDeleteInsert() {
        var elements = List.of(1, 5, 2, 3, 4);
        FibonacciHeap<Integer> heap = new FibonacciHeap<>();
        for (var element : elements) {
            heap.enqueue(element, element);
        }
        assertEquals(heap.size(), elements.size());
        assertEquals(heap.dequeueMin().getValue(), 1);
        assertEquals(heap.dequeueMin().getValue(), 2);
        assertEquals(heap.dequeueMin().getValue(), 3);
        assertEquals(heap.size(), elements.size() - 3);
        heap.enqueue(1, 1);
        heap.enqueue(6, 6);
        heap.enqueue(3, 3);
        assertEquals(heap.dequeueMin().getValue(), 1);
        assertEquals(heap.dequeueMin().getValue(), 3);
        assertEquals(heap.dequeueMin().getValue(), 4);
        assertEquals(heap.size(), elements.size() - 3);
    }
}
