# Demo API MongoDB

This project is a RESTful API built with Quarkus that connects to a MongoDB database. It provides endpoints to retrieve movie data from the `embedded_movies` collection in the `sample_mflix` database.

## Architecture

The project follows a layered architecture:

1. **Model Layer**: Defines the data structure for movies
2. **Repository Layer**: Handles database interactions using Panache
3. **Service Layer**: Contains business logic
4. **Resource Layer**: Exposes REST endpoints

## MongoDB Configuration

The application connects to MongoDB using the connection string specified in `application.properties`. Make sure to update this with your own MongoDB connection details:

```properties
quarkus.mongodb.connection-string=mongodb+srv://username:password@cluster.mongodb.net/sample_mflix
quarkus.mongodb.database=sample_mflix
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/movies` | Get all movies (paginated) |
| GET | `/api/movies/{id}` | Get a movie by ID |
| GET | `/api/movies/search/title?q={title}` | Search movies by title |
| GET | `/api/movies/search/year/{year}` | Search movies by year |
| GET | `/api/movies/search/genre/{genre}` | Search movies by genre |

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/demo-api-mongodb-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Example Usage

Here are some example curl commands to interact with the API:

```bash
# Get all movies (first page, 20 items)
curl -X GET "http://localhost:8080/api/movies"

# Get all movies (second page, 10 items per page)
curl -X GET "http://localhost:8080/api/movies?page=1&size=10"

# Get a movie by ID
curl -X GET "http://localhost:8080/api/movies/573a1390f29313caabcd4135"

# Search movies by title
curl -X GET "http://localhost:8080/api/movies/search/title?q=Star%20Wars"

# Search movies by year
curl -X GET "http://localhost:8080/api/movies/search/year/1977"

# Search movies by genre
curl -X GET "http://localhost:8080/api/movies/search/genre/Action"
```

## Bruno API Collection

This project includes a [Bruno](https://www.usebruno.com/) API collection for testing the API. Bruno is an open-source API client similar to Postman or Insomnia, but with a file-based approach that makes it easy to version control and share API collections.

### Using the Bruno Collection

1. Install Bruno from [https://www.usebruno.com/downloads](https://www.usebruno.com/downloads)
2. Open Bruno and click "Open Collection"
3. Navigate to the `bruno` folder in this project and select it
4. The Movies API collection will be loaded with all the requests

The collection includes requests for all API endpoints and uses environment variables for the base URL. See the `bruno/README.md` file for more details.

## Implementation Notes

### JSON Serialization

The API returns complete JSON responses with all fields from the Movie entity:

```json
{
  "id": "573a1390f29313caabcd42e8",
  "title": "The Great Train Robbery",
  "year": 1903,
  "genres": ["Western", "Action"],
  "cast": ["A.C. Abadie", "Gilbert M. 'Broncho Billy' Anderson"],
  "plot": "A group of bandits stage a brazen train hold-up, only to find a determined posse hot on their heels.",
  "fullplot": "Among the earliest existing films in American cinema - notable as the first film that presented a narrative story to tell - it depicts a group of cowboy outlaws who hold up a train and rob the passengers. They are then pursued by a posse, who finally catch the robbers, and bring them to justice.",
  "imdb": {
    "rating": 7.4,
    "votes": 17512,
    "id": 439
  }
}
```

The project uses Quarkus REST Jackson for automatic JSON serialization:

- The `quarkus-rest-jackson` dependency is included in the pom.xml
- The `@Produces(MediaType.APPLICATION_JSON)` annotation at the class level in MovieResource ensures all endpoints produce JSON
- Resource methods directly return domain objects (Movie, List<Movie>, PaginatedResponse<Movie>) without wrapping them in Response objects
- Quarkus automatically handles the serialization of these objects to JSON

The Movie class uses Jackson annotations to ensure proper JSON serialization:

- `@JsonIgnoreProperties(ignoreUnknown = true)` - Ignores unknown properties during deserialization
- `@JsonSerialize(using = ToStringSerializer.class)` - Properly serializes MongoDB ObjectId to string

### Pagination

The API supports pagination for endpoints that return multiple items:

- The `/api/movies` endpoint returns a paginated response with the following structure:
  ```json
  {
    "data": [
      {
        "id": "573a1390f29313caabcd5293",
        "title": "The Perils of Pauline",
        "year": 1914,
        "genres": ["Action"],
        "cast": ["Pearl White", "Crane Wilbur", "Paul Panzer"],
        "plot": "Young Pauline is left a lot of money when her wealthy uncle dies...",
        "imdb": {
          "rating": 6.9,
          "votes": 1033,
          "id": 4465
        }
      },
      {
        "id": "573a1391f29313caabcd68d0",
        "title": "From Hand to Mouth",
        "year": 1919,
        "genres": ["Comedy", "Short", "Action"],
        "cast": ["Harold Lloyd", "Mildred Davis", "Peggy Cartwright"],
        "plot": "A penniless young man tries to save an heiress from kidnappers..."
      }
    ],
    "totalCount": 1000,
    "page": 0,
    "pageSize": 20,
    "totalPages": 50
  }
  ```
- Use the `page` and `size` query parameters to control pagination
- The response includes metadata about the total count, current page, page size, and total pages

### Lombok

The project uses [Project Lombok](https://projectlombok.org/) to reduce boilerplate code:

- `@Getter` and `@Setter` - Automatically generates getters and setters for all fields
- `@NoArgsConstructor` - Generates a constructor with no parameters
- `@ToString` - Generates a toString() method with specified fields
- These annotations significantly reduce the amount of code that needs to be written and maintained

## Future Enhancements

Potential improvements for this API:

1. Add authentication and authorization
2. Implement POST, PUT, DELETE endpoints for full CRUD operations
3. Add data validation
4. Implement caching for frequently accessed data
5. Add rate limiting
6. Improve error handling and logging
7. Add Swagger/OpenAPI documentation
