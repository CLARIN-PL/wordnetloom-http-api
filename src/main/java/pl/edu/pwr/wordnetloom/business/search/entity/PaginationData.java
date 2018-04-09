package pl.edu.pwr.wordnetloom.business.search.entity;

public class PaginationData {

	private final int firstResult;
	private final int maxResults;

	public PaginationData(final int firstResult, final int maxResults){
		this.firstResult = firstResult;
		this.maxResults = maxResults;
	}

	public int getFirstResult() {
		return firstResult;
	}

	public int getMaxResults() {
		return maxResults;
	}

}