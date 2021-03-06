package restfulapi.dao;

import restfulapi.models.User;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jnap
 */

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao{
    @Override
	public User findById(int id) {
		return getByKey(id);
	}

        @Override
	public void saveUser(User user) {
		persist(user);
	}
	
        @Override
	public void saveOrUpdate(User user){
		super.saveOrUpdate(user);
	}
	
        @Override
	public void deleteUserById(int id) {
		Query query = getSession().createNativeQuery("delete from user where id = :id");
		query.setParameter("id", id);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
        @Override
	public List<User> findAllUsers() {
		CriteriaQuery<User> criteria = createEntityCriteria();
                Root<User> root = criteria.from(User.class);
                criteria.select(root);
                Query query = getSession().createQuery(criteria);
		return (List<User>) query.getResultList();
	}

        @Override
	public User findUserById(int id) {
                CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> root = criteria.from(User.class);
                criteria.select(root).where(builder.equal(root.get("id"), id));
                Query<User> q=getSession().createQuery(criteria);
		return (User) q.getSingleResult();
	}
}
