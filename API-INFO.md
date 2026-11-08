#

- Check and use right port for the API calls

## Swagger APIs

| Module  | Type    | HTTP  | URL              | Description |
|---------|-----------------|-------|------------------------------|-------------|
| API Doc | OpenAPI | GET   | /v3/api-docs     | Swagger generated API Documentation |
| API Doc | Swagger | GET   | /swagger-ui.html | Swagger Documentation Home          |


## Auth APIs

| Module | Type            | HTTP  | URL                          | Description |
|--------|-----------------|-------|------------------------------|-------------|
| Auth   | Signin          | POST  | /api/v1/auth/signin          | Authenticate user with password and returns JWT     |
| Auth   | Signup          | POST  | /api/v1/auth/signup          | Registers new user                                  |
| Auth   | Update Password | PUT   | /api/v1/auth/update-password | Updates password after signing in                   |
| Auth   | Forgot Password | PATCH | /api/v1/auth/forgot-password | Creates and sends reset password link               |
| Auth   | Update Password using Reset Token| PUT   | /api/v1/auth/reset-password | Sets new password using reset token |


## User APIs

| Module | Type                      | HTTP  | URL                          | Description |
|--------|---------------------------|-------|------------------------------|-------------|
| User   | Get Users Info            | GET   | /api/v1/users                | Retrieves paginated list of UserProfiles with filters. Only for ADMIN role |
| User   | Get User Info by Id       | GET   | /api/v1/users/{userId}       | Gets UserProfile by userId. Only for ADMIN role, or for the user's own userId |
| User   | Replace User Info by Id   | PUT   | /api/v1/users/{userId}       | Replaces UserProfile by userId. The body must carry the full resource: every mandatory attribute must be present, else the request is rejected with `400 - Bad Request`, and any other attribute that is missing or `null` is overwritten in DB as `null`. Only for ADMIN role, or for the user's own userId. Request `id`, when present, must match `userId` |
| User   | Update User Info by Id    | PATCH | /api/v1/users/{userId}       | Updates only the supplied attributes of UserProfile by userId; an attribute that is missing or `null` keeps its value in DB. Only for ADMIN role, or for the user's own userId. Request `id`, when present, must match `userId` |
| User   | Get Current User Info     | GET   | /api/v1/users/me             | Gets UserProfile for current user |
| User   | Replace Current User Info | PUT   | /api/v1/users/me             | Replaces UserProfile for current user. Same body and validation rules as Replace User Info by Id |
| User   | Update Current User Info  | PATCH | /api/v1/users/me             | Updates only the supplied attributes of UserProfile for current user |


- For `curl` & `json` request/response samples:
    - See [CURL-INFO.md](./CURL-INFO.md)
