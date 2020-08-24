
package acme.features.entrepreneur.investmentRounds;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.investmentRounds.InvestmentRound;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface EntrepreneurInvestmentRoundRepository extends AbstractRepository {

	@Query("select i from InvestmentRound i where i.id = ?1")
	InvestmentRound findOneById(int id);

	@Query("select i from InvestmentRound i")
	Collection<InvestmentRound> findManyAll();

	@Query("select a.investmentRound from Activity a where a.investmentRound.entrepreneur.id =?1")
	Collection<InvestmentRound> findInvestmentRoundsByEntrepreneurId(int id);

}
