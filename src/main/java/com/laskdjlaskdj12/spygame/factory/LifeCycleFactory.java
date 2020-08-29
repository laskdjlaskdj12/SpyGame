package com.laskdjlaskdj12.spygame.factory;

import com.laskdjlaskdj12.spygame.content.lifecycle.BornLifeCycle;
import com.laskdjlaskdj12.spygame.content.lifecycle.DieLifeCycle;
import com.laskdjlaskdj12.spygame.content.lifecycle.ILifecycle;
import com.laskdjlaskdj12.spygame.content.lifecycle.PerinitLifeCycle;
import com.laskdjlaskdj12.spygame.status.LIFE_STATUS;

public class LifeCycleFactory {
    public static ILifecycle createLifeCycle(LIFE_STATUS born){
        //Todo: 라이프 사이클을 만드는 switch case문을 작성할것
        switch(born){
            case BORN:
                return new BornLifeCycle();
            case PREINIT:
                return new PerinitLifeCycle();
            case DIE:
                return new DieLifeCycle();
        }
        return null;
    }
}
