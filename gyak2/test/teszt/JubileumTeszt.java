/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teszt;

import gyak2.PTEsResztvevo;
import gyak2.Rendezveny;
import gyak2.Resztvevo;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hallgato
 */
public class JubileumTeszt {

    private final String CIM = "cim";
    private final String IDOPONT = "dátum";
    private final int JEGY_AR = 1000;
    private final String NEV = "nev";
    private final double KEDVEZMENY_SZAZALEK = 10;

    public JubileumTeszt() {
    }

    private Rendezveny rendezveny;
    private Resztvevo resztvevo;

    @Before
    public void setUp() {
        rendezveny = new Rendezveny(CIM, IDOPONT, JEGY_AR);
        resztvevo = new Resztvevo(NEV);
        PTEsResztvevo.setKedvezmenySzazalek(KEDVEZMENY_SZAZALEK);
    }

    @Test
    public void rendezvenyTeszt() {
        // feltételezés: nincs senki és nincs bevétel
        assertTrue(rendezveny.getResztvevokSzama() == 0);
        assertEquals(rendezveny.getBevetel(), 0);

        resztvevo.penztKap(1500);
        rendezveny.resztVesz(resztvevo);

        // feltételezés: nem igaz, hogy nincs bevétel
        assertFalse(rendezveny.getBevetel() == 0);

        // feltételezés: részt tud venni, vagyis van már résztvevő 
        // és van bevétel
        // (ha egyenlőséget vizsgálunk, akkor az assertEquals és az 
        // assertTrue is jó.
        
        assertEquals(rendezveny.getResztvevokSzama(), 1);
        assertTrue(rendezveny.getBevetel() == JEGY_AR);

        // feltételezés: nem tud még egyszer részt venni, mert nincs elég pénze
        rendezveny.resztVesz(resztvevo);

        assertFalse(rendezveny.getResztvevokSzama() > 1);
        
        // Eddig csak annyit láttunk, hogy ha egy valaki részt tud venni, 
        // akkor helyes a résztvevőszám és a bevétel. 
        // Azt, hogy nem csak sima értékadásról, hanem összegzésről van szó,
        // csak akkor látjuk, ha még egy résztvevő is részt vesz a rendezvényen.
        // De mivel a kedvezményt is szeretnénk ellenőrizni, ezért legyen most
        // a résztvevő egy PTE polgár. 
        // Mivel a korábbi résztvevőre már nincs szükségünk ebben a metódusban,
        // nyugodtan felülírhatjuk. 
        // Mivel csak lokálisan használjuk, akár be is égethetjük az adatait.

        resztvevo = new PTEsResztvevo("nev", "azonosito");

        resztvevo.penztKap(900);
        rendezveny.resztVesz(resztvevo);

        // feltételezés: már ketten vannak és nőtt a bevétel
        assertEquals(rendezveny.getResztvevokSzama(), 2);
        assertTrue(rendezveny.getBevetel() == 1900);

    }

    @Test
    public void resztvevoTeszt() {
        Resztvevo resztvevo = new Resztvevo("nev");
        Rendezveny rendezveny2 = new Rendezveny("cim2", IDOPONT, JEGY_AR);

        resztvevo.penztKap(JEGY_AR);

        assertTrue(rendezveny.getResztvevokSzama() == 0);

        assertTrue(resztvevo.getRendezvenyek().isEmpty());

        rendezveny.resztVesz(resztvevo);

        assertEquals(rendezveny.getResztvevokSzama(), 1);
        assertTrue(resztvevo.getRendezvenyek().size() == 1);

        resztvevo.penztKap(JEGY_AR);

        // felt. be tud menni
        assertTrue(resztvevo.belephet(rendezveny2));

        rendezveny2.resztVesz(resztvevo);

        assertTrue(resztvevo.getRendezvenyek().size() == 2);

        assertEquals(rendezveny2.getResztvevokSzama(), 1);

    }
}