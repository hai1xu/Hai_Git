# Conmio Grails task

This is an exercise for new potential Conmio employees which are expected to work with the back-end.
This example uses the [Grails](http://grails.org/) web application framework.

## How to start

### Install Java SDK

Any version of Java Development Kit (JDK) above 1.6 will do.

### Fork this repository

[Fork](https://help.github.com/articles/fork-a-repo) this repository to your own GitHub account.
Clone your repository to local machine.

### Initial setup of the application

Issue the following command from the command promt (depending on your OS):

    # Windows
    .\grailsw
    # *NIX, iOS
    ./grailsw.sh

This will download and install a working Grails environment on your local machine and opens a Grails
interactive console. You can use the console to issue Grails commands or close it with 'exit'.

## Basic task

Here we have a book store website. Users are not able to buy books from the service at this point.
The application reads the inventory of books from a JSON feed (*books-feed.json*)
and renders the list of books on the page.

Now the customer wants to display the top 3 best selling books on the page too. The information
is based on another feed which contains the sales transactions.
The feed file is located in */web-app/WEB-INF/feeds/sales-feed.json*.

**Your task is to read the information from the second feed and display the correct results on the page.**

### Things to consider

* First read the Grails documentation. It should get you started if you are not familiar with this particular framework.
* Comment your code
* Currently the feeds are local files. What if the feeds came from remote location? What kind of issues this would raise?
* How would you improve the performance of the application?

## Advanced task

These tasks are considered to be implemented by advanced programmers. The tasks are:

1. Implement all feed reading in the basic task using real remote HTTP requests.
1. Read and parse a categories feed and render the category tree on the page.
1. Implement a simple cache which stores the objects in memory. There is no need to consider any TTLs and eviction policies at this time.
You may use only standard Java SE and Groovy APIs for the task (don't use any Grails plugins or libraries to implement the cache).

We strongly suggest you use Apache HttpComponents to make the remote calls. Install the required JARs by adding following in the BuildConfig.groovy:

    dependencies {
        compile 'org.apache.httpcomponents:httpclient:4.2.3' // add this
    }

Feeds are available at following locations:

* books: https://s3.amazonaws.com/conmio-recruitment/api/books-feed.json
* sales: https://s3.amazonaws.com/conmio-recruitment/api/sales-feed.json
* categories: https://s3.amazonaws.com/conmio-recruitment/api/categories.json

## Make your results available

Make your forked repository available to us and send us a link to it after you are ready.