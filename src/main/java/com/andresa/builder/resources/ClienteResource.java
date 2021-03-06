package com.andresa.builder.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.andresa.builder.domain.Cliente;
import com.andresa.builder.dto.ClienteDTO;
import com.andresa.builder.dto.ClienteDetalhamentoDTO;
import com.andresa.builder.dto.ClienteNewDTO;
import com.andresa.builder.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService serv;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente obj = serv.find(id);
		
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(value="/detalhe/{id}", method=RequestMethod.GET)
	public ResponseEntity<ClienteDetalhamentoDTO> findDetalhe(@PathVariable Integer id) {
		Cliente cli = serv.find(id);
		ClienteDetalhamentoDTO detalhesCliente =  new ClienteDetalhamentoDTO(cli);
		return ResponseEntity.ok().body(detalhesCliente);
	}
	
	@RequestMapping(value="/cpf", method=RequestMethod.GET)
	public ResponseEntity<Cliente> findCpf(@RequestParam(value="value") String cpf) {
		Cliente obj = serv.findByCpf(cpf);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> list = serv.findAll();
		List<ClienteDTO> listDTO = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@RequestMapping(value="/pages",method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value="page", defaultValue = "0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value="direction", defaultValue = "ASC") String direction) {
		Page<Cliente> list = serv.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listDTO = list.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objNewDTO) {
		Cliente obj = serv.fromNewDTO(objNewDTO);
		obj = serv.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
		
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id) {
		Cliente obj = serv.fromDTO(objDTO);
		obj.setId(id);
		obj = serv.update(obj);
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		serv.delete(id);
		return ResponseEntity.noContent().build();
	}
}