## This is documentaion of Test Classes. 

### DealRequestControllerTest

This class contains test cases for the DealRequestController class. The tests use Mockito to mock the service layer and verify that the controller is calling the service methods with the correct parameters.

Methods:

    `testAddNewDealTransactionSuccess()` - tests the scenario when a new deal transaction is successfully added to the system by the controller.
    `testAddNewDealTransactionWithMissingFields()` - tests the scenario when a new deal transaction is missing required fields and an error response is returned by the controller.
    `testAddNewDealTransactionWithInvalidCurrencyCodes()` - tests the scenario when the currency codes in the new deal transaction are not three characters long and an error response is returned by the controller.
    `testAddNewDealTransactionWithInvalidDealAmount()` - tests the scenario when the deal amount in the new deal transaction is not a positive decimal number and an error response is returned by the controller.
    `testAddNewDealTransactionWithDuplicateDealUniqueId()` - tests the scenario when the unique ID in the new deal transaction is already present in the system and an error response is returned by the controller.
    
    
 ---
### DealRequestServiceImplTest
This class contains test cases for the DealRequestServiceImpl class. The tests use Mockito to mock the repository layer and verify that the service methods are working correctly.

Methods:

    `addNewDealTransaction_ShouldThrowInvalidDealTransactionException_WhenRequiredFieldsAreMissing()` - tests the scenario when a new deal transaction is missing required fields and an InvalidDealTransactionException is thrown by the service method.
    `addNewDealTransaction_ShouldThrowInvalidDealTransactionException_WhenCurrencyCodesAreNotThreeCharactersLong()` - tests the scenario when the currency codes in the new deal transaction are not three characters long and an InvalidDealTransactionException is thrown by the service method.
    `addNewDealTransaction_ShouldThrowInvalidDealTransactionException_WhenDealAmountIsNotAPositiveDecimalNumber()` - tests the scenario when the deal amount in the new deal transaction is not a positive decimal number and an InvalidDealTransactionException is thrown by the service method.
    `addNewDealTransaction_ShouldThrowDuplicateDealTransactionException_WhenDealUniqueIdAlreadyExists()` - tests the scenario when the unique ID in the new deal transaction is already present in the system and a DuplicateDealTransactionException is thrown by the service method.
    `addNewDealTransaction()` - tests the scenario when a new deal transaction is successfully added to the system by the service method.
