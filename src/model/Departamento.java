package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table
public class Departamento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3135320912020767306L;

	
	
	@Id
	 @Column(name="idDepartamento")
	private Integer idDepartamento;

	 @Column
	private String nomeDepartamento;
	 
	 
	 
	public Departamento(Integer idDepartamento, String nomeDepartamento) {
		super();
		this.idDepartamento = idDepartamento;
		this.nomeDepartamento = nomeDepartamento;
	}

	public Departamento() {
		super();
	}

	public Integer getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(Integer idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public String getNomeDepartamento() {
		return nomeDepartamento;
	}

	public void setNomeDepartamento(String nomeDepartamento) {
		this.nomeDepartamento = nomeDepartamento;
	}

	@Override
	public String toString() {
		return "Departamento [idDepartamento=" + idDepartamento + ", nomeDepartamento=" + nomeDepartamento + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idDepartamento == null) ? 0 : idDepartamento.hashCode());
		result = prime * result + ((nomeDepartamento == null) ? 0 : nomeDepartamento.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Departamento other = (Departamento) obj;
		if (idDepartamento == null) {
			if (other.idDepartamento != null)
				return false;
		} else if (!idDepartamento.equals(other.idDepartamento))
			return false;
		if (nomeDepartamento == null) {
			if (other.nomeDepartamento != null)
				return false;
		} else if (!nomeDepartamento.equals(other.nomeDepartamento))
			return false;
		return true;
	}
	
	

}
