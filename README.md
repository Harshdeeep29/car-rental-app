# 🚗 Car Rental App

A full-stack car rental application built with:
- **Spring Boot** (backend, PostgreSQL)
- **React.js** (separate frontends for Admin and Client)
- **Docker + Docker Compose** for containerization

---

## 📂 Project Structure

  Car-Rental-App/
│── backend/               # Spring Boot backend (REST API, PostgreSQL)
│   │── src/               # Java source code
│   │── pom.xml            # Maven dependencies
│   │── application.properties  # Spring Boot config
│
│── clientfrontend/         # React frontend for Client users
│   │── public/             # Static assets
│   │── src/                # React components & logic
│   │── package.json        # Dependencies & scripts
│
│── carrentfrontend/        # React frontend for Admin users
│   │── public/             # Static assets
│   │── src/                # React components & logic
│   │── package.json        # Dependencies & scripts
│
│── docker-compose.yml      # Multi-container Docker setup
│── .env.example            # Example environment variables
│── .gitignore              # Ignored files/folders (logs, node_modules, build, target)
│── README.md               # Project documentation
