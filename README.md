# DesaAPI

DesaAPI is a RESTful API for managing projects, task columns, and tasks. It is built using Java, Spring Boot, and JPA with a Maven build system.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
    - [Projects](#projects)
    - [Task Columns](#task-columns)
    - [Tasks](#tasks)
- [Contributing](#contributing)
- [License](#license)

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/KirDemorgan/desaAPI.git
    cd desaAPI
    ```

2. Build the project using Maven:
    ```sh
    mvn clean install
    ```

3. Run the application:
    ```sh
    mvn spring-boot:run
    ```

## Usage

The API can be accessed at `http://localhost:8080/api`.

## API Endpoints

### Projects

- **Get all projects**
    ```http
    GET /api/projects
    ```
  **Query Parameters:**
    - `prefix_name` (optional): Filter projects by name prefix.

  **Response:**
    ```json
    [
        {
            "id": 1,
            "name": "Project 1",
            "taskColumnIds": [1, 2]
        },
        ...
    ]
    ```

- **Create or update a project**
    ```http
    POST /api/projects
    ```
  **Request Parameters:**
    - `project_id` (optional): ID of the project to update.
    - `project_name` (optional): Name of the project.

  **Response:**
    ```json
    {
        "id": 1,
        "name": "Project 1",
        "taskColumnIds": [1, 2]
    }
    ```

- **Delete a project**
    ```http
    DELETE /api/projects/{projectId}
    ```
  **Path Parameters:**
    - `projectId`: ID of the project to delete.

  **Response:**
    ```json
    {
        "answer": "Project with id 1 deleted successfully"
    }
    ```

### Task Columns

- **Change task column position**
    ```http
    PATCH /api/task_columns/{task_column_id}/position
    ```
  **Path Parameters:**
    - `task_column_id`: ID of the task column to update.

  **Request Parameters:**
    - `task_column_new_position`: New position of the task column.

  **Response:**
    ```json
    {
        "id": 1,
        "name": "Column 1",
        "position": 2,
        "tasks": [...]
    }
    ```

- **Delete a task column**
    ```http
    DELETE /api/task_columns/{task_column_id}
    ```
  **Path Parameters:**
    - `task_column_id`: ID of the task column to delete.

  **Response:**
    ```json
    {
        "answer": "Task column with id 1 deleted successfully"
    }
    ```

### Tasks

- **Get all tasks in a column**
    ```http
    GET /api/projects/{task_column_id}/tasks
    ```
  **Path Parameters:**
    - `task_column_id`: ID of the task column.

  **Response:**
    ```json
    [
        {
            "id": 1,
            "name": "Task 1",
            "description": "Description 1",
            "taskColumnId": 1
        },
        ...
    ]
    ```

- **Create a task**
    ```http
    POST /api/projects/{task_column_id}/task
    ```
  **Path Parameters:**
    - `task_column_id`: ID of the task column.

  **Request Body:**
    ```json
    {
        "taskName": "Task 1",
        "optionalTaskDescription": "Description 1"
    }
    ```

  **Response:**
    ```json
    {
        "id": 1,
        "name": "Task 1",
        "description": "Description 1",
        "taskColumnId": 1
    }
    ```

- **Update a task**
    ```http
    PATCH /api/projects/{task_id}/task
    ```
  **Path Parameters:**
    - `task_id`: ID of the task to update.

  **Request Body:**
    ```json
    {
        "taskName": "Updated Task 1",
        "optionalTaskDescription": "Updated Description 1"
    }
    ```

  **Response:**
    ```json
    {
        "id": 1,
        "name": "Updated Task 1",
        "description": "Updated Description 1",
        "taskColumnId": 1
    }
    ```

- **Delete a task**
    ```http
    DELETE /api/projects/{task_id}/task
    ```
  **Path Parameters:**
    - `task_id`: ID of the task to delete.

  **Response:**
    ```json
    {
        "answer": "Task with id 1 deleted successfully"
    }
    ```

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any changes.

## License

This project is licensed under the MIT License.