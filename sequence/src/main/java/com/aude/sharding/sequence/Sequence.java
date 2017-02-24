package com.aude.sharding.sequence;

import com.aude.sharding.sequence.exception.SequenceException;

public interface Sequence {
	
	Long next() throws SequenceException;

}
