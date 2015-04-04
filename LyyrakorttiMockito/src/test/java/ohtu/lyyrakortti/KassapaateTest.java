package ohtu.lyyrakortti;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class KassapaateTest {

    private Kassapaate kassa;
    private Lyyrakortti kortti;

    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = mock(Lyyrakortti.class);
    }

    @Test
    public void kortiltaVelotetaanHintaJosRahaaOn() {
        when(kortti.getSaldo()).thenReturn(10);
        kassa.ostaLounas(kortti);

        verify(kortti, times(1)).getSaldo();
        verify(kortti).osta(eq(Kassapaate.HINTA));
    }

    @Test
    public void kortiltaEiVelotetaJosRahaEiRiita() {
        when(kortti.getSaldo()).thenReturn(4);
        kassa.ostaLounas(kortti);

        verify(kortti, times(1)).getSaldo();
        verify(kortti, times(0)).osta(anyInt());
    }

    @Test
    public void kortilleLadataanPositiivinenSummaLisaa() {
        final int ladattuRaha = 15;
        kassa.lataa(kortti, ladattuRaha);

        verify(kortti, times(1)).lataa(ladattuRaha);
    }

    @Test
    public void kortilleLadataanNegatiivinenSummaEiLisaa() {
        final int ladattuRaha = -1231;
        kassa.lataa(kortti, ladattuRaha);

        verify(kortti, times(0)).lataa(ladattuRaha);
    }
}
