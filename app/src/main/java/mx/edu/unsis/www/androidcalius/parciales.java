package mx.edu.unsis.www.androidcalius;

/**
 * Created by luis on 07/06/2017.
 */

public class parciales {
    public parciales(){};
    static private Double p1Materia1;
    static private Double p1Materia2;
    static private Double p1Materia3;
    static private Double p1Materia4;
    static private Double p1Materia5;

    static private Double p2Materia1;
    static private Double p2Materia2;
    static private Double p2Materia3;
    static private Double p2Materia4;
    static private Double p2Materia5;

    static private Double p3Materia1;
    static private Double p3Materia2;
    static private Double p3Materia3;
    static private Double p3Materia4;
    static private Double p3Materia5;

    static private Double orMateria1;
    static private Double orMateria2;
    static private Double orMateria3;
    static private Double orMateria4;
    static private Double orMateria5;

    static private boolean Materia1;
    static private boolean Materia2;
    static private boolean Materia3;
    static private boolean Materia4;
    static private boolean Materia5;

    public  boolean isMateria1() {
        return Materia1;
    }

    public  void setMateria1(boolean materia1) {
        Materia1 = materia1;
    }

    public  boolean isMateria2() {
        return Materia2;
    }

    public  void setMateria2(boolean materia2) {
        Materia2 = materia2;
    }

    public  boolean isMateria3() {
        return Materia3;
    }

    public  void setMateria3(boolean materia3) {
        Materia3 = materia3;
    }

    public  boolean isMateria4() {
        return Materia4;
    }

    public  void setMateria4(boolean materia4) {
        Materia4 = materia4;
    }

    public  boolean isMateria5() {
        return Materia5;
    }

    public  void setMateria5(boolean materia5) {
        Materia5 = materia5;
    }


    public Double getP1Materia1() {
        return p1Materia1;
    }

    public void setP1Materia1(Double p1Materia1) {

        this.p1Materia1 = p1Materia1;
    }

    public Double getP1Materia2() {
        return p1Materia2;
    }

    public void setP1Materia2(Double p1Materia2) {
        this.p1Materia2 = p1Materia2;
    }

    public Double getP1Materia3() {
        return p1Materia3;
    }

    public void setP1Materia3(Double p1Materia3) {
        this.p1Materia3 = p1Materia3;
    }

    public Double getP1Materia4() {
        return p1Materia4;
    }

    public void setP1Materia4(Double p1Materia4) {
        this.p1Materia4 = p1Materia4;
    }

    public Double getP1Materia5() {
        return p1Materia5;
    }

    public void setP1Materia5(Double p1Materia5) {
        this.p1Materia5 = p1Materia5;
    }

    public Double getP2Materia1() {
        return p2Materia1;
    }

    public void setP2Materia1(Double p2Materia1) {
        this.p2Materia1 = p2Materia1;
    }

    public Double getP2Materia2() {
        return p2Materia2;
    }

    public void setP2Materia2(Double p2Materia2) {
        this.p2Materia2 = p2Materia2;
    }

    public Double getP2Materia3() {
        return p2Materia3;
    }

    public void setP2Materia3(Double p2Materia3) {
        this.p2Materia3 = p2Materia3;
    }

    public Double getP2Materia4() {
        return p2Materia4;
    }

    public void setP2Materia4(Double p2Materia4) {
        this.p2Materia4 = p2Materia4;
    }

    public Double getP2Materia5() {
        return p2Materia5;
    }

    public void setP2Materia5(Double p2Materia5) {
        this.p2Materia5 = p2Materia5;
    }

    public Double getP3Materia1() {
        return p3Materia1;
    }

    public void setP3Materia1(Double p3Materia1) {
        this.p3Materia1 = p3Materia1;
    }

    public Double getP3Materia2() {
        return p3Materia2;
    }

    public void setP3Materia2(Double p3Materia2) {
        this.p3Materia2 = p3Materia2;
    }

    public Double getP3Materia3() {
        return p3Materia3;
    }

    public void setP3Materia3(Double p3Materia3) {
        this.p3Materia3 = p3Materia3;
    }

    public Double getP3Materia4() {
        return p3Materia4;
    }

    public void setP3Materia4(Double p3Materia4) {
        this.p3Materia4 = p3Materia4;
    }

    public Double getP3Materia5() {
        return p3Materia5;
    }

    public void setP3Materia5(Double p3Materia5) {
        this.p3Materia5 = p3Materia5;
    }

    public Double getOrMateria1() {
        return orMateria1;
    }

    public void setOrMateria1(Double orMateria1) {
        this.orMateria1 = orMateria1;
    }

    public Double getOrMateria2() {
        return orMateria2;
    }

    public void setOrMateria2(Double orMateria2) {
        this.orMateria2 = orMateria2;
    }

    public Double getOrMateria3() {
        return orMateria3;
    }

    public void setOrMateria3(Double orMateria3) {
        this.orMateria3 = orMateria3;
    }

    public Double getOrMateria4() {
        return orMateria4;
    }

    public void setOrMateria4(Double orMateria4) {
        this.orMateria4 = orMateria4;
    }

    public Double getOrMateria5() {
        return orMateria5;
    }

    public void setOrMateria5(Double orMateria5) {
        this.orMateria5 = orMateria5;
    }


    int validarCali(double calif) {
        if(calif>10 || calif <0)
        {
            return 1;
        }else
            return -1;
    }

}
