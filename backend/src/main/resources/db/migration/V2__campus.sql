CREATE TABLE campus_locations (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL UNIQUE,
    type VARCHAR(50) NOT NULL,
    parent_id BIGINT REFERENCES campus_locations(id),
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL,
    capacity INTEGER,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_locations_parent_type ON campus_locations(parent_id, type);

CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    display_name VARCHAR(100) NOT NULL,
    spatial_scope VARCHAR(20) NOT NULL DEFAULT 'LOCATION',
    window_minutes INTEGER NOT NULL DEFAULT 30,
    min_reporters INTEGER NOT NULL DEFAULT 3,
    half_life_minutes INTEGER NOT NULL DEFAULT 60,
    saturation_count INTEGER NOT NULL DEFAULT 8
);

CREATE TABLE location_subscriptions (
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    location_id BIGINT NOT NULL REFERENCES campus_locations(id) ON DELETE CASCADE,
    min_severity INTEGER NOT NULL DEFAULT 2,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    PRIMARY KEY (user_id, location_id)
);
