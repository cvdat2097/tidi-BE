package tuan.tidi.repository.product;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import tuan.tidi.entity.Campaign;

public interface CampaignRepository extends CrudRepository<Campaign, Long>{
	Campaign findById(int id);
	List<Campaign> findAll();
}
