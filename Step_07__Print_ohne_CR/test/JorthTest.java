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

    @Test
    void singleStringIsPutOnStackWithoutQuotes() {
        // Erwartet [hello] (Java List toString Repräsentation)
        assertEquals("[hello]", Jorth.run("\"hello\""));
    }

    @Test
    void stringWithSpacesIsHandledAsOneToken() {
        assertEquals("[hello world]", Jorth.run("\"hello world\""));
    }

    @Test
    void mixedStringsAndNumbers() {
        assertEquals("[abc, 123]", Jorth.run("\"abc\" 123"));
    }

    @Test
    void stringsAndMathOperations() {
        // Math sollte Strings auf dem Stack einfach ignorieren/unverändert lassen
        assertEquals("[a, b, 7]", Jorth.run("\"a\" \"b\" 3 4 +"));
    }

    @Test
    void emptyStringQuotes() {
        assertEquals("[]", Jorth.run("\"\""));
    }

    @Test
    void stringContainingOperators() {
        // Der Operator "+" innerhalb von Quotes ist ein String, kein Command
        assertEquals("[+]", Jorth.run("\"+\""));
    }

    @Test
    void stringConcatenationWorks() {
        assertEquals("[abcd]", Jorth.run("\"ab\" \"cd\" +"));
    }

    @Test
    void stringConcatenationWithSpacesWorks() {
        assertEquals("[hello world]", Jorth.run("\"hello \" \"world\" +"));
    }

    @Test
    void numericAdditionStillWorks() {
        assertEquals("[7]", Jorth.run("3 4 +"));
    }

    @Test
    void mixedTypesOnPlusAreIgnoredAndRemainOnStack() {
        // Gemäß Anforderung: "ab" 3 + -> [ab, 3]
        assertEquals("[ab, 3]", Jorth.run("\"ab\" 3 +"));
        assertEquals("[3, ab]", Jorth.run("3 \"ab\" +"));
    }

    @Test
    void concatenationOfEmptyStrings() {
        // "" "" + führt zu einem Stack mit einem leeren String-Element
        assertEquals("[]", Jorth.run("\"\" \"\" +").equals("[]") ? "[]" : "[\"\"]"); 
        // Hinweis: ArrayList.toString() gibt für einen leeren String "" nichts zwischen den Kommata aus.
        // Ein Stack mit einem leeren String sieht in Java so aus: [""] -> [ ]
    }

    @Test
    void multipleConcatenations() {
        assertEquals("[fullstack]", Jorth.run("\"full\" \"stack\" + \"\" +"));
    }

    @Test
    void dotAndUnderscoreDotDoNotModifyStack() {
        // Nach der Ausgabe von 42 muss 42 noch auf dem Stack liegen
        assertEquals("[42]", Jorth.run("42 ."));
        assertEquals("[42]", Jorth.run("42 ._"));
    }

    @Test
    void outputStringIsCorrectOnStack() {
        // Sicherstellen, dass Strings im Stack sauber bleiben
        assertEquals("[hello]", Jorth.run("\"hello\" ._"));
    }

    @Test
    void complexOutputSequenceStackIntegrity() {
        // Prüft, ob nach einer Kette von Ausgaben der Stack noch stimmt
        assertEquals("[10, 20]", Jorth.run("10 20 ._ . ._"));
    }

    @Test
    void outputCommandsOnEmptyStackAreIgnored() {
        assertEquals("[]", Jorth.run(". ._"));
    }
}