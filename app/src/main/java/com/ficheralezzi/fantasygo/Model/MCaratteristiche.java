package com.ficheralezzi.fantasygo.Model;


public class MCaratteristiche {

    private int livello = 0;
    private int puntiFerita = 0;
    private int attaccoFisico = 0;
    private int difesaFisico = 0;
    private int attaccoMagico = 0;
    private int difesaMagico = 0;
    private int velocitadAttacco = 0;
    private String abilità = null;
    private int caricaAbilità = 0;
    private int caricaMaxAbilità = 0;
    private String tipoAttBase = null;

    public MCaratteristiche(int livello, int puntiFerita, int attaccoFisico, int difesaFisico, int attaccoMagico,
                     int difesaMagico, String abilità, int caricaAbilità, int caricaMaxAbilità, String tipoAttBase ){

        if(livello == 0 & puntiFerita == 0 & attaccoFisico == 0 & difesaFisico == 0 &
           attaccoMagico == 0 & difesaMagico == 0 & abilità == null & caricaAbilità == 0 &
            caricaMaxAbilità == 0 & tipoAttBase == null ){

            this.livello = livello;
            this.puntiFerita = puntiFerita;
            this.attaccoFisico = attaccoFisico;
            this.difesaFisico = difesaFisico;
            this.attaccoMagico = attaccoMagico;
            this.difesaMagico = difesaMagico;
            this.abilità = abilità;
            this.caricaAbilità = caricaAbilità;
            this.caricaMaxAbilità = caricaMaxAbilità;
            this.tipoAttBase = tipoAttBase;
        }
    }

    public int getLivello() {
        return livello;
    }

    public void setLivello(int livello) {
        this.livello = livello;
    }

        public String getTipoAttBase() {
        return tipoAttBase;
    }

    public void setTipoAttBase(String tipoAttBase) {
        this.tipoAttBase = tipoAttBase;
    }

    public int getDifesaMagico() {
        return difesaMagico;
    }

    public void setDifesaMagico(int difesaMagico) {
        this.difesaMagico = difesaMagico;
    }

    public String getAbilità() {
        return abilità;
    }

    public void setAbilità(String abilità) {
        this.abilità = abilità;
    }

    public int getAttaccoMagico() {
        return attaccoMagico;
    }

    public void setAttaccoMagico(int attaccoMagico) {
        this.attaccoMagico = attaccoMagico;
    }

    public int getDifesaFisico() {
        return difesaFisico;
    }

    public void setDifesaFisico(int difesaFisico) {
        this.difesaFisico = difesaFisico;
    }

    public int getAttaccoFisico() {
        return attaccoFisico;
    }

    public void setAttaccoFisico(int attaccoFisico) {
        this.attaccoFisico = attaccoFisico;
    }

    public int getPuntiFerita() {
        return puntiFerita;
    }

    public void setPuntiFerita(int puntiFerita) {
        this.puntiFerita = puntiFerita;
    }

    public int getVelocitadAttacco() {
        return velocitadAttacco;
    }

    public void setVelocitadAttacco(int velocitadAttacco) {
        this.velocitadAttacco = velocitadAttacco;
    }

    public int getCaricaAbilità() {
        return caricaAbilità;
    }

    public void setCaricaAbilità(int caricaAbilità) {
        this.caricaAbilità = caricaAbilità;
    }

    public int getCaricaMaxAbilità() {
        return caricaMaxAbilità;
    }

    public void setCaricaMaxAbilità(int caricaMaxAbilità) {
        this.caricaMaxAbilità = caricaMaxAbilità;
    }

    public void azzeraCaricaAbilità(){
        this.caricaAbilità = 0;
    }

    public void incrementaCaricaAbilità(){
        this.caricaAbilità++;
    }
}
