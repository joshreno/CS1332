/**
 * Your implementation of an ArrayList.
 *
 * @author Joshua Reno
 * @version 1
 */
public class ArrayList<T> implements ArrayListInterface<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     *
     * You may add statements to this method.
     */
    public ArrayList() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public void addAtIndex(int index, T data) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index is negative");
        } else if (index > size) {
            throw new IndexOutOfBoundsException("The index is greater than"
                    + "the size of the array");
        } else if (data == null) {
            throw new IllegalArgumentException("The data is null");
        } else {
            if (backingArray.length == size) {
                growArray();
            }
            if (size == 0) {
                backingArray[0] = data;
                // check this
            } else {
                T[] temp = (T[]) new Object[backingArray.length];
                // check for regrowing the array
                for (int i = 0; i < index; i++) {
                    temp[i] = backingArray[i];
                }
                for (int i = index + 1; i < size + 1; i++) {
                    if (backingArray.length == size) {
                        growArray();
                    }
                    temp[i] = backingArray[i - 1];
                } // these two for loops traverse through the
                // whole array once so it is O(n)
                temp[index] = data;
                backingArray = temp;
            }
            size++;
        }
    }

    @Override
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        } else {
            if (backingArray.length == size) {
                growArray();
            }
            if (size  == 0) {
                backingArray[0] = data;
            } else {
                if (size == 0) {
                    backingArray[0] = data;
                } else {
                    T[] temp = (T[]) new Object[backingArray.length];
                    for (int i = 1; i < size + 1; i++) {
                        if (backingArray.length == size) {
                            growArray();
                        }
                        temp[i] = backingArray[i - 1];
                    }
                    temp[0] = data;
                    backingArray = temp;
                }
            }
            size++;
        }
    }

    @Override
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        } else {
            if (backingArray.length == size) {
                growArray();
            }
            backingArray[size] = data;
        }
        size++;
    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index is less than 0");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("The index is greater than "
                    + "the size of the array");
        } else {
            T temp = backingArray[index];
            backingArray[index] = null;
            for (int i = index + 1; i < size; i++) {
                // check to make sure i is not over length
                backingArray[i - 1] = backingArray[i];
            }
            backingArray[size - 1] = null;
            size--;
            return temp;
        }
    }

    @Override
    public T removeFromFront() {
        T temp = null;
        if (isEmpty()) {
            return null;
        } else {
            temp = backingArray[0];
            for (int i = 1; i < size; i++) {
                backingArray[i - 1] = backingArray[i];
            }
            backingArray[size - 1] = null;
            size--;
            return temp;
        }
    }

    @Override
    public T removeFromBack() {
        if (isEmpty()) {
            return null;
        } else {
            T returnedData = backingArray[size - 1];
            backingArray[size - 1] = null;
            size--;
            return returnedData; // is this the last one
        }
    }

    @Override
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("The index is "
                    + "less than zero");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("The index is "
                    + "greater than or equal to the size of the array");
        } else {
            return (backingArray[index]);
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

    @Override
    public void clear() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public Object[] getBackingArray() {
        // DO NOT MODIFY.
        return backingArray;
    }


    /**
     * Grows the size of the backingArray if necessary
     *
     * O(n).
     */
    private void growArray() {
        T[] temp = (T[]) new Object[2 * size()];
        for (int i = 0; i < size; i++) {
            temp[i] = backingArray[i];
        }
        backingArray = temp;
    }
}
