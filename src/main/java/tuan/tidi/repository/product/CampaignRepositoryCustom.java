package tuan.tidi.repository.product;

import java.util.List;

import tuan.tidi.DTO.admin.SearchDTO;
import tuan.tidi.entity.Campaign;

public interface CampaignRepositoryCustom {
	public List<Campaign> search(SearchDTO searchDTO);
}
