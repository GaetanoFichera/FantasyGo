package com.ficheralezzi.fantasygo.Model;

public class MCaratteristiche {

    private int livello = 0;
    private int puntiFerita = 0;
    private int puntiFeritaMax = 0;
    private int attaccoFisico = 0;
    private int difesaFisica = 0;
    private int attaccoMagico = 0;
    private int difesaMagica = 0;
    private String abilità = null;
    private String tipoAttBase = null;
    private int velocitadAttacco = 0;
    private int caricaAbilità = 0;
    private int caricaMaxAbilità = 0;

    public MCaratteristiche(int livello, int puntiFerita, int puntiFeritaMax, int attaccoFisico, int difesaFisica, int attaccoMagico,
                     int difesaMagica, String abilità, int caricaAbilità, int caricaMaxAbilità, String tipoAttBase, int velocitadAttacco ){

        this.livello = livello;
        this.puntiFerita = puntiFerita;
        this.puntiFeritaMax = puntiFeritaMax;
        this.attaccoFisico = attaccoFisico;
        this.difesaFisica = difesaFisica;
        this.attaccoMagico = attaccoMagico;
        this.difesaMagica = difesaMagica;
        this.abilità = abilità;
        this.caricaAbilità = caricaAbilità;
        this.caricaMaxAbilità = caricaMaxAbilità;
        this.tipoAttBase = tipoAttBase;
        this.velocitadAttacco = velocitadAttacco;
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

    public int getDifesaMagica() {
        return difesaMagica;
    }

    public void setDifesaMagica(int difesaMagico) {
        this.difesaMagica = difesaMagico;
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

    public int getDifesaFisica() {
        return difesaFisica;
    }

    public void setDifesaFisica(int difesaFisico) {
        this.difesaFisica = difesaFisico;
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

    public int getPuntiFeritaMax() {
        return puntiFeritaMax;
    }

    public void setPuntiFeritaMax(int puntiFeritaMax) {
        this.puntiFeritaMax = puntiFeritaMax;
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

    public void diminuisciPuntiFerita(int valore) {
        if(valore <= this.puntiFerita){
            this.puntiFerita = this.puntiFerita - valore;
        } else{
            this.puntiFerita = 0;
        }
    }

    public void aumentaPuntiFerita(int valore){
        if(this.puntiFerita + valore <= puntiFeritaMax){
            this.puntiFerita = this.puntiFerita + valore;
        } else this.puntiFerita = this.puntiFeritaMax;
    }

    @Override
    public String toString() {
        return "MCaratteristiche{" +
                "livello=" + livello +
                ", puntiFerita=" + puntiFerita +
                ", puntiFeritaMax=" + puntiFeritaMax +
                ", attaccoFisico=" + attaccoFisico +
                ", difesaFisico=" + difesaFisica +
                ", attaccoMagico=" + attaccoMagico +
                ", difesaMagico=" + difesaMagica +
                ", abilità='" + abilità + '\'' +
                ", tipoAttBase='" + tipoAttBase + '\'' +
                ", velocitadAttacco=" + velocitadAttacco +
                ", caricaAbilità=" + caricaAbilità +
                ", caricaMaxAbilità=" + caricaMaxAbilità +
                '}';
    }
}
