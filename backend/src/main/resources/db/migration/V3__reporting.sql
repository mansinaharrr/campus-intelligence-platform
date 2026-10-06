CREATE TABLE campus_events (
    id BIGSERIAL PRIMARY KEY,
    location_id BIGINT NOT NULL REFERENCES campus_locations(id),
    category_id BIGINT NOT NULL REFERENCES categories(id),
    source VARCHAR(20) NOT NULL,
    title VARCHAR(200) NOT NULL,
    severity INTEGER NOT NULL,
    confidence DOUBLE PRECISION NOT NULL,
    status VARCHAR(20) NOT NULL,
    started_at TIMESTAMPTZ NOT NULL,
    last_activity_at TIMESTAMPTZ NOT NULL,
    acknowledged_at TIMESTAMPTZ,
    acknowledged_by BIGINT REFERENCES users(id),
    resolved_at TIMESTAMPTZ,
    resolved_by BIGINT REFERENCES users(id),
    report_count INTEGER NOT NULL DEFAULT 0,
    confirm_count INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE reports (
    id BIGSERIAL PRIMARY KEY,
    reporter_id BIGINT NOT NULL REFERENCES users(id),
    location_id BIGINT NOT NULL REFERENCES campus_locations(id),
    category_id BIGINT NOT NULL REFERENCES categories(id),
    description TEXT NOT NULL,
    severity INTEGER NOT NULL CHECK (severity BETWEEN 1 AND 3),
    crowd_level INTEGER CHECK (crowd_level BETWEEN 1 AND 5),
    wait_minutes INTEGER,
    image_key VARCHAR(500),
    status VARCHAR(20) NOT NULL DEFAULT 'VISIBLE',
    event_id BIGINT REFERENCES campus_events(id),
    confirm_count INTEGER NOT NULL DEFAULT 0,
    disagree_count INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_reports_detection ON reports(location_id, category_id, created_at DESC);
CREATE INDEX idx_reports_recent ON reports(created_at DESC);
CREATE INDEX idx_reports_reporter ON reports(reporter_id, created_at DESC);
CREATE INDEX idx_reports_event ON reports(event_id);

CREATE TABLE report_confirmations (
    id BIGSERIAL PRIMARY KEY,
    report_id BIGINT NOT NULL REFERENCES reports(id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    vote VARCHAR(20) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    UNIQUE(report_id, user_id)
);

CREATE TABLE event_updates (
    id BIGSERIAL PRIMARY KEY,
    event_id BIGINT NOT NULL REFERENCES campus_events(id) ON DELETE CASCADE,
    update_type VARCHAR(50) NOT NULL,
    old_value TEXT,
    new_value TEXT,
    note TEXT,
    actor_id BIGINT REFERENCES users(id),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_event_updates_event_time ON event_updates(event_id, created_at);

CREATE UNIQUE INDEX uq_open_event_scope
ON campus_events(location_id, category_id, source)
WHERE status IN ('ACTIVE', 'ACKNOWLEDGED');
