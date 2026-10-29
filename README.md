# Bookstore

## Security

All bookstore pages and API endpoints require authentication. The login page
and Bootstrap stylesheet are public so users can sign in.

The application stores accounts in the `app_users` database table and hashes
passwords with BCrypt. On startup, these example accounts are inserted if their
usernames do not already exist:

| Username | Password | Role |
| --- | --- | --- |
| `user` | `user123` | USER |
| `admin` | `admin123` | ADMIN |

Only ADMIN users can delete books. Deletion uses a CSRF-protected POST request.
User accounts are managed internally and are not exposed through the Spring
Data REST API.

## Tests

Run the full test suite with `.\mvnw.cmd test`. Tests use an isolated in-memory
H2 database and cover the REST API plus create, search, and delete operations
for the book, category, and user repositories.