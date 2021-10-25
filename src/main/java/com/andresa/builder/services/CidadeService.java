package com.andresa.builder.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andresa.builder.domain.Cidade;
import com.andresa.builder.repositories.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository repo;
	
	public List<Cidade> find(Integer idEstado) {
		
		return repo.findCidades(idEstado);
		
	}
	
}
