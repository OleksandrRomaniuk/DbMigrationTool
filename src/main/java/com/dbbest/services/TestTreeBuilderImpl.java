package com.dbbest.services;

import com.dbbest.models.TreeNode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Service
public class TestTreeBuilderImpl implements TestTreeBuilder {

    @Override
    public TreeNode build() {
        TreeNode root = new TreeNode();
        root.setLabel("root");
        root.setValue("/root");

        TreeNode child1 = new TreeNode();
        TreeNode child2 = new TreeNode();

        child1.setLabel("child1");
        child1.setValue("/root/child1");
        child2.setLabel("child2");
        child2.setValue("/root/child2");
        root.setChildren(new ArrayList<>(Arrays.asList(child1, child2)));

        TreeNode child3 = new TreeNode();
        child3.setLabel("child3");
        child3.setValue("/root/child2/child3");
        child3.setChildren(null);

        child2.setChildren(new ArrayList<>(Collections.singletonList(child3)));
        child1.setChildren(null);


        return root;
    }
}
