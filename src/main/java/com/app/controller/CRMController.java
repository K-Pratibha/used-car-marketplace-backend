package com.app.controller;

import com.app.entity.evaluation.Agent;
import com.app.entity.evaluation.Area;
import com.app.entity.evaluation.CustomerVisit;
import com.app.repository.AgentRepository;
import com.app.repository.AreaRepository;
import com.app.repository.CustomerVisitRepository;
import com.app.service.SMSService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/crm")
public class CRMController {

    private AreaRepository areaRepository;
    private AgentRepository agentRepository;
    private CustomerVisitRepository customerRepository;
    private SMSService smsService;

    public CRMController(AreaRepository areaRepository, AgentRepository agentRepository, CustomerVisitRepository customerRepository, SMSService smsService) {
        this.areaRepository = areaRepository;
        this.agentRepository = agentRepository;
        this.customerRepository = customerRepository;
        this.smsService = smsService;
    }

    //firstly work on searching of customer agent based on the pinCode
    //perform search operation
    @GetMapping
    public ResponseEntity<List<Area>> searchAgent(
            @RequestParam int pinCode
    ){
        //Now returning list of area based on the pinCode
        List<Area> areas = areaRepository.findByPinCode(pinCode);
        return new ResponseEntity<>(areas, HttpStatus.OK);
    }

    @PutMapping
    public String allocateAgent(
        @RequestParam long customerId,
        @RequestParam long agentId
    ){
        //Implement logic to allocate agent based on the customer id and agent id
        //And return the allocated agent details

        Agent agent = null;
        Optional<Agent> opAgent = agentRepository.findById(agentId);
        if(opAgent.isPresent()){
            agent = opAgent.get();

            //update customer with allocated agent
            //customerVisitRepository.updateCustomerAgent(customerId, agentId)
        }
        CustomerVisit customerVisit = customerRepository.findById(customerId).get();
        customerVisit.setAgent(agent);

        customerRepository.save(customerVisit);

        //SMS notification to customer about the allocated agent
        smsService.sendSMS("+918825162593","Agent is now allocated - 9xxxxxxxxx");
        //WhatsApp notification to customer about the allocated agent
        smsService.sendWhatsApp("+918825162593","Agent is now allocated - 9xxxxxxxxx");
        return "Agent is now allocated";
    }

}
