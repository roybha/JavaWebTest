CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name TEXT,
    age INTEGER CHECK (age > 0),
    file TEXT,
    gender TEXT,
    password TEXT CHECK (LENGTH(password) >= 8),
    login TEXT UNIQUE CHECK (LENGTH(login) >= 8)
);

INSERT INTO users (id, name, age, file, gender, password, login) VALUES
    (2, 'Olena', 23, 'https://randomuser.me/api/portraits/women/31.jpg', 'woman', 'password5', 'Olena_2303'),
    (8, 'Mariya', 26, 'https://randomuser.me/api/portraits/women/68.jpg', 'woman', 'password8', 'Mariya_26'),
    (1, 'Olexander', 25, 'https://randomuser.me/api/portraits/men/47.jpg', 'man', 'password4', 'Olexander25'),
    (6, 'Taras', 28, 'https://randomuser.me/api/portraits/men/18.jpg', 'man', 'password1', 'Taras_1980'),
    (5, 'Kate', 21, 'https://randomuser.me/api/portraits/women/73.jpg', 'woman', 'password2', 'Kate_2101'),
    (4, 'Oleh', 21, 'https://randomuser.me/api/portraits/men/1.jpg', 'man', 'password3', 'Oleh_2102'),
    (3, 'Anna', 25, 'https://randomuser.me/api/portraits/women/10.jpg', 'woman', 'password6', 'Anna_2504'),
    (7, 'Andrew', 26, 'https://randomuser.me/api/portraits/men/32.jpg', 'man', 'password7', 'Andrew_26'),
    (10, 'John', 23, 'https://randomuser.me/api/portraits/men/60.jpg', 'man', 'password9', 'John_2305'),
    (9, 'Iryna', 28, 'https://randomuser.me/api/portraits/women/79.jpg', 'woman', 'password10', 'Iryna_2806');


CREATE TABLE user_responses (
    id SERIAL PRIMARY KEY,
    responder_id INT,
    receiver_id INT,
    response_text TEXT,
    FOREIGN KEY (responder_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE messages (
    id SERIAL PRIMARY KEY,
    from_user_id INTEGER NOT NULL,
    to_user_id INTEGER NOT NULL,
    text TEXT NOT NULL,
    sent_at TIMESTAMP DEFAULT NOW() NOT NULL
);