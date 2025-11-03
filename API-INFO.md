
# API Details 

## APIs for Auth
 
<table>
<tr>
<td><b>Signin</b> - <code>HTTP POST</code></td>
<td><a href="http://localhost:8080/api/auth/signin">http://localhost:8080/api/auth/signin</a></td>
</tr>
<tr>
<td style="vertical-align:text-top">
Sample Request JSON

```json
{
    "username": "muneer@example.com",
    "password": "Test#1234"
}
```
</td>
<td style="vertical-align:text-top">
Sample Response JSON

```json
{
    "iss": "dev.jwtauth.springboot.ishtech.fi",
    "sub": "muneer@example.com",
    "iat": 1693838542156,
    "exp": 1693840342156,
    "scopes": [
        "ROLE_USER"
    ],
    "token_type": "Bearer",
    "access_token": "eyJhbGciOiJIUzI1NiJ9"
}
```
</td>
</tr>
</table>
<br>
<br>
<br>
<table>
<tr>
<td><b>Signup</b> - <code>HTTP POST</code></td>
<td><a href="http://localhost:8080/api/auth/signup">http://localhost:8080/api/auth/signup</a></td>
</tr>
<tr>
<td style="vertical-align:text-top">
Sample Request JSON

```json
{
    "email": "muneer@example.com",
    "password": "Test#1234",
    "passwordRepeat": "Test#1234",
    "firstName": "Muneer",
    "lastName": "Syed"
}
```
</td>
<td>
Http Response code is 201 - Created<br>
Response is <code>id</code> of the user signed up<br><br>
Http Reponse code - 400 - Bad Request if username / email already exists

</td>
</tr>
</table>
<br>
<br>
<br>
<table>
<tr>
<td><b>Update Password</b> - <code>HTTP PUT</code></td>
<td><a href="http://localhost:8080/api/auth/update-password">http://localhost:8080/api/auth/update-password</a></td>
</tr>
<tr>
<td style="vertical-align:text-top">
Sample Request JSON

```json
{
    "password": "Test#6789",
    "passwordConfirm": "Test#6789"
}
```
</td>
<td>
Response is <code>BLANK</code> for HTTP 200
</td>
</tr>
</table>
