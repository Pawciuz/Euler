package observer;

import solver.CStepData;

public interface IObserver {
    void update(CStepData data);
}
