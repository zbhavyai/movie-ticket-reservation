DROP DATABASE IF EXISTS moviedb;
CREATE DATABASE moviedb;
CREATE USER IF NOT EXISTS 'movieapp'@'%' IDENTIFIED BY 'moviepassword';
GRANT ALL ON moviedb.* TO 'movieapp'@'%';
USE moviedb;


CREATE TABLE IF NOT EXISTS THEATRE
(
    theatreId      int             NOT NULL,
    theatreName    VARCHAR(30)     NOT NULL,
    primary key(theatreId)
);


CREATE TABLE IF NOT EXISTS MOVIE
(
    movieId        int             NOT NULL,
    title          VARCHAR(30)     NOT NULL,
    rating         double          NOT NULL,
    theatreId      int             NOT NULL,
    releasedate    date            NOT NULL,
    price          double AS (rating * 3.5)    NOT NULL,
    PRIMARY KEY (movieId),
    foreign key(theatreId) references THEATRE(theatreId)
);


CREATE TABLE IF NOT EXISTS SHOWTIME
(
    showtimeId     int             NOT NULL,
    movieId        int             NOT NULL,
    theatreId      int             NOT NULL,
    showtime       datetime        NOT NULL,
    primary key(showtimeId),
    foreign key(movieId) references MOVIE(movieId),
    foreign key(theatreId) references THEATRE(theatreId)
);


CREATE TABLE IF NOT EXISTS TICKET
(
    ticketId       int             NOT NULL AUTO_INCREMENT,
    showtimeId     int             NOT NULL,
    price          double          NOT NULL,
    primary key(ticketId),
    foreign key(showtimeId) references SHOWTIME(showtimeId)
);


CREATE TABLE IF NOT EXISTS COUPON
(
    couponId       int             NOT NULL AUTO_INCREMENT,
    couponCode     VARCHAR(255)    NOT NULL,
    couponAmount   double          NOT NULL,
    expiry         date            NOT NULL,
    primary key(couponId)
);


CREATE TABLE IF NOT EXISTS SEAT
(
    seatId         int             NOT NULL AUTO_INCREMENT,
    seatRow        int             NOT NULL,
    seatNum        int             NOT NULL,
    ticketId       int             NOT NULL,
    primary key (seatId)
);


CREATE TABLE IF NOT EXISTS PAYMENT
(
    paymentId      int             NOT NULL AUTO_INCREMENT,
    holderName     VARCHAR(255)    NOT NULL,
    cardNumber     VARCHAR(255)    NOT NULL,
    expiry         date            NOT NULL,
    primary key(paymentId)
);


CREATE TABLE IF NOT EXISTS REGISTERED_USER
(
    userId         int             NOT NULL AUTO_INCREMENT,
    username       VARCHAR(255)    NOT NULL,
    password       VARCHAR(255)    NOT NULL,
    email          VARCHAR(255)    NOT NULL,
    address        VARCHAR(255)    NOT NULL,
    card           int             NOT NULL,
    lastPaid       date            NOT NULL,
    primary key(userId),
    foreign key (card) references PAYMENT(paymentId)
);


INSERT INTO THEATRE (theatreId,theatreName) VALUES
("1","Crowfoot Crossing"),
("2","Canyon Meadows"),
("3","Westhills"),
("4","Chinook"),
("5","Eau Claire Market");


INSERT INTO MOVIE (movieId,title,rating,theatreId,releasedate) VALUES
("1","Anchorman","4","1","2022-12-01"),
("2","The Lion King","5","1","2021-12-02"),
("3","Titanic","5","2","2021-12-01"),
("4","Pulp Fiction","4","1","2021-11-01"),
("5","Finding Nemo","3","2","2021-12-03");


INSERT INTO SHOWTIME (showtimeId,movieId,theatreId,showtime) VALUES
("1","1","1","2021-12-08 09:00:00"),
("2","1","1","2021-12-08 21:00:00"),
("3","1","2","2021-12-09 09:00:00"),
("4","1","2","2021-12-09 21:00:00"),
("5","1","3","2021-12-10 09:00:00"),
("6","1","3","2021-12-10 21:00:00"),
("7","1","4","2021-12-15 09:00:00"),
("8","1","4","2021-12-15 21:00:00"),
("9","1","5","2021-12-31 09:00:00"),
("10","1","5","2021-12-31 21:00:00"),
("11","2","1","2021-12-08 09:00:00"),
("12","2","1","2021-12-08 21:00:00"),
("13","2","2","2021-12-09 09:00:00"),
("14","2","2","2021-12-09 21:00:00"),
("15","2","3","2021-12-10 09:00:00"),
("16","2","3","2021-12-10 21:00:00"),
("17","2","4","2021-12-15 09:00:00"),
("18","2","4","2021-12-15 21:00:00"),
("19","2","5","2021-12-31 09:00:00"),
("20","2","5","2021-12-31 21:00:00"),
("21","3","1","2021-12-08 09:00:00"),
("22","3","1","2021-12-08 21:00:00"),
("23","3","2","2021-12-09 09:00:00"),
("24","3","2","2021-12-09 21:00:00"),
("25","3","3","2021-12-10 09:00:00"),
("26","3","3","2021-12-10 21:00:00"),
("27","3","4","2021-12-15 09:00:00"),
("28","3","4","2021-12-15 21:00:00"),
("29","3","5","2021-12-31 09:00:00"),
("30","3","5","2021-12-31 21:00:00"),
("31","4","1","2021-12-08 09:00:00"),
("32","4","1","2021-12-08 21:00:00"),
("33","4","2","2021-12-09 09:00:00"),
("34","4","2","2021-12-09 21:00:00"),
("35","4","3","2021-12-10 09:00:00"),
("36","4","3","2021-12-10 21:00:00"),
("37","4","4","2021-12-15 09:00:00"),
("38","4","4","2021-12-15 21:00:00"),
("39","4","5","2021-12-31 09:00:00"),
("40","4","5","2021-12-31 21:00:00"),
("41","5","1","2021-12-08 09:00:00"),
("42","5","1","2021-12-08 21:00:00"),
("43","5","2","2021-12-09 09:00:00"),
("44","5","2","2021-12-09 21:00:00"),
("45","5","3","2021-12-10 09:00:00"),
("46","5","3","2021-12-10 21:00:00"),
("47","5","4","2021-12-15 09:00:00"),
("48","5","4","2021-12-15 21:00:00"),
("49","5","5","2021-12-31 09:00:00"),
("50","5","5","2021-12-31 21:00:00");


INSERT INTO TICKET (ticketId,showtimeId,price) VALUES
("1","1","14"),
("2","1","14"),
("3","2","14"),
("4","2","14"),
("5","3","14"),
("6","3","14"),
("7","4","14"),
("8","4","14"),
("9","5","14"),
("10","5","14"),
("11","6","14"),
("12","6","14"),
("13","7","14"),
("14","7","14"),
("15","8","14"),
("16","8","14"),
("17","9","14"),
("18","9","14"),
("19","10","14"),
("20","10","14"),
("21","11","17.5"),
("22","11","17.5"),
("23","12","17.5"),
("24","12","17.5"),
("25","13","17.5"),
("26","13","17.5"),
("27","14","17.5"),
("28","14","17.5"),
("29","15","17.5"),
("30","15","17.5"),
("31","16","17.5"),
("32","16","17.5"),
("33","17","17.5"),
("34","17","17.5"),
("35","18","17.5"),
("36","18","17.5"),
("37","19","17.5"),
("38","19","17.5"),
("39","20","17.5"),
("40","20","17.5"),
("41","21","17.5"),
("42","21","17.5"),
("43","22","17.5"),
("44","22","17.5"),
("45","23","17.5"),
("46","23","17.5"),
("47","24","17.5"),
("48","24","17.5"),
("49","25","17.5"),
("50","25","17.5"),
("51","26","17.5"),
("52","26","17.5"),
("53","27","17.5"),
("54","27","17.5"),
("55","28","17.5"),
("56","28","17.5"),
("57","29","17.5"),
("58","29","17.5"),
("59","30","17.5"),
("60","30","17.5"),
("61","31","14"),
("62","31","14"),
("63","32","14"),
("64","32","14"),
("65","33","14"),
("66","33","14"),
("67","34","14"),
("68","34","14"),
("69","35","14"),
("70","35","14"),
("71","36","14"),
("72","36","14"),
("73","37","14"),
("74","37","14"),
("75","38","14"),
("76","38","14"),
("77","39","14"),
("78","39","14"),
("79","40","14"),
("80","40","14"),
("81","41","10.5"),
("82","41","10.5"),
("83","42","10.5"),
("84","42","10.5"),
("85","43","10.5"),
("86","43","10.5"),
("87","44","10.5"),
("88","44","10.5"),
("89","45","10.5"),
("90","45","10.5"),
("91","46","10.5"),
("92","46","10.5"),
("93","47","10.5"),
("94","47","10.5"),
("95","48","10.5"),
("96","48","10.5"),
("97","49","10.5"),
("98","49","10.5"),
("99","50","10.5"),
("100","50","10.5");


INSERT INTO SEAT (seatRow,seatNum,ticketId) VALUES
("1","1","1"),
("1","2","2"),
("1","1","3"),
("1","2","4"),
("1","1","5"),
("1","2","6"),
("1","1","7"),
("1","2","8"),
("1","1","9"),
("1","2","10"),
("1","1","11"),
("1","2","12"),
("1","1","13"),
("1","2","14"),
("1","1","15"),
("1","2","16"),
("1","1","17"),
("1","2","18"),
("1","1","19"),
("1","2","20"),
("1","1","21"),
("1","2","22"),
("1","1","23"),
("1","2","24"),
("1","1","25"),
("1","2","26"),
("1","1","27"),
("1","2","28"),
("1","1","29"),
("1","2","30"),
("1","1","31"),
("1","2","32"),
("1","1","33"),
("1","2","34"),
("1","1","35"),
("1","2","36"),
("1","1","37"),
("1","2","38"),
("1","1","39"),
("1","2","40"),
("1","1","41"),
("1","2","42"),
("1","1","43"),
("1","2","44"),
("1","1","45"),
("1","2","46"),
("1","1","47"),
("1","2","48"),
("1","1","49"),
("1","2","50"),
("1","1","51"),
("1","2","52"),
("1","1","53"),
("1","2","54"),
("1","1","55"),
("1","2","56"),
("1","1","57"),
("1","2","58"),
("1","1","59"),
("1","2","60"),
("1","1","61"),
("1","2","62"),
("1","1","63"),
("1","2","64"),
("1","1","65"),
("1","2","66"),
("1","1","67"),
("1","2","68"),
("1","1","69"),
("1","2","70"),
("1","1","71"),
("1","2","72"),
("1","1","73"),
("1","2","74"),
("1","1","75"),
("1","2","76"),
("1","1","77"),
("1","2","78"),
("1","1","79"),
("1","2","80"),
("1","1","81"),
("1","2","82"),
("1","1","83"),
("1","2","84"),
("1","1","85"),
("1","2","86"),
("1","1","87"),
("1","2","88"),
("1","1","89"),
("1","2","90"),
("1","1","91"),
("1","2","92"),
("1","1","93"),
("1","2","94"),
("1","1","95"),
("1","2","96"),
("1","1","97"),
("1","2","98"),
("1","1","99"),
("1","2","100");


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


INSERT INTO REGISTERED_USER (username,password,email,address,card,lastPaid) VALUES
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


INSERT INTO COUPON (couponCode,couponAmount,expiry) VALUES
("SaTs1pKnCg","39","2021-11-25"),
("QnXfvFTrBo","24","2021-12-09"),
("s57NlehWQh","26","2022-03-01"),
("A2kGUPhyR8","15","2022-04-01"),
("tCpLtqccCT","23","2022-05-01");
