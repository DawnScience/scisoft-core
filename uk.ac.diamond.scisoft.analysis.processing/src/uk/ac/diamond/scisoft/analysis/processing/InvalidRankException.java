package uk.ac.diamond.scisoft.analysis.processing;

public class InvalidRankException extends OperationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3684722892559338586L;
	private int rank;
	
	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public InvalidRankException(IOperation operation, String reason) {
		super(operation, reason);
	}

	public InvalidRankException(IOperation operation, Throwable cause) {
		super(operation, cause);
	}

	public InvalidRankException(IOperation operation) {
		super(operation);
	}

}
