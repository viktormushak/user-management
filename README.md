Sub project for managing users

#Running docker swarm:

For minimal setting and run docker swarm:

1) Set number of service replicas in docker-compose.yml file

        services:
            web:
                deploy:
                    replicas: 1
                    
2) Change ports if needed

        services:
             web:
                 ports:
                    - "8081:8081"

3) Run:

        mvn package
        docker build --tag=user-management .
        docker stack deploy --compose-file docker-compose.yml user-management 
      
4) Visit [http://localhost:3500/](http://localhost:3500/) to see visualization of started services
   or [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html) to see the Swagger page 
   (Warning: be aware that in case you've changed a port in scope of point 2) then you need to use it instead of 8081 in the link)