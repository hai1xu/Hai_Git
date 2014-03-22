package grails.exercise

import org.codehaus.groovy.grails.web.context.ServletContextHolder

import grails.web.JSONBuilder
import groovy.json.JsonSlurper

import org.codehaus.groovy.grails.commons.ConfigurationHolder



class FeedService {

    def grailsApplication
	def feedCacheService

    def getBooksFeed() {
        //ConfigurationHolder ch;
        //def ctx = ServletContextHolder.servletContext
        //def input = ctx.getResourceAsStream(grailsApplication.config.bookStore.feedFile)
		//return new JsonSlurper().parseText(input.text)
        
		//return new JsonSlurper().parseText(getBooksFeedOnline("https://s3.amazonaws.com/conmio-recruitment/api/books-feed.json"))
		
		return new JsonSlurper().parseText(feedCacheService.getData("Books"))
    }
	
	def getTop3Feed() {
		//ConfigurationHolder ch;
		//def ctx = ServletContextHolder.servletContext
		//def input = ctx.getResourceAsStream(grailsApplication.config.top3.feedFile)
		//return new JsonSlurper().parseText(input.text)		
		
		//return new JsonSlurper().parseText(getBooksFeedOnline("https://s3.amazonaws.com/conmio-recruitment/api/sales-feed.json"))
		
		return new JsonSlurper().parseText(feedCacheService.getData("Top3"))
	}
}


