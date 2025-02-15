Digital Wallet Service
This project is a Digital Wallet Service designed to facilitate the creation and management of financial wallets, ensuring idempotency, consistency, and reliability of operations.

Project Overview
The service provides:

Creation of Digital Wallets: Prevents duplicate wallet creation using an Idempotency-Key.
In-Memory Persistence: Utilizes the H2 Database for lightweight development and testing.
Swagger Integration: Interactive API documentation for easy testing and integration.
📄 Documentation
For a detailed explanation of the design decisions, including functional and non-functional requirements, trade-offs, and technical choices, refer to the documentation PDF located in the docs folder.

📂 Access the Documentation:
vitormarcarvalho-gmail.com\digitalwallet\docs

[Documentation_Options_Design.pdf](https://github.com/user-attachments/files/18172736/Documentation_Options_Design.pdf)

[Documentation_Time_Limitations.pdf](https://github.com/user-attachments/files/18172735/Documentation_Time_Limitations.pdf)


🚀 Endpoints Overview
Base URL: http://localhost:8080/api/v1
Method	Endpoint	Description
POST	/wallets	Creates a digital wallet
GET	/h2-console	Access H2 database console
GET	/swagger-ui.html	Access Swagger API documentation
⚙️ Setup Instructions
To set up and run the project locally:

Clone the repository:

bash
Copiar código
git clone https://github.com/your-username/your-repo.git
cd your-repo
Build and Run the application:

bash
Copiar código
mvn spring-boot:run
Access the services:

Swagger UI: http://localhost:8080/swagger-ui.html
H2 Console: http://localhost:8080/h2-console
🤝 Contributing
Contributions are welcome! If you'd like to improve or expand the project, feel free to open a pull request or issue.

