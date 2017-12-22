package persistence;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Departamento;


public class DepartamentoDao {

	Session session;
	Transaction transaction;
	Query query;
	SQLQuery sqlQuery;
	Criteria criteria;

	public void cadastrar(Departamento d) throws Exception {
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		session.save(d);
		transaction.commit();
		session.close();
	}

	public List<Departamento> buscar(String nome) throws Exception {
		session = HibernateUtil.getSessionFactory().openSession();
		query = session.createQuery("from Departamento as d where d.nomeDepartamento like :param1");
		query.setParameter("param1", nome + "%");
		List<Departamento> lista = query.list();
		session.close();
		return lista;
	}
	
	public List<Departamento> buscarDpVazio() throws Exception {
		session = HibernateUtil.getSessionFactory().openSession();
		sqlQuery = session.createSQLQuery("select nomeDepartamento,idDepartamento from departamento where idDepartamento not in(select idDepartamento from departamento natural join funcionario)");
		List<Departamento> lista = sqlQuery.list();
		session.close();
		return lista;
	}
	
	
	

	public Departamento buscarId(Integer id) throws Exception {
		session = HibernateUtil.getSessionFactory().openSession();
		Departamento d = (Departamento) session.get(Departamento.class, id);
		session.close();
		return d;
	}

	public void excluir(Departamento d) throws Exception {
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		session.delete(d);
		transaction.commit();
		session.close();
	}

	public void atualizar(Departamento d) throws Exception {
		session = HibernateUtil.getSessionFactory().openSession();
		transaction = session.beginTransaction();
		session.update(d);
		transaction.commit();
		session.close();
	}

}
