package sse.ngts.testrobot.app.interf;

import sse.ngts.testrobot.app.ui.conditionsheet.ConditionSheetScreen;

public interface ISheetController
{
    public String getSheetName();

    public ConditionSheetScreen getUI();

    public boolean isApplSheetWorking();

    public void saveSheetInfo();

}
