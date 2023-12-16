package tests;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertTrue;


public class CheckStringLength {

    @Test
    public void TestValidString() {
        String testingString = "Very long long valid string";
        assertTrue(testingString.length() > 15, "Строка меньше 15 символов");
    }

    @Test
    public void TestFailedString() {
        String testingString = "Short string";
        assertTrue(testingString.length() > 15, "Строка меньше 15 символов");
    }


}
