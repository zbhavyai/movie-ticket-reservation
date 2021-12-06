DROP DATABASE IF EXISTS ENSF614PROJECT;
CREATE DATABASE IF NOT EXISTS ENSF614PROJECT;
USE ENSF614PROJECT;

CREATE TABLE IF NOT EXISTS THEATRE
    (theatreId            int                NOT NULL,
     theatreName        VARCHAR(30)     NOT NULL,
     primary key(theatreId));


CREATE TABLE IF NOT EXISTS MOVIE
    (movieId            int             NOT NULL,
     title                VARCHAR(30)        NOT NULL,
         rating                double            NOT NULL,
     theatreId            int            NOT NULL,
     released           BOOLEAN        NOT NULL,
     PRIMARY KEY (movieId),
     foreign key(theatreId) references THEATRE(theatreId)
);


CREATE TABLE IF NOT EXISTS SHOWTIME
    (showtimeId         int                NOT NULL,
     movieId            int                NOT NULL,
     theatreId            int                NOT NULL,
     showtime            datetime        NOT NULL,
     primary key(showtimeId),
     foreign key(movieId) references MOVIE(movieId),
     foreign key(theatreId) references THEATRE(theatreId));


CREATE TABLE IF NOT EXISTS TICKET
    (ticketId            int                NOT NULL AUTO_INCREMENT,
     showtimeId            int                NOT NULL,
     price              double          NOT NULL,
     primary key(ticketId),
     foreign key(showtimeId) references SHOWTIME(showtimeId));


CREATE TABLE IF NOT EXISTS COUPON
    (couponId       int             NOT NULL AUTO_INCREMENT,
     couponCode     VARCHAR(255)    NOT NULL,
     couponAmount   double          NOT NULL,
     redeemedAmount double          NOT NULL,
     expiry         date            NOT NULL,
     primary key(couponId));


CREATE TABLE IF NOT EXISTS SEAT
    (seatId         int             NOT NULL,
     seatRow        int             NOT NULL,
     seatNum        int             NOT NULL,
     ticketId       int             NOT NULL,
     primary key (seatId),
     foreign key (ticketID) references TICKET(ticketId));


CREATE TABLE IF NOT EXISTS PAYMENT
    (paymentId      int             NOT NULL AUTO_INCREMENT,
     holderName     VARCHAR(255)    NOT NULL,
     cardNumber     VARCHAR(255)    NOT NULL,
     expiry         date            NOT NULL,
     primary key(paymentId));


CREATE TABLE IF NOT EXISTS RUser
    (userId         int             NOT NULL AUTO_INCREMENT,
     username       VARCHAR(255)    NOT NULL,
     password       VARCHAR(255)    NOT NULL,
     email          VARCHAR(255)    NOT NULL,
     address        VARCHAR(255)    NOT NULL,
     card           int             NOT NULL,
     lastPaid       date            NOT NULL,
     primary key(userId),
     foreign key (card) references PAYMENT(paymentId));


INSERT INTO THEATRE (theatreId, theatreName)
VALUES (1,                "Crowfoot Crossing"),
        (2,                "Canyon Meadows"),
        (3,                "Westhills"),
        (4,                "Chinook"),
        (5,                "Eau Claire Market");


INSERT INTO MOVIE (movieId, title, rating, theatreId,released)
VALUES (1,                "Anchorman",                    4,1, true),
        (2,                "The Lion King",                5,1, true),
        (3,                "Titanic",                        5,2, true),
        (4,                "Pulp Fiction",                    4,1, true),
        (5,                "Finding Nemo",                    3,2, true),
	(6,                "Dynasty Future",                    4,2, false);
;



INSERT INTO SHOWTIME (showtimeId, movieId, theatreId, showtime)
VALUES (1,                1,                1,                '2021-12-01 09:23:00'),
        (2,                1,                1,                '2021-12-01 14:20:00'),
        (3,                1,                2,                '2021-12-01 10:25:00'),
        (4,                2,                3,                '2021-12-01 13:50:00'),
        (5,                3,                4,                '2021-12-01 07:00:00'),
        (6,                4,                4,                '2021-12-01 16:45:00'),
        (7,                4,                4,                '2021-12-01 22:10:00'),
        (8,                5,                5,                '2021-12-01 11:15:00'),
        (9,                5,                5,                '2021-12-01 15:00:00'),
        (10,            5,                1,                '2021-12-01 20:50:00'),
        (11,                6,                5,                '2021-12-20 11:15:00'),
        (12,                6,                5,                '2021-12-20 15:00:00'),
        (13,            6,                1,                '2021-12-20 20:50:00');


INSERT INTO TICKET (ticketId, showtimeId, price)
VALUES (1,                1, 15.5),
        (2,                2, 17.5),
        (3,                2, 14.5),
        (4,                3, 15.5),
        (5,                3, 15.5),
        (6,                3, 13.5),
        (7,                4, 15.5),
        (8,                4, 15.5),
        (9,                5, 20.5),
        (10,            5, 15.5);


INSERT INTO SEAT (seatId, seatRow, seatNum, ticketId)
VALUES (1,                1,                1,                1),
        (2,                1,                2,                2),
        (3,                2,                4,                3),
        (4,                2,                8,                4),
        (5,                3,                7,                5),
        (6,                3,                8,                6),
        (7,                4,                1,                7),
        (8,                4,                2,                8),
        (9,                4,                3,                9),
        (10,            4,                4,                10);


INSERT INTO PAYMENT (holderName,cardNumber,expiry) VALUES
("Lizeth Cowan","5355142077868730","2023-01-01"),
("Jovanny Orozco","5598855665566810","2022-01-01"),
("Donavan Hunter","5565149389994340","2022-02-01"),
("Bryce Blair","5468258212174080","2023-04-01"),
("Kelsey Curry","5119080811765270","2028-01-01"),
("Dayami Simon","5162680765761620","2022-11-01"),
("Caitlyn Bean","5179341601731640","2024-12-01"),
("Salvador Cain","5580095333314690","2025-11-01"),
("Jared Nash","5113181095909820","2026-12-01"),
("Romeo Sosa","5534346641931920","2023-06-01");


INSERT INTO RUser (username,password,email,address,card,lastPaid) VALUES
("romeo.sosa","password3","romeo.sosa@ucalgary.ca","721 Gates St. Lachute, QC J8H 8J8","10","2021-04-21"),
("jared.nash","password6","jared.nash@ucalgary.ca","8489 Tallwood St. Laval-sur-le-Lac, QC H7R 9L1","9","2021-03-17"),
("salvador.cain","password9","salvador.cain@ucalgary.ca","469 Pennington St. Sainte-Julie, QC J3E 1J1","8","2021-04-30"),
("caitlyn.bean","password12","caitlyn.bean@ucalgary.ca","638 East John Street Baker Brook, NB E7A 7J6","7","2021-06-06"),
("dayami.simon","password15","dayami.simon@ucalgary.ca","981 Second Street Dauphin, MB R7N 4V6","6","2021-05-03"),
("kelsey.curry","password18","kelsey.curry@ucalgary.ca","226 Newbridge Drive Senneville, QC H9K 7Y6","5","2021-08-11"),
("bryce.blair","password21","bryce.blair@ucalgary.ca","02 High Ridge Ave. Arnprior, ON K7S 0E6","4","2021-02-23"),
("donavan.hunter","password24","donavan.hunter@ucalgary.ca","991 South Brandywine St. Campbellton, NB E3N 3Y2","3","2021-04-12"),
("jovanny.orozco","password27","jovanny.orozco@ucalgary.ca","71 W. Argyle St. Cochrane, AB T4C 2C6","2","2021-08-03"),
("lizeth.cowan","password30","lizeth.cowan@ucalgary.ca","400 Aspen Dr. Rock Forest, QC J1N 8X5","1","2021-04-03");


INSERT INTO COUPON (couponCode,couponAmount,redeemedAmount,expiry) VALUES 
("SaTs1pKnCg","39","0","2021-11-25"),
("QnXfvFTrBo","24","0","2021-12-09"),
("s57NlehWQh","26","4","2022-03-01"),
("A2kGUPhyR8","15","15","2022-04-01"),
("tCpLtqccCT","23","24","2022-05-01");
