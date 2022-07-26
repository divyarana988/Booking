# For running project

-> clone the git repo

-> mvn clean install

-> install mysql in your PC

-> Go to R___ddl.sql file in project for creating database and tables in your system and run the queries and command in mysql.

-> Run the application in Intelliz idea.

-> first create user by hitting "http:localhost:8090/v1/addUser".

-> this User can be ADMIN or just USER.

-> Then create threatre by hitting "http:localhost:8090/v1/addThreatre".(only admin can create new threatres)

-> Then add movie to the threatre by hitting "http:localhost:8090/v1/addMovie".

-> Cast details can also be added for movies by hitting "http:localhost:8090/v1/addCast".

-> We can add shows for a movie in threatre by hitting "http:localhost:8090/v1/addShow".

-> We can add seats to this show by hitting "http:localhost:8090/v1/addDefaultSeat".

->We can book a/many seats in a show for a particular movie by hitting "http:localhost:8090/v1/bookSeats".

-> List of available seats in a show can be retrieved by hitting "http:localhost:8090/v1/getAvailabilityOnAShow".

-> List of show/threatres showing a particular movie in a city can be retrived by hitting "http:localhost:8090/v1/getShowsShowingMovie".

-> List of movies in a city can be get by hitting "http:localhost: 8090/v1/getMoviesByCity".

-> Information of a user can be get with "http:localhost:8090/v1/getUserDetails".

