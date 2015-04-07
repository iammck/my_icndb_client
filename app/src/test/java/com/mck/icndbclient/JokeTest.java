import junit.framework.TestCase;
public class JokeTest extends TestCase {

        // just to see where these would go.
        public testJokeUnitTest(){
              Joke joke = new Joke();
              assertNotNull("Passed joke instantiated a unit test.", joke);
        }

}