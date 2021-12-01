DROP DATABASE ENSF614PROJECT;
CREATE DATABASE IF NOT EXISTS ENSF614PROJECT;

USE ENSF614PROJECT;
CREATE TABLE IF NOT EXISTS MOVIE
	(movieId			int 			NOT NULL,
	 title				VARCHAR(30)		NOT NULL,
     rating				double			NOT NULL,
     PRIMARY KEY (movieId));
	
CREATE TABLE IF NOT EXISTS THEATRE
	(theatreId			int				NOT NULL,
     theatreName		VARCHAR(30) 	NOT NULL,
     primary key(theatreId));
     
CREATE TABLE IF NOT EXISTS SHOWTIME
	(showtimeId 		int				NOT NULL,
     movieId			int				NOT NULL,
     theatreId			int				NOT NULL,
     showtime			datetime		NOT NULL,
     primary key(showtimeId),
     foreign key(movieId) references MOVIE(movieId),
     foreign key(theatreId) references THEATRE(theatreId));
 
CREATE TABLE IF NOT EXISTS TICKET
	(ticketId			int				NOT NULL,
     showtimeId			int				NOT NULL,
     primary key(ticketId),
     foreign key(showtimeId) references SHOWTIME(showtimeId));
     
CREATE TABLE IF NOT EXISTS SEAT
	(seatId				int				NOT NULL,
     seatRow			int				NOT NULL,
     seatNum			int				NOT NULL,
     ticketId			int		NOT NULL,
     primary key (seatId),
     foreign key (ticketID) references TICKET(ticketId));
    
CREATE TABLE IF NOT EXISTS PAYMENT
	(paymentId			int				NOT NULL,
     method				VARCHAR(30)		NOT NULL,
     primary key(paymentId));
     
INSERT INTO MOVIE (movieId, title, rating)
VALUES (1,				"Anchorman",					4),
		(2,				"The Lion King",				5),
        (3,				"Titanic",						5),
        (4,				"Pulp Fiction",					4),
        (5,				"Finding Nemo",					3);
        
INSERT INTO THEATRE (theatreId, theatreName)
VALUES (1,				"Crowfoot Crossing"),
		(2,				"Canyon Meadows"),
        (3,				"Westhills"),
        (4,				"Chinook"),
        (5,				"Eau Claire Market");
        
INSERT INTO SHOWTIME (showtimeId, movieId, theatreId, showtime)
VALUES (1,				1,				1,				'2021-12-01 09:23:00'),
		(2,				1,				1,				'2021-12-01 14:20:00'),
        (3,				1,				2,				'2021-12-01 10:25:00'),
        (4,				2,				3,				'2021-12-01 13:50:00'),
        (5,				3,				4,				'2021-12-01 07:00:00'),
        (6,				4,				4,				'2021-12-01 16:45:00'),
        (7,				4,				4,				'2021-12-01 22:10:00'),
        (8,				5,				5,				'2021-12-01 11:15:00'),
        (9,				5,				5,				'2021-12-01 15:00:00'),
        (10,			5,				1,				'2021-12-01 20:50:00');
        
INSERT INTO TICKET (ticketId, showtimeId)
VALUES (1,				1),
		(2,				2),
        (3,				2),
        (4,				3),
        (5,				3),
        (6,				3),
        (7,				4),
        (8,				4),
        (9,				5),
        (10,			5);
        
INSERT INTO SEAT (seatId, seatRow, seatNum, ticketId)
VALUES (1,				1,				1,				1),
		(2,				1,				2,				2),
        (3,				2,				4,				3),
        (4,				2,				8,				4),
        (5,				3,				7,				5),
        (6,				3,				8,				6),
        (7,				4,				1,				7),
        (8,				4,				2,				8),
        (9,				4,				3,				9),
        (10,			4,				4,				10);
        
INSERT INTO PAYMENT (paymentId, method)
VALUES (1,				"credit"),
		(2,				"credit"),
        (3,				"debit"),
        (4,				"credit"),
        (5,				"debit"),
        (6,				"debit"),
        (7,				"debit"),
        (8,				"credit"),
        (9,				"credit"),
        (10,			"debit");