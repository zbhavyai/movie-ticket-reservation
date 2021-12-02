package movieTicketSystem.model;

/**
 * Seat class to store local seat number and if the seat has been reserved.
 *
 */
public class Seat {
    
	private int id;
	private int rowNumber;
	private int colNumber;
	private boolean reserved;

	public Seat(int rowNumber, int colNumber) {
		this.rowNumber = rowNumber;
		this.colNumber = colNumber;
	}

	public boolean isReserved() {
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}

	

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public int getColNumber() {
		return colNumber;
	}

	public void setColNumber(int colNumber) {
		this.colNumber = colNumber;
	}

	public void resetReservation() {
		this.reserved = false;
	}

	@Override
	public boolean equals(Object o) {
		return ((Seat) o).rowNumber == this.rowNumber && ((Seat) o).colNumber == this.colNumber;
	}

}
