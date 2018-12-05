package tuan.tidi.DTO.admin;

public class SearchDTO {
	private int limit;
	private int offset;
	private QueryDTO query;
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public QueryDTO getQuery() {
		return query;
	}
	public void setQuery(QueryDTO query) {
		this.query = query;
	}
	
}
