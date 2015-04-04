package ohtu.verkkokauppa;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public final class KauppaTest {

    private Kauppa kauppa;
    private Varasto varasto;
    private Pankki pankki;
    private Viitegeneraattori viitegeneraattori;

    @Before
    public void setUp() {
        varasto = mock(Varasto.class);
        pankki = mock(Pankki.class);
        viitegeneraattori = mock(Viitegeneraattori.class);
        kauppa = new Kauppa(varasto, pankki, viitegeneraattori);
    }

    @Test
    public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan() {
        when(viitegeneraattori.uusi()).thenReturn(42);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu("pekka", "12345");

        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(), anyInt());
    }

    @Test
    public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaanOikeillaArvoilla() {
        final String henkilo = "pekka";
        final String tiliNumero = "12345";
        final int hinta = 5;
        when(viitegeneraattori.uusi()).thenReturn(42);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", hinta));

        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu(henkilo, tiliNumero);

        verify(pankki).tilisiirto(eq(henkilo), anyInt(), eq(tiliNumero), anyString(), eq(hinta));
    }

    @Test
    public void ostossaKaksiTuotetta() {
        final String henkilo = "pekka";
        final String tiliNumero = "12345";
        final int hinta1 = 5;
        final int hinta2 = 123223;
        when(viitegeneraattori.uusi()).thenReturn(42);

        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", hinta1));

        when(varasto.saldo(2)).thenReturn(123123);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "kulta", hinta2));

        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(2);
        kauppa.tilimaksu(henkilo, tiliNumero);

        verify(pankki).tilisiirto(eq(henkilo), anyInt(), eq(tiliNumero), anyString(), eq(hinta1 + hinta2));
    }

    @Test
    public void ostossaKaksiSamaaTuotetta() {
        final String henkilo = "pekka";
        final String tiliNumero = "12345";
        final int hinta = 33;
        when(viitegeneraattori.uusi()).thenReturn(42);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", hinta));

        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(1);
        kauppa.tilimaksu(henkilo, tiliNumero);

        verify(pankki).tilisiirto(eq(henkilo), anyInt(), eq(tiliNumero), anyString(), eq(hinta * 2));
    }

    @Test
    public void ostossaKaksiTuotettaToinenLoppu() {
        final String henkilo = "pekka";
        final String tiliNumero = "12345";
        final int hinta1 = 1;
        final int hinta2 = 123223;
        when(viitegeneraattori.uusi()).thenReturn(42);

        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", hinta1));

        when(varasto.saldo(2)).thenReturn(0);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "kulta", hinta2));

        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.lisaaKoriin(2);
        kauppa.tilimaksu(henkilo, tiliNumero);

        verify(pankki).tilisiirto(eq(henkilo), anyInt(), eq(tiliNumero), anyString(), eq(hinta1));
    }

    @Test
    public void ostoNollaaEdellisen() {
        final String henkilo = "pekka";
        final String tiliNumero = "12345";
        final int hinta1 = 1;
        final int hinta2 = 123223;
        when(viitegeneraattori.uusi()).thenReturn(42);

        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", hinta1));

        when(varasto.saldo(2)).thenReturn(10);
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "kulta", hinta2));

        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);

        kauppa.tilimaksu(henkilo, tiliNumero);

        verify(pankki).tilisiirto(eq(henkilo), anyInt(), eq(tiliNumero), anyString(), eq(hinta1));

        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(2);
        kauppa.tilimaksu(henkilo, tiliNumero);

        verify(pankki).tilisiirto(eq(henkilo), anyInt(), eq(tiliNumero), anyString(), eq(hinta2));
    }

    @Test
    public void ostoPyytaaUudenViitenumeron() {
        final String henkilo = "pekka";
        final String tiliNumero = "12345";
        final int hinta1 = 1;
        final int viite = 123;
        when(viitegeneraattori.uusi()).thenReturn(viite).thenReturn(viite + 1).thenReturn(viite + 2);

        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", hinta1));

        for (int i = 0; i < 3; i++) {
            kauppa.aloitaAsiointi();
            kauppa.lisaaKoriin(1);
            kauppa.tilimaksu(henkilo, tiliNumero);
            verify(pankki, times(1)).tilisiirto(anyString(), eq(viite + i), anyString(), anyString(), anyInt());
        }
    }

    @Test
    public void ostoksenPalautusKutsuuVarastoaOikein() {
        final int hinta = 5;
        final Tuote maito = new Tuote(1, "maito", hinta);
        when(viitegeneraattori.uusi()).thenReturn(42);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(maito);

        kauppa.aloitaAsiointi();
        kauppa.lisaaKoriin(1);
        kauppa.poistaKorista(maito.getId());

        verify(varasto).palautaVarastoon(eq(maito));
    }
}
