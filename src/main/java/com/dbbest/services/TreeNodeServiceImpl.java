package com.dbbest.services;

import com.dbbest.models.TreeNode;
import org.springframework.stereotype.Service;

@Service
public class TreeNodeServiceImpl implements TreeNodeService {

    @Override
    public TreeNode checkTree(TreeNode root) {
        return root;
    }
}
