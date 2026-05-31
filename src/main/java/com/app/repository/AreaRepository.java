package com.app.repository;

import com.app.entity.evaluation.Agent;
import com.app.entity.evaluation.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface AreaRepository extends JpaRepository<Area, Long> {

    //giving list of agent as multiple agents can be associated with single area
    List<Area> findByPinCode(long pinCode);

}