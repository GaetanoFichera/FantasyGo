package com.ficheralezzi.fantasygo.Model;


public class MCaratteristiche {

    private int livello = 0;
    private int puntiFerita = 0;
    private int attaccoFisico = 0;
    private int difesaFisico = 0;
    private int attaccoMagico = 0;
    private int difesaMagico = 0;

    public int getVelocitaAttacco() {
        return velocitaAttacco;
    }

    public void setVelocitaAttacco(int velocitaAttacco) {
        this.velocitaAttacco = velocitaAttacco;
    }

    private int velocitaAttacco = 0;
    private String abilità = null;
    private String tipoAttBase = null;
    private static MCaratteristiche singletoneinstance = null;

    public MCaratteristiche() {
    }

    public void init(int livello, int puntiFerita, int attaccoFisico, int difesaFisico, int attaccoMagico,
                     int difesaMagico, String abilità, String tipoAttBase ){

        if(livello == 0 & puntiFerita == 0 & attaccoFisico == 0 & difesaFisico == 0 &
           attaccoMagico == 0 & difesaMagico == 0 & abilità == null & tipoAttBase == null ){

            this.livello = livello;
            this.puntiFerita = puntiFerita;
            this.attaccoFisico = attaccoFisico;
            this.difesaFisico = difesaFisico;
            this.attaccoMagico = attaccoMagico;
            this.difesaMagico = difesaMagico;
            this.abilità = abilità;
            this.tipoAttBase = tipoAttBase;
        }
    }

    public static MCaratteristiche getSingletonistance() {

        if(singletoneinstance == null){
            singletoneinstance = new MCaratteristiche();
        }

        return singletoneinstance;
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
}
