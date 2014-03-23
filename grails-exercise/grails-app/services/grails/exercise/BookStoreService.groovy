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
		
		// create return top 3 books list
		List<Book> top3 = []
		
		// create the list for each book, how many pieces sold
		int[] buyAmount = new int[books.size()]
		// set all books sold amount to 0
		buyAmount.each{
			it = 0
		}
		
		// get books sold Json file
		def feed = feedService.getTop3Feed()
		// for each book, if one piece sold, then add 1 to the corresponding "books sold list" slot
		feed.sales.each { jsonBook ->
			// i.e. for book id 2, if one piece sold, add 1 to the corresponding "books sold list" slot 1 (2-1)
			int temp = jsonBook.book
			buyAmount[temp-1] = buyAmount[temp-1] + 1 
		}
		
		// set the Position to first (0)
		int maxPos = 0
		
		// find top 3 book sold Position 
		for (int i=0;i<3;i++) {
			// find top 1 book sold Position
			for (int j=1;j<books.size();j++) {
				// compare current top sold amount with all the other sold amount
				if (buyAmount[maxPos] < buyAmount[j]) {
					// set current top sold Pos to higher one, if found
					maxPos = j
				}
			}
			// add the found top sold book to top3 list
			top3.add(books[maxPos])
			// set the added book amount to 0, so it will not be added again
			buyAmount[maxPos] = 0
		}
				
		return top3
	}
}
