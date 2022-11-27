package org.jinx.compare;

import java.util.ArrayList;

public class Comparator {

    public static java.util.Comparator<ArrayList<String>> comparator = (x, y) -> {
        for (int i = 0; i < Math.min(x.size(), y.size()); i++) {
            if (x.get(i) != y.get(i)) {
                String[] data = x.get(1).split("Kartensumme: ");
                String[] dataY = y.get(1).split("Kartensumme: ");
                return Integer.parseInt(dataY[1]) - Integer.parseInt(data[1]);
            }
        }
        return Integer.compare(x.size(), y.size());
    };

}
