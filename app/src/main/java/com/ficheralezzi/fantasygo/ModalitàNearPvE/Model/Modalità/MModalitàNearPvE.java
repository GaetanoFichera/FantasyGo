package com.ficheralezzi.fantasygo.ModalitàNearPvE.Model.Modalità;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MBattaglia;
import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MCombattente;
import com.ficheralezzi.fantasygo.ModalitàNearPvE.Model.IModalità;
import com.ficheralezzi.fantasygo.ModalitàNearPvE.Model.MGiocatore;
import com.ficheralezzi.fantasygo.ModalitàNearPvE.Model.MPersonaggio;
import com.ficheralezzi.fantasygo.ModalitàNearPvE.Model.MRegoleDiSoddisfazione;
import com.ficheralezzi.fantasygo.ModalitàNearPvE.Model.MZonaDiCaccia;
import com.ficheralezzi.fantasygo.ModalitàNearPvE.Model.RisultatoFinale;


import java.util.Observable;
import java.util.Observer;

public class MModalitàNearPvE extends IntentService implements IModalità, Observer {

    private RisultatoFinale risultatoFinale = null;
    private int idPersonaggioScelto;

    public MModalitàNearPvE(int idPersonaggioScelto){
        super("MModalitàNearPvE");
        this.idPersonaggioScelto = idPersonaggioScelto;
        this.risultatoFinale = new RisultatoFinale();
    }

    public void avviaModalità(){

        MPersonaggio personaggioScelto = MGiocatore.getSingletoneInstance().getOnePersonaggio(this.idPersonaggioScelto);
        MZonaDiCaccia.getSingletoneInstance().init();

        // il while controlla sul risultatoFinale gli avanzamenti dell'oro guadagnato fino a quel momento
        // e al giocatore le caratteristiche del combattente
        while(!MRegoleDiSoddisfazione.getSingletoneInstance().regoleSoddisfatte(this.risultatoFinale.getOro(),
            this.risultatoFinale.getPuntiEsperienza(), this.risultatoFinale.getNumeroDiBattaglie(),
                this.risultatoFinale.getPuntiFerita())){

            MBattaglia.getSingletoneInstance().init(personaggioScelto, MZonaDiCaccia.getSingletoneInstance().getOneMostro());
            Log.i("SonoinAvvModnelwhile", "ok");

            MBattaglia.getSingletoneInstance().elaboraBattaglia();

            this.updateRisultatoFinale(MZonaDiCaccia.getSingletoneInstance().getRicompensa(),
                    MZonaDiCaccia.getSingletoneInstance().getRicompensa()*2,
                    MBattaglia.getSingletoneInstance().getRisultato().getPuntiferitaA());
        }

        Log.i("uscito", "uscito");

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
        this.risultatoFinale.setPuntiFerita(this.risultatoFinale.getPuntiFerita() + puntiFerita);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        MZonaDiCaccia.getSingletoneInstance().update(MGiocatore.getSingletoneInstance().getLatitude(),
                MGiocatore.getSingletoneInstance().getLongitude());
    }
}

