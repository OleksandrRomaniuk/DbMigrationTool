package com.dbbest.xmlmanager.container;

import com.dbbest.xmlmanager.exceptions.ContainerException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class ContainerTest {

    private Container container;
    Container<String> childContainer1;
    Container<String> childContainer2;

    @Before
    public void setUp() throws Exception {
        container = new Container();
        container.setName("root");
        HashMap attributes = new HashMap();
        attributes.put("testKey", "testAttrValue");
        container.setAttributes(attributes);
        childContainer1 = new Container();
        childContainer1.setValue("testValue");
        childContainer1.setParent(container);
        container.addChild(childContainer1);

        childContainer2 = new Container();
        childContainer2.setValue("<test>testValue</test>");
        childContainer2.setParent(container);
        container.addChild(childContainer2);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void methodHasAttributesShouldReturnTrueAtContainerAndFalseAtTestContainer() {
        Container testContainer = new Container();
        Assert.assertEquals(testContainer.hasAttributes(), false);
        Assert.assertEquals(container.hasAttributes(), true);
    }

    @Test
    public void methodHasChildrenShouldReturnTrueAtContainerAndFalseAtTestContainer() {
        Container testContainer = new Container();
        Assert.assertEquals(testContainer.hasChildren(), false);
        Assert.assertEquals(container.hasChildren(), true);
    }

    @Test
    public void methodHasValueShouldReturnFalseBeforeValueSetAndTrueAfterValueSet() throws ContainerException {
        Container testContainer = new Container();
        Assert.assertEquals(testContainer.hasValue(), false);
        testContainer.setValue("test");
        Assert.assertEquals(testContainer.hasValue(), true);
    }

    @Test
    public void methodHasNameShouldReturnTrueAtContainerAndFalseAtTestContainer() {
        Container testContainer = new Container();
        Assert.assertEquals(testContainer.hasChildren(), false);
        Assert.assertEquals(container.hasChildren(), true);
    }

    @Test
    public void methodGetNameShouldReturnSetValue() {
        Assert.assertEquals(container.getName(), "root");
    }

    @Test
    public void methodGetValueShouldReturnSetValue() {
        Container testChildContainer1 = (Container) container.getChildren().item(0);
        Assert.assertEquals(testChildContainer1.getValue(), "testValue");
    }

    @Test
    public void methodGetAttributesShouldReturnSetValues() {
        Assert.assertEquals(container.getAttributes().get("testKey"), "testAttrValue");
    }

    @Test
    public void methodGetChildrenShouldReturnSetValue() {
        Assert.assertEquals(container.getChildren().item(0), childContainer1);
        Assert.assertEquals(container.getChildren().item(1), childContainer2);
    }

    @Test
    public void methodGetParentShouldReturnSetValue() {
        Assert.assertEquals(((Container)container.getChildren().item(0)).getParent(), container);
    }

    @Test
    public void methodAddChildShouldAddChildToListOfChildrenAddedChildShouldBeEqualToOriginallyAddedObject() throws ContainerException {
        Container testChildContainer1 = new Container();
        testChildContainer1.setValue("test");
        container.addChild(testChildContainer1);
        Assert.assertEquals(container.getChildren().item(2), testChildContainer1);
    }

    @Test
    public void methodHasParentShouldReturnFalseForContainerAndTrueForChild() {
        Container child = ((Container)container.getChildren().item(0));
        Assert.assertEquals(child.hasParent(), true);
        Assert.assertEquals(container.hasParent(), false);
    }

    @Test
    public void shouldAddNewAttributeGetItAndReconcileWithAttributesAdded() {
        Container testChildContainer2 = new Container();
        testChildContainer2.addAttribute("testKeyAttr", "testValueAttr");
        Assert.assertEquals(testChildContainer2.getAttributes().get("testKeyAttr"), "testValueAttr");
    }
}