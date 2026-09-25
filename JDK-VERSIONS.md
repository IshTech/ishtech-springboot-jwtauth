# JDK versions

The default and other supported JDK versions are listed in the README, section "Tech stack".

- Releases for the default JDK version have plain version numbers, for example `x.y.z`. They are built from the branches `dev` and `main`.
- Releases for another supported JDK version have the same version number with the suffix `-jdkNN`, for example `x.y.z-jdk21` for JDK 21. They are built from the branch `dev-jdkNN`, for example `dev-jdk21`, from the same code, adapted where that JDK version needs it.
- The libraries `ishtech-springboot-jwtauth-lib` and `ishtech-springboot-jwtauth-api` on Maven Central and the Docker image `muneer2ishtech/ishtech-springboot-jwtauth-web` on Docker Hub have the same version; the image tag is the version.
- Use the version that matches your JDK version. Dependency update tools may suggest the version without a suffix as newer; that version needs the default JDK version.
