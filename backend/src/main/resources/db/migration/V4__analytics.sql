CREATE TABLE analytics_records (
    id BIGSERIAL PRIMARY KEY,
    location_id BIGINT NOT NULL REFERENCES campus_locations(id),
    category_id BIGINT NOT NULL REFERENCES categories(id),
    bucket_start TIMESTAMPTZ NOT NULL,
    report_count INTEGER NOT NULL DEFAULT 0,
    confirmation_count INTEGER NOT NULL DEFAULT 0,
    avg_crowd_level DOUBLE PRECISION,
    avg_wait_minutes DOUBLE PRECISION,
    max_severity INTEGER,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    UNIQUE(location_id, category_id, bucket_start)
);

CREATE TABLE activity_baselines (
    location_id BIGINT NOT NULL REFERENCES campus_locations(id),
    day_of_week INTEGER NOT NULL,
    bucket_of_day INTEGER NOT NULL,
    mean DOUBLE PRECISION NOT NULL,
    std DOUBLE PRECISION NOT NULL,
    sample_count INTEGER NOT NULL,
    computed_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    PRIMARY KEY(location_id, day_of_week, bucket_of_day)
);

CREATE TABLE model_runs (
    id BIGSERIAL PRIMARY KEY,
    model_name VARCHAR(100) NOT NULL,
    version VARCHAR(50) NOT NULL,
    trained_at TIMESTAMPTZ NOT NULL,
    training_rows INTEGER NOT NULL,
    metrics JSONB
);

CREATE TABLE predictions (
    id BIGSERIAL PRIMARY KEY,
    location_id BIGINT NOT NULL REFERENCES campus_locations(id),
    target_start TIMESTAMPTZ NOT NULL,
    predicted_crowd_level DOUBLE PRECISION,
    high_crowd_probability DOUBLE PRECISION,
    estimated_wait_minutes DOUBLE PRECISION,
    model_run_id BIGINT REFERENCES model_runs(id),
    generated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    UNIQUE(location_id, target_start)
);
