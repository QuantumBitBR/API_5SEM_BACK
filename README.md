# API_5SEM_BACK

## Requirements

Before running the project, make sure you have the following installed:

- **Java** (recommended Java 17 or higher)
- **Maven** (Apache Maven 3.6.3 or higher)
- **PostgreSQL** (if using a PostgreSQL database)

## Installation

Follow the steps below to set up the environment and install the necessary dependencies for the project.

1. **Clone the repository** to your local machine:

    ```bash
    git clone https://github.com/your-repository/java-spring-project.git
    cd java-spring-project
    ```

2. **Environment Variables**

   Create an `external.properties` file in the root directory of your project and add the following environment variables:

   ```properties
   db.url=jdbc:postgresql://localhost:5432/dbo
   db.username=taiga
   db.password=taiga
   ```

   Alternatively, you can set these variables in the system environment.

3. **Build the project** using Maven:

    ```bash
    mvn clean install
    ```

## Running the Project

Once the dependencies are installed, you can run the project using the following command:

```bash
mvn spring-boot:run
```

Or if you prefer to run the JAR file:

```bash
java -jar target/your-application.jar
```
