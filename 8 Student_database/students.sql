SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;


CREATE TABLE `student`
(
    `id_student`         int                                                            NOT NULL,
    `first_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL,
    `last_name`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL,
    `patronymic` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   DEFAULT NULL,
    `gender`     set ('М','Ж') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `birthday`   date                                                           NOT NULL,
    `id_group`   varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci   NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE `group`
(
    `id_group`         varchar(10)                                NOT NULL,
    `id_curriculum`    int                                        NOT NULL,
    `qualification`    enum ('master','bachelor','specialist')    NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE `curriculum`
(
    `id_curriculum`     int        NOT NULL,
    `year`              year       NOT NULL,
    `id_specialization` varchar(10) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE curriculum_discipline
(
    `id_c_d`           int         NOT NULL,
    `id_curriculum`    int         NOT NULL,
    `id_discipline`    varchar(40) NOT NULL,
    `semester_number`  int         NOT NULL,
    `number_of_hours`  int         NOT NULL,
    `reporting_form`   varchar(10) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE `specialization`
(
    `id_specialization`   varchar(10)                                                  NOT NULL,
    `specialization_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE `academic_performance`
(
    `id_student_a_p`          INT NULL,
    `id_c_d`              INT NULL,
    `score`               INT NULL,
    `attempt`             INT NOT NULL DEFAULT '1'
) ENGINE = InnoDB;


CREATE TABLE `discipline`
(
    `id_discipline`          varchar(40) NOT NULL,
    `discipline_name`        varchar(80) NOT NULL,
    `id_department`          int         NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


CREATE TABLE `department`
(
    `id_department`   int         NOT NULL,
    `department_name` varchar(40) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


--
-- Индексы
--
ALTER TABLE `curriculum`
    ADD PRIMARY KEY (`id_curriculum`),
    ADD KEY `id_specialization` (`id_specialization`);

ALTER TABLE `department`
    ADD PRIMARY KEY (`id_department`);

ALTER TABLE `discipline`
    ADD PRIMARY KEY (`id_discipline`),
    ADD KEY `cathedra_id` (`id_department`);

ALTER TABLE `academic_performance`
    ADD PRIMARY KEY (`id_student_a_p`, `id_c_d`),
    ADD KEY (`id_c_d`),
    ADD KEY (`id_student_a_p`);

ALTER TABLE curriculum_discipline
    ADD PRIMARY KEY (`id_c_d`),
    ADD KEY `id_curriculum` (`id_curriculum`),
    ADD KEY `id_discipline` (`id_discipline`);

ALTER TABLE `group`
    ADD PRIMARY KEY (`id_group`),
    ADD KEY `id_curriculum` (`id_curriculum`);

ALTER TABLE `specialization`
    ADD PRIMARY KEY (`id_specialization`);

ALTER TABLE `student`
    ADD PRIMARY KEY (`id_student`),
    ADD KEY `name` (`last_name`, `first_name`),
    ADD KEY `group_id` (`id_group`);

ALTER TABLE `curriculum`
    MODIFY `id_curriculum` int NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 2;


--
-- AUTO_INCREMENT
--
ALTER TABLE `department`
    MODIFY `id_department` int NOT NULL AUTO_INCREMENT;

ALTER TABLE curriculum_discipline
    MODIFY `id_c_d` int NOT NULL AUTO_INCREMENT;

ALTER TABLE `student`
    MODIFY `id_student` int NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 2;


--
-- Ограничения внешнего ключа
--
ALTER TABLE `curriculum`
    ADD CONSTRAINT `id_specialization_ibfk_1` FOREIGN KEY (`id_specialization`) REFERENCES `specialization` (`id_specialization`) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE `discipline`
    ADD CONSTRAINT `id_department_ibfk_1` FOREIGN KEY (`id_department`) REFERENCES `department` (`id_department`) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE curriculum_discipline
    ADD CONSTRAINT `id_curriculum_ibfk_1` FOREIGN KEY (`id_curriculum`) REFERENCES `curriculum` (`id_curriculum`) ON DELETE RESTRICT ON UPDATE CASCADE,
    ADD CONSTRAINT `id_discipline_ibfk_2` FOREIGN KEY (`id_discipline`) REFERENCES `discipline` (`id_discipline`) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE `group`
    ADD CONSTRAINT `group_ibfk_1` FOREIGN KEY (`id_curriculum`) REFERENCES `curriculum` (`id_curriculum`) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE `student`
    ADD CONSTRAINT `student_ibfk_1` FOREIGN KEY (`id_group`) REFERENCES `group` (`id_group`) ON DELETE RESTRICT ON UPDATE CASCADE;
COMMIT;

ALTER TABLE `academic_performance`
    ADD FOREIGN KEY (`id_c_d`) REFERENCES `curriculum_discipline` (`id_c_d`) ON DELETE RESTRICT ON UPDATE CASCADE,
    ADD FOREIGN KEY (`id_student_a_p`) REFERENCES `student` (`id_student`) ON DELETE RESTRICT ON UPDATE CASCADE;


/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
