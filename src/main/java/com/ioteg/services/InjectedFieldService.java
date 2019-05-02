package com.ioteg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.ioteg.model.Block;
import com.ioteg.model.InjectedField;
import com.ioteg.repositories.InjectedFieldRepository;

@Service
public class InjectedFieldService {

	private UserService userService;
	private BlockService blockService;
	private InjectedFieldRepository injectedFieldRepository;

	/**
	 * @param userService
	 * @param blockService
	 * @param injectedFieldRepository
	 */
	@Autowired
	public InjectedFieldService(UserService userService, BlockService blockService,
			InjectedFieldRepository injectedFieldRepository) {
		super();
		this.userService = userService;
		this.blockService = blockService;
		this.injectedFieldRepository = injectedFieldRepository;
	}

	@PreAuthorize("hasPermission(#blockId, 'Block', 'OWNER') or hasRole('ADMIN')")
	public InjectedField createInjectedField(Long blockId, InjectedField injectedField) throws EntityNotFoundException {

		Block block = blockService.loadByIdWithInjectedFields(blockId);
		injectedField.setOwner(userService.loadLoggedUser());
		InjectedField storedInjectedField = injectedFieldRepository.save(injectedField);
		block.getInjectedFields().add(storedInjectedField);
		blockService.save(block);

		return storedInjectedField;
	}

	@PreAuthorize("hasPermission(#blockId, 'Block', 'OWNER') or hasRole('ADMIN')")
	public void removeInjectedFieldFromBlock(Long blockId, Long injectedFieldId) throws EntityNotFoundException {
		Block block = blockService.loadByIdWithInjectedFields(blockId);
		InjectedField injectedField = injectedFieldRepository.findById(injectedFieldId)
				.orElseThrow(() -> new EntityNotFoundException(InjectedField.class, "id", injectedFieldId.toString()));
		block.getInjectedFields().remove(injectedField);
		blockService.save(block);

		injectedFieldRepository.deleteById(injectedFieldId);
	}
}
