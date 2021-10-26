1. Which technologies did you choose to implement the application? Why did you choose them?

	-> spring-boot-starter-web : facilitates building REST applications
	
	-> h2database : convinient in-memory database
	
	-> lombok : helps to reduce code, makes it more clear
	
	-> swagger : describes API
	
	-> hibernate : ORM tool (mapping an object-oriented domain model to a relational database)
	
	-> spring-boot-starter-validation: helps to validate requests
	
	
2. What was the most difficult part of the task?

	-> The task was rather standard difficulty. It's hard to list the most difficult element. 
	Some elements were time-consuming - for example, preparing the appropriate DTOs, handling errors 
	or writing tests as you write the code.

3. How would you improve the quality of the diagnosis algorithm?

	-> We can use Disease.prevalence pool. If two diseases have the same symptoms and the probability of these symptoms, the more common disease should get the higher the score
	
	-> We can collect addidional data, such as
		
		- region in which the user lives (some diseases are local, not global)
		
		- the user's clinical history (certain diseases may be more likely to occur in the case of having or passing on other diseases)
		
		- serious diseases in the family (they will give information about being in a risk group)
		
		- user's age (it will give information about being in a risk group)

-----------------------
Aplikacja działa na defaultowym porcie 8080

By podejrzeć bazę danych należy po uruchomieniu aplikacji otworzyć stronę: http://localhost:8080/h2-console/

Parametry bazy danych:

Driver Class: org.h2.Driver

JDBC URL: jdbc:h2:mem:testdb

User Name: sa

Password:
