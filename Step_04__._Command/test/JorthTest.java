import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class JorthTest {

    @Test
    void emptyProgramReturnsEmptyStack() {
        assertEquals("[]", Jorth.run(""));
    }

    @Test
    void blankProgramReturnsEmptyStack() {
        assertEquals("[]", Jorth.run("   "));
    }

    @Test
    void singleNumberIsPutOnStack() {
        assertEquals("[3]", Jorth.run("3"));
    }

    @Test
    void multipleNumbersArePutOnStack() {
        assertEquals("[3, 4, 5]", Jorth.run("3 4 5"));
    }

    @Test
    void multipleSpacesAreIgnored() {
        assertEquals("[1, 2]", Jorth.run("  1   2  "));
    }

    @Test
    void nonNumericTokensAreIgnored() {
        assertEquals("[10, 20]", Jorth.run("10 ignoreMe 20"));
    }

    @Test
    void simpleAdditionWorks() {
        assertEquals("[7]", Jorth.run("3 4 +"));
    }

    @Test
    void nestedAdditionWorks() {
        assertEquals("[23]", Jorth.run("11 3 8 1 + + +"));
    }

    @Test
    void plusWithEmptyStackIsIgnored() {
        assertEquals("[]", Jorth.run("+"));
    }

    @Test
    void plusWithOnlyOneElementOnStackIsIgnored() {
        assertEquals("[5]", Jorth.run("5 +"));
    }

    @Test
    void additionFollowedByMoreNumbers() {
        assertEquals("[7, 10]", Jorth.run("3 4 + 10"));
    }

    @Test
    void complexMixedSequence() {
        assertEquals("[15]", Jorth.run("1 2 3 4 5 + + + +"));
    }

    @Test
    void subtractionWorks() {
        assertEquals("[7]", Jorth.run("10 3 -"));
    }

    @Test
    void negativeResultWorks() {
        assertEquals("[-1]", Jorth.run("3 4 -"));
    }

    @Test
    void subtractionOperandOrderIsCorrect() {
        // 20 - 5 = 15 (Nicht 5 - 20 = -15)
        assertEquals("[15]", Jorth.run("20 5 -"));
    }

    @Test
    void complexMixedSubtractionAndAddition() {
        // (10 - 3) + 5 = 12
        assertEquals("[12]", Jorth.run("10 3 - 5 +"));
    }

    @Test
    void minusWithInsufficientElementsIsIgnored() {
        assertEquals("[5]", Jorth.run("5 -"));
        assertEquals("[]", Jorth.run("-"));
    }

    @Test
    void dotCommandDoesNotChangeStack() {
        // Nach "3 4 ." muss der Stack immer noch [3, 4] sein
        assertEquals("[3, 4]", Jorth.run("3 4 ."));
    }

    @Test
    void dotCommandAfterAddition() {
        // 3 + 4 = 7. Stack sollte [7] sein.
        assertEquals("[7]", Jorth.run("3 4 + ."));
    }

    @Test
    void dotCommandOnEmptyStackDoesNothing() {
        // Sollte nicht abstürzen
        assertEquals("[]", Jorth.run("."));
    }

    @Test
    void multipleDotCommands() {
        assertEquals("[10]", Jorth.run("10 . . ."));
    }
}