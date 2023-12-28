# CartWave eCommerce App

CartWave is a sample eCommerce mobile app backend developed using Ktor and PostgreSQL.

## Setting Up System Environment Variables in IntelliJ IDEA

### 1. Open Project in IntelliJ IDEA

- Open your CartWave project in IntelliJ IDEA.

### 2. Access Run/Debug Configurations

- Click on the top menu: **Run > Edit Configurations...**.

### 3. Edit Run Configuration

- In the Run/Debug Configurations window, select your main run configuration for the Ktor application (e.g., `ApplicationKt` or your main class).

### 4. Open Environment Variables Settings

- Look for the **Environment Variables** section in the Run/Debug Configuration window.

### 5. Add PostgreSQL Environment Variables

- Click on the **... (Edit environment variables)** next to the Environment Variables field to open the Environment Variables dialog.

![Configuration](images/Screenshot-Configuration.png)

- Click on the **+ (Add)** button to add new environment variables.
- Add the following environment variables:
    - `DRIVER`: org.postgresql.Driver
    - `LOCAL_DB_URL`: URL of pgAdmin database
    - `LOCAL_DB_USER_NAME`: Username to access the pgAdmin database
    - `LOCAL_DB_PASSWORD`: Password for the pgAdmin database
    - `REMOTE_DB_URL`: URL of ElephantSQL database
    - `REMOTE_DB_USER_NAME`: Username to access the ElephantSQL database
    - `REMOTE_DB_PASSWORD`: Password for the ElephantSQL database

![Environment Variables](images/Screenshot-Env-Variable.png)

### 6. Apply and Save Changes

- Click **OK** to save the environment variables and close the dialogs.

### 7. Use Environment Variables in Code

- In your Ktor application code open **DatabaseFactory**, access these environment variables to establish the PostgreSQL database connection.

## Setting Up pgAdmin for PostgreSQL

### 1. Download and Install pgAdmin

- Download pgAdmin from the [official website](https://www.pgadmin.org/download/) and follow the installation instructions based on your operating system.

### 2. Configure pgAdmin

- Launch pgAdmin after installation.
- Add a new server connection by providing the PostgreSQL server details (host, port, username, and password).

![pgAdmin](images/Screenshot-pgAdmin.png)

### 3. Access PostgreSQL Database

- Once connected, you can manage your PostgreSQL databases, tables, and perform queries using pgAdmin.

## Setting Up ElephantSQL

### 1. Visit ElephantSQL Website

- Visit the [ElephantSQL](https://www.elephantsql.com/) website.

### 2. Sign Up or Log In

- Sign up or log in to ElephantSQL.

### 3. Create an Instance

- Create a new instance by following the provided instructions.
- Obtain the connection details (host, port, username, password) for your ElephantSQL instance.

## Contributing

- Contributions are welcome! Fork this repository and submit a pull request with your changes.