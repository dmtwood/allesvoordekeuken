package be.vdab.allesvoordekeuken.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("NF")
public class NonFoodArtikel extends Artikel {
    private Integer garantie;

    protected NonFoodArtikel() {
    }

    public NonFoodArtikel(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs, int maandenGarantie) {
        super(naam, aankoopprijs, verkoopprijs);
        this.garantie = maandenGarantie;
    }

    public int getMaandenGarantie() {
        return garantie;
    }
}
