/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gyak2;

import java.util.Comparator;

/**
 *
 * @author hallgato
 */
public class LetszamSzerint implements Comparator<Rendezveny>{

    public LetszamSzerint() {
    }

    @Override
    public int compare(Rendezveny o1, Rendezveny o2) {
        return o2.getResztvevokSzama() - o1.getResztvevokSzama();
    }    
}

