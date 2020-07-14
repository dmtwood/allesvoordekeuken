package be.vdab.allesvoordekeuken.domain;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class KortingTest {
    private Korting korting1, nogEensKorting1, korting2;
    @BeforeEach
    void beforeEach() {
        korting1 = new Korting( 1, BigDecimal.ONE );
        nogEensKorting1 = new Korting( 1, BigDecimal.ONE );
        korting2 = new Korting( 2, BigDecimal.TEN );
    }

    // equals ir Overriden to test on vanafAantal
    @Test
    void gelijkeKortingenHebbenGelijkeVanafAantallen() {
        assertThat( korting1 ).isEqualTo( nogEensKorting1 );
    }

    @Test
    void verschillendeKortingenHebbenAndereVanafAantallen() {
        assertThat( korting1 ).isNotEqualTo( korting2 );
    }

    @Test
    void eenKortingKanGeenNullZijn() {
        assertThat( korting1 ).isNotNull();
    }

    @Test
    void eenKortingKanGeenAnderObjectZijn() {
        assertThat( korting1 ).isNotEqualTo( "" );
    }

    @Test
    void gelijkeKortingenHebbenGelijkeHashCodes() {
        assertThat( korting1 ).hasSameHashCodeAs( nogEensKorting1 );
    }

    // may work may not, not tested yet
//    @Test
//    void gelijkeKortingenHebbenGelijkeHashCodes() {
//        assertThat( korting1.hashCode() ).isEqualTo( nogEensKorting1.hashCode() );
//    }





}
