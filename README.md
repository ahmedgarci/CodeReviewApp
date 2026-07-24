# 🔍 Code Review App

<p align="center">
  <img src="https://github.com/user-attachments/assets/e4c6cd29-85c3-45a4-aba3-29be7898bf4d" alt="Code Review App ERD" width="900"/>
</p>

<h3 align="center">
An intelligent platform for automated and collaborative code reviews
</h3>

---

## 🚀 Overview

**Code Review App** is a platform designed to simplify and improve the software review process.

It allows developers to submit their code, receive automated feedback, collaborate with reviewers, and maintain code quality through static analysis tools.

The goal of this project is to build a modern code review workflow similar to professional platforms while applying software engineering best practices.

---

# ✨ Features

## 👤 User Management
- User authentication and authorization
- JWT-based security
- User profiles

## 📁 Project Management
- Create and manage repositories/projects
- Add collaborators
- Manage project members
- Project invitations

## 🔎 Code Submission & Review

- Submit source code files with different programming languages.
- Multiple reviewer assignment
- Automatic code analysis
- Manual review workflow
- Review status tracking

## 🤖 Automated Code Review

Integration with static analysis tools:

- SonarQube integration
- Code quality analysis
- Bug detection
- Code smell detection
- Maintainability reports

## 🏷️ Smart Review Classification

- labels assignment
- Review categorization
- Review strategy pattern implementation

Examples:
- Security issues
- Performance issues
- Code quality
- Best practices

---

# 🏗️ Architecture

The project follows a clean and scalable architecture:

CodeReviewApp
├── Controller Layer
│
├── Service Layer
│
├── Repository Layer
│
├── Security Layer
│
├── Review Strategies
│
└── Database Layer


Implemented concepts:

- SOLID Principles
- Design Patterns
- Separation of Concerns
- Strategy Pattern
- Template Method Pattern

---

# 🧩 Review Strategy Design
The application uses the **Strategy Pattern** to support multiple review approaches.
