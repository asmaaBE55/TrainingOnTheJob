package user.store.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import user.store.management.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}