FeedR is an application that fetches feeds through HTTP concurrently with [GPars](http://gpars.codehaus.org/). It also provides an administration site to manage feed configuration and also to inspect feed items. This application is intended to be used as part of a bigger infrastructure that requires access to external feeds. The role of this application is just to just get and cache the feeds. The project itself is a [Grails](http://grails.org/) project.

## Screenshots
Here are some [screenshots](http://wijaya.posterous.com/feedr-asynchronous-feed-fetcher-with-administ) for the administration site.

## Features

  1. Retrieve multiple feeds concurrently with [GPars](http://gpars.codehaus.org/) to provide high concurrency operations.
 
  2. Cache the raw content of the feed such as the RSS content into the database for auditing purpose.

  3. Support HTTP conditional GET by checking the HTTP headers.

  4. Retrieve the actual pages of each feed item concurrently with [GPars](http://gpars.codehaus.org/).

  5. Retrieve image attachments in the feed items concurrently with [GPars](http://gpars.codehaus.org/).

  6. Administration site for configuring feed sources and feeds; and inspecting feed items.

  7. Many more to be added...

## Running
The application is using Grails 1.3.7. Assuming you have Grails 1.3.7 environment setup in your machine, to run it in the development mode, you can type the following in your shell under the root of the project:

    $ grails run-app

As soon as the application is started up, the background Quartz jobs will start and execute the fetchers periodically.

You can access the administration page at:

    http://localhost:8080/feedr/source/list

## Current Status
This application is still in development and not yet ready for production.
