# Farazpardazan Introduction to HTTP and REST


## Table of content
 - [What is the purpose of this document?]()
 - [introduction to HTTP protocol and REST]()
    - [HTTP basics]()
    - [Request Methods]()
      - [GET]()
      - [POST]()
      - [PUT]()
      - [PATCH]()
      - [DELETE]()
      - [HEAD]()
      - [OPTIONS]()
    - [HTTP Status Codes]()
      - [1xx Informational]()
      - [2xx Success]()
      - [3xx Redirection]()
      - [4xx Client Error]()
      - [5xx Server Error]()
 - [RESTFul APIs Best practices]()
 - [Authentication]()
 - [Caching]()
 
### What is the purpose of this document ?

This document represents an introduction to Http and REST. It provides a brief overview of Http Protocol, RESTFul API best practices, authentication and caching.


### introduction to HTTP protocol and REST

The Hypertext Transfer Protocol (HTTP) is a stateless application-
   level protocol for distributed, collaborative, hypertext information
   systems. 

   Each Hypertext Transfer Protocol (HTTP) message is either a request
   or a response.  A server listens on a connection for a request,
   parses each message received, interprets the message semantics in
   relation to the identified request target, and responds to that
   request with one or more response messages.  A client constructs
   request messages to communicate specific intentions, examines
   received responses to see if the intentions were carried out, and
   determines how to interpret the results. 
   
### Request Methods
   This specification defines a number of standardized methods that are
   commonly used in HTTP, as outlined by the following table.  By
   convention, standardized methods are defined in all-uppercase
   US-ASCII letters.
```
   +---------+-------------------------------------------------+-------+
   | Method  | Description                                     | Sec.  |
   +---------+-------------------------------------------------+-------+
   | GET     | Transfer a current representation of the target | 4.3.1 |
   |         | resource.                                       |       |
   | HEAD    | Same as GET, but only transfer the status line  | 4.3.2 |
   |         | and header section.                             |       |
   | POST    | Perform resource-specific processing on the     | 4.3.3 |
   |         | request payload.                                |       |
   | PUT     | Replace all current representations of the      | 4.3.4 |
   |         | target resource with the request payload.       |       |
   | DELETE  | Remove all current representations of the       | 4.3.5 |
   |         | target resource.                                |       |
   | CONNECT | Establish a tunnel to the server identified by  | 4.3.6 |
   |         | the target resource.                            |       |
   | OPTIONS | Describe the communication options for the      | 4.3.7 |
   |         | target resource.                                |       |
   | TRACE   | Perform a message loop-back test along the path | 4.3.8 |
   |         | to the target resource.                         |       |
   +---------+-------------------------------------------------+-------+
```
#### GET

GET requests are the most common and widely used methods in APIs and websites. Simply put, the GET method is used to 
retreive data from a server at the specified resource. For example, say you have an API with a /users endpoint.
Making a GET request to that endpoint should return a list of all available users.

Since a GET request is only requesting data and not modifying any resources,
it's considered a safe and idempotent method.
 - GET http://127.0.0.1:8081/api/v1/users
 - GET http://127.0.0.1:8081/api/v1/users?page=0&size=20
 - GET http://127.0.0.1:8081/api/v1/users/123/courses
    

#### POST
In web services, POST requests are used to send data to the API server to create or update a resource. 
The data sent to the server is stored in the request body of the HTTP request.

The simplest example is a contact form on a website. When you fill out the inputs in a form and hit Send,
that data is put in the request body and sent to the server. This may be JSON or query parameters 
 (there's plenty of other formats, but these are the most common)
 (here we use JSON format for request body and Query Params for simple filters).

It's worth noting that a POST request is non-idempotent. 
t mutates data on the backend server (by creating or updating a resource),
 as opposed to a GET request which does not change any data. Here is a great explanation of idempotentcy.

#### PUT 
Similar to POST, PUT requests are used to send data to the API to update a resource. The difference is that
PUT requests are idempotent. That is, calling the same PUT request multiple times will always produce the same result.
In contrast, calling a POST request repeatedly make have side effects of creating the same resource multiple times.

#### PATCH
A PATCH request is one of the lesser-known HTTP methods, but I'm including it this high in the list since it is similar 
to POST and PUT. The difference with PATCH is that you only apply partial modifications to the resource.

The difference between PATCH and PUT, is that a PATCH request is non-idempotent (like a POST request).

To expand on partial modification, say you're API has a /users/{{userid}} endpoint, and a user has a username. 
With a PATCH request, you may only need to send the updated username in the request body - as opposed to POST 
and PUT which require the full user entity.

#### DELETE
DELETE method is exactly as it sounds: delete the resource at the specified URL. This method is one of the more common
in RESTful APIs. so it's good to know how it works.

If a new user is created with a POST request to /users, and it can be retrieved with a GET request to /users/{{user-id}},
then making a DELETE request to /users/{{user-id}} will remove that user.
#### HEAD
The HEAD method is almost identical to GET, except without the response body. In other words, 
if GET /users returns a list of users, then HEAD /users will make the same request but won't get back the list of users.

HEAD requests are useful for checking what a GET request will return before actually making a GET request 
-- like before downloading a large file or response body. 
[Learn more about HEAD requests on MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods/HEAD).

#### OPTIONS
OPTIONS request should return data describing what other methods and operations the server supports at the given URL.

OPTIONS requests are more loosely defined and used than the others, making them a good candidate to test for fatal API 
errors. If an API isn't expecting an OPTIONS request, it's good to put a test case in place that verifies 
failing behavior.
#### notes : by convention we use only GET, POST, PUT, DELETE, PATCH

### HTTP Status Codes
  Http Status codes are issued by a server in response to a client's request made to the server.
  HTTP response status codes indicate whether a specific HTTP request has been successfully completed.
  Responses are grouped in five classes:

 - Informational responses 1XX
 - Successful responses 2XX
 - Redirects 3XX
 - Client errors 4XX
 - Server errors 5XX
 By convention we use these Http status codes in our project and other codes are invalid.
 
### 1xx Informational
we do not use any of 1XX response codes.
 
### 2xx Success
 in rage of 2XX we use these codes : 200
 - #### 200 OK
  The request has succeeded. The meaning of the success depends on the HTTP method:
  - GET: The resource has been fetched and is transmitted in the message body.
  - HEAD: The entity headers are in the message body.
  - PUT or POST: The resource describing the result of the action is transmitted in the message body.
### 3xx Redirection
  in range of 3XX we use these codes : 302
   - #### 302 Found
This response code means that the URI of requested resource has been changed temporarily.
 Further changes in the URI might be made in the future. Therefore, this same URI should be used by the client
  in future requests.

### 4xx Client Error
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

### 5xx Server Error
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

1. Use nouns but no verbs
For an easy understanding use this structure for every resource
Do not use verbs like :
```
/getAllCars
/createNewCar
/deleteAllRedCars
```
2. GET method and query parameters should not alter the state
   Use PUT, POST and DELETE methods instead of the GET method to alter the state.
   Do not use GET for state changes:
```
   GET /users/711?activate
   GET /users/711/activate
```

3. Use plural nouns
Do not mix up singular and plural nouns. Keep it simple and use only plural nouns for all resources.
```
/cars instead of /car
/users instead of /user
/products instead of /product
/settings instead of /setting
```

4. Use sub-resources for relations
If a resource is related to another resource use sub-resources.
```
GET /cars/711/drivers/ Returns a list of drivers for car 711
GET /cars/711/drivers/4 Returns driver #4 for car 711
```

5. Version your API
Always version your API. Versioning helps you iterate faster and prevents invalid requests from hitting updated endpoints.
It also helps smooth over any major API version transitions as you can continue to offer old API versions for a 
period of time.

```
/blog/api/v1
```
why and when to change version ?

if response dto of our api must be changed, we can't change old api response for backward compatibility. so adding 
a new version of api for new clients is the best solution. 

6. use kebab-case for naming in URLs.

7. use X-* naming convention for custom headers

### Authentication
A RESTful API should be stateless. This means that request authentication should not depend on cookies or sessions. 
Instead, each request should come with some sort authentication credentials.

By always using SSL, the authentication credentials can be simplified to a randomly generated access token 
that is delivered in the user name field of HTTP Basic Auth. The great thing about this is that it's completely browser
explorable - the browser will just popup a prompt asking for credentials if it receives a 401 Unauthorized status code 
from the server.

### Caching
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
