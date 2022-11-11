package org.jinx.utils;

public record Weight<T>(T object, int weight) {

    @Override
    public String toString() {
        return "Weight{" +
                "object=" + object.toString() +
                ", weight=" + weight +
                '}';
    }
}
