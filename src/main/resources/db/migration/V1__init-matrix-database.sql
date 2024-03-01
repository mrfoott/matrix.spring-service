
CREATE TABLE category
(
    id            INT        NOT NULL       AUTO_INCREMENT,
    `version`       INT,
    category_name VARCHAR(50) NOT NULL,
    is_deleted    TIMESTAMP,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP,
    PRIMARY KEY (id)
) ;

CREATE TABLE `role`
(
    id         INT          NOT NULL        AUTO_INCREMENT,
    `version`    INT,
    role_name  VARCHAR(255) NOT NULL,
    is_deleted TIMESTAMP,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    PRIMARY KEY (id)
) ;

CREATE TABLE product
(
    id                  VARCHAR(36)    NOT NULL,
    `version`             INT,
    product_name        VARCHAR(255)   NOT NULL,
    product_description TEXT           NOT NULL,
    price               DECIMAL(19, 2) NOT NULL CHECK (price > 0),
    product_quantity    INT            NOT NULL CHECK (product_quantity >= 0),
    brand               VARCHAR(255)   NOT NULL,
    sold_quantity       INT            NOT NULL CHECK (sold_quantity >= 0),
    is_deleted          TIMESTAMP,
    category_id         INT,
    created_at          TIMESTAMP,
    updated_at          TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (category_id) REFERENCES category (id)
) ;

CREATE TABLE membership
(
    id                  INT            NOT NULL         AUTO_INCREMENT,
    `version`             INT,
    membership_rank     INT            NOT NULL CHECK (membership_rank >= 0),
    discount_percentage INT            NOT NULL,
    min_price           DECIMAL(19, 2) NOT NULL CHECK (min_price >= 0),
    max_price           DECIMAL(19, 2) NOT NULL CHECK (max_price > 0),
    is_deleted          TIMESTAMP,
    created_at          TIMESTAMP,
    updated_at          TIMESTAMP,
    PRIMARY KEY (id)
) ;

CREATE TABLE `user`
(
    id                      VARCHAR(36)  NOT NULL,
    `version`                 INT,
    user_email              VARCHAR(255) NOT NULL,
    `password`                VARCHAR(255) NOT NULL,
    full_name               VARCHAR(255) NOT NULL,
    user_phone              VARCHAR(20)  NOT NULL,
    avatar                  VARCHAR(255) NOT NULL,
    membership_point        DOUBLE       NOT NULL,
    membership_promoted_day TIMESTAMP,
    membership_expired_day  TIMESTAMP,
    is_deleted              TIMESTAMP,
    role_id                 INT,
    membership_id           INT,
    created_at              TIMESTAMP,
    updated_at              TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (role_id) REFERENCES role (id),
    FOREIGN KEY (membership_id) REFERENCES membership (id)
) ;

CREATE TABLE `cart_detail`
(
    id            VARCHAR(36) NOT NULL,
    `version`       INT,
    user_id       VARCHAR(36),
    product_id       VARCHAR(36),
    product_name        VARCHAR(255),
    item_quantity INT         NOT NULL CHECK (item_quantity >= 0),
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP,
    PRIMARY KEY (id)
) ;

CREATE TABLE receiver_info
(
    id               VARCHAR(36)  NOT NULL,
    `version`          INT,
    receiver_name    VARCHAR(255) NOT NULL,
    receiver_address VARCHAR(255) NOT NULL,
    receiver_phone   VARCHAR(20)  NOT NULL,
    is_default       INT          NOT NULL,
    is_deleted       TIMESTAMP,
    user_id          VARCHAR(36),
    created_at       TIMESTAMP,
    updated_at       TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES `user` (id)
) ;

CREATE TABLE `orders`
(
    id             VARCHAR(36)    NOT NULL,
    `version`          INT,
    total_price      DECIMAL(19, 2) NOT NULL CHECK (total_price > 0),
    shipping_fee     DECIMAL(19, 2) NOT NULL CHECK (shipping_fee >= 0),
    payment_method   VARCHAR(255)   NOT NULL,
    payment_status   VARCHAR(255)   NOT NULL,
    user_id          VARCHAR(36),
    receiver_info_id VARCHAR(36),
    created_at       TIMESTAMP,
    updated_at       TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES `user` (id),
    FOREIGN KEY (receiver_info_id) REFERENCES receiver_info (id)
) ;

CREATE TABLE product_image
(
    id                VARCHAR(36)  NOT NULL,
    `version`           INT,
    image_link        VARCHAR(255) NOT NULL,
    image_description VARCHAR(255) NOT NULL,
    is_deleted        TIMESTAMP,
    product_id        VARCHAR(36),
    created_at        TIMESTAMP,
    updated_at        TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
) ;

CREATE TABLE review
(
    id             VARCHAR(36) NOT NULL,
    `version`        INT,
    review_content TEXT        NOT NULL,
    review_rating  DOUBLE      NOT NULL CHECK (review_rating > 0),
    is_deleted     TIMESTAMP,
    user_id        VARCHAR(36),
    product_id     VARCHAR(36),
    created_at     TIMESTAMP,
    updated_at     TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES `user` (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
) ;

CREATE TABLE review_image
(
    id                VARCHAR(36)  NOT NULL,
    `version`           INT,
    user_review_image VARCHAR(255) NOT NULL,
    is_deleted        TIMESTAMP,
    review_id         VARCHAR(36),
    created_at        TIMESTAMP,
    updated_at        TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (review_id) REFERENCES review (id)
) ;

CREATE TABLE shipping
(
    id                VARCHAR(36)  NOT NULL,
    `version`           INT,
    shipping_status   VARCHAR(255) NOT NULL,
    shipping_location VARCHAR(255) NOT NULL,
    order_id          VARCHAR(36),
    created_at        TIMESTAMP,
    updated_at        TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES `orders` (id)
) ;

CREATE TABLE view_history
(
    id         VARCHAR(36) NOT NULL,
    `version`    INT,
    `view`       INT,
    user_id    VARCHAR(36),
    product_id VARCHAR(36),
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES `user` (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
) ;

CREATE TABLE order_detail
(
    id                    VARCHAR(36)    NOT NULL,
    `version`               INT,
    order_quantity        INT            NOT NULL CHECK (order_quantity > 0),
    price_at_order        DECIMAL(19, 2) NOT NULL CHECK (price_at_order > 0),
    product_name_at_order VARCHAR(255)   NOT NULL,
    order_id              VARCHAR(36),
    product_id            VARCHAR(36),
    created_at            TIMESTAMP,
    updated_at            TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES `orders` (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
) ;
