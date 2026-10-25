#

Known issues that have been confirmed by testing but not yet fixed. Each entry has enough detail to reproduce and fix without re-investigating.

---

## 1. `PUT /api/v1/users/{userId}` wipes fields that are absent from the request body

**Status:** Open
**Impact:** High — silent data loss on a documented API. A partial update (the exact request shown in `CURL-INFO.md`) clears `default_lang` and flips `t_user_profile.is_active` to `false`, with a `200 OK` and no warning.
**Affects:** `UserProfileMapper.toEntity(UserProfileDto, @MappingTarget UserProfile)` in `ishtech-springboot-jwtauth-lib`, used by `UserProfileServiceImpl.updateAndMapToDto(...)`

### Description

The update API is meant to be a partial update — `CURL-INFO.md` documents a request body containing only `id`, `firstName` and `lastName`. Any field not present in that body is deserialized as `null` in `UserProfileDto` and then written as `null` (or the primitive default) onto the loaded entity, overwriting the stored value.

Two confirmed symptoms from a single documented call:

1. **`default_lang` is cleared.** A user signed up with `"lang": "en"` has `default_lang = 'en'`. After one rename it is `NULL`.
2. **`t_user_profile.is_active` becomes `false`.** The profile row's `is_active` goes from `true` to `false`, so the user is effectively deactivated by a rename.

Symptom 2 also leaves the two active flags disagreeing: `t_user.is_active` stays `true` (it is a different column on a different table and is not touched by this mapper), while `t_user_profile.is_active` is now `false`. Any code reading one flag will disagree with code reading the other.

Both symptoms are genuinely persisted — this is not a stale-read or caching artifact. Reproduced on two independent databases: the local dev PostgreSQL and a throwaway `docker compose` PostgreSQL created from scratch.

### Steps to reproduce

```sh
# 1. signup
curl --request POST --location 'http://localhost:8080/api/v1/auth/signup' \
--header 'Content-Type: application/json' \
--data-raw '{"email":"x@example.com","password":"Test#1234","passwordConfirm":"Test#1234","firstName":"Api","lastName":"Test","acceptTermsConditions":true,"lang":"en"}'

# 2. signin, keep the access_token
curl --request POST --location 'http://localhost:8080/api/v1/auth/signin' \
--header 'Content-Type: application/json' \
--data-raw '{"email":"x@example.com","password":"Test#1234"}'

# 3. read it back -> "defaultLang":"en", "isActive":true
curl --request GET --location 'http://localhost:8080/api/v1/users/1' \
--header 'Authorization: Bearer <ACCESS_TOKEN>'

# 4. rename, exactly as documented in CURL-INFO.md
curl --request PUT --location 'http://localhost:8080/api/v1/users/1' \
--header 'Authorization: Bearer <ACCESS_TOKEN>' \
--header 'Content-Type: application/json' \
--data-raw '{"id":1,"firstName":"New Api","lastName":"New Test"}'
```

Expected after step 4: `defaultLang` still `en`, `isActive` still `true`.
Actual: `"defaultLang":null`, `"isActive":false`.

Verify in the database:

```sql
SELECT u.id, u.email, u.is_active AS user_active, p.is_active AS profile_active, p.default_lang
  FROM ishtech_auth_dev_schema.t_user u
  JOIN ishtech_auth_dev_schema.t_user_profile p ON p.id = u.id
 ORDER BY u.id;
```

Rows that have been through the update show `profile_active = f` and `default_lang` empty, while never-updated rows show `t` and `en`.

### Likely cause

`UserProfileMapper.toEntity(...)` sets the null-handling strategy on a single property instead of on the whole bean:

```java
@InheritInverseConfiguration(name = "toBriefDto")
@Mapping(source = "id", target = "id", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
UserProfile toEntity(UserProfileDto dto, @MappingTarget UserProfile entity);
```

`NullValuePropertyMappingStrategy.IGNORE` here applies only to `id`. Every other property inherited from `toBriefDto` (`middleName`, `nickName`, `title`, `prefix`, `suffix`, `defaultLang`, and the active flag) falls back to MapStruct's default `SET_TO_NULL`, so a null in the DTO overwrites whatever the loaded entity held. For the boolean active flag, a null `Boolean` mapped onto a primitive target lands as `false`, which is why the rename deactivates the profile.

`UserProfileServiceImpl.updateAndMapToDto(...)` loads the entity and passes it as `@MappingTarget`, which is correct — the merge semantics are what is wrong, not the lookup.

There is currently **no test coverage for the update path**. `UserControllerTest` contains only read tests (`testFindUserProfileByIdByAdmin`, `testFindUserProfileByUserIdBySelf`, `testFindUserProfileByMeBySelf`), which is presumably why this was never caught.

### Suggested fix

1. Move the null-handling to bean level on `toEntity`, e.g. `@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)`, so absent fields leave the loaded entity untouched. Keep `id` non-overwritable.
2. Decide deliberately whether `isActive` should be updatable through this API at all — if not, ignore it explicitly in the mapping rather than relying on null handling.
3. Clarify the relationship between `t_user.is_active` and `t_user_profile.is_active`; today they can silently diverge.
4. Add tests: update with only `firstName`/`lastName` preserves `defaultLang` and the active flag; the `PUT` response matches a subsequent `GET`; the persisted row matches the API response.

---

## 2. `PUT /api/v1/users/{userId}` for a user that doesn't exist returns `500` instead of `404`

**Status:** Open. Also noted as a TODO in `UserController.updateUserProfile`. `TEST-SUITE.md`, case 16, accepts `500` until this is fixed.
**Impact:** Low — an `ADMIN` who updates a user ID that doesn't exist gets `500 - Internal Server Error` instead of `404 - Not Found`, and the application logs an `ERROR` with a stack trace.
**Affects:** `UserController.updateUserProfile(...)` in `ishtech-springboot-jwtauth-api`, through `UserProfileServiceImpl.updateAndMapToDto(...)` in `ishtech-springboot-jwtauth-lib`. Seen in this repo's own application, `ishtech-springboot-jwtauth-web`. Applications whose own `@ControllerAdvice` maps `NoSuchElementException` to `404` (`springboot-books-app`, `springboot-multi-port`, `springboot-oms`) aren't affected.

### Description

`UserProfileServiceImpl.updateAndMapToDto(...)` loads the profile with `findOneByIdOrElseThrow(id)` from `ishtech-base-jpa`, which calls `Optional.orElseThrow()` and throws `java.util.NoSuchElementException: No value present` when the ID doesn't exist. Nothing in `ishtech-springboot-jwtauth` maps that exception to an HTTP status (`AuthExceptionHandler` doesn't handle it, and its `@ControllerAdvice` is commented out), so Spring Boot returns `500`.

Application log:

```
java.util.NoSuchElementException: No value present
	at java.base/java.util.Optional.orElseThrow(Optional.java:377)
	at fi.ishtech.base.service.BaseStandardNoIdService.findOneByIdOrElseThrow(BaseStandardNoIdService.java:72)
	at fi.ishtech.springboot.jwtauth.service.impl.UserProfileServiceImpl.updateAndMapToDto(UserProfileServiceImpl.java:79)
```

`GET /api/v1/users/{userId}` loads the profile the same way (`UserProfileServiceImpl.findOneByIdAndMapToVoOrElseThrow(...)`), so it probably returns `500` for an `ADMIN` and an ID that doesn't exist too (not tested).

### Steps to reproduce

Run `ishtech-springboot-jwtauth-web` (`ishtech-springboot-jwtauth-web/README.md`), and get an `ADMIN` token as `TEST-SUITE.md` describes, then run `TEST-SUITE.md`, case 16:

```sh
curl -s -o /dev/null -w "%{http_code}\n" -X PUT "http://localhost:8080/api/v1/users/9999" \
  -H "Authorization: Bearer <ADMIN_ACCESS_TOKEN>" -H "Content-Type: application/json" \
  -d '{"id":9999,"firstName":"User3.16","lastName":"Three3.16"}'
```

Expected: `404`.
Actual: `500`, and the application log shows the `NoSuchElementException` above.

### Likely cause

`findOneByIdOrElseThrow(id)` throws a generic `NoSuchElementException`, and this library has no exception handler that maps it to `404`.

### Suggested fix

1. In `UserProfileServiceImpl`, use the overload `findOneByIdOrElseThrow(id, exceptionSupplier)` from `ishtech-base-jpa` with an exception that carries `404`, for example `new ResponseStatusException(HttpStatus.NOT_FOUND, ...)` or a library exception annotated with `@ResponseStatus(HttpStatus.NOT_FOUND)`, for both the update and the get-by-id paths. Don't add a global `@ControllerAdvice` to the library for this, because it would compete with the consuming applications' own handlers.
2. Remove the TODO in `UserController.updateUserProfile`.
3. In `TEST-SUITE.md`, change the expected result of case 16 to `404` only.
4. Add tests in `UserControllerTest` for update and get-by-id of an ID that doesn't exist, expecting `404`.
