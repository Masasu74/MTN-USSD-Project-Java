-- Create USSD Sessions table
CREATE TABLE ussd_sessions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(20) NOT NULL,
    current_state VARCHAR(50),
    selected_bundle_id BIGINT,
    last_input VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    INDEX idx_session_id (session_id),
    INDEX idx_phone_number (phone_number),
    INDEX idx_expires_at (expires_at),
    INDEX idx_is_active (is_active)
);

-- Add foreign key constraint to bundles table
ALTER TABLE ussd_sessions 
ADD CONSTRAINT fk_ussd_session_bundle 
FOREIGN KEY (selected_bundle_id) REFERENCES bundles(id);

