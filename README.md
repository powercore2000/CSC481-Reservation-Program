## Statement

Long wait times and insufficient seatings are recurring problems in the restaurant industry. Traditional paper waitlists often frustrate customers and staff alike. A digital reservation system can optimize seating, improve turnover, and enhance the guest experience. Some problems that customers face include long wait times, poor seating allocation, as well as manual booking errors. Some solutions include timely updates and notifications about their reservations as well as advance booking days before the date. Some proposed names of this reservation system include ReserveNow, Book-N-Dine and Smart-N-Dine. We propose to develop the backend of the software first before doing the frontend of the software.

## Roles

Jonathan Martinez Cruz: Documentation, (Helper)
Samuel Omenwu: Front End
Daniel Anzora: Data Base
Corvalis Cohen: Backend
Eric Cordova: Fullstack (Backend)


## How To Setup Project

If working in eclipse or intelliJ, you need to  import the project as an existing ***maven*** project to ensure your IDE of choice can properly read the file structure and dependencies.


run the following command to install all dependencies

```bash
mvn install 
``` 

```bash
mvn dependency:resolve
```

### Adding Dependencies IntelliJ
To add a dependency from Maven:
Open your pom.xml file.
Locate the <dependencies> block
add the desired <dependency> block.
Save the file.
In IntelliJ, right-click the project and select Maven > Reload Project/Sync Project to update dependencies.


## How To Run Project

Run ReservationClient.java to start the frontend of the application.

Run ReservationServer.java to start the backend of the application.