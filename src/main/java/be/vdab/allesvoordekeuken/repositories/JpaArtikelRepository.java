package be.vdab.allesvoordekeuken.repositories;

import be.vdab.allesvoordekeuken.domain.Artikel;
import org.hibernate.type.EntityType;

import javax.persistence.EntityManager;
import java.util.Optional;

public class JpaArtikelRepository implements ArtikelRepository {

    private final EntityManager manager;

    public JpaArtikelRepository(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Optional<Artikel> findById(long id) {
        return Optional
                .ofNullable(
                        manager
                                .find(
                                        Artikel.class,
                                        id
                                )
                );
    }
}
