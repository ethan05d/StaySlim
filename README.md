# StaySlim

## Project Overview

StaySlim is a lightweight web application for tracking daily fitness metrics: weight, calorie intake, and workout check-ins. Users can create an account, log their daily stats, view historical trends, and see a leaderboard showcasing commitment streaks. The backend uses Java, JDBC, and a three-tier architecture (JSP/Servlet presentation, service/DAO logic, MySQL data layer) deployed on Apache Tomcat.

## Setup & Running

1. **Clone the Repository**

   ```bash
   git clone https://github.com/ethan05d/StaySlim.git
   cd StaySlim
   ```

2. **Install Prerequisites**

   * **Java JDK 11+**: [https://adoptium.net/](https://adoptium.net/)
   * **Apache Maven**: [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)
   * **MySQL Server** & **Workbench**: [https://dev.mysql.com/downloads/](https://dev.mysql.com/downloads/)

3. **Configure MySQL & Initialize Schema**

   * Start your MySQL server.
   * Open **MySQL Workbench**, connect as a user with privileges, and both open `db/create_schema.sql` and `db/initialize_data.sql` independently.
   * Click the **Execute** button on both scripts separately to run the script and create the `StaySlim` database and tables.

4. **Verify DB Connection Settings**

   Ensure the credentials in `src/main/java/util/DBConnection.java` match your MySQL setup:
   ```java
   URL = "jdbc:mysql://localhost:3306/StaySlim?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
   USER = "root";
   PASSWORD = "root";
   ```

   In this case, `USER = "root"` and `PASSWORD = "root"` set via MySQL server connection. Adjust as needed for your environment.

6. **Build & Run**

   ```bash
   mvn clean package
   mvn tomcat7:run
   ```

   Access the app at [http://localhost:8080/](http://localhost:8080/)

## Dependencies & Software

* Java SE Development Kit 11 or later
* Apache Maven 3.6+
* MySQL Server 8.x
* MySQL Workbench (optional GUI)
* Apache Tomcat 7+ (embedded via Maven plugin)

---

## User Manual

### 1. Signing Up & Logging In

* Navigate to `/signup`, fill in username, email, password, and height.
* After account creation, go to `/login` and enter your credentials.

### 2. Dashboard Overview

* **Daily Check-In**: Click “I worked out today!” to record a check-in and build your streak.
* **Daily Log**: Enter date, weight (kg), and calories, then click “Save Entry.”
* **Weight History**: View past entries with options to **Edit** or **Delete** each.
* **Streak & BMI**: See your current workout streak and latest BMI calculation.

### 3. Editing & Deleting Entries

* In **Weight History**, click **Edit** to modify an entry or **Delete** to remove it.
* After either action, you’ll be returned to the dashboard.

### 4. Leaderboard

* Navigate to `/app/leaderboard` to see top streaks. Your row is highlighted.

### 5. Admin Page

* Go to `/app/admin` (requires login) to reset or repair all user data.
