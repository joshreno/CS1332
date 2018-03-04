import java.util.NoSuchElementException;
/**
 * Your implementation of a linked queue.
 *
 * @author YOUR NAME HERE
 * @version 1.0
 */
public class LinkedQueue<T> implements QueueInterface<T> {

    // Do not add new instance variables.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    @Override
    public T dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("The queue is null");
        } else {
            if (size == 0) {
                return null;
            }
            T data = head.getData();
            head = head.getNext();
            size--;
            if (size == 0) {
                head = null;
                tail = null;
            }
            return data;
        }
    }

    @Override
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data provided is null");
        } else {
            LinkedNode<T> newNode = new LinkedNode(data, null);
            if (size == 0) {
                head = newNode;
                tail = newNode;
            } else {
                tail.setNext(newNode);
            }

            tail = newNode;

            size++;
        }
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the head of this queue.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the head node
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail of this queue.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the tail node
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}