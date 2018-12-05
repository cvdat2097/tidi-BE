package tuan.tidi.DTO.product;

public class BranchDTO {
	private int id;
	private String branchName;

	public BranchDTO() {
		
	}
	
	public BranchDTO(int id, String branchName) {
		this.id = id;
		this.branchName = branchName;

	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
}
