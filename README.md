```markdown
**Agilysys Spring Boot Project README**

---

## Agilysys Project

The Agilysys project is a Spring Boot-based application designed to manage customer information and their associated cloud services. It includes features such as customer creation, retrieval, update, and deletion, as well as the addition and management of cloud services.

---

### Instructions to Run the Application

Follow the steps below to run the Agilysys Spring Boot application locally:

#### Prerequisites

1. **Java Development Kit (JDK):** Ensure that you have Java 17 or a compatible version installed on your machine.

2. **Maven:** Make sure Maven is installed. You can download it from [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi) and follow the installation instructions.

3. **Git:** Install Git for version control. You can download Git from [https://git-scm.com/downloads](https://git-scm.com/downloads) and follow the installation instructions.

#### Clone the Repository

Open a terminal and run the following command to clone the Agilysys project repository:

```bash
git clone https://github.com/your-username/Agilysys.git
```

Replace `your-username` with your actual GitHub username.

#### Build the Project

Navigate to the project directory using the terminal:

```bash
cd Agilysys
```

Build the project using Maven:

```bash
mvn clean install
```

#### Run the Application

Once the build is successful, run the Spring Boot application:

```bash
mvn spring-boot:run
```

The application will start, and you should see log messages indicating that the server is running.

#### Access Swagger UI

Open a web browser and go to [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) to access Swagger UI. This page provides an interactive API documentation where you can test and explore the available endpoints.

#### Test Endpoints

Feel free to test the different endpoints using Swagger UI or tools like curl, Postman, or your preferred API testing tool.

#### Input Data Example

Use the provided input data JSON example to create a customer with associated cloud services:

```json
{
    "name": "Pravar Goel",
    "email": "pravargoel29@gmail.com",
    "address": "1207 Willow Ave, Hoboken",
    "cloudServices": [
        {
            "serviceName": "S3",
            "plan": "Standard Storage"
        }
    ]
}
```

#### Running JWT Authentication

Before using JWT authentication, ensure that the roles "ADMIN" and "USER" are inserted into the roles table.

To register a new user, send a POST request to the `api/auth/register` endpoint with the following JSON payload:

```json
{
    "username": "your-username",
    "password": "your-password"
}
```

Upon successful registration, you can log in using the same credentials by sending a POST request to the `api/auth/login` endpoint. You will receive an access token upon successful login.

Use this access token in the Authorization header (Bearer token) for accessing other endpoints in the CustomerController.

#### Terminate the Application

To stop the running Spring Boot application, press `Ctrl + C` in the terminal where the application is running.

---

Now you have successfully run the Agilysys Spring Boot application locally. Feel free to explore and modify the code to suit your needs! If you encounter any issues, refer to the error messages in the console or check the project's documentation for troubleshooting.
```
