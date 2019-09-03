package br.com.bruno.ecommerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bruno.ecommerce.domain.Cliente;
import br.com.bruno.ecommerce.repository.ClienteRepository;
import br.com.bruno.ecommerce.service.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id +
				", Tipo: " + Cliente.class.getName()));
	}
}