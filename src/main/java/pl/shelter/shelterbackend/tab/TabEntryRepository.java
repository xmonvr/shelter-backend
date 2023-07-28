package pl.shelter.shelterbackend.tab;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TabEntryRepository extends JpaRepository<TabEntry, Long> {

}
