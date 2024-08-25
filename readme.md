# Moonlight

A simple concept REST API server to search and notify available relievers, built with a straightforward schema for its simple use case, using Java Spring Boot.

## Features

- Simple doctor and moonlight management
- Available doctor identification based on location from hospital
- Email notification for available doctors

## Tech Stack

**Server:** Java, Spring Boot, PostgreSQL, Spring Boot Security, Spring Web, Java Mail Sender, Lombok

## Deployment

To deploy, go to the root directory of the project which contains the `docker-compose.yaml` file, then run:

```bash
  docker compose up
```


## API Reference

When running the application, visit the [Swagger Docs](http://localhost:8080/swagger-ui/index.html) to see the API documentation.


## Authors

- [Engr. Kirk Alyn Santos](https://github.com/kirkalyn13)

## License

[MIT](https://choosealicense.com/licenses/mit/)