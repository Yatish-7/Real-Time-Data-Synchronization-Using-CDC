package com.newmeksolutions.repository.pos;

import com.newmeksolutions.model.pos.POSKPHB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface POSKPHBRepository extends JpaRepository<POSKPHB, Integer> 
{
	
}
