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
}