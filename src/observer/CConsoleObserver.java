package observer;

import solver.CStepData;

public class CConsoleObserver implements IObserver{
    @Override
    public void update(CStepData data) {
        System.out.printf(String.format("%-6.4f.    %-6.4f   %-6.4f\n",data.T,data.Alpha,data.Omega));
    }
}
