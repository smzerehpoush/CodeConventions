# Farazpardazan RESTFul API Design Strategy


## Table of content
 - [What is the purpose of this document?]()
 - [Introduction to HTTP protocol and REST]()
    - [HTTP basics]()
    - [Request Methods]()
      - [GET]()
      - [POST]()
      - [PUT]()
      - [DELETE]()
    - [HTTP Status Codes]()
      - [1xx Informational]()
      - [2xx Success]()
      - [3xx Redirection]()
      - [4xx Client Error]()
      - [5xx Server Error]()
 - [Restful API Best practices]()
       - [Use RESTful URLs and actions]()
       - [SSL everywhere - all the time]()
       - [Documentation]()
       - [Versioning]()
       - [Result Filtering]()
       - [Updates and creation should return a resource representation]()
       - [Field Naming Convention]()
       - [Don't use an envelope by default, but make it possible when needed]()
       - [JSON encoded POST, PUT and PATCH bodies]()
       - [Pagination]()
       - [Rate limiting]()
       - [Authentication]()
       - [Caching]()
       - [Error Handling]()
 - [Code Example](../root/src/main/java/com/mahdiyar/controller/UserController.java)
 
### What is the purpose of this document ?

This document represents Farazpardazan restful api design strategy. It provides a brief overview of how we design our
restful apis and provide them to our clients.
### introduction to HTTP protocol and REST

we have craeted a [document](HTTP-Docs.md) specially for this purpose. you can read this document.

### Request Methods
   Http protocol has 8 standard methods but in FarazPardazan we support these methods : 
``` GET, POST, PUT, DELETE ```
- #### GET

GET method is used to retreive data from a server at the specified resource. 
    
- #### POST
POST requests are used to send data to the API server to create or update a resource. 
The data sent to the server is stored in the request body of the HTTP request.
POST requests are non-idempotent. it mutates data on the backend server.

- #### PUT 
Similar to POST, PUT requests are used to send data to the API to update a resource. The difference is that
PUT requests are idempotent. That is, calling the same PUT request multiple times will always produce the same result.
In contrast, calling a POST request repeatedly make have side effects of creating the same resource multiple times.

- #### DELETE
DELETE method is exactly as it sounds: delete the resource at the specified URL.

### HTTP Status Codes
  Http Protocol has 5 ranges of status codes. in each range, we support these codes :

- ### 1xx Informational
we do not use any of 1XX response codes.
 
- ### 2xx Success
 in rage of 2XX we use these codes : 200
 - #### 200 OK
  The request has succeeded. The meaning of the success depends on the HTTP method:
  - GET: The resource has been fetched and is transmitted in the message body.
  - HEAD: The entity headers are in the message body.
  - PUT or POST: The resource describing the result of the action is transmitted in the message body.
- ### 3xx Redirection
  in range of 3XX we use these codes : 302
   - #### 302 Found
This response code means that the URI of requested resource has been changed temporarily.
 Further changes in the URI might be made in the future. Therefore, this same URI should be used by the client
  in future requests.

- ### 4xx Client Error
in range of 4XXX we use these codes : 400, 401, 403, 404, 429

 - #### 400 Bad Request
The server could not understand the request due to invalid syntax.

 - #### 401 Unauthorized
Although the HTTP standard specifies "unauthorized", semantically this response means "unauthenticated". 
That is, the client must authenticate itself to get the requested response.

 - #### 403 Forbidden
The client does not have access rights to the content; that is, it is unauthorized, so the server is refusing to 
give the requested resource. Unlike 401, the client's identity is known to the server.

 - #### 404 Not Found
The server can not find the requested resource. In the browser, this means the URL is not recognized. In an API, 
this can also mean that the endpoint is valid but the resource itself does not exist. Servers may also 
send this response instead of 403 to hide the existence of a resource from an unauthorized client. 
This response code is probably the most famous one due to its frequent occurrence on the web.

 - #### 405 Method Not Allowed
The request method is known by the server but has been disabled and cannot be used. For example, 
an API may forbid DELETE-ing a resource. The two mandatory methods, GET and HEAD, 
must never be disabled and should not return this error code.

 - #### 429 Too Many Requests
The user has sent too many requests in a given amount of time ("rate limiting").

- ### 5xx Server Error
note that we can't return any code of range 5XX explicitly. 
some error codes may be throw by web server like 502 , or 504

 - #### 500 Internal Server Error
The server has encountered a situation it doesn't know how to handle.

 - #### 502 Bad Gateway
This error response means that the server, while working as a gateway to get a response needed to handle the request,
 got an invalid response.
 - #### 503 Service Unavailable
The server is not ready to handle the request. Common causes are a server that is down for maintenance or
 that is overloaded. Note that together with this response, a user-friendly page explaining the problem should be sent. 
 This responses should be used for temporary conditions and the Retry-After: HTTP header should, if possible, 
 contain the estimated time before the recovery of the service. The webmaster must also take care about the 
 caching-related headers that are sent along with this response, as these temporary condition responses should 
 usually not be cached.
- #### 504 Gateway Timeout
This error response is given when the server is acting as a gateway and cannot get a response in time.

### Best Practices
#### Use RESTful URLs and actions
If there's one thing that has gained wide adoption, it's RESTful principles. These were first introduced by [Roy Fielding](http://roy.gbiv.com/) in Chapter 5 of his dissertation on [network based software architectures](http://www.ics.uci.edu/~fielding/pubs/dissertation/top.htm).

The key principles of [REST](https://en.wikipedia.org/wiki/Representational_state_transfer) involve separating your API into logical resources. These resources are manipulated using HTTP requests where the method (GET, POST, PUT, PATCH, DELETE) has specific meaning.

**But what can I make a resource ?** Well, these should be nouns that make sense from the perspective of the API consumer, not verbs. To be clear: a noun is a thing, a verb is what you do to it. Some of Enchant's nouns would be ticket, user and customer.

**Watch out though :** Although your internal models may map neatly to resources, it isn't necessarily a one-to-one mapping. The key here is to not leak irrelevant implementation details out to your API! Your API resources need to make sense from the perspective of the API consumer.

Once you have your resources defined, you need to identify what actions apply to them and how those would map to your API. RESTful principles provide strategies to handle [CRUD](http://en.wikipedia.org/wiki/Create,_read,_update_and_delete) actions using HTTP methods mapped as follows:

   - ```GET /tickets``` - Retrieves a list of tickets
   - ```GET /tickets/12``` - Retrieves a specific ticket
   - ```POST /tickets``` - Creates a new ticket
   - ```PUT /tickets/12``` - Updates ticket #12
   - ```PATCH /tickets/12``` - Partially updates ticket #12
   - ```DELETE /tickets/12``` - Deletes ticket #12
   
The great thing about REST is that you're leveraging existing HTTP methods to implement significant functionality on just a single /tickets endpoint. There are no method naming conventions to follow and the URL structure is clean and clear.

**Should the endpoint name be singular or plural?** The keep-it-simple rule applies here. Although your inner-grammatician will tell you it's wrong to describe a single instance of a resource using a plural, the pragmatic answer is to keep the URL format consistent and **always use a plural**. Not having t o deal with odd pluralization (person/people, goose/geese) makes the life of the API consumer better and is easier for the API provider to implement (as most modern frameworks will natively handle /tickets and /tickets/12 under a common controller).

**But how do you deal with relations?** If a relation can only exist within another resource, RESTful principles provide useful guidance. Let's look at this with an example. A ticket consists of a number of messages. These messages can be logically mapped to the /tickets endpoint as follows:

    - ```GET /tickets/12/messages``` - Retrieves list of messages for ticket #12
    - ```GET /tickets/12/messages/5``` - Retrieves message #5 for ticket #12
    - ```POST /tickets/12/messages``` - Creates a new message in ticket #12
    - ```PUT /tickets/12/messages/5``` - Updates message #5 for ticket #12
    - ```PATCH /tickets/12/messages/5``` - Partially updates message #5 for ticket #12
    - ```DELETE /tickets/12/messages/5``` - Deletes message #5 for ticket #12

**Alternative 1:** If a relation can exist independently of the resource, it makes sense to just include an identifier for it within the output representation of the resource. The API consumer would then have to hit the relation's endpoint.

**What about actions that don't fit into the world of CRUD operations?**    

This is where things can get fuzzy. There are a number of approaches:

1. Restructure the action to appear like a field of a resource. This works if the action doesn't take parameters. For example an activate action could be mapped to a boolean activated field and updated via a PATCH to the resource.
2. Treat it like a sub-resource with RESTful principles. For example, GitHub's API lets you star a gist with PUT /gists/:id/star and unstar with DELETE /gists/:id/star.
3. Sometimes you really have no way to map the action to a sensible RESTful structure. For example, a multi-resource search doesn't really make sense to be applied to a specific resource's endpoint. In this case, /search would make the most sense even though it isn't a resource. This is OK - just do what's right from the perspective of the API consumer and make sure it's documented clearly to avoid confusion.

#### SSL everywhere - all the time
Always use SSL. No exceptions. Today, your web APIs can get accessed from anywhere there is internet (like libraries, coffee shops, airports among others). Not all of these are secure. Many don't encrypt communications at all, allowing for easy eavesdropping or impersonation if authentication credentials are hijacked.

Another advantage of always using SSL is that guaranteed encrypted communications simplifies authentication efforts - you can get away with simple access tokens instead of having to sign each API request.

One thing to watch out for is non-SSL access to API URLs. Do not redirect these to their SSL counterparts. Throw a hard error instead! When a automatic redirect is in place, a poorly configured client could unknowingly leak request parameters over the unencrypted endpoint. A hard error ensures this mistake is caught early and the client is configured properly.

#### Documentation
An API is only as good as its documentation. The docs should be easy to find and publically accessible. Most developers will check out the docs before attempting any integration effort. When the docs are hidden inside a PDF file or require signing in, they're not only difficult to find but also not easy to search.

The docs should show examples of complete request/response cycles. Preferably, the requests should be pastable examples - either links that can be pasted into a browser or curl examples that can be pasted into a terminal. GitHub and Stripe do a great job with this.

Once you release a public API, you've committed to not breaking things without notice. The documentation must include any deprecation schedules and details surrounding externally visible API updates. Updates should be delivered via a blog (i.e. a changelog) or a mailing list (preferably both!).
notes: we use **Swagger** for our API Documentatoin

#### Versioning
Always version your API. Versioning helps you iterate faster and prevents invalid requests from hitting updated endpoints. It also helps smooth over any major API version transitions as you can continue to offer old API versions for a period of time.

There are mixed opinions around whether an API version should be included in the URL or in a header. Academically speaking, it should be in the URL to ensure browser explorability of the resources across versions and to have a simpler developer experience. for example ```/api/v1/users``` or ```/api/v2/tickets```

An API is never going to be completely stable. Change is inevitable. What's important is how that change is managed. Well documented and announced multi-month deprecation schedules can be an acceptable practice for many APIs. It comes down to what is reasonable given the industry and possible consumers of the API.

#### Result Filtering
It's best to keep the base resource URLs as lean as possible. Complex result filters, sorting requirements and advanced searching (when restricted to a single type of resource) can all be easily implemented as query parameters on top of the base URL. Let's look at these in more detail:

**Filtering:** Use a unique optional query parameter for each field that implements filtering. For example, when requesting a list of tickets from the ```/tickets endpoint```, you may want to limit these to only those in the open state. This could be accomplished with a request like ```GET /tickets?state=open```. Here, ```state``` is a query parameter that implements a filter.

#### Updates and creation should return a resource representation
A PUT, POST or PATCH call may make modifications to fields of the underlying resource that weren't part of the provided parameters (for example: created_at or updated_at timestamps). To prevent an API consumer from having to hit the API again for an updated representation, have the API return the updated (or created) representation as part of the response.

#### Field Naming Convention
If you're using JSON (JavaScript Object Notation) as your primary representation format, the "right" thing to do is to follow JavaScript naming conventions - and that means camelCase for field names! If you then go the route of building client libraries in various languages, it's best to use idiomatic naming conventions in them - camelCase for C# and Java, snake_case for python and ruby.

#### Don't use an envelope by default, but make it possible when needed
Many APIs wrap their responses in envelopes like this:

```{
  "data" : {
    "id" : 123,
    "name" : "John"
  }
}```
There are a couple of justifications for doing this - it makes it easy to include additional metadata or pagination information, some REST clients don't allow easy access to HTTP headers and JSONP requests have no access to HTTP headers. However, with standards that are being rapidly adopted like CORS and the Link header from RFC 5988, enveloping is starting to become unnecessary.

#### Pagination
we use ```page``` and ```pageSize``` in query parameters for implementing pagination in our projects. default page size is 20 and default page is 0.

#### Rate limiting
To prevent abuse, it is standard practice to add some sort of rate limiting to an API. [RFC 6585](http://tools.ietf.org/html/rfc6585) introduced a HTTP status code [429 Too Many Requests](http://tools.ietf.org/html/rfc6585#section-4) to accommodate this.

However, it can be very useful to notify the consumer of their limits before they actually hit it. This is an area that currently lacks standards but has a number of popular conventions using HTTP response headers.

At a minimum, include the following headers:
    - ```X-Rate-Limit-Limit``` - The number of allowed requests in the current period
    - ```X-Rate-Limit-Remaining``` - The number of remaining requests in the current period
    - ```X-Rate-Limit-Reset``` - The number of seconds left in the current period

**Why is number of seconds left being used instead of a time stamp for X-Rate-Limit-Reset?**

A timestamp contains all sorts of useful but unnecessary information like the date and possibly the time-zone. An API consumer really just wants to know when they can send the request again and the number of seconds answers this question with minimal additional processing on their end. 

Some APIs use a UNIX timestamp (seconds since epoch) for X-Rate-Limit-Reset. Don't do this!

Why is it bad practice to use a UNIX timestamp for X-Rate-Limit-Reset?

The HTTP spec already specifies using [RFC 1123 date formats](http://www.ietf.org/rfc/rfc1123.txt) (currently being used in Date, If-Modified-Since and Last-Modified HTTP headers). If we were to specify a new HTTP header that takes a timestamp of some sort, it should follow RFC 1123 conventions instead of using UNIX timestamps.


#### Authentication
A RESTful API should be stateless. This means that request authentication should not depend on cookies or sessions. 
Instead, each request should come with some sort authentication credentials.

By always using SSL, the authentication credentials can be simplified to a randomly generated access token 
that is delivered in the user name field of HTTP Basic Auth. The great thing about this is that it's completely browser
explorable - the browser will just popup a prompt asking for credentials if it receives a 401 Unauthorized status code 
from the server.
	
#### Caching
HTTP provides a built-in caching framework! All you have to do is include some additional outbound response headers 
and do a little validation when you receive some inbound request headers.

There are 2 approaches: ETag and Last-Modified

ETag: When generating a response, include a HTTP header ETag containing a hash or checksum of the representation. 
This value should change whenever the output representation changes. Now, if an inbound HTTP requests contains an
If-None-Match header with a matching ETag value, the API should return a 304 Not Modified status code instead of 
the output representation of the resource.

Last-Modified: This basically works like to ETag, except that it uses timestamps. 
The response header Last-Modified contains a timestamp in RFC 1123 format which is validated against If-Modified-Since. 
Note that the HTTP spec has had 3 different acceptable date formats and the server should be prepared to accept any of them.

#### Error Handling
Just like an HTML error page shows a useful error message to a visitor, an API should provide a useful error message in a known consumable format. The representation of an error should be no different than the representation of any resource, just with its own set of fields.

The API should always return sensible HTTP status codes. API errors typically break down into 2 types: 400 series status codes for client issues and 500 series status codes for server issues. At a minimum, the API should standardize that all 400 series errors come with consumable JSON error representation. If possible (i.e. if load balancers and reverse proxies can create custom error bodies), this should extend to 500 series status codes.

A JSON error body should provide a few things for the developer - a useful error message, a unique error code (that can be looked up for more details in the docs) and possibly a detailed description. JSON output representation for something like this would look like:

```{
  "code" : 1234,
  "message" : "Something bad happened :(",
  "description" : "More details about the error here"
}```
Validation errors for PUT, PATCH and POST requests will need a field breakdown. This is best modeled by using a fixed top-level error code for validation failures and providing the detailed errors in an additional ```errors``` field, like so:

```{
  "code" : 1024,
  "message" : "Validation Failed",
  "errors" : [
    {
      "code" : 5432,
      "field" : "first_name",
      "message" : "First name cannot have fancy characters"
    },
    {
       "code" : 5622,
       "field" : "password",
       "message" : "Password cannot be blank"
    }
  ]
}```