package com.dbbest.xmlmanager.container;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Resizable implementation of the dbList and ListIterable interfaces.
 * @param <T> the type of elements of the list.
 */
public class ListOfChildren<T> extends ArrayList<T> implements DbList<T> {

    public boolean containsAll(List<T> elements) {
        for (int i = 0; i < this.size(); i++) {
            int counter = 0;
            for (int j = 0; j < elements.size(); j++) {
                if (this.get(j).equals(elements.get(j))) {
                    counter++;
                }
            }
            if (counter == 0) {
                return false;
            }
        }
        return true;
    }

    public T item(int index) {
        return (T) this.get(index);
    }

    public boolean set(T element, int index) {
        if (index < size()) {
            this.set(index, element);
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        if (!this.isEmpty() && this.get(0) != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < this.size(); i++) {
                if (this.get(i) != null) {
                    sb.append(this.get(i).toString() + ";");
                }
            }

            return sb.toString();
        } else {
            return "";
        }
    }
}

