package project.muramidara.hotelsapi.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.muramidara.hotelsapi.database.entity.Contacts;

public interface ContactsRepository extends JpaRepository<Contacts, Long> {
}
