import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Your implementation of HashMap.
 *
 * @author Joshua Reno
 * @version 1.0
 */
public class HashMap<K, V> implements HashMapInterface<K, V> {

    // Do not make any new instance variables.
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code INITIAL_CAPACITY}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        table = (MapEntry<K, V>[]) new MapEntry[initialCapacity];
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key is null");
        }
        if (value == null) {
            throw new IllegalArgumentException("Value is null");
        }
        int hashCode = Math.abs(key.hashCode());
        hashCode = hashCode % table.length;
        if (table[hashCode] == null) {
            table[hashCode] = new MapEntry<K, V>(key, value);
            size++;
            resize();
            return null;
        } else {
            if (table[hashCode].getKey().equals(key)
                    && !table[hashCode].isRemoved()) {
                V oldValue = table[hashCode].getValue();
                table[hashCode].setValue(value);
                return oldValue;
            }
            int[] removeMarker = {-1};
            int[] duplicateIndex = {-1};
            int probeIndex = quadraticProbing(hashCode, 0,
                    table.length, table,
                    removeMarker, duplicateIndex,
                    key);
            while (probeIndex == -1 && removeMarker[0] == -1) {
                resizeBackingTable(2 * table.length + 1);
                probeIndex = quadraticProbing(hashCode, 0,
                        table.length, table,
                        removeMarker, duplicateIndex,
                        key);
            }
            if (duplicateIndex[0] != -1
                    && !table[duplicateIndex[0]].isRemoved()) {
                V data = table[duplicateIndex[0]].getValue();
                table[duplicateIndex[0]].setValue(value);
                return data;
            } else if (removeMarker[0] != -1
                    && table[removeMarker[0]].isRemoved()) {
                table[removeMarker[0]] = new MapEntry<K, V>(key, value);
                size++;
                resize();
                return null;
            } else {
                table[probeIndex] = new MapEntry<K, V>(key, value);
                size++;
                resize();
                return null;
            }
        }
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key provided is null");
        }
        int hashCode = (Math.abs(key.hashCode())) % table.length;
        int[] duplicateIndex = {-1};
        int[] removeMarker = {-1};
        quadraticProbing(hashCode, 0, table.length,
                table, removeMarker, duplicateIndex, key);
        if (duplicateIndex[0] != -1
                && !table[duplicateIndex[0]].isRemoved()) {
            size--;
            table[duplicateIndex[0]].setRemoved(true);
            return table[duplicateIndex[0]].getValue();
        }
        throw new NoSuchElementException("Key not in the map");
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key provided is null");
        }
        int hashCode = (Math.abs(key.hashCode())) % table.length;
        int[] duplicateIndex = {-1};
        int[] removeMarker = {-1};
        quadraticProbing(hashCode, 0, table.length,
                table, removeMarker, duplicateIndex, key);
        if (duplicateIndex[0] != -1) {
            return table[duplicateIndex[0]].getValue();
        }
        throw new NoSuchElementException("Key not in the map");
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key provided is null");
        }
        int hashCode = (Math.abs(key.hashCode())) % table.length;
        int[] duplicateIndex = {-1};
        int[] removeMarker = {-1};
        quadraticProbing(hashCode, 0, table.length,
                table, removeMarker, duplicateIndex, key);
        if (duplicateIndex[0] != -1) {
            return (!table[duplicateIndex[0]].isRemoved());
        }
        return false;
    }

    @Override
    public void clear() {
        size = 0;
        table = (MapEntry<K, V>[]) new MapEntry[INITIAL_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<K> keySet() {
        Set<K> setOfKeys = new HashSet<K>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null
                    && table[i].getKey() != null
                    && !table[i].isRemoved()) {
                setOfKeys.add(table[i].getKey());
            }
        }
        return setOfKeys;
    }

    @Override
    public List<V> values() {
        List<V> listOfValues = new LinkedList<V>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null
                    && table[i].getValue() != null
                    && !table[i].isRemoved()) {
                listOfValues.add(table[i].getValue());
            }
        }
        return listOfValues;
    }

    @Override
    public void resizeBackingTable(int length) {
        if (!(length > 0)) {
            throw new IllegalArgumentException("length is non-positive");
        }
        if (length < size) {
            throw new IllegalArgumentException("length is less"
                    + " than current length of table");
        }
        MapEntry<K, V>[] tempArray = (MapEntry<K, V>[]) new MapEntry[length];
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null
                    && !table[i].isRemoved()) {

                int[] duplicateIndex = {-1};
                int[] removeMarker = {-1};

                int hashCode = Math.abs(table[i].getKey().hashCode());
                hashCode = hashCode % tempArray.length;
                int probeIndex = quadraticProbing(hashCode, 0,
                        tempArray.length, tempArray, removeMarker,
                        duplicateIndex, null);
                tempArray[probeIndex] = table[i];
            }
        }
        table = tempArray;
    }

    @Override
    public MapEntry<K, V>[] getTable() {
        // DO NOT EDIT THIS METHOD!
        return table;
    }

    /**
     * Returns the appropriate index value for an element given the
     * parameters below
     *
     * Note: This is MapEntry[] specific
     * The -1 return is in cases where more searches have to be done
     * but in a resized array, in this case, the probing for adding
     * elements to the resized array occur in the new tempArray
     *
     * @param hashCode the hashCode of the key
     * @param addition the integer value added to the hashCode
     * @param tableLength the length of the appropriate table to be checked
     * @param array the table to be checked
     * @param removeMarker a placeholder for a possible removed marker
     * @param duplicateIndex the index of a duplicate
     * @param key the key that is used to search for duplicates
     * @return the appropriate index in the table
     */
    private int quadraticProbing(int hashCode, int addition, int tableLength,
                                 MapEntry[] array, int[] removeMarker,
                                 int[] duplicateIndex, K key) {
        if (addition == table.length) {
            return -1;
        }
        if (array[((hashCode + (int) (Math.pow(addition, 2))) % tableLength)]
                == null) {
            return (hashCode + (int) (Math.pow(addition, 2))) % tableLength;
        } else {
            if (array[((hashCode + (int) (Math.pow(addition, 2)))
                    % tableLength)] != null
                    && (array[((hashCode + (int) (Math.pow(addition, 2)))
                    % tableLength)].isRemoved())
                    && removeMarker[0] == -1) {
                removeMarker[0] = ((hashCode + (int) (Math.pow(addition, 2)))
                        % tableLength);
            }
            if ((array[((hashCode + (int) (Math.pow(addition, 2)))
                    % tableLength)].getKey().equals(key))) {
                duplicateIndex[0] = ((hashCode + (int) (Math.pow(addition, 2)))
                        % tableLength);
            }
            return quadraticProbing(hashCode, addition + 1,
                    tableLength, array, removeMarker, duplicateIndex, key);
        }
    }

    /**
     * Grows the backing array by the standard 2*n+1 value
     */
    private void resize() {
        if (size > table.length * MAX_LOAD_FACTOR) {
            resizeBackingTable(2 * table.length + 1);
        }
    }
}
