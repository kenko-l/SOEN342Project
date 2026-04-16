# SOEN342Project

Team Leader: Ken Ko Latchman (40299132)  

Team Member: Mark Tadros (40250850), Jeremie Beaudoin (40276582)

## Running the Project (Windows CMD)

1. Make sure your folder structure looks like this:

TaskManager/

├── src/    (all .java files)

├── data/   (csv files used by the app)

IMPORTANT: The "data" folder must exist.

2. Open Command Prompt in the project root folder and run:

javac -d . src\\*.java

3. Create the executable JAR:

jar cfe TaskManager.jar TaskManagementUI *.class

4. Run the application:

java -jar TaskManager.jar

Notes:
- Always run the JAR from the project root (so the "data" folder is found)
- Requires Java (JDK) installed
