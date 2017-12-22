package persistence;

import java.util.List;

import model.Funcionario;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class FuncionarioDao  {

	Session session;
	Transaction transaction;
	Query query;
	Criteria criteria;
	
	public void cadastrar(Funcionario f)throws Exception{
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		session.save(f);
		transaction.commit();
		session.close();
	}
	
	public List<Funcionario> buscar(String nome)throws Exception{
		session = HibernateUtil.getSessionFactory().openSession();
		query = session.createQuery("from Funcionario as f where f.nome like :param1");
		query.setParameter("param1", nome + "%");
		List<Funcionario> lista = query.list();
		session.close();
		return lista;
	}
	
	public Funcionario buscarId(Integer id)throws Exception{
		session = HibernateUtil.getSessionFactory().openSession();
		Funcionario f = (Funcionario) session.get(Funcionario.class, id);
		session.close();
		return f;
	}
	
	public void excluir(Funcionario f)throws Exception{
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		session.delete(f);
		transaction.commit();
		session.close();
	}
	
	public void atualizar(Funcionario f)throws Exception{
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		session.update(f);
		transaction.commit();
		session.close();
	}
}
