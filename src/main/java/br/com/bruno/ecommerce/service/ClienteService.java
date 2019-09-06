package br.com.bruno.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.bruno.ecommerce.domain.Cidade;
import br.com.bruno.ecommerce.domain.Cliente;
import br.com.bruno.ecommerce.domain.Endereco;
import br.com.bruno.ecommerce.domain.enums.TipoCliente;
import br.com.bruno.ecommerce.dto.ClienteDTO;
import br.com.bruno.ecommerce.dto.ClienteNewDTO;
import br.com.bruno.ecommerce.repository.ClienteRepository;
import br.com.bruno.ecommerce.repository.EnderecoRepository;
import br.com.bruno.ecommerce.service.exception.DataIntegrityExcpetion;
import br.com.bruno.ecommerce.service.exception.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id +
				", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityExcpetion("Não é possível excluir o cliente!");
		}		
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}

	public Page<Cliente> findPage(Integer page /*número da página que vai ser carregada*/, Integer linesPerPage /*número de linhas que serão retornados*/, String orderBy /*tipo de ordenação*/, String direction /*ascendente ou descendente*/) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteNewDTO obj) {
		Cliente cli = new Cliente(null, obj.getNome(), obj.getEmail(), obj.getCpfOuCnpj(), TipoCliente.toEnum(obj.getTipo()));
		Cidade cid = new Cidade(obj.getCidadeId(), null, null);
		Endereco end = new Endereco(null, obj.getLogradouro(), obj.getNumero(), obj.getComplemento(), obj.getBairro(), obj.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(obj.getTelefone1());
		if (obj.getTelefone2() != null) {
			cli.getTelefones().add(obj.getTelefone2());
		}

		if (obj.getTelefone3() != null) {
			cli.getTelefones().add(obj.getTelefone3());
		}
		return cli;
	}

	public Cliente fromDTO(ClienteDTO obj) {
		return new Cliente(obj.getId(), obj.getNome(), obj.getEmail(), null, null);
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}