package com.andresa.builder.dto;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.andresa.builder.domain.Cliente;
import com.andresa.builder.services.validations.ClienteUpdate;
import com.andresa.builder.util.Util;
import com.fasterxml.jackson.annotation.JsonFormat;

@ClienteUpdate
public class ClienteDTO  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dataNascimento;	
	private int idade;
	private boolean aniversario;
	private String cpfOuCnpj;
	
	@NotEmpty(message = "Preenchimento obrigatório.")
	@Length(min = 5, max=120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;
	
	@NotEmpty(message = "Preenchimento obrigatório.")
	@Email(message = "E-mail inválido.")
	private String email;
	
	public ClienteDTO() {
	}
	
	
	public ClienteDTO(Cliente obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.email = obj.getEmail();
		this.dataNascimento = obj.getDataNascimento();
		this.cpfOuCnpj = obj.getCpfOuCnpj();
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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
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


	public Date getDataNascimento() {
		return dataNascimento;
	}


	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	public String getDataNascimentoTexto() throws ParseException {
		if(getDataNascimento() != null) {
			return Util.converteData(getDataNascimento());
		}
		return "Data não informada!";
	}


	public boolean isAniversario() {
		return aniversario;
	}


	public void setAniversario(boolean aniversario) {
		this.aniversario = aniversario;
	}


	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}


	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

}
