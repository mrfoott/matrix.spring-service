ALTER TABLE `orders`
ADD COLUMN `estimated_delivery_date` VARCHAR(255) AFTER `receiver_info_id`,
ADD COLUMN `delivery_date` TIMESTAMP AFTER `estimated_delivery_date`,
ADD COLUMN `bill_of_lading_code` VARCHAR(255) AFTER `delivery_date`,
ADD COLUMN `shipping_unit` VARCHAR(255) AFTER `bill_of_lading_code`;