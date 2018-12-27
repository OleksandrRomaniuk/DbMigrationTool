package com.dbbest.xmlmanager.container;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ListOfChildrenTest {

    DbList<String> listToTest;
    @Before
    public void setUp() throws Exception {
        listToTest = new ListOfChildren();
        listToTest.add("test");
    }

    @Test
    public void shouldAddToObjects() {
        listToTest.add("one");
        listToTest.add("two");
        Assert.assertEquals(listToTest.size(), 3);
    }

    @Test
    public void isEmptyOfShouldReturnFalseAndTrueAfterElementAdded() {
        DbList test = new ListOfChildren();
        Assert.assertEquals(test.isEmpty(), true);
        test.add("test");
        Assert.assertEquals(test.isEmpty(), false);

    }

    @Test
    public void shouldReturnFalseBeforeClaerAndTrueAfterClear() {
        DbList test = new ListOfChildren();
        test.add("one");
        test.add("two");
        test.add("tree");
        Assert.assertEquals(test.isEmpty(), false);
        test.clear();
        Assert.assertEquals(test.isEmpty(), true);

    }

    @Test
    public void remove() {
        DbList test = new ListOfChildren();
        test.add("one");
        test.add("two");
        test.add("three");
        Assert.assertEquals(test.item(1), "two");
        test.remove("two");
        Assert.assertEquals(test.item(1), "three");
    }

    @Test
    public void ShouldReturnArrayOfThreeElements() {
        DbList test = new ListOfChildren();
        test.add("one");
        test.add("two");
        test.add("three");
        Object[] array = new Object[3];
        array[0] = "one";
        array[1] = "two";
        array[2] = "three";
        Assert.assertEquals(test.toArray(), array);
    }

    @Test
    public void ShouldReturnSizyOfThreeElements() {
        DbList test = new ListOfChildren();
        test.add("one");
        test.add("two");
        test.add("three");
        Assert.assertEquals(test.size(), 3);
    }

    @Test
    public void shouldReturnTrueForTwoAndFalseForFour() {
        DbList test = new ListOfChildren();
        test.add("one");
        test.add("two");
        test.add("three");
        Assert.assertEquals(test.contains("two"), true);
        Assert.assertEquals(test.contains("four"), false);
    }

    @Test
    public void shouldReturnTrueForFirstCaseAndfalseForSecond() {
        DbList test = new ListOfChildren();
        test.add("one");
        test.add("two");
        test.add("three");
        DbList test1 = new ListOfChildren();
        test1.add("one");
        test1.add("two");
        test1.add("three");

        Assert.assertEquals(test.containsAll(test1), true);
        DbList test2 = new ListOfChildren();
        test2.add("one");
        test2.add("two");
        test2.add("four");
        Assert.assertEquals(test.containsAll(test2), false);
    }

    @Test
    public void shouldReturnThree() {
        DbList test = new ListOfChildren();
        test.add("one");
        test.add("two");
        test.add("three");
        Assert.assertEquals(test.item(2), "three");
    }

    @Test
    public void shouldReturnFour() {
        DbList test = new ListOfChildren();
        test.add("one");
        test.add("two");
        test.add("three");
        test.set("four",1);
        Assert.assertEquals(test.item(1), "four");
    }


    @Test
    public void iteratorShouldReturnTrueInFirstCaseAndFalseInSecond() {
        DbList test = new ListOfChildren();
        test.add("one");
        test.add("two");
        test.add("three");

        Assert.assertEquals(test.iterator().hasNext(), true);
        test.iterator().next();
        test.iterator().next();
        test.iterator().next();
        Assert.assertEquals(test.iterator().hasNext(), false);
    }

    @Test
    public void shouldRemoveSecondElement() {
        DbList test = new ListOfChildren();
        test.add("one");
        test.add("two");
        test.add("three");
        test.iterator().next();
        test.iterator().remove();
        Assert.assertEquals(test.item(1), "three");
        Assert.assertEquals(test.size(), 2);
    }

    @Test
    public void shouldMoveToSecondElementAndThenToThird() {
        DbList test = new ListOfChildren();
        test.add("one");
        test.add("two");
        test.add("three");
        test.iterator().next();
        test.iterator().next();
        Assert.assertEquals(test.listIterator().hasPrevious(), true);
        test.iterator().next();
        test.listIterator().previous();
        test.listIterator().previous();
        test.listIterator().set("four");
        Assert.assertEquals(test.item(1), "four");
    }
}