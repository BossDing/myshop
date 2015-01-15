package net.shopxx.dao;

import java.util.List;

import net.shopxx.entity.Instruction;

public interface InstructionDao extends BaseDao<Instruction, Long> {

	List<Instruction> search(String keyword, Integer count);

	Instruction findByInstructionName(String name);
	
}
