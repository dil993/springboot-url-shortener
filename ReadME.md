# Spring Boot URL Shortener

A simple yet powerful URL shortening service built with Spring Boot, PostgreSQL, and Thymeleaf.
This application allows users to shorten long URLs into more manageable links that redirect to the original URLs.

## Features

- Shorten long URLs to concise, shareable links
- Custom alias support for personalized short URLs
- URL validation to ensure only valid URLs are shortened
- Configurable link expiration
- Simple and intuitive web interface
- RESTful API for programmatic access
- PostgreSQL database for persistent storage

## Prerequisites

- Java 17 or higher
- Maven 3.6.3 or higher
- PostgreSQL 12 or higher
- Git (optional)

## Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/springboot-url-shortener.git
   cd springboot-url-shortener
   ```

2. **Configure the database**
    - Create a new PostgreSQL database named `url_shortener`
    - Update the database configuration in [src/main/resources/application.properties](cci:7://file:///D:/Sofwares/Applications/springboot-url-shortener/src/main/resources/application.properties:0:0-0:0):
      ```properties
      spring.datasource.url=jdbc:postgresql://localhost:5432/url_shortener
      spring.datasource.username=your_username
      spring.datasource.password=your_password
      ```

3. **Build the application**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the application**
    - Web Interface: http://localhost:8080
    - API Base URL: http://localhost:8080/api

## API Endpoints

### Create Short URL
```
POST /api/shorten
Content-Type: application/json

{
    "originalUrl": "https://example.com/very/long/url/to/be/shortened",
    "customAlias": "example"  // optional
}
```

### Get URL Information
```
GET /api/urls/{shortCode}
```

### Redirect to Original URL
```
GET /{shortCode}
```

## Configuration

You can customize the application behavior by modifying the following properties in [application.properties](cci:7://file:///D:/Sofwares/Applications/springboot-url-shortener/src/main/resources/application.properties:0:0-0:0):

```properties
# Base URL for the short links (must match your application's domain)
app.base-url=http://localhost:8080

# Default expiration period for URLs in days
app.default-expiry-days=30

# Enable/disable URL validation
app.validate-url=true
```

## Technologies Used

- **Backend**: Spring Boot 3.x
- **Database**: PostgreSQL
- **Templating**: Thymeleaf
- **Build Tool**: Maven
- **Java**: 17+

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License
```

You can create a new file named `README.md` in your project's root directory and paste this content. The README includes:

1. Project description
2. Key features
3. Prerequisites
4. Installation and setup instructions
5. API documentation
6. Configuration options
7. Technologies used
8. Contributing guidelines
9. License information

Would you like me to help you with anything specific in the README or any other part of your project?