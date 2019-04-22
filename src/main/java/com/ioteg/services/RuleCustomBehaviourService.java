package com.ioteg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.ioteg.model.CustomBehaviour;
import com.ioteg.model.RuleCustomBehaviour;
import com.ioteg.repositories.RuleCustomBehaviourRepository;

@Service
public class RuleCustomBehaviourService {

	private CustomBehaviourService customBehaviourService;
	private RuleCustomBehaviourRepository ruleCustomBehaviourRepository;
	private UserService userService;

	/**
	 * @param customBehaviourService
	 * @param ruleCustomBehaviourRepository
	 * @param userService
	 */
	@Autowired
	public RuleCustomBehaviourService(CustomBehaviourService customBehaviourService,
			RuleCustomBehaviourRepository ruleCustomBehaviourRepository, UserService userService) {
		super();
		this.customBehaviourService = customBehaviourService;
		this.ruleCustomBehaviourRepository = ruleCustomBehaviourRepository;
		this.userService = userService;
	}

	@PreAuthorize("hasPermission(#customBehaviourId, 'CustomBehaviour', 'OWNER') or hasRole('ADMIN')")
	public RuleCustomBehaviour createRuleCustomBehaviour(Long customBehaviourId,
			RuleCustomBehaviour ruleCustomBehaviour) throws EntityNotFoundException {

		ruleCustomBehaviour.setOwner(userService.loadLoggedUser());
		RuleCustomBehaviour storedRuleCustomBehaviour = ruleCustomBehaviourRepository.save(ruleCustomBehaviour);

		CustomBehaviour customBehaviour = customBehaviourService.loadById(customBehaviourId);
		customBehaviour.getRules().add(storedRuleCustomBehaviour);
		customBehaviourService.save(customBehaviour);

		return storedRuleCustomBehaviour;
	}

	@PreAuthorize("hasPermission(#ruleCustomBehaviourId, 'RuleCustomBehaviour', 'OWNER') or hasRole('ADMIN')")
	public RuleCustomBehaviour modifyRuleCustomBehaviour(Long ruleCustomBehaviourId,
			RuleCustomBehaviour ruleCustomBehaviour) throws EntityNotFoundException {

		RuleCustomBehaviour storedRuleCustomBehaviour = this.loadById(ruleCustomBehaviourId);

		storedRuleCustomBehaviour.setMax(ruleCustomBehaviour.getMax());
		storedRuleCustomBehaviour.setMin(ruleCustomBehaviour.getMin());
		storedRuleCustomBehaviour.setSequence(ruleCustomBehaviour.getSequence());
		storedRuleCustomBehaviour.setValue(ruleCustomBehaviour.getValue());
		storedRuleCustomBehaviour.setWeight(ruleCustomBehaviour.getWeight());

		return ruleCustomBehaviourRepository.save(storedRuleCustomBehaviour);
	}

	@PreAuthorize("hasPermission(#ruleCustomBehaviourId, 'RuleCustomBehaviour', 'OWNER') or hasRole('ADMIN')")
	public void removeRuleCustomBehaviour(Long ruleCustomBehaviourId) {
		ruleCustomBehaviourRepository.deleteById(ruleCustomBehaviourId);
	}

	@PreAuthorize("hasPermission(#ruleCustomBehaviourId, 'RuleCustomBehaviour', 'OWNER') or hasRole('ADMIN')")
	public RuleCustomBehaviour loadById(Long ruleCustomBehaviourId) throws EntityNotFoundException {
		return ruleCustomBehaviourRepository.findById(ruleCustomBehaviourId)
				.orElseThrow(() -> new EntityNotFoundException(RuleCustomBehaviour.class, "id", ruleCustomBehaviourId.toString()));
	}

	public RuleCustomBehaviour save(RuleCustomBehaviour ruleCustomBehaviour) {
		return ruleCustomBehaviourRepository.save(ruleCustomBehaviour);
	}
}
