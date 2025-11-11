CREATE TABLE categories
(
    id      UUID PRIMARY KEY,
    user_id UUID         NOT NULL,
    name    VARCHAR(100) NOT NULL,
    color   VARCHAR(20),
    CONSTRAINT uq_category_user_name UNIQUE (user_id, name)
);

