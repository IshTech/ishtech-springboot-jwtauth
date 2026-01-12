## User Sign-up

```sh
curl --location 'http://localhost:8080/api/v1/auth/signup' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "muneer@example.com",
    "password": "Test#1234",
    "passwordConfirm": "Test#1234",
    "firstName": "Muneer",
    "lastName": "Syed",
    "acceptTermsConditions": true,
    "lang": "en"
}'

```

##  User  sign-in

```sh
curl --location 'http://localhost:8080/api/v1/auth/signin' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "muneer@example.com",
    "password": "Test#1234"
}'

```
