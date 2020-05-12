# Farazpardazan RESTFul API Design Strategy


## Table of content
 - [What is the purpose of this document?]()
 - [introduction to HTTP protocol and REST]()
    - [HTTP basics]()
    - [Request Methods]()
      - [GET]()
      - [POST]()
      - [PUT]()
      - [DELETE]()
      - [OPTIONS]()
 - [How to choose Http Methods ?]()
 - [How to choose returning Http Status code ?]()
 - [Restful API Design]()

### What is the purpose of this document ?

This document represents Farazpardazan restful api design strategy. It provides a brief overview of how we design our     restful apis and provide them to our clients.
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

GET requests are the most common and widely used methods in APIs and websites. Simply put, the GET method is used to retreive data from a server at the specified resource. For example, say you have an API with a /users endpoint. Making a GET request to that endpoint should return a list of all available users.

Since a GET request is only requesting data and not modifying any resources, it's considered a safe and idempotent method.   example request URLs
    - GET http://127.0.0.1:8081/api/v1/users
    - GET http://127.0.0.1:8081/api/v1/users?page=0&size=20
    - GET http://127.0.0.1:8081/api/v1/users/123/courses
    

#### POST
In web services, POST requests are used to send data to the API server to create or update a resource. The data sent to the server is stored in the request body of the HTTP request.

The simplest example is a contact form on a website. When you fill out the inputs in a form and hit Send, that data is put in the request body and sent to the server. This may be JSON or query parameters (there's plenty of other formats, but these are the most common)(here we use JSON format for request body and Query Params for simple filters).

It's worth noting that a POST request is non-idempotent. It mutates data on the backend server (by creating or updating a resource), as opposed to a GET request which does not change any data. Here is a great explanation of idempotentcy.

#### PUT 
Simlar to POST, PUT requests are used to send data to the API to update a resource. The difference is that PUT requests are idempotent. That is, calling the same PUT request multiple times will always produce the same result. In contrast, calling a POST request repeatedly make have side effects of creating the same resource multiple times

#### PATCH
A PATCH request is one of the lesser-known HTTP methods, but I'm including it this high in the list since it is similar to POST and PUT. The difference with PATCH is that you only apply partial modifications to the resource.

The difference between PATCH and PUT, is that a PATCH request is non-idempotent (like a POST request).

To expand on partial modification, say you're API has a /users/{{userid}} endpoint, and a user has a username. With a PATCH request, you may only need to send the updated username in the request body - as opposed to POST and PUT which require the full user entity.

#### DELETE
The DELETE method is exactly as it sounds: delete the resource at the specified URL. This method is one of the more common in RESTful APIs so it's good to know how it works.

If a new user is created with a POST request to /users, and it can be retrieved with a GET request to /users/{{userid}}, then making a DELETE request to /users/{{userid}} will remove that user.
#### HEAD
The HEAD method is almost identical to GET, except without the response body. In other words, if GET /users returns a list of users, then HEAD /users will make the same request but won't get back the list of users.

HEAD requests are useful for checking what a GET request will return before actually making a GET request -- like before downloading a large file or response body. [Learn more about HEAD requests on MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods/HEAD).

#### OPTIONS
OPTIONS request should return data describing what other methods and operations the server supports at the given URL.

OPTIONS requests are more loosely defined and used than the others, making them a good candidate to test for fatal API errors. If an API isn't expecting an OPTIONS request, it's good to put a test case in place that verifies failing behavior.

