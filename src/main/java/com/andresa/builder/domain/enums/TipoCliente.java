package com.andresa.builder.domain.enums;

public enum TipoCliente {

	PESSOAFISICA (1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	private int cod;
	private String descricao;
	
	private TipoCliente(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	
	public static TipoCliente toEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		
		for (TipoCliente t: TipoCliente.values()) {
			if(t.getCod() == cod) {
				return t;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: "+cod);
	}
	
	
	
}
