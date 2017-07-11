package com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.Modalità;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MBattaglia;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.IModalità;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MGiocatore;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MPersonaggio;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MRegoleDiSoddisfazione;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MZonaDiCaccia;
import com.ficheralezzi.fantasygo.Utils.RisultatoFinale;


import java.util.Observable;
import java.util.Observer;

public class MModalitàNearPvE extends IntentService implements IModalità, Observer {

    private RisultatoFinale risultatoFinale = null;
    private String idPersonaggioScelto;

    public MModalitàNearPvE(String idPersonaggioScelto){
        super("MModalitàNearPvE");
        this.idPersonaggioScelto = idPersonaggioScelto;
        this.risultatoFinale = new RisultatoFinale();
    }

    public boolean createModalità(){
        return true;
    }


    public boolean enterRegolediSoddisfazione(){
        return true;
    }

    public void avviaModalità(){

        MPersonaggio personaggioScelto = MGiocatore.getSingletoneInstance().getOnePersonaggio(this.idPersonaggioScelto);
        MZonaDiCaccia.getSingletoneInstance().init();
        MZonaDiCaccia.getSingletoneInstance().update(0,0);
        MZonaDiCaccia.getSingletoneInstance().getMostri().toString();

        // il while controlla sul risultatoFinale gli avanzamenti dell'oro guadagnato fino a quel momento
        // e al giocatore le caratteristiche del combattente
        while(!MRegoleDiSoddisfazione.getSingletoneInstance().regoleSoddisfatte(this.risultatoFinale.getOro(),
            this.risultatoFinale.getPuntiEsperienza(), this.risultatoFinale.getNumeroDiBattaglie(),
                this.risultatoFinale.getPuntiFerita())){

            String idmostro = MZonaDiCaccia.getSingletoneInstance().getOneMostro().getId();
            MBattaglia.getSingletoneInstance().init(personaggioScelto, MZonaDiCaccia.getSingletoneInstance().getOneMostroById(idmostro));
            MBattaglia.getSingletoneInstance().elaboraBattaglia();

            this.updateRisultatoFinale(MZonaDiCaccia.getSingletoneInstance().getRicompensa(idmostro),
                    MZonaDiCaccia.getSingletoneInstance().getRicompensa(idmostro)*2,
                    MBattaglia.getSingletoneInstance().getRisultato().getPuntiferitaA());
            Log.i("MMod", MBattaglia.getSingletoneInstance().getCombattenteA().toString());
        }
        this.terminaModalità();
    }

    public RisultatoFinale terminaModalità(){ return this.risultatoFinale;}

    public void update(Observable observable, Object o) {

        startService(new Intent(this, MModalitàNearPvE.class));

    }

    private void updateRisultatoFinale(int oro, int puntiEsperienza, int puntiFerita){

        this.risultatoFinale.setOro(this.risultatoFinale.getOro() + oro);
        this.risultatoFinale.setPuntiEsperienza(this.risultatoFinale.getPuntiEsperienza() + puntiEsperienza);
        this.risultatoFinale.setNumeroDiBattaglie(this.risultatoFinale.getNumeroDiBattaglie() + 1);
        this.risultatoFinale.setPuntiFerita(puntiFerita);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        MZonaDiCaccia.getSingletoneInstance().update(MGiocatore.getSingletoneInstance().getLatitude(),
                MGiocatore.getSingletoneInstance().getLongitude());
    }
}

