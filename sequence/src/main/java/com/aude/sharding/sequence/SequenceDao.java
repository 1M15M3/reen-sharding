package com.aude.sharding.sequence;

import com.aude.sharding.sequence.exception.SequenceException;

public interface SequenceDao {
	
	SequenceRange nextRange(String name) throws SequenceException;

	SequenceRange nextRange(String name, String tableName, int rangeSize) throws SequenceException;

}
