# RESTFul API Design

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
