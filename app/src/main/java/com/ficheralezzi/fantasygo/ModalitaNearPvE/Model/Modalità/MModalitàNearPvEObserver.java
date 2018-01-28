package com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.Modalità;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ficheralezzi.fantasygo.ElaboraBattaglia.Model.MBattaglia;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.IModalità;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MGiocatore;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MPersonaggio;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.RegoleDiSoddisfazione.MRegoleDiSoddisfazione;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MZonaDiCaccia;
import com.ficheralezzi.fantasygo.Utils.RisultatoFinale;


import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class MModalitàNearPvEObserver extends IntentService implements IModalità, Observer {

    private static final String TAG = "MModNearPvEObserver";
    private static final int MOD_STATE_NONE = 0;
    private static final int MOD_STATE_INITIALIZED = 1;
    private static final int MOD_STATE_DECIDED_RULES = 2;
    private static final int MOD_STATE_STARTED = 3;
    private static final int MOD_STATE_RUNNING_BATTLE_NOT_IN_PROGRESS = 4;
    private static final int MOD_STATE_RUNNING_BATTLE_IN_PROGRESS = 5;
    private static final int MOD_STATE_TERMINATED = 6;

    private RisultatoFinale risultatoFinale = null;
    private String idPersonaggioScelto;
    private static MModalitàNearPvEObserver singletoneinstance = null;
    private Thread mThreadEsecuzioneModalità = null;
    private int mState = 0;
    private boolean mStopEsterno = false;

    public MModalitàNearPvEObserver(){
        super("MModalitàNearPvEObserver");
    }

    public void init(){
        if(this.idPersonaggioScelto == null & this.risultatoFinale == null & this.mState == MOD_STATE_NONE){
            this.risultatoFinale = new RisultatoFinale();
            this.mState = MOD_STATE_INITIALIZED;
        }
    }

    public static MModalitàNearPvEObserver getSingletoneInstance() {

        if(singletoneinstance == null){
            //Log.d(TAG, "no");
            singletoneinstance = new MModalitàNearPvEObserver();
        } //else Log.d(TAG, "si");

        return singletoneinstance;
    }

    public boolean createModalità(){
        return true;
    }

    public void selezionaPersonaggio(String idPersonaggioScelto) {
        this.idPersonaggioScelto = idPersonaggioScelto;
    }

    /*
    public void enterRegolediSoddisfazione(int oroMinimoScelto, int puntiEsperienzaMinimiScelti, int numeroDiBattaglieScelte, int puntiFeritaMinimiScelti){

        MRegoleDiSoddisfazione.getSingletoneInstance().destroy();
        MRegoleDiSoddisfazione.getSingletoneInstance().init(oroMinimoScelto, puntiEsperienzaMinimiScelti, numeroDiBattaglieScelte, puntiFeritaMinimiScelti);

        mState = MOD_STATE_DECIDED_RULES;
    }
    */

    public void enterRegolediSoddisfazione(HashMap<String, Integer> valori){

        MRegoleDiSoddisfazione.getSingletoneInstance().destroy();
        MRegoleDiSoddisfazione.getSingletoneInstance().init();
        MRegoleDiSoddisfazione.getSingletoneInstance().setRegoleDiSoddisfazione(valori);

        mState = MOD_STATE_DECIDED_RULES;
    }

    //da far fare in background
    public void avviaModalità(){

        final MPersonaggio personaggioScelto = MGiocatore.getSingletoneInstance().getOnePersonaggioById(this.idPersonaggioScelto);
        MZonaDiCaccia.getSingletoneInstance().init();
        MZonaDiCaccia.getSingletoneInstance().update(MGiocatore.getSingletoneInstance().getLatitude(),MGiocatore.getSingletoneInstance().getLongitude());

        mState = MOD_STATE_STARTED;
        Log.i(TAG, "ModalitàNearPvE avviata");

        /**
         * Utilizziamo un thread parallelo per l'esecuzione della modalità così da non bloccare
         * il dispositivo
         */
        mThreadEsecuzioneModalità = new Thread(new Runnable() {
            @Override
            public void run() {
                /**
                 * Il While termina :
                 *  1) alla soddisfazione delle Regole
                 *      oppure
                 *  2) per una Interruzione esterna
                 */
                while(!MRegoleDiSoddisfazione.getSingletoneInstance().regoleSoddisfatte() && !mStopEsterno){

                    mState = MOD_STATE_RUNNING_BATTLE_IN_PROGRESS;

                    MZonaDiCaccia.getSingletoneInstance().update(MGiocatore.getSingletoneInstance().getLatitude(), MGiocatore.getSingletoneInstance().getLongitude());

                    String idMostro = MZonaDiCaccia.getSingletoneInstance().getOneMostro().getId();

                    MBattaglia.getSingletoneInstance().init(personaggioScelto, MZonaDiCaccia.getSingletoneInstance().getOneMostroById(idMostro));
                    MBattaglia.getSingletoneInstance().elaboraBattaglia();

                    updateRisultatoFinale(MZonaDiCaccia.getSingletoneInstance().getRicompensa(idMostro),
                            MZonaDiCaccia.getSingletoneInstance().getRicompensa(idMostro)*2,
                            MBattaglia.getSingletoneInstance().getRisultato().getPuntiferitaA());

                    MZonaDiCaccia.getSingletoneInstance().reviveMostroById(idMostro);

                    Log.i(TAG, MBattaglia.getSingletoneInstance().getCombattenteA().toString());

                    MBattaglia.getSingletoneInstance().destroy();

                    mState = MOD_STATE_RUNNING_BATTLE_NOT_IN_PROGRESS;
                }
                terminaModalità();
            }
        });

        mThreadEsecuzioneModalità.start();
    }

    /**
     * Metodo utilizzato dal Controllore per interrompere in anticipo la Modalità, mediante l'attributo
     * mStopEsterno
     */
    public void stopModalità(){
        mStopEsterno = true;
    }

    public void terminaModalità(){

        mState = MOD_STATE_TERMINATED;

        Log.i(TAG, "Modalità terminata");
        Log.i(TAG, "Risultato finale: " + risultatoFinale.toString());

        aggiornaBottinoPersonaggio();
    }

    private void aggiornaBottinoPersonaggio(){
        MGiocatore.getSingletoneInstance().getOnePersonaggioById(this.idPersonaggioScelto).increaseOro(this.risultatoFinale.getOro());
        MGiocatore.getSingletoneInstance().getOnePersonaggioById(this.idPersonaggioScelto).increasePuntiEsperienza(this.risultatoFinale.getPuntiEsperienza());
    }

    public void resetModalità(){
        destroy();
    }

    public void update(Observable observable, Object o) {

        startService(new Intent(this, MModalitàNearPvEObserver.class));

    }

    private void updateRisultatoFinale(int oro, int puntiEsperienza, int puntiFerita){

        if(MModalitàNearPvEObserver.getSingletoneInstance().getRisultatoFinale() != null){
            this.risultatoFinale.setOro(this.risultatoFinale.getOro() + oro);
            this.risultatoFinale.setPuntiEsperienza(this.risultatoFinale.getPuntiEsperienza() + puntiEsperienza);
            this.risultatoFinale.setNumeroDiBattaglie(this.risultatoFinale.getNumeroDiBattaglie() + 1);
            this.risultatoFinale.setPuntiFerita(puntiFerita);
        }else Log.i(TAG, "Risultato Finale di Mod è null");

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
            mState = 0;
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

    public int getmState() {
        return mState;
    }

    public void setmState(int mState) {
        this.mState = mState;
    }

    public boolean ismStopEsterno() {
        return mStopEsterno;
    }

    public void setmStopEsterno(boolean mStopEsterno) {
        this.mStopEsterno = mStopEsterno;
    }

    public boolean isRunning(){
        return mState == MOD_STATE_RUNNING_BATTLE_NOT_IN_PROGRESS || mState == MOD_STATE_RUNNING_BATTLE_IN_PROGRESS;
    }
}

