package org.jinx.utils;

/**
 * Generic weight for weighing objects
 *
 * @param object Object that we are weighting
 * @param weight Weight of the Object
 * @param reason Reason for the weight
 * @param <T>    Generic Type
 */
public record Weight<T>(T object, int weight, String reason) {

    @Override
    public String toString() {
        return "Weight{" +
                "object=" + object.toString() +
                ", weight=" + weight +
                '}';
    }
}
