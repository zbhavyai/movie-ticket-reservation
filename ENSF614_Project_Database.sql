CREATE DATABASE IF NOT EXISTS ENSF614PROJECT;

USE ENSF614PROJECT;
  

CREATE TABLE IF NOT EXISTS THEATRE
	(theatreId			int				NOT NULL,
     theatreName		VARCHAR(30) 	NOT NULL,
     primary key(theatreId));
     
CREATE TABLE IF NOT EXISTS MOVIE
	(movieId			int 			NOT NULL,
	 title				VARCHAR(30)		NOT NULL,
         rating				double			NOT NULL,
	 theatreId			int			NOT NULL,	
     PRIMARY KEY (movieId),seat
     foreign key(theatreId) references THEATRE(theatreId)
);
	

     
CREATE TABLE IF NOT EXISTS SHOWTIME
	(showtimeId 		int				NOT NULL,
     movieId			int				NOT NULL,
     theatreId			int				NOT NULL,
     showtime			datetime		NOT NULL,
     primary key(showtimeId),
     foreign key(movieId) references MOVIE(movieId),
     foreign key(theatreId) references THEATRE(theatreId));
 
CREATE TABLE IF NOT EXISTS TICKET
	(ticketId			int				NOT NULL AUTO_INCREMENT,
     showtimeId			int				NOT NULL,
     price              double          NOT NULL,
     primary key(ticketId),
     foreign key(showtimeId) references SHOWTIME(showtimeId));
     
CREATE TABLE IF NOT EXISTS COUPON
	(couponId			int				NOT NULL AUTO_INCREMENT,
     expiry			datetime		NOT NULL,
     redeemedAmount              double          NOT NULL,
     primary key(couponId));
     
CREATE TABLE IF NOT EXISTS SEAT
	(seatId				int				NOT NULL,
     seatRow			int				NOT NULL,
     seatNum			int				NOT NULL,
     ticketId			int		NOT NULL,
     primary key (seatId),
     foreign key (ticketID) references TICKET(ticketId));
    
CREATE TABLE IF NOT EXISTS PAYMENT
	(paymentId			int				NOT NULL,
     cardNumber				VARCHAR(30)		NOT NULL,
     cvc  VARCHAR(30)		NOT NULL,
     primary key(paymentId));
     
     
CREATE TABLE IF NOT EXISTS RUser
	(id			int 			NOT NULL AUTO_INCREMENT,
	 username				VARCHAR(30)		NOT NULL,
     password				VARCHAR(30)		NOT NULL,
	 email			VARCHAR(30)		NOT NULL,	
     paid boolean NOT NULL,
     primary key(id)
);
     
     INSERT INTO THEATRE (theatreId, theatreName)
VALUES (1,				"Crowfoot Crossing"),
		(2,				"Canyon Meadows"),
        (3,				"Westhills"),
        (4,				"Chinook"),
        (5,				"Eau Claire Market");
     
INSERT INTO MOVIE (movieId, title, rating, theatreId)
VALUES (1,				"Anchorman",					4,1),
		(2,				"The Lion King",				5,1),
        (3,				"Titanic",						5,2),
        (4,				"Pulp Fiction",					4,1),
        (5,				"Finding Nemo",					3,2);
        

        
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
        

        

        
INSERT INTO TICKET (ticketId, showtimeId, price)
VALUES (1,				1, 15.5),
		(2,				2, 17.5),
        (3,				2, 14.5),
        (4,				3, 15.5),
        (5,				3, 15.5),
        (6,				3, 13.5),
        (7,				4, 15.5),
        (8,				4, 15.5),
        (9,				5, 20.5),
        (10,			5, 15.5);
        
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
        
INSERT INTO COUPON (couponId, expiry, redeemedAmount)
VALUES (1,				'2021-12-02 09:23:00', 5.5),
		(2,				'2021-12-03 04:23:00', 6.5);
        
INSERT INTO RUser (username, password,email,paid)
VALUES ("u1","123321", "dacdd@gmail.com", true),
		("u2","456", "dsdsdddd@gmail.com",false);
        
INSERT INTO PAYMENT (paymentId, cardNumber, cvc)
VALUES (1,				"32123", "223"),
		(2,				"3244324", "111"),
        (3,				"232132", "554");