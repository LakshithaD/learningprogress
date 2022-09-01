package tracker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class ValidatorTest {

    @Test
    void validEmail() {

        String email = "jcda123@google.net";
        boolean actual = Validator.validEmail(email);
        Assertions.assertEquals(true, actual);
    }

    @Test
    void validEmailWithDot() {

        String email = "jane.doe@yahoo.com";
        boolean actual = Validator.validEmail(email);
        Assertions.assertEquals(true, actual);
    }

    @Test
    void validNameWithHyphen() {
        String name = "Jean-Claude";
        boolean actual = Validator.validName(name);
        Assertions.assertEquals(true, actual);
    }

    @Test
    void validNameWithApostrophe() {
        String name = "O'Neill";
        boolean actual = Validator.validName(name);
        Assertions.assertEquals(true, actual);
    }

    @Test
    void invalidName() {
        String name = "Stanisław Oğuz";
        boolean actual = Validator.validName(name);
        Assertions.assertEquals(false, actual);
    }

    @Test
    void validPoints() {

        String[] points = {"10", "10", "5", "8"};
        boolean valid = Validator.validPoints(points);
        Assertions.assertEquals(valid, true);
    }

    @Test
    void invalidPointsSize() {

        String[] points = {"7", "7", "7", "7", "7"};
        boolean valid = Validator.validPoints(points);
        Assertions.assertEquals(valid, false);
    }

    @Test
    void invalidPointsMinus() {

        String[] points = {"-1", "7", "7", "7"};
        boolean valid = Validator.validPoints(points);
        Assertions.assertEquals(valid, false);
    }

    @Test
    void invalidPointsChar() {

        String[] points = {"?", "7", "7", "7"};
        boolean valid = Validator.validPoints(points);
        Assertions.assertEquals(valid, false);
    }

}