package coursera_stanford_2013.week5;

import java.util.ArrayList;
import java.util.List;

public class BSTImpl implements BST {
    private BSTNode rootNode;
    private int size;

    @Override
    public void add(Integer key) {
        BSTNode nodeToAdd = new BSTNode(key);
        if (rootNode == null) {
            rootNode = nodeToAdd;
            size++;
            return;
        }

        BSTNode currentNode = rootNode;

        while (currentNode != null) {
            currentNode = processTreeLevel(currentNode, nodeToAdd);
        }
    }

    private BSTNode processTreeLevel(BSTNode currentNode, BSTNode nodeToAdd) {
        if (shouldGoToTheLeft(currentNode, nodeToAdd)) {
            return processLeft(currentNode, nodeToAdd);
        } else {
            return processRight(currentNode, nodeToAdd);
        }
    }

    private BSTNode processLeft(BSTNode currentNode, BSTNode nodeToAdd) {
        if (currentNode.getLeft() == null) {
            addLeftNode(currentNode, nodeToAdd);
            return null;
        } else {
            return currentNode.getLeft();
        }
    }

    private void addLeftNode(BSTNode currentNode, BSTNode nodeToAdd) {
        currentNode.setLeft(nodeToAdd);
        nodeToAdd.setParent(currentNode);
        size++;
    }

    private boolean shouldGoToTheLeft(BSTNode currentNode, BSTNode nodeToAdd) {
        return nodeToAdd.getKey() < currentNode.getKey();
    }

    private BSTNode processRight(BSTNode currentNode, BSTNode nodeToAdd) {
        if (currentNode.getRight() == null) {
            addRightNode(currentNode, nodeToAdd);
            return null;
        } else {
            return currentNode.getRight();
        }
    }

    private void addRightNode(BSTNode currentNode, BSTNode nodeToAdd) {
        currentNode.setRight(nodeToAdd);
        nodeToAdd.setParent(currentNode);
        size++;
    }

    @Override
    public BSTNode search(Integer key) {
        return search(key, rootNode);
    }

    private BSTNode search(Integer key, BSTNode currentNode) {
        if (currentNode == null || key.equals(currentNode.getKey()))
            return currentNode;

        if (key < currentNode.getKey())
            return search(key, currentNode.getLeft());
        else
            return search(key, currentNode.getRight());

    }

    @Override
    public Integer predecessor(int key) {
        BSTNode searchResult = search(key);
        if (searchResult == null)
            return null;

        if (searchResult.getLeft() != null) {
            return min(searchResult.getLeft());
        } else {
            return getPredecessorUpperInTree(searchResult);
        }
    }

    private Integer getPredecessorUpperInTree(BSTNode searchResult) {
        BSTNode currentNode = searchResult.getParent();
        while (currentNode != null && isLeftChildOfParent(currentNode)) {
            currentNode = currentNode.getParent();
        }
        return getParentKeyOrNull(currentNode);
    }

    @Override
    public Integer successor(int key) {
        BSTNode searchResult = search(key);
        if (searchResult == null)
            return null;

        if (searchResult.getRight() != null) {
            return min(searchResult.getRight());
        } else {
            return getSuccessorUpperInTree(searchResult);
        }
    }

    private Integer getSuccessorUpperInTree(BSTNode searchResult) {
        BSTNode currentNode = searchResult.getParent();
        while (currentNode != null && isRightChildOfParent(currentNode)) {
            currentNode = currentNode.getParent();
        }
        return getParentKeyOrNull(currentNode);
    }

    private Integer getParentKeyOrNull(BSTNode currentNode) {
        if (currentNode == null)
            return null;
        else
            return currentNode.getParent() == null ? null : currentNode.getParent().getKey();
    }

    private boolean isRightChildOfParent(BSTNode currentNode) {
        return currentNode.getParent() != null && currentNode.getParent().getRight() == currentNode;
    }

    private boolean isLeftChildOfParent(BSTNode currentNode) {
        return currentNode.getParent() != null && currentNode.getParent().getLeft() == currentNode;
    }

    @Override
    public boolean remove(Integer key) {
        BSTNode searchResult = search(key);
        if (searchResult == null)
            return false;

        if (hasNoChildren(searchResult)) {
            deleteWithoutChildren(searchResult);
        } else if (hasTwoChildren(searchResult)) {
            return false;
        } else if (hasOneChild(searchResult)) {
            deleteWithOneChild(searchResult);
        }

        size--;
        return true;

    }

    private boolean hasTwoChildren(BSTNode searchResult) {
        return searchResult.getLeft() != null && searchResult.getRight() != null;
    }

    private void deleteWithOneChild(BSTNode searchResult) {
        if (isInRootPosition(searchResult))
            deleteWithOneChildIfRootElement(searchResult);
        else
            deleteWithOneChildIfNotRootElement(searchResult);

    }

    private boolean isInRootPosition(BSTNode searchResult) {
        return searchResult.getParent() == null;
    }

    private void deleteWithOneChildIfNotRootElement(BSTNode searchResult) {
        BSTNode parent = searchResult.getParent();
        getNotNullNode(searchResult).setParent(parent);

        if (parent.getLeft() != null)
            parent.setLeft(null);
        if (parent.getRight() != null)
            parent.setRight(null);

        searchResult.setParent(null);
    }

    private void deleteWithOneChildIfRootElement(BSTNode searchResult) {
        BSTNode notNullNode = getNotNullNode(searchResult);
        notNullNode.setParent(null);
        rootNode = notNullNode;
    }

    private BSTNode getNotNullNode(BSTNode searchResult) {
        return searchResult.getLeft() == null ? searchResult.getRight() : searchResult.getLeft();
    }

    private boolean hasOneChild(BSTNode searchResult) {
        return searchResult.getLeft() != null || searchResult.getRight() != null;
    }

    private void deleteWithoutChildren(BSTNode searchResult) {
        BSTNode parent = searchResult.getParent();
        if (parent == null) {
            rootNode = null;
            return;
        }

        if (isNodeLeftParentLeaf(parent, searchResult)) {
            parent.setLeft(null);
        } else {
            parent.setRight(null);
        }
    }

    private boolean isNodeLeftParentLeaf(BSTNode parent, BSTNode searchResult) {
        return parent.getLeft() != null && parent.getLeft().getKey() == searchResult.getKey();
    }

    private boolean hasNoChildren(BSTNode searchResult) {
        return searchResult.getLeft() == null && searchResult.getRight() == null;
    }

    @Override
    public void toArray(Integer[] resultArray) {
        List<Integer> resultList = new ArrayList<Integer>(size);
        print(rootNode, resultList);

        for (int i = 0; i < resultList.size(); ++i) {
            resultArray[i] = resultList.get(i);
        }
    }


    public void print(BSTNode node, List<Integer> list) {
        if (node == null)
            return;

        print(node.getLeft(), list);
        list.add(node.getKey());
        print(node.getRight(), list);
    }

    @Override
    public Integer max() {
        return max(rootNode);
    }

    private Integer max(BSTNode startNode) {
        if (startNode == null)
            return null;

        BSTNode currentNode = startNode;
        while (currentNode.getRight() != null) {
            currentNode = currentNode.getRight();
        }
        return currentNode.getKey();
    }

    @Override
    public Integer min() {
        return min(rootNode);
    }

    private Integer min(BSTNode startNode) {
        if (startNode == null)
            return null;

        BSTNode currentNode = startNode;
        while (currentNode.getLeft() != null) {
            currentNode = currentNode.getLeft();
        }
        return currentNode.getKey();
    }

    @Override
    public int getSize() {
        return size;
    }
}