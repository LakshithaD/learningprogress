class CalculatorTest {
    void testAddition() {
        Calculator calculator = new Calculator();
        Assertions.assertEquals(13,calculator.add(10,3));
    }
}