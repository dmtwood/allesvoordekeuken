package be.vdab.allesvoordekeuken.repositories;

import be.vdab.allesvoordekeuken.domain.FoodArtikel;
import be.vdab.allesvoordekeuken.domain.Korting;
import be.vdab.allesvoordekeuken.domain.NonFoodArtikel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Import(JpaArtikelRepository.class)
@Sql("/insertArtikel.sql")

public class JpaArtikelRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

    private final JpaArtikelRepository jpaArtikelRepository;
    private EntityManager entityManager;

    private static final String ARTIKELS = "artikels";

    JpaArtikelRepositoryTest(JpaArtikelRepository jpaArtikelRepository, EntityManager entityManager) {
        this.jpaArtikelRepository = jpaArtikelRepository;
        this.entityManager = entityManager;
    }

    private long idVanTestArtikel() {
        return super.jdbcTemplate.queryForObject(
                "select id from artikels where naam='test'", Long.class);
    }

    private long idVanTestFoodArtikel() {
        return super.jdbcTemplate
                .queryForObject(
                        "select id from artikels where naam='testfood'",
                        Long.class
                );
    }

    private long idVanTestNonFoodArtikel() {
        return super.jdbcTemplate
                .queryForObject(
                        "select id from artikels where naam='testnonfood'",
                        Long.class
                );
    }

    @Test
    void findFoodArtikelById() {
        assertThat(
                (
                        (FoodArtikel)
                                jpaArtikelRepository
                                        .findById(
                                                idVanTestFoodArtikel()
                                        ).get()
                ).getHoudbaarheid()
        ).isEqualTo(
                7
        );
    }

    @Test
    void findNonFoodArtikelById() {
        assertThat(
                (
                        (NonFoodArtikel)
                                jpaArtikelRepository
                                    .findById(
                                        idVanTestNonFoodArtikel()
                                    ).get()
                ).getMaandenGarantie()
        ).isEqualTo(
                30
        );
    }

    @Test
    void findByOnbestaandeId() {
        assertThat(
                jpaArtikelRepository
                        .findById(-1)
        ).isNotPresent();
    }

    // with one sort of Artikel, before food & nonfood seperation
//    @Test
//    void create() {
//        Artikel artikel = new Artikel("test2", BigDecimal.ONE, BigDecimal.TEN);
//        jpaArtikelRepository.create(artikel);
//        assertThat(
//                super.countRowsInTableWhere(
//                        ARTIKELS,
//                        "id=" + artikel.getId()
//                )
//        ).isOne();
//    }

    @Test
    void createFoodArtikel() {
        FoodArtikel foodArtikel = new FoodArtikel("testfood2", BigDecimal.ONE, BigDecimal.TEN, 14);
        jpaArtikelRepository.create( foodArtikel );
        assertThat(
                super.countRowsInTableWhere(
                        ARTIKELS,
                        "id='" + foodArtikel.getId() + "'"
                )
        ).isOne();
    }

    @Test
    void createNonFoodArtikel() {
        NonFoodArtikel nonFoodArtikel = new NonFoodArtikel("testnonfood2", BigDecimal.ONE, BigDecimal.TEN, 24);
        jpaArtikelRepository.create( nonFoodArtikel );
        assertThat(
                super.countRowsInTableWhere(
                        ARTIKELS,
                        "id='" + nonFoodArtikel.getId() + "'"
                )
        ).isOne();
    }


    @Test
    void findByNameContaining() {
        assertThat(
                jpaArtikelRepository.findByNameContaining("es")
        ).hasSize(
                super.jdbcTemplate.queryForObject(
                        "select count(*) from artikels where naam like '%es%'",
                        Integer.class
                )
        ).extracting(
                artikel -> artikel.getNaam().toLowerCase()
        ).allSatisfy(
                naam -> assertThat(naam).contains("es")
        ).isSorted();
    }

    @Test
    void algemenePrijsverhoging(){
        assertThat(
        jpaArtikelRepository.algemenePrijsverhoging(BigDecimal.TEN)
        ).isEqualTo(
                super.countRowsInTable(ARTIKELS)
        );

        assertThat(
                super.jdbcTemplate.queryForObject(
                        "select verkoopprijs from Artikels where id=?",
                        BigDecimal.class,
                        idVanTestArtikel()
                )
        ).isEqualByComparingTo("132");
    }

    @Test
    void kortingenLezen() {
        assertThat(
                jpaArtikelRepository.findById(
                        idVanTestFoodArtikel()
                ).get().getKortingen()
        ).containsOnly(
                new Korting(1, BigDecimal.TEN)
        );
    }

}
