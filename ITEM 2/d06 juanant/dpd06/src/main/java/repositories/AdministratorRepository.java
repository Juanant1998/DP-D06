package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.SystemConfig;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer>{

	@Query("select s1 from SystemConfig s1")
	public List<SystemConfig> returnSystemConfig();
}
