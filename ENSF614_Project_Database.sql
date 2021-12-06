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
     price				DOUBLE AS 	(rating * 4.5),
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


CREATE TABLE IF NOT EXISTS SEAT
    (seatId         int             NOT NULL AUTO_INCREMENT,
     seatRow        int             NOT NULL,
     seatNum        int             NOT NULL,
     ticketId       int             NOT NULL,
     primary key (seatId));



INSERT INTO THEATRE (theatreId, theatreName)
VALUES (1,                "Crowfoot Crossing"),
        (2,                "Canyon Meadows"),
        (3,                "Westhills"),
        (4,                "Chinook"),
        (5,                "Eau Claire Market");


INSERT INTO MOVIE (movieId, title, rating, theatreId)
VALUES (1,                "Anchorman",                    4,1),
        (2,                "The Lion King",                5,1),
        (3,                "Titanic",                        5,2),
        (4,                "Pulp Fiction",                    4,1),
        (5,                "Finding Nemo",                    3,2);



INSERT INTO SHOWTIME (showtimeId, movieId, theatreId, showtime)
VALUES (1,                1,                1,                '2022-05-01 09:23:00'),
        (2,                1,                1,                '2021-12-01 14:20:00'),
        (3,                1,                2,                '2021-12-01 10:25:00'),
        (4,                2,                3,                '2021-12-01 13:50:00'),
        (5,                3,                4,                '2021-12-01 07:00:00'),
        (6,                4,                4,                '2021-12-01 16:45:00'),
        (7,                4,                4,                '2021-12-01 22:10:00'),
        (8,                5,                5,                '2021-12-01 11:15:00'),
        (9,                5,                5,                '2021-12-01 15:00:00'),
        (10,            5,                1,                '2021-12-01 20:50:00');


INSERT INTO TICKET (showtimeId, price)
VALUES (1, 15.5),
        (2, 17.5),
        (2, 14.5),
        (3, 15.5),
        (3, 15.5),
        (3, 13.5),
        (4, 15.5),
        (4, 15.5),
        (5, 20.5),
        (5, 15.5);


INSERT INTO SEAT (seatRow, seatNum, ticketId)
VALUES (1,                1,                1),
        (1,                2,                2),
        (2,                4,                3),
        (2,                8,                4),
        (3,                7,                5),
        (3,                8,                6),
        (4,                1,                7),
        (4,                2,                8),
        (4,                3,                9),
        (4,                4,                10);


CREATE TABLE IF NOT EXISTS PAYMENT
(
    paymentId       int             NOT NULL AUTO_INCREMENT,
    holderName      VARCHAR(255)    NOT NULL,
    cardNumber      VARCHAR(255)    NOT NULL,
    expiry          date            NOT NULL,
    primary key(paymentId)
);

CREATE TABLE IF NOT EXISTS SALE
(
    paymentId       int             NOT NULL,
    ticketId		int			    NOT NULL,
    foreign key (paymentId) references payment(paymentId),
    foreign key (ticketId) references ticket(ticketId)
);


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

INSERT INTO SALE (paymentId, ticketId) VALUES
(1,				1),
(2,				2),
(3,				3),
(4,				4),
(5,				5),
(6,				6),
(7,				7),
(8,				8),
(9,				9),
(10,			10);


CREATE TABLE IF NOT EXISTS REGISTERED_USER
(
    userId          int             NOT NULL AUTO_INCREMENT,
    email           VARCHAR(255)    NOT NULL,
    password        VARCHAR(255)    NOT NULL,
    address         VARCHAR(255)    NOT NULL,
    card            int             NOT NULL,
    lastPaid        date            NULL,
    primary key(userId),
    foreign key (card) references PAYMENT(paymentId)
);


INSERT INTO REGISTERED_USER (email,password,address,card,lastPaid) VALUES
("romeo.sosa@ucalgary.ca","password3","721 Gates St. Lachute, QC J8H 8J8","10","2021-04-21"),
("jared.nash@ucalgary.ca","password6","8489 Tallwood St. Laval-sur-le-Lac, QC H7R 9L1","9","2021-03-17"),
("salvador.cain@ucalgary.ca","password9","469 Pennington St. Sainte-Julie, QC J3E 1J1","8","2021-04-30"),
("caitlyn.bean@ucalgary.ca","password12","638 East John Street Baker Brook, NB E7A 7J6","7","2021-06-06"),
("dayami.simon@ucalgary.ca","password15","981 Second Street Dauphin, MB R7N 4V6","6","2021-05-03"),
("kelsey.curry@ucalgary.ca","password18","226 Newbridge Drive Senneville, QC H9K 7Y6","5","2021-08-11"),
("bryce.blair@ucalgary.ca","password21","02 High Ridge Ave. Arnprior, ON K7S 0E6","4","2021-02-23"),
("donavan.hunter@ucalgary.ca","password24","991 South Brandywine St. Campbellton, NB E3N 3Y2","3","2021-04-12"),
("jovanny.orozco@ucalgary.ca","password27","71 W. Argyle St. Cochrane, AB T4C 2C6","2","2021-08-03"),
("lizeth.cowan@ucalgary.ca","password30","400 Aspen Dr. Rock Forest, QC J1N 8X5","1","2021-04-03");


CREATE TABLE IF NOT EXISTS COUPON
(
    couponId        int             NOT NULL AUTO_INCREMENT,
    couponCode      VARCHAR(255)    NOT NULL,
    couponAmount    double          NOT NULL,
    expiry          date            NOT NULL,
    primary key(couponId)
);


INSERT INTO COUPON (couponCode,couponAmount,expiry) VALUES
("SaTs1pKnCg","39","2021-11-25"),
("QnXfvFTrBo","24","2021-12-09"),
("s57NlehWQh","26","2022-03-01"),
("A2kGUPhyR8","15","2022-04-01"),
("tCpLtqccCT","23","2022-05-01");


CREATE TABLE IF NOT EXISTS RECEIPT
(
    receiptId       int             NOT NULL AUTO_INCREMENT,
    paymentCard     VARCHAR(255)    NOT NULL,
    price           double          NOT NULL,
    generationTime  datetime        NOT NULL,
    primary key(receiptId)
);


INSERT INTO RECEIPT (paymentCard,price,generationTime) VALUES
("5355142077868730","34","2021-11-25 09:00:00"),
("5565149389994340","64","2021-12-09 09:00:00"),
("5119080811765270","12","2022-03-01 09:00:00"),
("5179341601731640","67","2022-04-01 09:00:00"),
("5113181095909820","98","2022-05-01 09:00:00");