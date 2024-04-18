package solver;

import observer.IObservedObject;
import observer.IObserver;

import java.util.ArrayList;

public abstract class CSolver implements IObservedObject {

    protected double T0, Tk, dt;
    protected double L, m, M, kt, g;
    protected double Alpha0, Omega0;
    protected ArrayList<IObserver> observers;

    public CSolver(double Tk, double Alpha0, double Omega0) {
        T0 = 0.;              // Czas startowy [s]
        this.Tk = Tk;         // Czas końcowy [s]
        dt = 0.01;            // Krok czasowy [s]
        L  = 1.0;             // Długość wahadła [m]
        m  = 0.1;             // Masa wahadła [kg]
        M  = 0.0;             // Moment zewnętrzny [Nm]
        kt = 1.0;             // Tarcie wiskotyczne [Ns/m]
        g  = 9.81;            // Przyspieszenie ziemskie [m/s2]
        this.Alpha0 = Alpha0;   // Początkowy kąt obrotu (od pionu)
        this.Omega0 = Omega0; // Początkowa prędkość obr.
        observers = new ArrayList<IObserver>();
    }

    protected ESolverType type = null;

    protected void pendulum(double [] aa, double [] dd){
        dd[1] = M/(m*L*L)-kt*aa[1]+g/L*Math.sin(aa[0]);
        dd[0] = aa[1];
    }

    public void solve() {
        double[] Y0 = new double[2];
        double[] Y1;
        Y0[0] = Alpha0;
        Y0[1] = Omega0;
        notifyObservers(new CStepData(0.0, Alpha0, Omega0));
        for (double dd = T0 + dt; dd <= Tk; dd += dt) {
            Y1 = doStep(Y0);
            Y0[0] = Y1[0];
            Y0[1] = Y1[1];
            notifyObservers(new CStepData(dd, Y1[0], Y1[1]));
        }
    }

    abstract public CSolver getSolver();
    abstract protected double[] doStep(double [] Y);
    @Override
    public void addObserver(IObserver observer) {
        observers.add(observer);
    }
    @Override
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }
    @Override
    public void notifyObservers(CStepData data) {
        for (IObserver obs : observers) {
            obs.update(data);
        }
    }

}
