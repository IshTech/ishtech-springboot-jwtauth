CREATE TABLE ${audit_schema_name}.revinfo (
  rev      BIGINT AUTO_INCREMENT PRIMARY KEY,
  revtstmp BIGINT NULL,
  user_id  BIGINT NULL
);
