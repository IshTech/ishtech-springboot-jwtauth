CREATE TABLE ${audit_schema_name}.t_user_profile_aud (
  id             BIGINT        NOT NULL,
  rev            BIGINT        NOT NULL,
  revtype        SMALLINT      NOT NULL,
  rev_timestamp  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  first_name     VARCHAR(255)      NULL,
  middle_name    VARCHAR(255)      NULL,
  last_name      VARCHAR(255)      NULL,
  nick_name      VARCHAR(255)      NULL,
  title          VARCHAR(255)      NULL,
  prefix         VARCHAR(255)      NULL,
  suffix         VARCHAR(255)      NULL,
  default_lang   VARCHAR(2)        NULL,
  is_active      BOOLEAN           NULL,
  description    TEXT              NULL,
  PRIMARY KEY(id, rev)
);
