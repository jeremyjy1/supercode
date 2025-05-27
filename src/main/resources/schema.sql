
CREATE TABLE IF NOT EXISTS `problem` (
  `uuid` varchar(255) NOT NULL,
  `description` longtext NOT NULL,
  `example_stdio` longtext NOT NULL,
  `large_stack` bit(1) NOT NULL,
  `memory_limit` int NOT NULL,
  `memory_reserved` int NOT NULL,
  `output_limit` int NOT NULL,
  `process_limit` int NOT NULL,
  `stdio` longtext NOT NULL,
  `time_limit` int NOT NULL,
  `time_reserved` int NOT NULL,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `submission` (
  `submission_id` varchar(255) NOT NULL,
  `code` longtext NOT NULL,
  `lang` varchar(255) NOT NULL,
  `memory` int NOT NULL,
  `problem_id` varchar(255) NOT NULL,
  `result` varchar(255) NOT NULL,
  `score` int NOT NULL,
  `stdio` longtext NOT NULL,
  `submission_time` datetime(6) NOT NULL,
  `time` int NOT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`submission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `user` (
  `uuid` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `UKsb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;