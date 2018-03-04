import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Your implementation of a binary search tree.
 *
 * @author Joshua Reno
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> implements BSTInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty BST.
     * YOU DO NOT NEED TO IMPLEMENT THIS CONSTRUCTOR!
     */
    public BST() {
    }

    /**
     * Initializes the BST with the data in the Collection. The data in the BST
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data provided is null");
        }
        for (T elem: data) {
            if (elem == null) {
                throw new IllegalArgumentException("Data provided is null");
            }
            add(elem);
        }
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data provided is null");
        } else if (size == 0) {
            root = new BSTNode<T>(data);
            size++;
        } else {
            try {
                add(data, root);
            } catch (IllegalArgumentException e) {
                size--;
            }
            size++;
        }
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data provided is null");
        } else if (size == 0) {
            throw new NoSuchElementException("BST is empty");
        } else if (size == 1) {
            T rootData = root.getData();
            root = null;
            size--;
            return rootData;
        }

        if (root.getData().equals(data)) {
            T rootData = root.getData();
            BSTNode<T> parentNode = root.getRight();
            if (parentNode == null) {
                root = root.getLeft();
            } else if (root.getRight().getLeft() == null) {
                parentNode.setLeft(root.getLeft());
                root = parentNode;
            } else {
                BSTNode<T> tempNode = root.getRight().getLeft();
                while (tempNode.getLeft() != null) {
                    parentNode = parentNode.getLeft();
                    tempNode = tempNode.getLeft();
                }
                parentNode.setLeft(tempNode.getRight());
                tempNode.setRight(root.getRight());
                tempNode.setLeft(root.getLeft());
                root = tempNode;
            }
            size--;
            return rootData;
        }
        BSTNode<T> dummyNode = new BSTNode<T>(null);
        root = remove(data, root, dummyNode);
        T nodeData = dummyNode.getData();
        size--;
        return nodeData;
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data provided is null");
        } else if (size == 0) {
            throw new NoSuchElementException("Element not in BST");
        } else {
            BSTNode<T> helperNode = get(data, root);
            if (helperNode == null) {
                throw new NoSuchElementException("Element not in BST");
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
        Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
        queue.add(root);
        while (!(queue.isEmpty())) {
            BSTNode<T> node = queue.remove();
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
        return maximumHeight(root);
    }

    @Override
    public BSTNode<T> getRoot() {
        // DO NOT EDIT THIS METHOD!
        return root;
    }


    /**
     * Should have a running time of O(n) because
     * it traverses through each node's left and right
     * subtrees
     *
     * @param node the root node
     * @return the maximum height of the BST
     */
    private int maximumHeight(BSTNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            int leftHeight = maximumHeight(node.getLeft());
            int rightHeight = maximumHeight(node.getRight());

            if (leftHeight > rightHeight) {
                return leftHeight + 1;
            } else {
                return rightHeight + 1;
            }
        }
    }




    /**
     * If a duplicate is found the method calls an IllegalArgumentException
     * which is handled by the add method above. The size is only decremented
     * above if no duplicate is found.
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @param data the data to be added
     * @param node the root node
     * @return the node with the added subtree
     */
    private BSTNode<T> add(T data, BSTNode<T> node) {
        if (node == null) {
            node = new BSTNode<T>(data);
        } else {
            if (node.getData().equals(data)) {
                throw new IllegalArgumentException("Duplicate data");
            }
            if (node.getData().compareTo(data) > 0) {
                node.setLeft(add(data, node.getLeft()));
            } else {
                node.setRight(add(data, node.getRight()));
            }
        }
        return node;
    }

    /**
     * Removes the data from the tree in 4 cases:
     * 1: the data is a leaf
     * 2: the left subtree is null
     * 3: the right subtree is null
     * 4: there are two children, in which case,
     * the successor needs to be found and used.
     *
     * A NoSuchElementException is thrown if the data is not found in the BST
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @param data the data to be added
     * @param node the root node
     * @param dummyNode the node to be returned with the data
     * @return the node with the added subtree
     * @throws NoSuchElementException if the data is not found
     */
    private BSTNode<T> remove(T data, BSTNode<T> node, BSTNode<T> dummyNode)
            throws NoSuchElementException {
        if (node == null) {
            throw new NoSuchElementException("Element not in BST");
        } else if (data.compareTo(node.getData()) < 0) {
            BSTNode<T> tempNode = remove(data, node.getLeft(), dummyNode);
            node.setLeft(tempNode);
            return node;
        } else if (data.compareTo(node.getData()) > 0) {
            BSTNode<T> tempNode = remove(data, node.getRight(), dummyNode);
            node.setRight(tempNode);
            return node;
        } else {
            // if the data is equal to the data in the node
            BSTNode<T> left = node.getLeft();
            BSTNode<T> right = node.getRight();

            dummyNode.setData(node.getData());
            if (left == null && right == null) {
                return null;
            } else if (left == null) {
                return right;
            } else if (right == null) {
                return left;
            } else {
                BSTNode<T> successorParent = right;
                if (successorParent.getLeft() == null) {
                    node.setData(successorParent.getData());
                    node.setRight(successorParent.getRight());
                } else {
                    BSTNode<T> successor = successorParent.getLeft();
                    while (successor.getLeft() != null) {
                        successorParent = successorParent.getLeft();
                        successor = successor.getLeft();
                    }
                    successorParent.setLeft(successor.getRight());
                    node.setData(successor.getData());
                }

                return node;
            }
        }
    }

    /**
     * Should run in O(n)
     *
     * @param node the root node
     * @param list the list to which data is added
     * @return the preorder traversal of the tree
     */
    private List<T> preOrder(BSTNode<T> node, List<T> list) {
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
    private List<T> postOrder(BSTNode<T> node, List<T> list) {
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
    private List<T> inOrder(BSTNode<T> node, List<T> list) {
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
     * @return if the data is in the BST
     */
    private boolean contains(T data, BSTNode<T> node) {
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
     * @param data the data searched for in the BST
     * @param node the root node
     * @return the node in the BST with the data if it exists/
     *  returns null otherwise
     */
    private BSTNode<T> get(T data, BSTNode<T> node) {
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
}