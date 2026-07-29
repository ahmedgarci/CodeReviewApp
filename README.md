# Code Review Platform

A collaborative code review platform built with **Spring Boot**, **React**, **PostgreSQL**, and **RabbitMQ**. The application allows developers to create projects, invite collaborators, submit source code for review, and receive automated static analysis results.

> **Project Status:** 🚧 Under Active Development

This project is currently **not finished**. The core backend, frontend, authentication, project management, and submission workflow are implemented. I am currently developing the asynchronous **Worker Service** responsible for repository cloning, code compilation, SonarQube analysis, and issue processing. Some infrastructure and configuration issues related to the worker are still being resolved.

---

# Features

## Authentication

* JWT-based authentication
* Secure login and registration
* Spring Security integration

## Project Management

* Create projects
* Invite users to collaborate
* Manage project members

## Code Submissions

* Upload source code
* Assign one or more reviewers
* Attach labels to submissions
* Track submission status

## Static Analysis

* Asynchronous analysis using RabbitMQ
* SonarQube integration
* Automatic issue extraction
* Display code quality issues for each submission

---

# Architecture

<img width="6325" height="2739" alt="Sans-titre-2025-09-15-0046" src="https://github.com/user-attachments/assets/d6cd8cda-fa8a-4a65-97c6-db38b9ad576f" />


# Technologies

### Backend

* Java
* Spring Boot
* Spring Security
* Spring Data JPA
* jOOQ
* RabbitMQ
* Flyway

### Frontend

* React
* Material UI
* Axios

### Database

* PostgreSQL

### DevOps

* Docker
* Docker Compose

### Code Quality

* SonarQube

---

# Database

Main entities:

* Users
* Projects
* Project Members
* Invitations
* Submissions
* Submission Reviewers
* Labels
* Files
* Issues
  
<img width="2870" height="1504" alt="Untitled (1)" src="https://github.com/user-attachments/assets/d9dc44e0-8456-48fa-b117-5eabf59c2f7b" />


---

# Current Progress

### Completed

* User authentication
* Project management
* Invitations
* Member management
* File upload
* Submission creation
* Reviewer assignment
* Label management
* Database design
* RabbitMQ messaging
* Frontend interface

### In Progress

* Worker Service
* Repository cloning
* SonarQube execution
* Issue persistence
* Worker status updates

### Planned

* Notifications
* Dashboard improvements
* CI/CD deployment

---

# Known Issues

The project is still under development.

Current work focuses on the Worker Service. Some configuration issues remain, particularly around:

* RabbitMQ integration
* SonarQube execution
* Docker configuration
* Worker environment setup

These are expected development-stage issues and will be resolved as the worker implementation is completed.

---


# Screenshots

---

# Future Improvements
* Multi-language analysis support


If you'd like, I can also Create a GitHub-style README with badges, screenshots placeholders, a table of contents, and a cleaner layout similar to popular open-source repositories.
