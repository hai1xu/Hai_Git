package grails.exercise

import grails.exercise.model.Book

class BookStoreService {

    def feedService

    List<grails.exercise.model.Book> listBooks() {
        List<Book> books = []
        def feed = feedService.getBooksFeed()
        feed.books.each { jsonBook ->
            Book book = new Book()
            book.title = jsonBook.title
            book.author = jsonBook.author
            book.yearReleased = jsonBook.year
            books.add(book)
        }
        return books
    }
	
	List<grails.exercise.model.Book> top3Books() {		
		List<Book> books = listBooks()
		
		List<Book> top3 = []
		
		int[] buyAmount = new int[books.size()]
		buyAmount.each{
			it = 0
		}
		
		def feed = feedService.getTop3Feed()
		feed.sales.each { jsonBook ->
			int temp = jsonBook.book
			buyAmount[temp-1] = buyAmount[temp-1] + 1 
		}
		
		int maxPos = 0
		
		for (int i=0;i<3;i++) {
			for (int j=1;j<books.size();j++) {
				if (buyAmount[maxPos] < buyAmount[j]) {
					maxPos = j
				}
			}
			top3.add(books[maxPos])
			buyAmount[maxPos] = 0
		}
				
		return top3
	}
}
