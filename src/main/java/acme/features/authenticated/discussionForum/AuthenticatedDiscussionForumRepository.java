
package acme.features.authenticated.discussionForum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.discussionForum.DiscussionForum;
import acme.framework.entities.Authenticated;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedDiscussionForumRepository extends AbstractRepository {

	@Query("select i from DiscussionForum i where i.id = ?1")
	DiscussionForum findOneById(int id);

	@Query("select i from DiscussionForum i")
	Collection<DiscussionForum> findManyAll();

	@Query("select distinct p.discussionForum from Participant p where p.authenticated.id = ?1")
	Collection<DiscussionForum> findManyByAuthenticatedId(int authenticatedId);

	@Query("select count(p)>0 from Participant p where p.discussionForum.id =?1 and p.authenticated.id = ?2")
	Boolean findExistsDiscussionForumParticipant(int discussionForumId, int authenticatedId);

	@Query("select a from Authenticated a where a.id = ?1")
	Authenticated findOneAuthenticatedById(int id);

}
