package grails.exercise

import grails.test.mixin.*
import org.junit.*
import groovy.json.JsonSlurper
import groovy.mock.interceptor.MockFor

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(BookStoreService)
class BookStoreServiceTests {

    void testListBooks() {
        def feedMock = new MockFor(FeedService)
        feedMock.demand.getBooksFeed() {
            return new JsonSlurper().parseText('{"books":[{"title":"FOO","author":"BAR","year":1234}]}')
        }
        service.feedService = feedMock.proxyInstance()
        def books = service.listBooks()
        assert books.size() == 1
        assert books[0].title == "FOO"
        assert books[0].author == "BAR"
        assert books[0].yearReleased == 1234
    }
}
