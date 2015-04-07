package com.mck.icndbclient;

import junit.framework.TestCase;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class JokeRetrieverImplTest extends TestCase {

    public void testGetJoke() throws Exception {
        String firstName = "Chuck";
        String lastName = "Jones";

        // set-up the mocks, and result.
        // will need a mockRestTemplate
        RestTemplate mockRestTemplate = mock(RestTemplate.class);
        //Need to call a real method so use partial mock on JokeRetrieverImpl via a spy.
        JokeRetrieverImpl jokeRetrieverImplSpy = spy(new JokeRetrieverImpl());
        // return the mockRestTemplate when jokeRetrieverImplSpy.getRestTemplate()
        doReturn(mockRestTemplate)
                .when(jokeRetrieverImplSpy)
                .getRestTemplate();

        // need a mocked list of converters to verify adding of converter.
        ArrayList mockConvertersList = mock(ArrayList.class);
        // return  the mockConverterList when mockRestTemplate.getConverters()
        doReturn(mockConvertersList)
                .when(mockRestTemplate)
                .getMessageConverters();
        // then return true for mockConverterList.add()
        doReturn(true)
                .when(mockConvertersList)
                .add(any(HttpMessageConverter.class));

        // return the expected joke when mockRestTemplate.getForObject is called.
        Joke expectedJoke = new Joke();
        doReturn(expectedJoke)
                .when(mockRestTemplate)
                .getForObject(anyString(), eq(Joke.class));

        // Get the resulting joke from the jokeRetriever.
        Joke resultJoke = jokeRetrieverImplSpy.getJoke(firstName, lastName);

        // verify that the methods are all accessed.
        verify(jokeRetrieverImplSpy).getRestTemplate();
        verify(mockRestTemplate).getMessageConverters();
        verify(mockConvertersList)
                .add(any(HttpMessageConverter.class));
        verify(mockRestTemplate).getForObject(anyString(), eq(Joke.class));

        // Assert that the result and expected jokes are equal
        assertEquals(expectedJoke, resultJoke);

        // throw a RestClientException when mockRestTemplate.getForObject is called
        doThrow(new RestClientException("mock throws exception"))
                .when(mockRestTemplate)
                .getForObject(anyString(), eq(Joke.class));
        // JokeRetrieverImpl should catch the exception and return null.
        assertNull(jokeRetrieverImplSpy.getJoke(firstName, lastName));
    }

    /**
     * Test that
     * JokeRetrieverImpl does return a joke instance.
     * @throws Exception
     */
    public void testGetJokeFromInternetIntegrationTest() throws Exception{
        JokeRetrieverImpl retriever = new JokeRetrieverImpl();
        Joke result = retriever.getJoke("Michael", "King");
        // assert we have received a response.
        assertNotNull(result);
        // assert the response is a success.
        String type = result.type;
        assertEquals("success", type);
        System.out.println(result.getJoke());
    }

}