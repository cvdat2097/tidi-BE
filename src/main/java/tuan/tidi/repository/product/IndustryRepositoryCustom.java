package tuan.tidi.repository.product;

import java.util.List;

import tuan.tidi.DTO.admin.SearchDTO;
import tuan.tidi.entity.Industry;

public interface IndustryRepositoryCustom {
	public List<Industry> search(SearchDTO searchDTO);
	public void updateIndustry(Industry industry);
}
