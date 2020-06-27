package be.vdab.allesvoordekeuken.repositories;

import be.vdab.allesvoordekeuken.domain.Artikel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@DataJpaTest
@Import(JpaArtikelRepository.class)
@Sql("/insertArtikel.sql")

public class JpaArtikelRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    JpaArtikelRepository jpaArtikelRepository;

    private static final String ARTIKELS = "artikels";

    JpaArtikelRepositoryTest(JpaArtikelRepository jpaArtikelRepository) {
        this.jpaArtikelRepository = jpaArtikelRepository;
    }

    private long idVanTestArtikel() {
        return super.jdbcTemplate
                .queryForObject(
                        "select id from artikels where naam='test'",
                        Long.class
                );
    }

    @Test
    void findById() {
        assertThat(
                jpaArtikelRepository
                        .findById(
                                idVanTestArtikel()
                        ).get()
                        .getNaam()
        ).isEqualTo(
                "test"
        );
    }

    @Test
    void findByOnbestaandeId() {
        assertThat(
                jpaArtikelRepository
                        .findById(-1)
        ).isNotPresent();
    }

    @Test
    void create() {
        Artikel artikel = new Artikel("test2", BigDecimal.ONE, BigDecimal.TEN);
        jpaArtikelRepository.create(artikel);
        assertThat(
                super.countRowsInTableWhere(
                        ARTIKELS,
                        "id=" + artikel.getId()
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

}
