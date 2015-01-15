package net.shopxx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.shopxx.dao.PreSellApplyDao;
import net.shopxx.entity.Member;
import net.shopxx.entity.PreSellApply;
import net.shopxx.entity.PreSellRole;
import net.shopxx.service.PreSellApplyService;
import org.springframework.stereotype.Service;

/**
 * Service - 预约申请
 * 
 * @author hfh
 * @version 10
 */
@Service("preSellApplyServiceImpl")
public class PreSellApplyServiceImpl extends BaseServiceImpl<PreSellApply, Long> implements
		PreSellApplyService {

	@Resource(name = "preSellApplyDaoImpl")
	private PreSellApplyDao preSellApplyDao;
	
	@Resource(name = "preSellApplyDaoImpl")
	public void setBaseDao(PreSellApplyDao preSellApplyDao) {
		super.setBaseDao(preSellApplyDao);
	}
	
	public void save(PreSellApply apply) {
		preSellApplyDao.persist(apply);
	}

	public boolean findCountByCreateBy(PreSellRole preSellRole, String createBy) {
		return preSellApplyDao.findCountByCreateBy(preSellRole,createBy);
	}

	@Override
	public List<PreSellApply> findListByMember(Member member) {
		// TODO Auto-generated method stub
		return preSellApplyDao.findListByMember(member);
	}

}
