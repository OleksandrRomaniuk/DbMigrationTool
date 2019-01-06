package com.dbbest.xmlmanager.container;

import com.dbbest.exceptions.ContainerException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

public class HorizontalPassageSearchManagerTest {

    Container<String> container;
    Container<String> child1;
    Container<String> child2;
    Container<String> child3;
    Container<String> child4;
    Container<String> child5;
    Container<String> child6;


    @Before
    public void createContainer() throws ContainerException {
        container = new Container();
        container.setName("test");
        DbList<Container<String>> children = new ListOfChildren();
        container.setChildren(children);

        child1 = new Container();
        children.add(child1);
        child1.setValue("child1");
        child1.setParent(container);

        child2 = new Container();
        children.add(child2);
        child2.setValue("child2");
        child2.setParent(container);

        child3 = new Container();
        children.add(child3);
        child3.setValue("child1");
        child3.setParent(container);

        child4 = new Container();
        children.add(child4);
        child4.setName("child1");
        HashMap attributesChild4 = new HashMap();
        attributesChild4.put("child4AttrKey", "child4AttrValue");
        child4.setAttributes(attributesChild4);
        child4.setParent(container);

        child5 = new Container();
        children.add(child5);
        child5.setName("child6");
        child5.setParent(container);
        HashMap attributesChild5 = new HashMap();
        attributesChild5.put("child5AttrKey", "child5AttrValue");
        child5.setAttributes(attributesChild5);

        child6 = new Container();
        children.add(child6);
        child6.setName("child6");
        child6.setParent(container);
        child6.setAttributes(attributesChild4);
    }

    @Test
    public void shouldFindNameInContainers() {

        HorizontalPassageSearchManager horizontalPassageSearchManager = new HorizontalPassageSearchManager(container);
        List<Container> listActual = horizontalPassageSearchManager.searchInNames("child6");
        DbList<Container> listExpected = new ListOfChildren();
        listExpected.add(child5);
        listExpected.add(child6);
        Assert.assertEquals(listExpected.size(), listActual.size());
        Assert.assertEquals(listExpected.item(0), listActual.get(0));
        Assert.assertEquals(listExpected.item(1), listActual.get(1));
    }

    @Test
    public void shouldFindValueInContainers() {
        HorizontalPassageSearchManager horizontalPassageSearchManager = new HorizontalPassageSearchManager(container);
        List<Container> listActual = horizontalPassageSearchManager.searchInValues("child1");
        DbList<Container> listExpected = new ListOfChildren();
        listExpected.add(child1);
        listExpected.add(child3);
        Assert.assertEquals(listExpected.size(), listActual.size());
        Assert.assertEquals(listExpected.item(0), listActual.get(0));
        Assert.assertEquals(listExpected.item(1), listActual.get(1));
    }

    @Test
    public void shouldFindKeyValueInContainers() {
        HorizontalPassageSearchManager horizontalPassageSearchManager = new HorizontalPassageSearchManager(container);
        List<Container> listActual = horizontalPassageSearchManager.searchInKeyValues("child4AttrKey", "child4AttrValue");
        DbList<Container> listExpected = new ListOfChildren();
        listExpected.add(child4);
        listExpected.add(child6);
        Assert.assertEquals(listExpected.size(), listActual.size());
        Assert.assertEquals(listExpected.item(0), listActual.get(0));
    }

}