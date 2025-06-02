CREATE TABLE provider
(
    provider_id   SERIAL PRIMARY KEY,
    provider_name VARCHAR(255) NOT NULL,
    provider_url TEXT NOT NULL,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE download
(
    download_id     SERIAL PRIMARY KEY,
    provider_id     INTEGER REFERENCES provider (provider_id) ON DELETE CASCADE,
    downloaded_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    raw_file_path   VARCHAR(255)
);