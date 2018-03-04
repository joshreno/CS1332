
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by Raj Shrimali for Homework 1
 * @version 1
 */
public class ArrayListTest_raj {
    private ArrayListInterface<String> list;
    private Object[] expected;
    private static final int TIMEOUT = 200;

    /**
     * This will test the growth as well.
     * This relies on addToBack working
     * If addToBack doesn't work, its a problem.
     * AddToBack is pretty much the easiest one to implement.
     */
    @Before
    public void setUp() {
        list = new ArrayList<>();
        list.addToBack("1");
        list.addToBack("2");
        list.addToBack("3");
        list.addToBack("4");
        list.addToBack("5");
        list.addToBack("6");
        list.addToBack("7");
        list.addToBack("8");
        list.addToBack("9");
        list.addToBack("10");
        list.addToBack("11");
        list.addToBack("12");
        list.addToBack("13");
        list.addToBack("14");
        expected = new Object[ArrayListInterface.INITIAL_CAPACITY * 2];
        expected[0] = "1";
        expected[1] = "2";
        expected[2] = "3";
        expected[3] = "4";
        expected[4] = "5";
        expected[5] = "6";
        expected[6] = "7";
        expected[7] = "8";
        expected[8] = "9";
        expected[9] = "10";
        expected[10] = "11";
        expected[11] = "12";
        expected[12] = "13";
        expected[13] = "14";

    }

    @Test(timeout = TIMEOUT)
    public void addAtIndex() throws Exception {
        list.addAtIndex(4, "test_4");
        list.addAtIndex(6, "test_6");
        list.addAtIndex(12, "test_12");
        expected[0] = "1";
        expected[1] = "2";
        expected[2] = "3";
        expected[3] = "4";
        expected[4] = "test_4";
        expected[5] = "5";
        expected[6] = "test_6";
        expected[7] = "6";
        expected[8] = "7";
        expected[9] = "8";
        expected[10] = "9";
        expected[11] = "10";
        expected[12] = "test_12";
        expected[13] = "11";
        expected[14] = "12";
        expected[15] = "13";
        expected[16] = "14";
        assertArrayEquals(expected, list.getBackingArray());
        try {
            list.addAtIndex(4, null);
            assert false;
        } catch (IllegalArgumentException e) {
            assert true;
        }
        try {
            list.addAtIndex(-1, "should fail");
            assert false;
        } catch (IndexOutOfBoundsException e) {
            assert true;
        }

        try {
            list.addAtIndex(18, "this should fail");
            assert false;
        } catch (IndexOutOfBoundsException e) {
            assert true;
        }

    }

    @Test(timeout = TIMEOUT)
    public void addToFront() throws Exception {
        list.addToFront("goodbye");
        expected[0] = "goodbye";
        expected[1] = "1";
        expected[2] = "2";
        expected[3] = "3";
        expected[4] = "4";
        expected[5] = "5";
        expected[6] = "6";
        expected[7] = "7";
        expected[8] = "8";
        expected[9] = "9";
        expected[10] = "10";
        expected[11] = "11";
        expected[12] = "12";
        expected[13] = "13";
        expected[14] = "14";
        assertArrayEquals(expected, list.getBackingArray());
        try {
            list.addToFront(null);
            assert false;
        } catch (IllegalArgumentException e) {
            assert true;
        }
    }

    @Test(timeout = TIMEOUT)
    public void addToBack() throws Exception {
        list.addToBack("hello");
        expected[0] = "1";
        expected[1] = "2";
        expected[2] = "3";
        expected[3] = "4";
        expected[4] = "5";
        expected[5] = "6";
        expected[6] = "7";
        expected[7] = "8";
        expected[8] = "9";
        expected[9] = "10";
        expected[10] = "11";
        expected[11] = "12";
        expected[12] = "13";
        expected[13] = "14";
        expected[14] = "hello";
        assertArrayEquals(expected, list.getBackingArray());
        try {
            list.addToBack(null);
            assert false;
        } catch (IllegalArgumentException e) {
            assert true;
        }
    }

    @Test(timeout = TIMEOUT)
    public void removeAtIndex() throws Exception {
        String out = list.removeAtIndex(5);
        assertEquals("6", out);
        expected[0] = "1";
        expected[1] = "2";
        expected[2] = "3";
        expected[3] = "4";
        expected[4] = "5";
        expected[5] = "7";
        expected[6] = "8";
        expected[7] = "9";
        expected[8] = "10";
        expected[9] = "11";
        expected[10] = "12";
        expected[11] = "13";
        expected[12] = "14";
        expected[13] = null;
        assertArrayEquals(expected, list.getBackingArray());
        try {
            list.removeAtIndex(-1);
            assert false;
        } catch (IndexOutOfBoundsException e) {
            assert true;
        }

        try {
            list.removeAtIndex(1000);
            assert false;
        } catch (IndexOutOfBoundsException e) {
            assert true;
        }
        try {
            list.removeAtIndex(13); // try removeAtIndex(size)
            assert false;
        } catch (IndexOutOfBoundsException e) {
            assert true;
        }
    }

    @Test(timeout = TIMEOUT)
    public void removeFromFront() throws Exception {
        String out = list.removeFromFront();
        assertEquals("1", out);
        expected[0] = "2";
        expected[1] = "3";
        expected[2] = "4";
        expected[3] = "5";
        expected[4] = "6";
        expected[5] = "7";
        expected[6] = "8";
        expected[7] = "9";
        expected[8] = "10";
        expected[9] = "11";
        expected[10] = "12";
        expected[11] = "13";
        expected[12] = "14";
        expected[13] = null;
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void removeFromBack() throws Exception {
        String out = list.removeFromBack();
        assertEquals("14", out);
        expected[1] = "2";
        expected[2] = "3";
        expected[3] = "4";
        expected[4] = "5";
        expected[5] = "6";
        expected[6] = "7";
        expected[7] = "8";
        expected[8] = "9";
        expected[9] = "10";
        expected[10] = "11";
        expected[11] = "12";
        expected[12] = "13";
        expected[13] = null;
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void get() throws Exception {
        assertEquals("5", list.get(4));
        try {
            list.get(-1);
            assert false;
        } catch (IndexOutOfBoundsException e) {
            assert true;
        }

        try {
            list.get(14);
            assert false;
        } catch (IndexOutOfBoundsException e) {
            assert true;
        }
    }

    @Test(timeout = TIMEOUT)
    public void clear() throws Exception {
        list.clear();
        expected = new Object[ArrayListInterface.INITIAL_CAPACITY];
        assertArrayEquals(expected, list.getBackingArray());
    }

    @Test(timeout = TIMEOUT)
    public void isEmpty() throws Exception {
        assertEquals(false, list.isEmpty());
        list.clear();
        assertEquals(true, list.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void size() throws Exception {
        assertEquals(14, list.size());
    }
}
