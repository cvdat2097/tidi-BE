package tuan.tidi.repository.product;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tuan.tidi.DTO.admin.CampaignDTO;
import tuan.tidi.DTO.admin.SearchDTO;
import tuan.tidi.entity.Campaign;
import tuan.tidi.entity.Category;
import tuan.tidi.service.FormatDate;

@Repository
public class CampaignRepositoryCustomImpl implements CampaignRepositoryCustom {
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private CampaignRepository campaignRepository;
	
	public List<Campaign> search(SearchDTO searchDTO) {
		String k = searchDTO.getQuery().getKeyword();
		String startTime = searchDTO.getQuery().getStartTime();
		String expiredTime = searchDTO.getQuery().getExpiredTime();
		if (k != null && !k.isEmpty()) {
			if ((startTime == null || startTime.isEmpty()) && (expiredTime == null || expiredTime.isEmpty())) {
				try {
					String sql = "Select e from Campaign e where e.campaignName like CONCAT('%',?0,'%') or e.active like CONCAT('%',?1,'%') or e.description like CONCAT('%',?2,'%')";
					return (List<Campaign>) entityManager.createQuery(sql).setParameter(0, k).setParameter(1, k)
							.setParameter(2, k).setMaxResults(searchDTO.getLimit())
							.setFirstResult(searchDTO.getOffset()).getResultList();
				} catch (NoResultException e) {
					return null;
				}
			} else {
				Date st, et;
				try {
					st = FormatDate.parseDateTime(startTime);
				} catch (Exception e) {
					st = new Date();
				}
				try {
					et = FormatDate.parseDateTime(expiredTime);
				} catch (Exception e) {
					et = new Date();
				}
				try {
					String sql = "Select e from Campaign e where (e.campaignName like CONCAT('%',?0,'%') or e.active like CONCAT('%',?1,'%') or e.description like CONCAT('%',?2,'%')) and e.startTime <= ?3 and e.expiredTime >= ?4";
					return (List<Campaign>) entityManager.createQuery(sql).setParameter(0, k).setParameter(1, k)
							.setParameter(2, k).setParameter(3, st).setParameter(4, et)
							.setMaxResults(searchDTO.getLimit()).setFirstResult(searchDTO.getOffset()).getResultList();
				} catch (NoResultException e) {
					return null;
				}
			}
		} else {
			if ((startTime == null || startTime.isEmpty()) && (expiredTime == null || expiredTime.isEmpty())) {
				try {
					String sql = "Select e from Campaign e";
					return (List<Campaign>) entityManager.createQuery(sql).setMaxResults(searchDTO.getLimit())
							.setFirstResult(searchDTO.getOffset()).getResultList();
				} catch (NoResultException e) {
					return null;
				}
			} else {
				Date st, et;
				try {
					st = FormatDate.parseDateTime(startTime);
				} catch (Exception e) {
					st = new Date();
				}
				try {
					et = FormatDate.parseDateTime(expiredTime);
				} catch (Exception e) {
					et = new Date();
				}
				try {
					String sql = "Select e from Campaign e where e.startTime <= ?0 and e.expiredTime >= ?1";
					return (List<Campaign>) entityManager.createQuery(sql).setParameter(0, st).setParameter(1, et)
							.setMaxResults(searchDTO.getLimit()).setFirstResult(searchDTO.getOffset()).getResultList();
				} catch (NoResultException e) {
					return null;
				}
			}
		}
	}

	public void updateCampaign(CampaignDTO campaignDTO) {
		Campaign campaign = campaignRepository.findById(campaignDTO.getId());
		if (campaignDTO.getActive() != null) campaign.setActive(campaignDTO.getActive());
		if (campaignDTO.getCampaignName() != null) campaign.setCampaignName(campaignDTO.getCampaignName());
		if (campaignDTO.getDescription() != null) campaign.setDescription(campaignDTO.getDescription());
		if (campaignDTO.getExpiredTime() != null) campaign.setExpiredTime(FormatDate.parseDateTime(campaignDTO.getExpiredTime()));
		if (campaignDTO.getStartTime() != null) campaign.setStartTime(FormatDate.parseDateTime(campaignDTO.getStartTime()));
		campaignRepository.save(campaign);
	}
	
}
