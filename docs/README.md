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
 - [Authentication]()
 - [Code Example](../root/src/main/java/com/mahdiyar/controller/UserController.java)
 
### What is the purpose of this document ?

This document represents Farazpardazan restful api design strategy. It provides a brief overview of how we design our
restful apis and provide them to our clients.
### introduction to HTTP protocol and REST

we have craeted a [document](Http-Docs.md) specially for this purpose. you can read this document.

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
