import java.util.NoSuchElementException;

/**
 * Your implementation of a SinglyLinkedList
 *
 * @author Joshua Reno
 * @version 1.0
 */
public class SinglyLinkedList<T> implements LinkedListInterface<T> {
    // Do not add new instance variables.
    private LinkedListNode<T> head;
    private LinkedListNode<T> tail;
    private int size;

    @Override
    public void addAtIndex(int index, T data) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Negative index");
        } else if (index > size) {
            throw new IndexOutOfBoundsException("Index is greater "
                    + "than the size of the linked list. "
                    + "Data cannot be added past the linked list size.");
        } else if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else {
            LinkedListNode<T> newNode = new LinkedListNode<T>(data);
            if (isEmpty()) {
                head = newNode;
                tail = newNode;
                size++;
            } else if (index == 0) {
                addToFront(data);
            } else {

                LinkedListNode<T> currentNode = head;
                int count = 1;
                while (count < index) {
                    currentNode = currentNode.getNext();
                    count++;
                }
                LinkedListNode<T> futureNode = currentNode.getNext();
                currentNode.setNext(newNode);
                newNode.setNext(futureNode);

                if (futureNode == null) {
                    tail = newNode;
                }

                size++;
            }
        }
    }

    @Override // done
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data entered is null");
        } else {
            if (isEmpty()) {
                LinkedListNode<T> newNode = new LinkedListNode<T>(data);
                head = newNode;
                tail = newNode;
                size++;
            } else {
                LinkedListNode<T> newHead = new LinkedListNode<T>(data, head);
                head = newHead;
                size++;
            }
        }
    }

    @Override // done
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data entered is null");
        } else {
            if (isEmpty()) {
                LinkedListNode<T> newNode = new LinkedListNode<T>(data);
                head = newNode;
                tail = newNode;
                size++;
            } else {
                LinkedListNode<T> newTail = new LinkedListNode<T>(data);
                tail.setNext(newTail);
                tail = newTail;
                size++;
            }
        }
    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Negative index");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("Index is greater "
                    + "than the size of the linked list. "
                    + "Data cannot be added past the linked list size.");
        } else {
            if (isEmpty()) {
                return null;
            } else if (index == 0) {
                return (removeFromFront());
            } else if (index - 1 == size - 1) {
                removeFromBack(); // is this efficient?
            } else {
                LinkedListNode<T> currentNode = head;
                LinkedListNode<T> removeNode = null;
                LinkedListNode<T> nextNode = null;

                for (int i = 0; i < index - 1; i++) {
                    currentNode = currentNode.getNext();
                }
                if (currentNode.getNext() != null) {
                    removeNode = currentNode.getNext();
                }

                if (removeNode != null) {
                    nextNode = removeNode.getNext();
                }

                currentNode.setNext(nextNode);
                if (nextNode == null) {
                    tail = currentNode;
                }

                size--;
                return removeNode.getData();
            }
            return null;
        }
    }

    @Override // done
    public T removeFromFront() {
        if (size == 0) {
            return null;
        } else if (size == 1) {
            T data = head.getData();
            size--;
            head = null;
            tail = null;
            return data;
        } else {
            T data = head.getData();
            if (head.getNext() != null) {
                LinkedListNode<T> newHead = head.getNext();
                head = newHead;
            }

            size--;
            return data;
        }

    }

    @Override // done - check
    public T removeFromBack() {
        if (isEmpty()) {
            return null;
        } else if (size == 1) {
            T data = head.getData();
            head = null;
            tail = null;
            size--;
            return data;
        } else if (size == 2) {
            T data = tail.getData();
            head.setNext(null);
            tail = head;
            size--;
            return data;
        } else {
            T data = null;
            LinkedListNode<T> currentNode = head;
            for (int i = 0; i < size - 2; i++) {
                currentNode = currentNode.getNext();
            }
            data = currentNode.getNext().getData();
            currentNode.setNext(null);
            tail = currentNode;
            size--;
            return data;
        }
    }

    @Override
    public T removeFirstOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data entered is null");
        } else {
            if (size == 0) {
                throw new NoSuchElementException("Element not in linked list");
            } else if (size == 1) {
                if (!head.getData().equals(data)) {
                    throw new NoSuchElementException(
                            "Element not in linked list");
                } else {
                    size--;
                    head = null;
                    tail = null;
                    return data;
                }
            } else {
                if (head.getData().equals(data)) {
                    head = head.getNext();
                    size--;
                    return data;
                }

                LinkedListNode<T> currentNode = head;
                LinkedListNode<T> dataNode;
                LinkedListNode<T> afterNode;

                for (int i = 0; i < size - 1; i++) {
                    if (currentNode.getNext().getData().equals(data)) {
                        dataNode = currentNode.getNext();
                        afterNode = dataNode.getNext();
                        currentNode.setNext(afterNode);
                        if (afterNode == null) {
                            tail = currentNode;
                        }
                        size--;
                        return dataNode.getData();
                    }
                    currentNode = currentNode.getNext();
                }
            }
            throw new NoSuchElementException("Element not in linked list");
        }
    }

    @Override
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Negative index");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("Index is greater "
                    + "than the size of the linked list. "
                    + "Data cannot be added past the linked list size.");
        } else {
            if (size == 1) {
                return head.getData();
            } else if (index == 0) {
                return head.getData();
            }
            LinkedListNode<T> currentNode = head;
            for (int i = 0; i < index; i++) {
                if (currentNode != null && currentNode.getNext() != null) {
                    currentNode = currentNode.getNext();
                }
            }
            if (currentNode != null) {
                return currentNode.getData();
            }
        }
        return null;
    }

    @Override
    public Object[] toArray() {
        T[] dataArray = (T[]) new Object[size];
        LinkedListNode<T> currentNode = head;
        if (!isEmpty()) {
            dataArray[0] = head.getData();
            for (int i = 0; i < size - 1; i++) {
                if (currentNode != null && currentNode.getNext() != null) {
                    currentNode = currentNode.getNext();
                    dataArray[i + 1] = currentNode.getData();
                }
            }
        }
        return (dataArray);
    }

    @Override // done
    public boolean isEmpty() {
        return (size == 0 && head == null && tail == null);
    }

    @Override // done
    public int size() {
        return size;
    }

    @Override // done
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    @Override
    public LinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}
