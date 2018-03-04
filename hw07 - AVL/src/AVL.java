import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Joshua Reno
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> implements AVLInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty AVL tree.
     * DO NOT IMPLEMENT THIS CONSTRUCTOR!
     */
    public AVL() {
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data provided is null");
        }
        for (T elem: data) {
            if (elem == null) {
                throw new IllegalArgumentException("Element "
                        + "in data provided is null");
            }
            add(elem);
        }
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data provided is null");
        } else if (size == 0) {
            root = new AVLNode<T>(data);
            size++;
        } else {
            AVLNode<T> node = add(data, root);
            if (node != null) {
                root = node;
                size++;
            }
        }
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data provided is null");
        } else if (size == 0) {
            throw new NoSuchElementException("AVL is empty");
        } else if (size == 1) {
            T rootData = root.getData();
            if (rootData.equals(data)) {
                root = null;
                size--;
                return rootData;
            }
            return null;
        }
        AVLNode<T> dummyNode = new AVLNode<T>(null);
        root = remove(data, root, dummyNode);
        T nodeData = dummyNode.getData();
        size--;
        updateHeight(root);
        updateBalanceFactor(root);
        return nodeData;
    }

    /**
     * returns the corrected subtree or throws an exception if the
     * data is not in the AVL
     *
     * @param data to be removed
     * @param node node to search
     * @param dummyNode node with the new data
     * @return the corrected subtree
     * @throws NoSuchElementException if element is not in the AVL
     */
    private AVLNode<T> remove(T data, AVLNode<T> node, AVLNode<T> dummyNode)
            throws NoSuchElementException {
        if (node == null) {
            throw new NoSuchElementException("Element not in AVL");
        } else if (data.compareTo(node.getData()) < 0) {
            AVLNode<T> tempNode = remove(data, node.getLeft(), dummyNode);
            node.setLeft(tempNode);
            updateHeight(node);
            updateBalanceFactor(node);
            AVLNode<T> rotationNode = rotation(node);
            if (rotationNode != null) {
                return rotationNode;
            }
            return node;
        } else if (data.compareTo(node.getData()) > 0) {
            AVLNode<T> tempNode = remove(data, node.getRight(), dummyNode);
            node.setRight(tempNode);
            updateHeight(node);
            updateBalanceFactor(node);
            AVLNode<T> rotationNode = rotation(node);
            if (rotationNode != null) {
                return rotationNode;
            }
            return node;
        } else {
            AVLNode<T> left = node.getLeft();
            AVLNode<T> right = node.getRight();
            dummyNode.setData(node.getData());
            if (left == null && right == null) {
                return null;
            } else if (left == null) {
                return right;
            } else if (right == null) {
                return left;
            } else {
                AVLNode<T> parentNode = node.getRight();
                if (parentNode == null) {
                    node = left;
                    updateHeight(node);
                    updateBalanceFactor(node);
                    rotation(node);
                } else if (right.getLeft() == null) {
                    // update height
                    parentNode.setLeft(node.getLeft());
                    node = parentNode;
                    updateHeight(node);
                    updateBalanceFactor(node);
                    rotation(node);
                } else {
                    AVLNode<T> secondDummyNode = new AVLNode<T>(null);
                    AVLNode<T> newNode = removeSuccessor(node.getRight(),
                            secondDummyNode);
                    if (secondDummyNode.getData() != null) {
                        node.setData(secondDummyNode.getData());
                    }
                    node.setRight(newNode);
                    updateHeight(node);
                    updateBalanceFactor(node);
                }
                return node;
            }
        }
    }

    /**
     * Retrusn the successor in the form of the dummyNode
     *
     * @param node the start of the  left
     *             "diagonal" that is searched for the successor
     * @param dummyNode node used to store the successor data
     * @return the correctly balanced subtree
     */
    private AVLNode<T> removeSuccessor(AVLNode<T> node, AVLNode<T> dummyNode) {
        if (node.getLeft() == null) {
            dummyNode.setData(node.getData());
            return node.getRight();
        } else {
            node.setLeft(removeSuccessor(node.getLeft(), dummyNode));
            updateHeight(node);
            updateBalanceFactor(node);
            AVLNode<T> subtree = rotation(node);
            if (subtree != null) {
                return subtree;
            } else {
                return node;
            }
        }
    }

    /**
     * adds the data to the AVL and checks for equal data
     *
     * @param data data to be added
     * @param node root node
     * @return root node with proper subtrees
     */
    private AVLNode<T> add(T data, AVLNode<T> node) {
        if (node == null) {
            node = new AVLNode<T>(data);
            updateBalanceFactor(node);
        } else {
            if (node.getData().compareTo(data) == 0) {
                return null;
            } else {
                if (node.getData().compareTo(data) > 0) {
                    node.setLeft(add(data, node.getLeft()));
                } else {
                    node.setRight(add(data, node.getRight()));
                }
                updateHeight(node);
                updateBalanceFactor(node);
                AVLNode<T> rotationNode = rotation(node);
                if (rotationNode != null) {
                    return rotationNode;
                }
            }
        }
        return node;
    }

    /**
     * checks if node is balanced and returns balanced node with subtree
     *
     * @param node the node which may or may not be unbalanced
     * @return the balanced subtree
     */
    private AVLNode<T> rotation(AVLNode<T> node) {
        if (node.getBalanceFactor() == -2
                && (node.getRight().getBalanceFactor() == 0
                || node.getRight().getBalanceFactor() == -1)) {
            return leftRotation(node);
        } else if (node.getBalanceFactor() == 2
                && (node.getLeft().getBalanceFactor() == 0
                || node.getLeft().getBalanceFactor() == 1)) {
            return rightRotation(node);
        } else if (node.getBalanceFactor() == 2
                && node.getLeft().getBalanceFactor() == -1) {
            node.setLeft(quasiLeftRotation(node.getLeft()));
            return rightRotation(node);
        } else if (node.getBalanceFactor() == -2
                && node.getRight().getBalanceFactor() == 1) {
            node.setRight(quasiRightRotation(node.getRight()));
            return leftRotation(node);
        }
        return null;
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data provided is null");
        } else if (size == 0) {
            throw new NoSuchElementException("Element not in AVL");
        } else {
            AVLNode<T> helperNode = get(data, root);
            if (helperNode == null) {
                throw new NoSuchElementException("Element not in AVL");
            } else {
                return helperNode.getData();
            }
        }
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data provided is null");
        } else if (size == 0) {
            return false;
        } else if (size == 1) {
            return (root.getData().equals(data));
        } else {
            return contains(data, root);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> preorder() {
        List<T> list = new ArrayList<T>();
        list = preOrder(root, list);
        return list;
    }

    @Override
    public List<T> postorder() {
        List<T> list = new ArrayList<T>();
        list = postOrder(root, list);
        return list;
    }

    @Override
    public List<T> inorder() {
        List<T> list = new ArrayList<T>();
        list = inOrder(root, list);
        return list;
    }

    @Override
    public List<T> levelorder() {
        List<T> list = new ArrayList<T>();
        Queue<AVLNode<T>> queue = new LinkedList<AVLNode<T>>();
        queue.add(root);
        while (!(queue.isEmpty())) {
            AVLNode<T> node = queue.remove();
            if (node != null && node.getData() != null) {
                list.add(node.getData());
                queue.add(node.getLeft());
                queue.add(node.getRight());
            }
        }
        return list;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int height() {
        if (size == 0) {
            return -1;
        }
        return root.getHeight();
    }

    @Override
    public AVLNode<T> getRoot() {
        // DO NOT EDIT THIS METHOD!
        return root;
    }

    /**
     * Should run in O(n)
     *
     * @param node the root node
     * @param list the list to which data is added
     * @return the preorder traversal of the tree
     */
    private List<T> preOrder(AVLNode<T> node, List<T> list) {
        if (node != null && node.getData() != null) {
            list.add(node.getData());
            preOrder(node.getLeft(), list);
            preOrder(node.getRight(), list);
        }
        return list;
    }

    /**
     * Should run in O(n)
     *
     * @param node the root node
     * @param list the list to which data is added
     * @return the postorder traversal of the tree
     */
    private List<T> postOrder(AVLNode<T> node, List<T> list) {
        if (node != null && node.getData() != null) {
            postOrder(node.getLeft(), list);
            postOrder(node.getRight(), list);
            list.add(node.getData());
        }
        return list;
    }

    /**
     * Should run in O(n)
     *
     * @param node the root node
     * @param list the list to which data is added
     * @return the inorder traversal of the tree
     */
    private List<T> inOrder(AVLNode<T> node, List<T> list) {
        if (node != null && node.getData() != null) {
            inOrder(node.getLeft(), list);
            list.add(node.getData());
            inOrder(node.getRight(), list);
        }
        return list;
    }

    /**
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @param data the data that is searched for
     * @param node the root node
     * @return if the data is in the AVL
     */
    private boolean contains(T data, AVLNode<T> node) {
        if (node == null) {
            return false;
        }
        if (data.compareTo(node.getData()) == 0) {
            return true;
        } else if (data.compareTo(node.getData()) < 0) {
            return contains(data, node.getLeft());
        } else {
            return contains(data, node.getRight());
        }
    }

    /**
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @param data the data searched for in the AVL
     * @param node the root node
     * @return the node in the AVL with the data if it exists/
     *  returns null otherwise
     */
    private AVLNode<T> get(T data, AVLNode<T> node) {
        if (data.compareTo(node.getData()) == 0) {
            return node;
        } else if (data.compareTo(node.getData()) < 0
                && node.getLeft() != null) {
            return get(data, node.getLeft());
        } else if (data.compareTo(node.getData()) > 0
                && node.getRight() != null) {
            return get(data, node.getRight());
        } else {
            return null;
        }
    }

    /**
     * Updates the height of a node
     *
     * @param node the node for which the height
     *             has to be updated
     */
    private void updateBalanceFactor(AVLNode<T> node) {
        if (node == null) {
            return;
        }
        int left;
        int right;
        if (node.getLeft() == null) {
            left = -1;
        } else {
            left = node.getLeft().getHeight();
        }
        if (node.getRight() == null) {
            right = -1;
        } else {
            right = node.getRight().getHeight();
        }
        node.setBalanceFactor(left - right);

    }

    /**
     * updates the height of the parameter node
     *
     * @param node which needs its height corrected
     */
    private void updateHeight(AVLNode<T> node) {
        if (node != null && node.getLeft() != null && node.getRight() != null) {
            node.setHeight(Math.max(node.getLeft().getHeight(),
                    node.getRight().getHeight()) + 1);
        } else if (node != null && node.getLeft() != null) {
            node.setHeight(node.getLeft().getHeight() + 1);
        } else if (node != null && node.getRight() != null) {
            node.setHeight(node.getRight().getHeight() + 1);
        } else {
            if (node != null) {
                node.setHeight(0);
            }
        }
    }

    /**
     * does a left rotation about a node
     *
     * @param node node which requires a left rotation
     * @return the subtree with the proper rotation
     */
    private AVLNode<T> leftRotation(AVLNode<T> node) {
        // nodes
        AVLNode<T> parentNode = node;
        AVLNode<T> child = node.getRight();
        AVLNode<T> grandChild = node.getRight().getRight();

        // subtree's
        AVLNode<T> parentLeftSubtree = node.getLeft();
        AVLNode<T> childLeftSubtree = node.getRight().getLeft();
        AVLNode<T> grandChildLeftSubtree = node.getRight().getRight().getLeft();
        AVLNode<T> grandChildRightSubtree = node.getRight()
                .getRight().getRight();

        //setting the nodes
        node = new AVLNode<T>(child.getData());
        node.setLeft(parentNode);
        node.setRight(grandChild);
        node.getLeft().setLeft(parentLeftSubtree);
        node.getLeft().setRight(childLeftSubtree);
        node.getRight().setLeft(grandChildLeftSubtree);
        node.getRight().setRight(grandChildRightSubtree);

        //updating height's and balance factors
        updateHeight(node.getLeft());
        updateBalanceFactor(node.getLeft());
        updateHeight(node.getRight());
        updateBalanceFactor(node.getRight());
        updateHeight(node);
        updateBalanceFactor(node);
        return node;
    }

    /**
     * does a right rotation about a node
     *
     * @param node node which requires a right rotation
     * @return the subtree with the proper rotation
     */
    private AVLNode<T> rightRotation(AVLNode<T> node) {
        // nodes
        AVLNode<T> parentNode = node;
        AVLNode<T> child = node.getLeft();
        AVLNode<T> grandChild = node.getLeft().getLeft();

        // subtree's
        AVLNode<T> parentRightSubtree = node.getRight();
        AVLNode<T> childRightSubtree = node.getLeft().getRight();
        AVLNode<T> grandChildRightSubtree = node.getLeft().getLeft().getRight();
        AVLNode<T> grandChildLeftSubtree = node.getLeft().getLeft().getLeft();

        // setting the nodes
        node = new AVLNode<T>(child.getData());
        node.setLeft(grandChild);
        node.setRight(parentNode);
        node.getLeft().setLeft(grandChildLeftSubtree);
        node.getLeft().setRight(grandChildRightSubtree);
        node.getRight().setLeft(childRightSubtree);
        node.getRight().setRight(parentRightSubtree);

        //updating height's and balance factors
        updateHeight(node.getLeft());
        updateBalanceFactor(node.getLeft());
        updateHeight(node.getRight());
        updateBalanceFactor(node.getRight());
        updateHeight(node);
        updateBalanceFactor(node);
        return node;
    }

    /**
     * does a left rotation about a node for a
     * left-right rotation
     *
     * @param node node which requires a left rotation
     * @return the subtree with the proper rotation
     */
    private AVLNode<T> quasiLeftRotation(AVLNode<T> node) {
        AVLNode<T> parentNode = node;
        AVLNode<T> child = node.getRight();

        AVLNode<T> parentLeftSubtree = parentNode.getLeft();
        AVLNode<T> childLeftSubtree = child.getLeft();
        AVLNode<T> childRightSubtree = child.getRight();

        node = new AVLNode<T>(child.getData());
        node.setLeft(parentNode);
        node.setRight(childRightSubtree);
        node.getLeft().setLeft(parentLeftSubtree);
        node.getLeft().setRight(childLeftSubtree);

        updateHeight(node.getLeft());
        updateBalanceFactor(node.getLeft());
        updateHeight(node);
        updateBalanceFactor(node);
        return node;
    }

    /**
     * does a right rotation about a node for a
     * right-left rotation
     *
     * @param node node which requires a right rotation
     * @return the subtree with the proper rotation
     */
    private AVLNode<T> quasiRightRotation(AVLNode<T> node) {
        AVLNode<T> parentNode = node;
        AVLNode<T> child = node.getLeft();

        AVLNode<T> childLeftSubtree = child.getLeft();
        AVLNode<T> childRightSubtree = child.getRight();
        AVLNode<T> parentRightSubtree = parentNode.getRight();

        node = new AVLNode<T>(child.getData());
        node.setRight(parentNode);
        node.setLeft(childLeftSubtree);
        node.getRight().setLeft(childRightSubtree);
        node.getRight().setRight(parentRightSubtree);

        updateHeight(node.getRight());
        updateBalanceFactor(node.getRight());
        updateHeight(node);
        updateBalanceFactor(node);
        return node;
    }
}
