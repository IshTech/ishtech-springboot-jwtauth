-- CREATE DATABASE ishtech_dev_db;

\connect ishtech_dev_db;

-- ===== Common =====
CREATE USER ishtech_auth_dev_user        PASSWORD 'ishtech_auth_dev_pass'        NOSUPERUSER;
CREATE USER ishtech_auth_dev_flyway_user PASSWORD 'ishtech_auth_dev_flyway_pass' NOSUPERUSER;

GRANT CONNECT ON DATABASE ishtech_dev_db TO ishtech_auth_dev_user;
GRANT CONNECT ON DATABASE ishtech_dev_db TO ishtech_auth_dev_flyway_user;

CREATE SCHEMA ishtech_auth_dev_schema;
CREATE SCHEMA ishtech_auth_dev_aud_schema;

-- ===== App User ===== 
GRANT USAGE ON SCHEMA public                      TO ishtech_auth_dev_user;
GRANT USAGE ON SCHEMA ishtech_auth_dev_schema     TO ishtech_auth_dev_user;
GRANT USAGE ON SCHEMA ishtech_auth_dev_aud_schema TO ishtech_auth_dev_user;

-- For existing tables
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA ishtech_auth_dev_schema     TO ishtech_auth_dev_user;
GRANT SELECT, INSERT                 ON ALL TABLES IN SCHEMA ishtech_auth_dev_aud_schema TO ishtech_auth_dev_user;

-- For existing sequences
GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA ishtech_auth_dev_schema     TO ishtech_auth_dev_user;
GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA ishtech_auth_dev_aud_schema TO ishtech_auth_dev_user;

-- For future tables and sequences created by ishtech_auth_dev_flyway_user
ALTER DEFAULT PRIVILEGES FOR ROLE ishtech_auth_dev_flyway_user IN SCHEMA ishtech_auth_dev_schema     GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO ishtech_auth_dev_user;
ALTER DEFAULT PRIVILEGES FOR ROLE ishtech_auth_dev_flyway_user IN SCHEMA ishtech_auth_dev_aud_schema GRANT SELECT, INSERT                 ON TABLES TO ishtech_auth_dev_user;

ALTER DEFAULT PRIVILEGES FOR ROLE ishtech_auth_dev_flyway_user IN SCHEMA ishtech_auth_dev_schema     GRANT USAGE, SELECT, UPDATE ON SEQUENCES TO ishtech_auth_dev_user;
ALTER DEFAULT PRIVILEGES FOR ROLE ishtech_auth_dev_flyway_user IN SCHEMA ishtech_auth_dev_aud_schema GRANT USAGE, SELECT, UPDATE ON SEQUENCES TO ishtech_auth_dev_user;

-- ===== Flyway User =====
GRANT USAGE         ON SCHEMA public                      TO ishtech_auth_dev_flyway_user;
GRANT USAGE, CREATE ON SCHEMA ishtech_auth_dev_schema     TO ishtech_auth_dev_flyway_user;
GRANT USAGE, CREATE ON SCHEMA ishtech_auth_dev_aud_schema TO ishtech_auth_dev_flyway_user;

-- For existing tables
GRANT SELECT ON ALL TABLES IN SCHEMA ishtech_auth_dev_schema     TO ishtech_auth_dev_flyway_user;
GRANT SELECT ON ALL TABLES IN SCHEMA ishtech_auth_dev_aud_schema TO ishtech_auth_dev_flyway_user;

-- For existing sequences
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA ishtech_auth_dev_schema     TO ishtech_auth_dev_flyway_user;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA ishtech_auth_dev_aud_schema TO ishtech_auth_dev_flyway_user;

-- For future tables and sequences created by ishtech_auth_dev_flyway_user
ALTER DEFAULT PRIVILEGES FOR ROLE ishtech_auth_dev_flyway_user IN SCHEMA ishtech_auth_dev_schema     GRANT SELECT ON TABLES TO ishtech_auth_dev_flyway_user;
ALTER DEFAULT PRIVILEGES FOR ROLE ishtech_auth_dev_flyway_user IN SCHEMA ishtech_auth_dev_aud_schema GRANT SELECT ON TABLES TO ishtech_auth_dev_flyway_user;

ALTER DEFAULT PRIVILEGES FOR ROLE ishtech_auth_dev_flyway_user IN SCHEMA ishtech_auth_dev_schema     GRANT USAGE, SELECT ON SEQUENCES TO ishtech_auth_dev_flyway_user;
ALTER DEFAULT PRIVILEGES FOR ROLE ishtech_auth_dev_flyway_user IN SCHEMA ishtech_auth_dev_aud_schema GRANT USAGE, SELECT ON SEQUENCES TO ishtech_auth_dev_flyway_user;
