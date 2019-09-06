package br.com.bruno.ecommerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bruno.ecommerce.domain.Pedido;
import br.com.bruno.ecommerce.repository.PedidoRepository;
import br.com.bruno.ecommerce.service.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = pedidoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado: " + id +
				", Tipo: " + Pedido.class.getName()));
	}
}
