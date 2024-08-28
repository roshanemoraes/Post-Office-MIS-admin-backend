# Post-office MIS with Postman Assistant Mobile Application

## Project ID: PID6

### Mentor: Prof. Dulani Meedeniya

---

## Project Overview

The **Post-office MIS with Postman Assistant Mobile Application** is a comprehensive solution designed to modernize and streamline the operations of a post office. The system facilitates various postal services, including normal post, fast track courier, money orders, mail distribution, and more. The application includes an admin panel for post office staff and a mobile application for postmen to assist in their daily tasks.

### Key Features

- **Address Verification**: Validates and verifies addresses to ensure accuracy, reducing delivery errors.
- **Mail Sorting**: Assists in sorting mail items based on ZIP codes, addresses, weights, sizes, and delivery routes.
- **Barcode Generation**: Generates and prints barcodes for mail items, ensuring efficient tracking and processing.
- **Tracking and Tracing**: Provides real-time tracking of mail items for both customers and postal staff.
- **Delivery Route Optimization**: Optimizes delivery routes to enhance efficiency, reduce costs, and minimize delivery time.
- **Electronic Data Interchange (EDI)**: Facilitates electronic communication and data exchange with vendors, customers, and partner postal services.
- **Customer Management**: Manages customer information, including addresses, contact details, and transaction history.
- **Postal Rates Calculation**: Calculates postage rates based on item weight, dimensions, destination, and delivery speed.
- **Return Mail Management**: Manages undeliverable mail, including return-to-sender processes and address updates.
- **Financial Management**: Tracks financial activities, including invoicing, payment processing, and revenue management.
- **Reporting and Analytics**: Provides comprehensive reporting and analytics for operational performance monitoring.
- **Integration with External Systems**: Integrates with CRM, inventory management, and accounting software.
- **Bulk Mailing**: Supports bulk mail handling, including importing mailing lists, printing labels, and applying bulk discounts.
- **Postage Printing**: Facilitates the printing of postage labels and receipts.
- **Security and Compliance**: Ensures data security and compliance with industry standards and postal regulations.

## Technologies Used

- **Backend**: Spring Boot
- **Frontend**: React.js
- **Database**: MongoDB
- **Real-time Location Tracking**: Firebase Realtime Database

## Project Structure

```plaintext
post-office-mis/
├── backend/
│   ├── src/
│   ├── pom.xml
│   └── ...
├── frontend/
│   ├── src/
│   ├── public/
│   ├── package.json
│   └── ...
├── README.md
└── ...

Backend
The backend is built using Spring Boot and handles the core business logic, database interactions, and REST API endpoints.

Spring Boot: Used for creating RESTful services and managing application logic.
MongoDB: Acts as the primary database, storing all necessary data for the application.
Realtime Location Tracking
For real-time tracking of postmen, Firebase Realtime Database is utilized. This allows the admin to monitor the current locations of postmen during their delivery routes.

Backend Setup
1.Navigate to the backend directory:
  cd backend
2.Install dependencies and build the project:
  mvn clean install
3.Run the Spring Boot application:
  mvn spring-boot:run

Acknowledgements
Thanks to Prof. Dulani Meedeniya for mentorship and guidance throughout the project.
