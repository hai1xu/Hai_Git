package grails.exercise

import grails.test.mixin.*
import org.junit.*
import groovy.mock.interceptor.MockFor
import groovy.json.JsonSlurper
import grails.exercise.model.Book

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(BookStoreController)
class BookStoreControllerTests {

    void testIndex() {
        def storeMock = new MockFor(BookStoreService)
        storeMock.demand.listBooks() {
            return [new Book(), new Book()]
        }
        controller.bookStoreService = storeMock.proxyInstance()
        def model = controller.index()
        assert model.books.size() == 2
    }
}
