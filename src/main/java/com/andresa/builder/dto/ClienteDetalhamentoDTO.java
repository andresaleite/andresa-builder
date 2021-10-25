package com.andresa.builder.dto;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.andresa.builder.domain.Cliente;
import com.andresa.builder.domain.Endereco;
import com.andresa.builder.domain.enums.TipoCliente;
import com.andresa.builder.util.Util;

public class ClienteDetalhamentoDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;	

	private Integer id;
	private String nome;
	private Date dataNascimento;
	private String email;
	private String cpfOuCnpj;
	private Integer tipo;
	private List<Endereco> enderecos = new ArrayList<>();
	private Set<String> telefones = new HashSet<>();
	private int idade;
	private boolean aniversario;
	
	public ClienteDetalhamentoDTO(Cliente cliente) {
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.dataNascimento = cliente.getDataNascimento();
		this.email = cliente.getEmail();
		this.cpfOuCnpj = cliente.getCpfOuCnpj();
		this.enderecos = cliente.getEnderecos();
		this.telefones = cliente.getTelefones();
		this.tipo =  (cliente.getTipo() == null) ? null : cliente.getTipo().getCod();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}
	
	public String getDataNascimentoTexto() throws ParseException {
		if(getDataNascimento() != null) {
			return Util.converteData(getDataNascimento());
		}
		return "Data não informada!";
	}
	

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public TipoCliente getTipo() {
		return TipoCliente.toEnum(tipo);
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}

	public int getIdade() {
		if(getDataNascimento() != null) {
			return Util.calculoIdade(getDataNascimento());
		}
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public boolean isAniversario() {
		if(getDataNascimento() != null) {
			return Util.comparaDiaEMes(getDataNascimento(), new Date());
		}
		return aniversario;
	}

	public void setAniversario(boolean aniversario) {
		this.aniversario = aniversario;
	}

}
