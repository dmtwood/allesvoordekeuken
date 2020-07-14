package be.vdab.allesvoordekeuken.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@Access( AccessType.FIELD )
public class Korting {
    private int vanafAantal;
    private BigDecimal percentage;

    protected Korting() {
    }

    public Korting(int vanafAantal, BigDecimal kortingsPercentage) {
        this.vanafAantal = vanafAantal;
        this.percentage = kortingsPercentage;
    }

    public int getVanafAantal() {
        return vanafAantal;
    }

    public BigDecimal getKortingsPercentage() {
        return percentage;
    }

    // equals & hashCode for use within a Set representing options for not more than one Artikel
    //
    @Override
    public boolean equals( Object object) {
        if ( object instanceof Korting) {
            var andereKorting = (Korting) object;
            return andereKorting.vanafAantal == vanafAantal;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return vanafAantal;
    }

}
