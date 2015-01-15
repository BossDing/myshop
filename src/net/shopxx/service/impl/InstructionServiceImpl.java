package net.shopxx.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.shopxx.dao.InstructionDao;
import net.shopxx.entity.Instruction;
import net.shopxx.service.InstructionService;

@Service("instructionServiceImpl")
public class InstructionServiceImpl extends BaseServiceImpl<Instruction, Long> implements
		InstructionService {
	@Resource(name = "instructionDaoImpl")
	private InstructionDao instructionDao;
	
	@Resource(name = "instructionDaoImpl")
	public void setBaseDao(InstructionDao instructionDao) {
		super.setBaseDao(instructionDao);
	}

	@Override
	public List<Instruction> search(String keyword, Integer count) {
		// TODO Auto-generated method stub
		return instructionDao.search(keyword, count);
	}

	@Override
	public Instruction findByInstructionName(String name) {
		// TODO Auto-generated method stub
		return instructionDao.findByInstructionName(name);
	}
}
