### How to run in docker ?

Follow the steps to run in docker.

1. Download zip of project.
2. Extract zip and cd ClususInformationSystem.
3. Build the package using `mvn clean package -DskipTests`.
4. Open Docker in the terminal, build the image of spring boot using as `docker build -t <nameOfImage>`
5. Pull the Postgres image from public docker hub as `docker pull postgres`
6. Check if any process is running in 8082 (spring-boot) and 5437 (postgres) as : `sudo lsof -t -i:5432`
7. If No, run the postgre image as : `docker run -d --name my-postgres10 -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=DealRequestTable     -p 5432:5432 postgres`
8. Link postgre and spring boot project as : docker run -d --name spring-boot-container-final --link my-postgres-final:postgres -p 8082:8082 springimage
9. Use command : `docker logs postgres-container` to check if sprint-boot application started or not.
10. Use any API platform to send deal request (POST) to http://localhost:8082/deals/add. 
   SAMPLE RESPONSE BODY: {
    "dealUniqueId": 123,
    "fromCurrencyIsoCode": "",
    "toCurrencyIsoCode": "EUR",
    "dealTimestamp": "2022-01-01T10:00:00",
    "dealAmount": 100.00
}

11. Check logs from `docker logs postgres-container`.

