CREATE TABLE IF NOT EXISTS events (
    id INTEGER PRIMARY KEY,
    specific_calendar_event_id TEXT,
    general_calendar_event_id TEXT,
    name TEXT NOT NULL,
    start TEXT NOT NULL,
    end TEXT NOT NULL,
    location TEXT NOT NULL,
    link TEXT NOT NULL,
    event_type TEXT NOT NULL
)