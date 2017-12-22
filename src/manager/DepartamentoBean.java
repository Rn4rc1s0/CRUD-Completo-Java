package manager;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import model.Departamento;
import model.Funcionario;
import persistence.DepartamentoDao;
import persistence.FuncionarioDao;

@ManagedBean(name = "dBean")
@ViewScoped
public class DepartamentoBean {

	private Departamento d;
	private Departamento d2;
	private List<Departamento> listaD;

	public DepartamentoBean() {
		d = new Departamento();
		listaD = new ArrayList<Departamento>();

	}

	public Departamento getD() {
		return d;
	}

	public void setD(Departamento d) {
		this.d = d;
	}

	public Departamento getD2() {
		return d2;
	}

	public void setD2(Departamento d2) {
		this.d2 = d2;
	}

	public List<Departamento> getListaD() {
		return listaD;
	}

	public void setListaD(List<Departamento> listaD) {
		this.listaD = listaD;
	}

	public String cadastrar() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {

			Boolean valido = true;
			Pattern p1 = Pattern.compile("[A-Za-z ]{3,30}");
			Matcher m1 = p1.matcher(d.getNomeDepartamento());

			if (!m1.matches()) {
				valido = false;
				fc.addMessage("form3:nome", new FacesMessage("Nome é inválido"));
			}

			if (valido == true) {
				// cadastrar no banco de dados
				new DepartamentoDao().cadastrar(d);
				fc.addMessage("form3", new FacesMessage("Departamento cadastrado com sucesso!"));

				d = new Departamento();
			}

		} catch (Exception e) {
			fc.addMessage("form3", new FacesMessage("Erro: " + e.getMessage()));
		}
		return null;
	}

	public String buscar() {

		FacesContext fc = FacesContext.getCurrentInstance();
		try {

			if (d.getNomeDepartamento().length() >= 1) {
				// Carregar a lista de Departamentos
				listaD = new DepartamentoDao().buscar(d.getNomeDepartamento());

			} else {
				fc.addMessage("form4", new FacesMessage(FacesMessage.SEVERITY_WARN, "ERRO NOME",
						"Preencha o campo nome do departamento para a busca!"));
			}

		} catch (Exception e) {
			fc.addMessage("form4", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO", e.getMessage()));
		}

		// Manter na memsma pagina que chamou o metodo
		return null;
	}

	public String buscar2() {

		FacesContext fc = FacesContext.getCurrentInstance();
		try {

			listaD = new DepartamentoDao().buscarDpVazio();

		} catch (Exception e) {
			fc.addMessage("form4", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO", e.getMessage()));
		}

		// Manter na memsma pagina que chamou o metodo
		return null;
	}

	public String detalhes() {
		if (d2 == null) {
			return "buscar";
		} else {
			// Se o d2 nao for null gravar o d2 em memória FLASH
			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("d", d2);
			return "detalhe";
		}
	}

	public String editar() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			DepartamentoDao dpDao = new DepartamentoDao();
			dpDao.atualizar(d2);

			fc.addMessage("form3", new FacesMessage("Departamento editado com sucesso!"));
		} catch (Exception e) {
			e.printStackTrace();
			fc.addMessage("form3", new FacesMessage("Departamento não editado!"));
		}
		return null;
	}

	public String excluir() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			DepartamentoDao dpDao = new DepartamentoDao();
			dpDao.excluir(d2);

			fc.addMessage("form4", new FacesMessage("Departamento excluido com sucesso!"));
		} catch (Exception e) {
			e.printStackTrace();
			fc.addMessage("form4", new FacesMessage("Departamento não excluido!"));
		}
		// Depois excluir buscar novamente
		return buscar();
	}

	// Metodo sera chamado DEPOIS do construtor
	@PostConstruct
	public void carregar() {
		// Se exitir o objeto d na memoria flash
		if (FacesContext.getCurrentInstance().getExternalContext().getFlash().get("d") != null) {
			d2 = (Departamento) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("d");
		}
	}
}
