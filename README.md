# harvester
Harvester project
---

This application is providing scheduled downloading for anything you like.
The only thing you need to provide is your the `.jar` file containing an implementation of `IHarvester` class. Through web UI you can add and manage scheduled tasks for **Harvester**.

It works only on MS SQL Server =(, as it is heavily relies on triggers and stored procedures. This is to be reimplemented using scheduling libraries like Quartz.

---
## How to run
  
  1. Clone the project.
  2. Run the scripts from `sql` folder. That should create a database and all of its tables.
  3. Edit `src/main/resources/application.properties` file to your database settings.
  4. Run `gradlew jettyRunWar` to run it on Jetty application server. Default port is 8083, you can change that in `build.gradle`. Or you can build `.war` file to run it on your favorite application server.
  5. The starting URL for the application is `[server]/login`.

## IDE import
There are tasks in gradle for creating Intellij IDEA and Eclipse projects. `gradlew idea` and `gradle eclipse`.
