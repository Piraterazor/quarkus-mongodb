# Demo API MongoDB

This project is a RESTful API built with Quarkus that connects to a MongoDB database. It provides endpoints to retrieve Store data from the `embedded_Stores` collection in the `sample_mflix` database.

## Architecture

The project follows a layered architecture:

1. **Model Layer**: Defines the data structure for stores
2. **Repository Layer**: Handles database interactions using Panache
3. **Service Layer**: Contains business logic
4. **Resource Layer**: Exposes REST endpoints

## MongoDB Configuration

The application connects to MongoDB using the connection string specified in `application.properties`. Make sure to update this with your own MongoDB connection details:

```properties
quarkus.mongodb.connection-string=xxxxxx
quarkus.mongodb.database=xxxxxx
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/stores` | Get all stores (paginated) |
| GET | `/api/stores/{id}` | Get a Store by ID |

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
# Get all stores (first page, 20 items)
curl -X GET "http://localhost:8080/api/stores"

# Get all stores (second page, 10 items per page)
curl -X GET "http://localhost:8080/api/stores?page=1&size=10"

# Get a Store by ID
curl -X GET "http://localhost:8080/api/stores/1"

```

## Bruno API Collection

This project includes a [Bruno](https://www.usebruno.com/) API collection for testing the API. Bruno is an open-source API client similar to Postman or Insomnia, but with a file-based approach that makes it easy to version control and share API collections.

### Using the Bruno Collection

1. Install Bruno from [https://www.usebruno.com/downloads](https://www.usebruno.com/downloads)
2. Open Bruno and click "Open Collection"
3. Navigate to the `bruno` folder in this project and select it
4. The Stores API collection will be loaded with all the requests

The collection includes requests for all API endpoints and uses environment variables for the base URL. See the `bruno/README.md` file for more details.

## Implementation Notes

### JSON Serialization

The API returns complete JSON responses with all fields from the Store entity:

The project uses Quarkus REST Jackson for automatic JSON serialization:

- The `quarkus-rest-jackson` dependency is included in the pom.xml
- The `@Produces(MediaType.APPLICATION_JSON)` annotation at the class level in StoreResource ensures all endpoints produce JSON
- Resource methods directly return domain objects (Store, List<Store>, PaginatedResponse<Store>) without wrapping them in Response objects
- Quarkus automatically handles the serialization of these objects to JSON

The Store class uses Jackson annotations to ensure proper JSON serialization:

- `@JsonIgnoreProperties(ignoreUnknown = true)` - Ignores unknown properties during deserialization
- `@JsonSerialize(using = ToStringSerializer.class)` - Properly serializes MongoDB ObjectId to string

### Pagination

The API supports pagination for endpoints that return multiple items:

- The `/api/stores` endpoint returns a paginated response with the following structure:
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

## CI/CD Pipeline

This project uses Azure DevOps for continuous integration and deployment. The pipeline configuration is defined in the `azure-pipelines.yml` file at the root of the repository.

### Pipeline Configuration

The pipeline consists of two stages:

1. **Build and Test**
   - Builds the project using Maven and JDK 21
   - Runs all tests
   - Publishes test results
   - Creates and publishes build artifacts

2. **Deploy to Development** (Placeholder)
   - Downloads the build artifacts
   - Contains placeholder steps for deployment

### Triggers

The pipeline is triggered automatically when changes are pushed to the `main` branch, except for changes to:
- README.md
- .gitignore
- bruno-api/ directory

### Running the Pipeline

The pipeline runs automatically on push to the main branch. You can also manually trigger it from the Azure DevOps portal.

## Future Enhancements

Potential improvements for this API:

1. Add authentication and authorization
2. Implement POST, PUT, DELETE endpoints for full CRUD operations
3. Add data validation
4. Implement caching for frequently accessed data
5. Add rate limiting
6. Improve error handling and logging
7. Add Swagger/OpenAPI documentation
8. Implement full deployment pipeline for production environments
