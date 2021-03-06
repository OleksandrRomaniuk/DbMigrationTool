package com.dbbest.xmlmanager.container;

import java.util.ArrayList;
import java.util.List;

/**
 * Resizable implementation of the dbList and ListIterable interfaces.
 *
 * @param <T> the type of elements of the list.
 */
public class ListOfChildren<T> extends ArrayList<T> implements DbList<T> {

    @Override
    public boolean containsAll(List<T> elements) {
        for (int i = 0; i < this.size(); i++) {
            int counter = 0;
            for (int j = 0; j < elements.size(); j++) {
                if (this.get(i).equals(elements.get(j))) {
                    counter++;
                }
            }
            if (counter == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public T item(int index) {
        return (T) this.get(index);
    }

    @Override
    public boolean set(T element, int index) {
        if (index < size()) {
            this.set(index, element);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void addAll(T[] elements) {
        for (T element : elements) {
            super.add(element);
        }
    }

    @Override
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

