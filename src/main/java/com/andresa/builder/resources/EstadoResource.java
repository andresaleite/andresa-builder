package com.andresa.builder.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.andresa.builder.domain.Cidade;
import com.andresa.builder.domain.Estado;
import com.andresa.builder.dto.CidadeDTO;
import com.andresa.builder.dto.EstadoDTO;
import com.andresa.builder.services.CidadeService;
import com.andresa.builder.services.EstadoService;


@RestController
@RequestMapping(value="/estados")
public class EstadoResource {

	@Autowired
	private EstadoService serv;
	
	@Autowired
	private CidadeService cidadeService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> find() {
		List<Estado> lista = serv.find();
		List<EstadoDTO> listDto = lista.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="/{idEstado}/cidades", method=RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> find(@PathVariable Integer idEstado) {
		List<Cidade> lista = cidadeService.find(idEstado);
		List<CidadeDTO> listDto = lista.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}

}