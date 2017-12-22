package manager;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import persistence.FuncionarioDao;
import model.Departamento;
import model.Funcionario;

@ManagedBean(name="fBean")
@ViewScoped
public class FuncionarioBean {

	private Funcionario f;
	//private Departamento d;
	private List<Funcionario> listaF;
	
	//Sera p funcionario editado
	private Funcionario f2;
	
	public FuncionarioBean() {
		f = new Funcionario();
	//	d = new Departamento();
		listaF = new ArrayList<Funcionario>();
	}

	public Funcionario getF2() {
		return f2;
	}

	public void setF2(Funcionario f2) {
		this.f2 = f2;
	}


	public List<Funcionario> getListaF() {
		return listaF;
	}

	public void setListaF(List<Funcionario> listaF) {
		this.listaF = listaF;
	}


	public Funcionario getF() {
		return f;
	}

	public void setF(Funcionario f) {
		this.f = f;
	}
	
	public String cadastrar(){
		FacesContext fc = FacesContext.getCurrentInstance();
		try{
			
			Boolean valido = true;
			Pattern p1 = Pattern.compile("[A-Za-z ]{3,30}");
			Matcher m1 = p1.matcher(f.getNome());
			
			if(!m1.matches()){
				valido = false;
				fc.addMessage("form1:nome", new FacesMessage("Nome é inválido"));
			}
			
			if(f.getSalario() < 800){
				valido = false;
				fc.addMessage("form1:salario", new FacesMessage("Salário deve ser superior a R$ 800,00"));
			}
			
			if(valido == true){
				//cadastrar no banco de dados
				new FuncionarioDao().cadastrar(f);
				fc.addMessage("form1", new FacesMessage("Funcionario cadastrado com sucesso!"));
				
				f = new Funcionario();
			}
			
		}catch(Exception e){
			fc.addMessage("form1", new FacesMessage("Erro: " + e.getMessage()));
		}		
		return null;
	}
	
	public String buscar(){
		
		FacesContext fc = FacesContext.getCurrentInstance();
		try{
			
			if(f.getNome().length() >= 1){
				//Carregar a lista de Funcionarios
				listaF = new FuncionarioDao().buscar(f.getNome());
				
			}else{
				fc.addMessage("form2", 
						new FacesMessage(FacesMessage.SEVERITY_WARN, "ERRO NOME", 
								"Preencha o campo nome para a busca!"));
			}
			
		}catch(Exception e){
			fc.addMessage("form2", 
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO", e.getMessage()));
		}
		
		//Manter na memsma pagina que chamou o metodo
		return null;
	}
	
	public String detalhes(){
		if(f2 == null){
			return "buscar";
		}else{
			//Se o f2 nao for null gravar o f2 em memória FLASH
			FacesContext.getCurrentInstance().getExternalContext()
			.getFlash().put("f", f2);
			return "detalhes";			
		}
	}
	
	public String editar(){
		FacesContext fc = FacesContext.getCurrentInstance();
		try{
			FuncionarioDao fd = new FuncionarioDao();
			fd.atualizar(f2);
			
			fc.addMessage("form1", new FacesMessage("Funcionario editado com sucesso!"));
		}catch(Exception e){
			e.printStackTrace();
			fc.addMessage("form1", new FacesMessage("Funcionario não editado!"));
		}
		return null;
	}
	
	public String excluir(){
		FacesContext fc = FacesContext.getCurrentInstance();
		try{
			FuncionarioDao fd = new FuncionarioDao();
			fd.excluir(f2);
			
			fc.addMessage("form2", new FacesMessage("Funcionario excluido com sucesso!"));
		}catch(Exception e){
			e.printStackTrace();
			fc.addMessage("form2", new FacesMessage("Funcionario não editado!"));
		}
		//Depois excluir buscar novamente
		return buscar();
	}
	
	//Metodo sera chamado DEPOIS do construtor
	@PostConstruct
	public void carregar(){
		//Se exitir o objeto f na memoria flash
		if(FacesContext.getCurrentInstance().getExternalContext()
				.getFlash().get("f") != null){
			f2 = (Funcionario) FacesContext.getCurrentInstance().getExternalContext()
					.getFlash().get("f");
		}
	}
}
