package net.shopxx.service;

import java.util.List;

import net.shopxx.entity.Instruction;

public interface InstructionService extends BaseService<Instruction, Long>{

	List<Instruction> search(String keyword, Integer count);

	Instruction findByInstructionName(String name);
	
}
