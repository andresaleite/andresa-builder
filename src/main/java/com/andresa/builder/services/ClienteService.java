package com.andresa.builder.services;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.andresa.builder.domain.Cidade;
import com.andresa.builder.domain.Cliente;
import com.andresa.builder.domain.Endereco;
import com.andresa.builder.domain.enums.TipoCliente;
import com.andresa.builder.dto.ClienteDTO;
import com.andresa.builder.dto.ClienteNewDTO;
import com.andresa.builder.dto.ClienteUpdateDTO;
import com.andresa.builder.repositories.ClienteRepository;
import com.andresa.builder.repositories.EnderecoRepository;
import com.andresa.builder.servicies.exceptions.DataIntegrityException;
import com.andresa.builder.servicies.exceptions.ObjectNotFoundException;


@Service
public class ClienteService {
	
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepo;
	

	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName(), null));
		
	}
	
	
	public Cliente findByCpf(String cpf) {
	
		Cliente obj = repo.findByCpfOuCnpj(cpf);
		if (obj == null) {
			throw new ObjectNotFoundException("CPF não encontrado!");
		}
		return obj;
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest =  PageRequest.of(page, linesPerPage, Direction.valueOf(direction),	orderBy);
		return repo.findAll(pageRequest);
	}
	
	public List<Cliente> findAll() {
		return repo.findAll();
		
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.saveAndFlush(obj);
		enderecoRepo.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = this.find(obj.getId());
		updateData(newObj, obj);
		return (Cliente) repo.saveAndFlush(newObj);
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		newObj.setEnderecos(obj.getEnderecos());
		newObj.setTelefones(obj.getTelefones());
		newObj.setDataNascimento(obj.getDataNascimento());
	}

	public void delete(Integer id) {
		this.find(id);
		try {
			repo.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir pois há entidades relacionadas.");
		}
	}
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(),  null, null, objDTO.getDataNascimento()); 
	}
	
	public Cliente fromNewDTO(ClienteNewDTO objNewDTO) {
		Cliente cli 	= new Cliente(null, objNewDTO.getNome(), objNewDTO.getEmail(), objNewDTO.getCpfOuCnpj(), TipoCliente.toEnum(objNewDTO.getTipo()), objNewDTO.getDataNascimento());
		
		Cidade cid		= new Cidade(objNewDTO.getCidadeId(), null, null);
		
		Endereco end 	= new Endereco(null, objNewDTO.getLogradouro(), 
						objNewDTO.getNumero(), objNewDTO.getComplemento(), 
						objNewDTO.getBairro(), objNewDTO.getCep(), cli, cid);
		
		cli.getEnderecos().add(end);
		cli.getTelefones().addAll(Arrays.asList(objNewDTO.getTelefone1(), objNewDTO.getTelefone2(), objNewDTO.getTelefone3()));
		
		return cli;
	}
	
	public Cliente fromAlteracaoDTO(ClienteUpdateDTO objAlteracaoDTO) {
		Cliente cli 	= new Cliente(objAlteracaoDTO.getId(), objAlteracaoDTO.getNome(), objAlteracaoDTO.getEmail(), objAlteracaoDTO.getCpfOuCnpj(), TipoCliente.toEnum(objAlteracaoDTO.getTipo()), objAlteracaoDTO.getDataNascimento());
		
		Cidade cid		= new Cidade(objAlteracaoDTO.getCidadeId(), null, null);
		
		Endereco end 	= new Endereco(null, objAlteracaoDTO.getLogradouro(), 
						objAlteracaoDTO.getNumero(), objAlteracaoDTO.getComplemento(), 
						objAlteracaoDTO.getBairro(), objAlteracaoDTO.getCep(), cli, cid);
		
		cli.getEnderecos().add(end);
		cli.getTelefones().addAll(Arrays.asList(objAlteracaoDTO.getTelefone1(), objAlteracaoDTO.getTelefone2(), objAlteracaoDTO.getTelefone3()));
		
		return cli;
	}
	
}
