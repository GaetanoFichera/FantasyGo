package com.ficheralezzi.fantasygo.Home.Activity;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.MGiocatore;
import com.ficheralezzi.fantasygo.ModalitaNearPvE.Model.Modalità.MModalitàNearPvE;
import com.ficheralezzi.fantasygo.R;
import com.ficheralezzi.fantasygo.Utils.NetworkManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by gaetano on 31/07/17.
 */

public class AvanzamentoModalitaNearPvEFragment extends Fragment {
    private static final String TAG = "AvanzModNearPvEFragment";
    private static final int TIME_VIEW_REFRESH = 500;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private String mIdPersonaggioCorrente;
    private TextView mTextViewPuntiFeritaCorrenti;
    private TextView mTextViewPuntiFeritaMassimi;
    private TextView mTextViewNumeroDiBattaglieAffrontate;
    private TextView mTextViewOroPosseduto;
    private Thread mControlloModalitàTerminata;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_avvia_modalita_mod_near_pve_in_corso, container, false);
        ViewGroup avanzamentoModNearPvE =  (ViewGroup) inflater.inflate(R.layout.fragment_avanzamento_mod_near_pve, null);
        ((FrameLayout) view.findViewById(R.id.avanzamento_mod_near_pve_container)).addView(avanzamentoModNearPvE);

        mIdPersonaggioCorrente = MGiocatore.getSingletoneInstance().getPersonaggi().get(0).getId();
        System.out.println(MGiocatore.getSingletoneInstance().getPersonaggi().get(0).getId());
        ((TextView) view.findViewById(R.id.nomePersonaggio)).setText(MGiocatore.getSingletoneInstance().getOnePersonaggioById(mIdPersonaggioCorrente).getNome());
    
        mTextViewPuntiFeritaCorrenti = ((TextView) view.findViewById(R.id.puntiFeritaCorrenti));
        mTextViewPuntiFeritaMassimi = ((TextView) view.findViewById(R.id.puntiFeritaMassimi));
        mTextViewNumeroDiBattaglieAffrontate = ((TextView) view.findViewById(R.id.numeroDiBattaglieAffrontate));
        mTextViewOroPosseduto = ((TextView) view.findViewById(R.id.oroPosseduto));
        setButtonListener(view);
        controlloTermineModNearPvE();

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        mTimer.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            mTimer = new Timer();
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    updateView();
                }
            };
            mTimer.schedule(mTimerTask, TIME_VIEW_REFRESH, TIME_VIEW_REFRESH);
        } catch (IllegalStateException e){
            android.util.Log.i(TAG, "Resume error");
        }
    }

    /**
     * Aggiorna i parametri della View inerenti alle statistiche della Modalità in Corso
     */
    private void updateView(){
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if (MModalitàNearPvE.getSingletoneInstance().getRisultatoFinale() != null) {
                    mTextViewPuntiFeritaCorrenti.setText(String.valueOf(MModalitàNearPvE.getSingletoneInstance().getRisultatoFinale().getPuntiFerita()));
                    mTextViewPuntiFeritaMassimi.setText(String.valueOf(MGiocatore.getSingletoneInstance().getOnePersonaggioById(mIdPersonaggioCorrente).getCaratteristiche().getPuntiFeritaMax()));
                    mTextViewNumeroDiBattaglieAffrontate.setText(String.valueOf(MModalitàNearPvE.getSingletoneInstance().getRisultatoFinale().getNumeroDiBattaglie()));
                    mTextViewOroPosseduto.setText(String.valueOf(MModalitàNearPvE.getSingletoneInstance().getRisultatoFinale().getOro()));
                }
            }

        });
    }

    private void setButtonListener(View v){
        Button endModButton = ((Button) v.findViewById(R.id.button_end_mod));
        endModButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                terminaModalitaNearPvE();
            }
        });
    }

    /**
     * Metodo utilizzato quando viene premuto il pulsante "Termina"
     */
    private void terminaModalitaNearPvE(){
        if(mControlloModalitàTerminata != null){
            if(mControlloModalitàTerminata.isAlive()){
                mControlloModalitàTerminata.interrupt();
                Log.i(TAG, "Thread Controllo Modalità Interrotto");
                Log.i(TAG, String.valueOf(mControlloModalitàTerminata.isInterrupted()));
            }

        }
        MModalitàNearPvE.getSingletoneInstance().stopModalità();

        Log.i(TAG, "Mod Terminata");

        showDialogEndModNearPvE(R.string.dialog_testo, getActivity());
    }

    /**
     * Metodo che avvia un thread parallelo che controlla:
     *  1) se la Modalità Near PvE è in ancora in fase di RUNNING;
     *  2) se è ancora presente la Connessione ad Internet;
     * se uno di questi due casi viene a mancare allora visualizza un AlertDialog con un messaggio
     * inerente al caso in questione
     */
    private void controlloTermineModNearPvE(){

        mControlloModalitàTerminata = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isRunning = true;

                final Activity activity = getActivity();

                while (isRunning && NetworkManager.isOnline(activity)){
                    if(MModalitàNearPvE.getSingletoneInstance().getRisultatoFinale() != null){
                        if(!MModalitàNearPvE.getSingletoneInstance().isRunning()){
                            isRunning = false;
                        }
                    }else isRunning = false;
                }

                //Personalizzo il Testo del messagio nell'AlertDialog in base all'Evento
                int resStringIdMessage = 0;

                //Controllo se il motivo per cui il While si è interrotto è legato alla mancanza di Connessione ad Internet
                if(!NetworkManager.isOnline(activity) && MModalitàNearPvE.getSingletoneInstance().getRisultatoFinale() != null) {
                    resStringIdMessage = R.string.dialog_testo_internet_disconnesso;
                    if (MModalitàNearPvE.getSingletoneInstance().isRunning()) {
                        MModalitàNearPvE.getSingletoneInstance().stopModalità();
                    }
                }else{
                    resStringIdMessage = R.string.dialog_testo;
                }

                Log.i(TAG, "Mod Terminata in automatico");

                if (!Thread.interrupted()){

                    final int finalResStringIdMessage = resStringIdMessage;

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((SwipeHomeActivity) activity).moveToFirstPage();
                            showDialogEndModNearPvE(finalResStringIdMessage, activity);
                        }
                    });
                }

            }
        });

        mControlloModalitàTerminata.start();
    }

    /**
     * Metodo per la Creazione di un AlertDialog che attende il termine dell'ultima Battaglia per
     * visualizzare il tasto "OK" per uscire
     * @param resStringId Messaggio da visualizzare nell'AlertDialog
     */
    private void showDialogEndModNearPvE(final int resStringId, final Activity activity){

        ((SwipeHomeActivity) activity).moveToFirstPage();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.dialog_titolo)
                .setMessage(R.string.dialog_testo_mod_in_corso)
                .setPositiveButton(R.string.string_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MModalitàNearPvE.getSingletoneInstance().resetModalità();
                        ((SwipeHomeActivity) activity).stopAvanzamentoModNearPvE();
                    }
                });

        final AlertDialog dialog = builder.create();

        dialog.show();

        dialog.getButton(Dialog.BUTTON_POSITIVE).setClickable(false);
        dialog.getButton(Dialog.BUTTON_POSITIVE).setTextColor(activity.getResources().getColor(R.color.FGcolorAccentGreyfy));

        Thread controlloTermineUltimaBattaglia = new Thread(new Runnable() {
            @Override
            public void run() {

            while(MModalitàNearPvE.getSingletoneInstance().isRunning()){

            }
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Log.i(TAG, "Id res string messaggio AlertDialog: " + resStringId);

                    dialog.setMessage(activity.getResources().getString(resStringId));
                    dialog.getButton(Dialog.BUTTON_POSITIVE).setClickable(true);
                    dialog.getButton(Dialog.BUTTON_POSITIVE).setTextColor(activity.getResources().getColor(R.color.FGcolorAccent));
                }
            });
            }
        });

        controlloTermineUltimaBattaglia.start();
    }
}
