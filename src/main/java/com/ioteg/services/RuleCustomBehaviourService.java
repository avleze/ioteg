package com.ioteg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ioteg.model.CustomBehaviour;
import com.ioteg.model.RuleCustomBehaviour;
import com.ioteg.repositories.RuleCustomBehaviourRepository;

@Service
public class RuleCustomBehaviourService {

	@Autowired
	private CustomBehaviourService customBehaviourService;
	
	@Autowired
	private RuleCustomBehaviourRepository ruleCustomBehaviourRepository;
	
	
	public RuleCustomBehaviour createRuleCustomBehaviour(Long customBehaviourId,
			RuleCustomBehaviour ruleCustomBehaviour) throws ResourceNotFoundException {
	
		RuleCustomBehaviour storedRuleCustomBehaviour = ruleCustomBehaviourRepository.save(ruleCustomBehaviour);
		
		CustomBehaviour customBehaviour = customBehaviourService.loadById(customBehaviourId);
		customBehaviour.getRules().add(storedRuleCustomBehaviour);
		customBehaviourService.save(customBehaviour);

		return storedRuleCustomBehaviour;
	}
	
	public RuleCustomBehaviour modifyRuleCustomBehaviour(Long ruleCustomBehaviourId,
			RuleCustomBehaviour ruleCustomBehaviour) throws ResourceNotFoundException {
	
		RuleCustomBehaviour storedRuleCustomBehaviour =  ruleCustomBehaviourRepository.findById(ruleCustomBehaviourId).orElseThrow(() -> new ResourceNotFoundException("RuleCustomBehaviour " + ruleCustomBehaviourId, "RuleCustomBehaviour not found."));
		
		storedRuleCustomBehaviour.setMax(ruleCustomBehaviour.getMax());
		storedRuleCustomBehaviour.setMin(ruleCustomBehaviour.getMin());
		storedRuleCustomBehaviour.setSequence(ruleCustomBehaviour.getSequence());
		storedRuleCustomBehaviour.setValue(ruleCustomBehaviour.getValue());
		storedRuleCustomBehaviour.setWeight(ruleCustomBehaviour.getWeight());

		return ruleCustomBehaviourRepository.save(storedRuleCustomBehaviour);
	}
	
	public void removeRuleCustomBehaviour(Long ruleCustomBehaviourId) {
		ruleCustomBehaviourRepository.deleteById(ruleCustomBehaviourId);
	}

	public RuleCustomBehaviour loadById(Long ruleCustomBehaviourId) throws ResourceNotFoundException {
		return ruleCustomBehaviourRepository.findById(ruleCustomBehaviourId)
				.orElseThrow(() -> new ResourceNotFoundException("RuleCustomBehaviour " + ruleCustomBehaviourId, "RuleCustomBehaviour not found."));
	}

	public RuleCustomBehaviour save(RuleCustomBehaviour ruleCustomBehaviour) {
		return ruleCustomBehaviourRepository.save(ruleCustomBehaviour);
	}
}
