# Bruno API Collection for Movies API

This folder contains a [Bruno](https://www.usebruno.com/) API collection for testing the Movies API.

## What is Bruno?

Bruno is an open-source API client similar to Postman or Insomnia, but with a file-based approach. It allows you to store your API collections as files in your project repository, making it easy to version control and share with your team.

## Getting Started

1. Install Bruno from [https://www.usebruno.com/downloads](https://www.usebruno.com/downloads)
2. Open Bruno and click "Open Collection"
3. Navigate to this `bruno` folder and select it
4. The Movies API collection will be loaded with all the requests

## Available Requests

The collection includes the following requests:

- **Get all movies**: Retrieves a paginated list of movies
- **Get movie by ID**: Retrieves a specific movie by its ID
- **Search movies by title**: Searches for movies with a specific title
- **Search movies by year**: Searches for movies released in a specific year
- **Search movies by genre**: Searches for movies with a specific genre

## Environment Variables

The collection uses the following environment variables:

- `baseUrl`: The base URL of the API (default: http://localhost:8080)

You can modify these variables in the "Environments" section in Bruno.

## Running the API

Make sure the Movies API is running before testing the requests:

```bash
./mvnw quarkus:dev
```

The API will be available at http://localhost:8080.