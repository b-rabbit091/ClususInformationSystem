## Here is the documentation of all the classes used for only development (not testing).

### DealRequest

This class is a POJO that represents a deal request. It contains fields for the unique ID, from currency ISO code, to currency ISO code, deal timestamp, and deal amount.

---

### DealRequestController

This class is a Spring MVC controller that handles HTTP requests related to deal requests. It is responsible for parsing incoming request payloads and forwarding them to the appropriate service methods for processing.
Methods:

    `addNewDealTransaction(DealRequest dealRequest)`: This method handles HTTP POST requests to the "/deal" endpoint and is responsible for adding a new deal transaction to the database. It first checks that the request contains all the required fields and that the currency codes are valid. If the check passes, it forwards the request to the DealRequestService to add the deal to the database.

    `getDealById(Long dealUniqueId)`: This method handles HTTP GET requests to the "/deal/{dealUniqueId}" endpoint and is responsible for retrieving a specific deal transaction from the database by its unique ID.

    `getAllDeals()`: This method handles HTTP GET requests to the "/deals" endpoint and is responsible for retrieving all deal transactions from the database.
    
    
---
    
### DealRequestServiceImpl

This class is the implementation of the DealRequestService interface and is responsible for processing business logic related to deal requests. It communicates with the DealRepository to perform CRUD operations on deal transactions in the database.
Methods:

    `addNewDealTransaction(DealRequest dealRequest)`: This method adds a new deal transaction to the database after performing validation checks. It first checks that the deal transaction contains all the required fields and that the currency codes are valid. If the check passes, it checks if a deal with the same unique ID already exists in the database. If not, it saves the deal transaction to the database using the DealRepository.

    `getDealById(Long dealUniqueId)`: This method retrieves a specific deal transaction from the database by its unique ID using the DealRepository.

    `getAllDeals()`: This method retrieves all deal transactions from the database using the DealRepository.

    `updateDealTransaction(DealRequest dealRequest)`: This method updates an existing deal transaction in the database after performing validation checks. It first checks that the deal transaction contains all the required fields and that the currency codes are valid. If the check passes, it checks if a deal with the same unique ID already exists in the database. If so, it updates the deal transaction in the database using the DealRepository.

    `deleteDealTransaction(Long dealUniqueId)`: This method deletes a specific deal transaction from the database by its unique ID using the DealRepository.
    
 ---
### DealRepository

This interface extends the Spring JpaRepository and is responsible for performing CRUD operations on deal transactions in the database.
Methods:

    `existsByDealUniqueId(Long dealUniqueId)`: This method checks if a deal with the given unique ID exists in the database
