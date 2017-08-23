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

    private static final String TAG = "MModalitàNearPvE";
    private RisultatoFinale risultatoFinale = null;
    private String idPersonaggioScelto;
    private static MModalitàNearPvE singletoneinstance = null;
    private Thread mSimulazioneBattaglie = null;
    private boolean isRunning = false;
    private boolean battagliaInCorso = false;

    public MModalitàNearPvE(){
        super("MModalitàNearPvE");
    }

    public void init(String idPersonaggioScelto){
        if(this.idPersonaggioScelto == null & this.risultatoFinale == null){
            this.idPersonaggioScelto = idPersonaggioScelto;
            this.risultatoFinale = new RisultatoFinale();
        }
    }

    public static MModalitàNearPvE getSingletoneInstance() {

        if(singletoneinstance == null){
            //Log.d(TAG, "no");
            singletoneinstance = new MModalitàNearPvE();
        } //else Log.d(TAG, "si");

        return singletoneinstance;
    }

    public boolean createModalità(){
        return true;
    }

    public void enterRegolediSoddisfazione(int oroMinimoScelto, int puntiEsperienzaMinimiScelti, int numeroDiBattaglieScelte, int puntiFeritaMinimiScelti){

        MRegoleDiSoddisfazione.getSingletoneInstance().destroy();
        MRegoleDiSoddisfazione.getSingletoneInstance().init(oroMinimoScelto, puntiEsperienzaMinimiScelti, numeroDiBattaglieScelte, puntiFeritaMinimiScelti);
    }

    //da far fare in background
    public void avviaModalità(){

        final MPersonaggio personaggioScelto = MGiocatore.getSingletoneInstance().getOnePersonaggioById(this.idPersonaggioScelto);
        MZonaDiCaccia.getSingletoneInstance().init();
        MZonaDiCaccia.getSingletoneInstance().update(0,0);
        MZonaDiCaccia.getSingletoneInstance().getMostri().toString();

        Log.i(TAG, "ModalitàNearPvE avviata");

        isRunning = true;



        mSimulazioneBattaglie = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!MRegoleDiSoddisfazione.getSingletoneInstance().regoleSoddisfatte(risultatoFinale.getOro(),
                        risultatoFinale.getPuntiEsperienza(), risultatoFinale.getNumeroDiBattaglie(),
                        risultatoFinale.getPuntiFerita()) && isRunning ){
                    battagliaInCorso = true;

                    String idmostro = MZonaDiCaccia.getSingletoneInstance().getOneMostro().getId();
                    MBattaglia.getSingletoneInstance().init(personaggioScelto, MZonaDiCaccia.getSingletoneInstance().getOneMostroById(idmostro));
                    MBattaglia.getSingletoneInstance().elaboraBattaglia();

                    updateRisultatoFinale(MZonaDiCaccia.getSingletoneInstance().getRicompensa(idmostro),
                            MZonaDiCaccia.getSingletoneInstance().getRicompensa(idmostro)*2,
                            MBattaglia.getSingletoneInstance().getRisultato().getPuntiferitaA());

                    MZonaDiCaccia.getSingletoneInstance().reviveMostroById(idmostro);

                    Log.i(TAG, MBattaglia.getSingletoneInstance().getCombattenteA().toString());

                    MBattaglia.getSingletoneInstance().destroy();

                    battagliaInCorso = false;
                }
                if (isRunning) terminaModalità();
            }
        });

        mSimulazioneBattaglie.start();

        /*
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
        */
    }

    public void terminaModalità(){

        isRunning = false;
        // mSimulazioneBattaglie.interrupt();

        Log.i(TAG, "Modalità terminata");
        Log.i(TAG, "Risultato finale: " + risultatoFinale.toString());


        /**
         * continua a controllare finche non c'è alcuna battaglia in corso, a quel punto avvia il reset della modalità con stampa
         * del risultato
         */
        Thread controlloBattagliaInCorso = new Thread(new Runnable() {
            @Override
            public void run() {
                while (battagliaInCorso){
                    //non fare niente
                }
                Log.i(TAG, risultatoFinale.toString());
                destroy();
            }
        });

        controlloBattagliaInCorso.start();
    }

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

    public void destroy(){
        if(singletoneinstance != null) {
            singletoneinstance = null;
            idPersonaggioScelto = null;
            risultatoFinale = null;
        }
    }

    public String getIdPersonaggioScelto() {
        return idPersonaggioScelto;
    }

    public void setIdPersonaggioScelto(String idPersonaggioScelto) {
        this.idPersonaggioScelto = idPersonaggioScelto;
    }

    public RisultatoFinale getRisultatoFinale() {
        return risultatoFinale;
    }

    public void setRisultatoFinale(RisultatoFinale risultatoFinale) {
        this.risultatoFinale = risultatoFinale;
    }

    public boolean isBattagliaInCorso() {
        return battagliaInCorso;
    }

    public void setBattagliaInCorso(boolean battagliaInCorso) {
        this.battagliaInCorso = battagliaInCorso;
    }
}

