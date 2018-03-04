import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;


/**
 * Your implementation of various sorting algorithms.
 *
 * @author Joshua Reno
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement bubble sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable).
     *
     * See the PDF for more info on this sort.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator is null");
        }
        if (arr.length == 0 || arr.length == 1) {
            return;
        }
        boolean swapped;
        for (int i = arr.length - 1; i > 0; i--) {
            swapped = false;
            for (int j = 0; j < i; j++) {
                if (comparator.compare(arr[j], arr[j + 1]) > 0) {
                    T data1 = arr[j];
                    T data2 = arr[j + 1];
                    arr[j] = data2;
                    arr[j + 1] = data1;
                    swapped = true;
                }
            }
            if (!swapped) {
                return;
            }
        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)z
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable).
     *
     * See the PDF for more info on this sort.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator is null");
        }
        if (arr.length == 0 || arr.length == 1) {
            return;
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (comparator.compare(arr[j], arr[j - 1]) < 0) {
                    swap(arr, j, j - 1);
                } else {
                    j = 0;
                }
            }
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots.
     * For example if you need a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     *
     * int pivotIndex = r.nextInt(b - a) + a;
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * Note that there may be duplicates in the array.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not use the one we have taught you!
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator is null");
        }
        if (rand == null) {
            throw new IllegalArgumentException("Random object is null");
        }
        if (arr.length == 0 || arr.length == 1) {
            return;
        }
        quickSort(arr, 0, arr.length - 1, rand, comparator);
    }

    /**
     * Runs quicksort based on the indeces
     *
     * @param arr the array on which quickSort is run
     * @param index1 the starting index to be sorted
     * @param index2 the final index to be sorted
     * @param rand the Random object used to select pivots
     * @param comparator the Comparator used to compare the data in arr
     * @param <T> data type to sort
     */
    private static <T> void quickSort(T[] arr, int index1, int index2,
                                      Random rand, Comparator<T> comparator) {
        if (index1 == index2) {
            return;
        }
        if (index2 < index1) {
            return;
        }
        int pivot = rand.nextInt(index2 - index1) + index1;
        swap(arr, pivot, index1);
        pivot = index1;
        int left = index1 + 1;
        int right = index2;
        while (left <= right) {
            while (left <= right
                    && comparator.compare(arr[left], arr[pivot]) <= 0) {
                left++;
            }
            while (left <= right
                    && comparator.compare(arr[right], arr[pivot])
                    >= 0) {
                right--;
            }
            if (left <= right) {
                swap(arr, left, right);
                left++;
                right--;
            }
        }
        swap(arr, pivot, right);
        quickSort(arr, index1, right - 1, rand, comparator);
        quickSort(arr, right + 1, index2, rand, comparator);
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator is null");
        }
        if (arr.length == 0 || arr.length == 1) {
            return;
        }
        int midIndex = arr.length / 2;
        T[] leftArray = (T[]) (new Object[midIndex]);
        T[] rightArray = (T[]) (new Object[arr.length - midIndex]);
        for (int i = 0; i < leftArray.length; i++) {
            leftArray[i] = arr[i];
        }
        for (int i = 0; i < rightArray.length; i++) {
            rightArray[i] = arr[i + midIndex];
        }
        mergeSort(leftArray, comparator);
        mergeSort(rightArray, comparator);
        int left = 0;
        int right = 0;
        int cur = 0;
        while (left < midIndex && right < arr.length - midIndex) {
            int comparison = comparator.compare(leftArray[left],
                    rightArray[right]);
            if (comparison < 0) {
                arr[cur] = leftArray[left];
                left++;
            } else if (comparison > 0) {
                arr[cur] = rightArray[right];
                right++;
            } else {
                arr[cur] = leftArray[left];
                left++;
            }
            cur++;
        }
        while (left < midIndex) {
            arr[cur] = leftArray[left];
            left++;
            cur++;
        }
        while (right < arr.length - midIndex) {
            arr[cur] = rightArray[right];
            right++;
            cur++;
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code!
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable)
     *
     * Do NOT use {@code Math.pow()} in your sort. Instead, if you need to, use
     * the provided {@code pow()} method below.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     * @return the sorted array
     */
    public static int[] lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null");
        }
        if (arr.length == 0 || arr.length == 1) {
            return arr;
        }
        int digitsLength = 1;
        LinkedList<Integer>[] buckets = new LinkedList[19];
        int max = arr[0];
        int min = arr[0];
        for (int a = 0; a < arr.length; a++) {
            if (arr[a] > max) {
                max = arr[a];
            }
            if (arr[a] < min) {
                min = arr[a];
            }
        }
        int countMax = 0;
        int countMin = 0;
        while (max != 0) {
            max = max / 10;
            countMax++;
        }
        while (min != 0) {
            min = min / 10;
            countMin++;
        }
        digitsLength = Math.max(countMax, countMin);
        for (int i = 0; i < arr.length; i++) {
            if (buckets[bucket(arr[i], 1)] == null) {
                buckets[bucket(arr[i], 1)] = new LinkedList<Integer>();
            }
            buckets[bucket(arr[i], 1)].add(arr[i]);
        }
        int count = 0;
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != null) {
                while (!buckets[i].isEmpty()) {
                    arr[count] = buckets[i].remove();
                    count++;
                }
            }
        }
        if (digitsLength == 0) {
            return arr;
        }
        buckets = new LinkedList[19];
        for (int i = 2; i <= digitsLength; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (buckets[bucket(arr[j], pow(10, i - 1))]
                        == null) {
                    buckets[bucket(arr[j], pow(10, i - 1))]
                            = new LinkedList<Integer>();
                }
                buckets[bucket(arr[j], pow(10, i - 1))]
                        .add(arr[j]);
            }
            count = 0;
            for (int k = 0; k < buckets.length; k++) {
                if (buckets[k] != null) {
                    while (!buckets[k].isEmpty()) {
                        arr[count] = buckets[k].remove();
                        count++;
                    }
                }
            }
        }
        return arr;
    }

    /**
     * Implement MSD (most significant digit) radix sort.
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code!
     *
     * It should:
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Do NOT use {@code Math.pow()} in your sort. Instead, if you need to, use
     * the provided {@code pow()} method below.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     * @return the sorted array
     */
    public static int[] msdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null");
        }
        if (arr.length == 0 || arr.length == 1) {
            return arr;
        }
        int digitsLength = 1;
        LinkedList<Integer>[] buckets = new LinkedList[19];
        int max = arr[0];
        int min = arr[0];
        for (int a = 0; a < arr.length; a++) {
            if (arr[a] > max) {
                max = arr[a];
            }
            if (arr[a] < min) {
                min = arr[a];
            }
        }
        int countMax = 0;
        int countMin = 0;
        while (max != 0) {
            max = max / 10;
            countMax++;
        }
        while (min != 0) {
            min = min / 10;
            countMin++;
        }
        digitsLength = Math.max(countMax, countMin);
        if (digitsLength == 0) {
            return arr;
        }
        int i = digitsLength;
        for (int j = 0; j < arr.length; j++) {
            if (buckets[bucket(arr[j], pow(10, i - 1))]
                    == null) {
                buckets[bucket(arr[j], pow(10, i - 1))]
                        = new LinkedList<Integer>();
            }
            buckets[bucket(arr[j], pow(10, i - 1))]
                    .add(arr[j]);
        }
        int count = 0;
        for (int k = 0; k < buckets.length; k++) {
            if (buckets[k] != null) {
                if (buckets[k].size() == 1) {
                    arr[count] = buckets[k].remove();
                    count++;
                } else {
                    LinkedList<Integer> list = buckets[k];
                    int[] bucketArray = new int[buckets[k].size()];
                    for (int b = 0; b < bucketArray.length; b++) {
                        bucketArray[b] = list.remove();
                    }
                    int[] msdArray = msdHelper(bucketArray, i - 1);
                    for (int p : msdArray) {
                        arr[count] = p;
                        count++;
                    }
                }
            }
        }
        return arr;
    }

    /**
     * In the case a single bucket has more than one element
     * msdHelper sorts the everything properly
     *
     * @param arr the arr to be used
     * @param digit the digit value to be sorted
     * @return the properly sorted array
     */
    private static int[] msdHelper(int[] arr, int digit) {
        if (digit < 0) {
            return arr;
        }
        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int j = 0; j < arr.length; j++) {
            if (buckets[bucket(arr[j], pow(10, digit))]
                    == null) {
                buckets[bucket(arr[j], pow(10, digit))]
                        = new LinkedList<Integer>();
            }
            buckets[bucket(arr[j], pow(10, digit))]
                    .add(arr[j]);
        }
        int count = 0;
        for (int k = 0; k < buckets.length; k++) {
            if (buckets[k] != null) {
                if (buckets[k].size() == 1) {
                    arr[count] = buckets[k].remove();
                    count++;
                } else {
                    LinkedList<Integer> list = buckets[k];
                    int[] bucketArray = new int[buckets[k].size()];
                    for (int b = 0; b < bucketArray.length; b++) {
                        bucketArray[b] = list.remove();
                    }
                    int[] msdArray = msdHelper(bucketArray, digit - 1);
                    for (int p : msdArray) {
                        arr[count] = p;
                        count++;
                    }

                }

            }
        }
        return arr;
    }

    /**
     * Calculate the result of a number raised to a power. Use this method in
     * your radix sorts instead of {@code Math.pow()}.
     *
     * DO NOT MODIFY THIS METHOD.
     *
     * @throws IllegalArgumentException if both {@code base} and {@code exp} are
     * 0
     * @throws IllegalArgumentException if {@code exp} is negative
     * @param base base of the number
     * @param exp power to raise the base to. Must be 0 or greater.
     * @return result of the base raised to that power
     */
    private static int pow(int base, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Exponent cannot be negative.");
        } else if (base == 0 && exp == 0) {
            throw new IllegalArgumentException(
                    "Both base and exponent cannot be 0.");
        } else if (exp == 0) {
            return 1;
        } else if (exp == 1) {
            return base;
        }
        int halfPow = pow(base, exp / 2);
        if (exp % 2 == 0) {
            return halfPow * halfPow;
        } else {
            return halfPow * halfPow * base;
        }
    }

    /**
     * Swaps elements of type T at index1 and index2 in arr
     *
     * @param arr arr of elements
     * @param index1 an index to be swapped
     * @param index2 an index to be swapped
     * @param <T> data type to sort
     */
    private static <T> void swap(T[] arr, int index1, int index2) {
        T data1 = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = data1;
    }

    /**
     * Returns the digit based on the value and exponent
     *
     * @param value the value from the array
     * @param exponent the value of the exponent (from the digit)
     * @return the digit value
     */
    private static int digit(int value, int exponent) {
        return (value / exponent) % 10;
    }

    /**
     * Returns the bucket for the value num
     *
     * @param num the value to be put into a bucket
     * @param exp the value of the exponent from the pow method
     * @return the bucket the num will be put in
     */
    private static int bucket(int num, int exp) {
        return digit(num, exp) + 9;
    }
}
