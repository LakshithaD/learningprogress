class SampleClassTests {
    // Your task is to setup TestUtils and instantiate the instance field.
    SampleClass instance;

    // @BeforeAll
    static void beforeAll() {

    }

    // @AfterAll
    static void afterAll() {

    }

    // @BeforeEach
    void beforeEach() {

    }

    // @AfterEach
    void afterEach() {

    }

    // @Test
    void testMethodOne() {
        Assertions.assertTrue(instance.methodOne());
    }

    // @Test
    void testMethodTwo() {
        Assertions.assertTrue(instance.methodTwo());
    }

    void beore
}

class SampleClass {

    public boolean methodOne() {
        // Implementation details
    }

    public boolean methodTwo() {
        // Implementation details
    }
}

class TestUtils {

    static SampleClass getSampleClassInstance() {
        // implementation details
    }

    static void timeConsumingSetup() {
        // implementation details
    }
}