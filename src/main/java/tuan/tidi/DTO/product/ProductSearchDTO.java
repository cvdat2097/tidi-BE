package tuan.tidi.DTO.product;

public class ProductSearchDTO {
	private int limit;
	private int offset;
	private ProductQueryDTO query;
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
	public ProductQueryDTO getQuery() {
		return query;
	}
	public void setQuery(ProductQueryDTO query) {
		this.query = query;
	}
	
}
