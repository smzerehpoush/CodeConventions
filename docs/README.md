# Farazpardazan RESTFul API Design Strategy


## Table of content
 - (What is the purpose of this document?)[]
 - (introduction to HTTP protocol and REST)[]
    - HTTP basics
    - Request Methods
 - (How to choose Http Methods ?)[]
 - (How to choose returning Http Status code ?)[]
 - (Restful API Design)[]

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

   HTTP provides a uniform interface for interacting with a resource,
   regardless of its type, nature, or implementation, via
   the manipulation and transfer of representations.

   HTTP semantics include the intentions defined by each request method,
   extensions to those semantics that might be described in
   request header fields , the meaning of status codes to
   indicate a machine-readable response , and the meaning of
   other control data and resource metadata that might be given in
   response header fields .

   This document also defines representation metadata that describe how
   a payload is intended to be interpreted by a recipient, the request
   header fields that might influence content selection, and the various
   selection algorithms that are collectively referred to as "content 
   negotiation".
### Request Methods
   This specification defines a number of standardized methods that are
   commonly used in HTTP, as outlined by the following table.  By
   convention, standardized methods are defined in all-uppercase
   US-ASCII letters.

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
assueme that we have a controller for our users with basic CRUD operations.
let's create UserController .

```java


/**
 * @author name of auther of class
 */

@Api(value = "Users Controller")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ApiOperation("Create User")
    @PostMapping
    public ResponseEntity<UserModel> create(CreateUserRequestDto requestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.create(requestDto));
    }

    @ApiOperation("Read Users")
    @GetMapping
    public ResponseEntity<PagedQueryResponse<UserModel>> read(PagedQuery pagedQuery) {
        return ResponseEntity.ok(userService.read(pagedQuery));
    }

    @ApiOperation("Read User")
    @GetMapping("/{id}")
    public ResponseEntity<UserModel> read(@PathVariable("id") String id) {
        return ResponseEntity.ok(userService.read(id));
    }

    @ApiOperation("Update User")
    @PutMapping("/{id}")
    public ResponseEntity<UserModel> update(
            @PathVariable("id") String id,
            @RequestBody UpdateUserRequestDto requestDto) {
        return ResponseEntity.ok(userService.update(id, requestDto));
    }

    @ApiOperation("Delete User")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        userService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
```

tips : 
 - base url of apis must start with a version like /v1
 - service must be an interface and autowired by Spring Dependency injector and you should use ```@RequiredArgsConstructor ``` annotation to provide suitable constructor for spring to autorwire beans.
 - id of users must be passes in path variables with ```@PathVariable("id")```
 - your entities may have an id with a numerical long value and another uniqueId with ```UUID``` value.
you must pass these uniqueIds to your client and numerical id must be used in intersystem operations

## how to choose HttpMethod ?
 - if you want to read or query items in application you should use ```GET``` methods and filters must be passed in query params with ```@RequestParam.``` params may be optional and you can specify thme in annotation and give them a default value.
 - if you want to update an existing entity on application, you should choose ```PUT``` methods and items to be changed must be placed in request body and unique id of existsing entity in path param with ```@PathVariable```
- if you want to delete/disable an item, you should choose ```DELETE``` method and pass unique id in path param with ```@PathVariable```
 - if you want to create an entity or do something that is not in operations list up, you should use ```POST``` method.
