# StaySlim

## Project Overview

StaySlim is a lightweight web application for tracking daily fitness metrics: weight, calorie intake, and workout check-ins. Users can create an account, log their daily stats, view historical trends, and see a leaderboard showcasing commitment streaks. The backend uses Java, JDBC, and a three-tier architecture (JSP/Servlet presentation, service/DAO logic, MySQL data layer) deployed on Apache Tomcat.

## Setup & Running

1. **Clone the Repository**

   ```bash
   git clone https://github.com/ethan05d/StaySlim.git
   cd StaySlim
   ```
---
2. **Install Prerequisites**

   * **Java JDK 21+**: [https://adoptium.net/](https://adoptium.net/)
   * **Apache Maven**: [https://maven.apache.org/install.html](https://maven.apache.org/install.html)
   * **MySQL Server** & **Workbench**: [https://dev.mysql.com/downloads/workbench/](https://dev.mysql.com/downloads/workbench/)
---
3. **Configure MySQL & Initialize Schema**

   #### Option A: MySQL Workbench

   1. **Start** your MySQL server and **launch** MySQL Workbench. 
   2. In the **Navigator**, create your `StaySlim` connection (make sure the server is running).
   3. **Open** `db/create_schema.sql`
      - Click the **Execute** button (⚡).
   4. **Open** `db/initialize_data.sql`
      - Click **Execute** again.

   #### Option B: Command Line (using MySQL's `root` user, adjust as needed)

   ```bash
   # From your project root:
   mysql -u root -p < db/create_schema.sql
   mysql -u root -p < db/initialize_data.sql
   ```

---
4. **Verify DB Connection Settings**

   Ensure the credentials in `src/main/java/util/DBConnection.java` match your MySQL setup:
   ```java
   URL = "jdbc:mysql://localhost:3306/StaySlim?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
   USER = "root";
   PASSWORD = "root";
   ```

   In this case, `DB_NAME = StaySlim`, `USER = "root"` and `PASSWORD = "root"` set via MySQL server. Adjust as needed for your environment.
---
5. **Build & Run**

   ```bash
   mvn clean package
   mvn tomcat7:run
   ```

   Access the app at [http://localhost:8080/](http://localhost:8080/)

## Dependencies & Software

* Java SDK 21+
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
