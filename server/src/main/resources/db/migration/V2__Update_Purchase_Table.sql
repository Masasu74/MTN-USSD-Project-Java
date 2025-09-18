-- Migration script to update Purchase table for foreign key relationship
-- This script removes the old bundle detail columns and adds the foreign key

-- First, drop the old columns that are no longer needed
ALTER TABLE mtn_purchases DROP COLUMN IF EXISTS bundle_name;
ALTER TABLE mtn_purchases DROP COLUMN IF EXISTS bundle_price;
ALTER TABLE mtn_purchases DROP COLUMN IF EXISTS bundle_data;
ALTER TABLE mtn_purchases DROP COLUMN IF EXISTS bundle_minutes;
ALTER TABLE mtn_purchases DROP COLUMN IF EXISTS bundle_sms;
ALTER TABLE mtn_purchases DROP COLUMN IF EXISTS bundle_valid_until;
ALTER TABLE mtn_purchases DROP COLUMN IF EXISTS bundle_is_weekend;

-- Add the foreign key column
ALTER TABLE mtn_purchases ADD COLUMN bundle_id INT NOT NULL;

-- Add foreign key constraint
ALTER TABLE mtn_purchases ADD CONSTRAINT fk_purchase_bundle 
    FOREIGN KEY (bundle_id) REFERENCES mtn_bundles(id);


