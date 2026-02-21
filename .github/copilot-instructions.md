# Copilot Instructions for Apache Fortress Demo

## Project Overview

This is a Java web application that demonstrates end-to-end security using:
- **Apache Fortress** (RBAC security framework) with LDAP backend
- **Apache Wicket** (component-based web framework)
- **Spring Framework** (dependency injection, JDBC)
- **MyBatis** (SQL persistence)
- **MariaDB** (relational database)

The app is packaged as a WAR and deployed on Apache Tomcat. It showcases role-based access control (RBAC) across three protected pages (`Page1`, `Page2`, `Page3`) with full SSL/TLS security.

## Repository Layout

```
src/main/java/com/mycompany/   # Wicket pages, models, DAO, app wiring
src/main/resources/            # Spring context, fortress.properties, log4j2, ehcache config
src/main/webapp/               # Static assets, WEB-INF/web.xml
src/test/java/com/mycompany/   # Selenium integration tests
src/test/resources/            # Test keystores, LDIF fixtures
```

## Build

```bash
# Compile and package the WAR
mvn clean package

# Generate Javadoc (contains detailed installation guides)
mvn javadoc:javadoc
```

Requires **Java 17+** and **Maven 3+**.

## Testing

Tests are Selenium integration tests that require a running, fully configured instance of the application (Tomcat + LDAP + MariaDB). They are **not** runnable in a bare CI environment without that infrastructure.

```bash
mvn test -Dtest=ApacheFortressDemoSeleniumITCase
```

To load the RBAC policy into the LDAP server during the test phase:

```bash
mvn test -Dload.file=apache-fortress-demo-load-policy.xml
```

## Code Style & Conventions

- Java source targets **Java 11** bytecode (compiler plugin setting) with Java 17 runtime.
- Follow existing package structure: all application classes live under `com.mycompany`.
- Wicket pages extend `MyBasePage`; new pages must be registered in `WicketApplication.java`.
- Database access goes through DAO classes in `com.mycompany.dao`; use MyBatis mappers (XML in the same package).
- Spring beans are wired in `src/main/resources/applicationContext.xml` — avoid annotation-based Spring config to stay consistent with the existing style.
- Use `GlobalIds` for shared string constants instead of inline literals.
- Logging uses **SLF4J + Log4j2**; use `LogManager.getLogger(ClassName.class)` following existing patterns.

## Security Notes

- Access control is enforced by Apache Fortress; do **not** add ad-hoc permission checks in page code.
- SSL/TLS is required for both the LDAP connection and the Tomcat connector; see the Javadoc guides for certificate setup.
- Fortress properties (`fortress.properties`) must never be committed with real credentials.
- The Tomcat manager credentials in `pom.xml` are intentionally demo-only values.
