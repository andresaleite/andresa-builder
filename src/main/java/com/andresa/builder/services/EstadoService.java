package com.andresa.builder.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andresa.builder.domain.Estado;
import com.andresa.builder.repositories.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository repo;
	
	public List<Estado> find() {
		return repo.findAllByOrderByNome();
		
	}
}
