package be.vdab.allesvoordekeuken.repositories;

import be.vdab.allesvoordekeuken.domain.Artikel;
import org.hibernate.type.EntityType;

import javax.persistence.EntityManager;
import java.util.List;
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

    @Override
    public void create(Artikel artikel) {
        manager
                .persist(
                        artikel
                );
    }

    @Override
    public List<Artikel> findByNameContaining(String zoekString) {
        return manager.createNamedQuery(
                "Artikel.findByNameContaining", Artikel.class
        ).setParameter(
                "zoals", '%' + zoekString + '%'
        ).getResultList();
    }
}
