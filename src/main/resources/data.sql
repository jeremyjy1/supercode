INSERT INTO user(uuid,username,password,salt,nickname,email,role)
SELECT '0', 'root', 'e20748883a8c5dd25a4afb24bd7edffdb523cb25f12b95072ccbc16f1025ba58b6f8b5b229fa2e44d5f9225ed08c64e8eb7c749cb8c9142ed292ad7932c3c3a7', 'asdf', 'root', 'example@example.com','root'
FROM DUAL WHERE
NOT EXISTS(SELECT username FROM user WHERE username = 'root');