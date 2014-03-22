package grails.exercise

import grails.exercise.model.Book

class BookStoreController {

    def bookStoreService

    def index() {
        List<Book> books = bookStoreService.listBooks()
        List<Book> top3 = bookStoreService.top3Books()
        return [books: books, top3: top3]
    }
}
