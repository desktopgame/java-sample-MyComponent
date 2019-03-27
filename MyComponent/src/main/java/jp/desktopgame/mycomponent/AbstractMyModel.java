package jp.desktopgame.mycomponent;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

/**
 * MyModelのベースクラスです。
 * ここでは最小限の実装をサポートします。
 * この例ではイベントしかサポートしていません。
 */
public abstract class AbstractMyModel implements MyModel {
    private EventListenerList listenerList;

    public AbstractMyModel() {
        this.listenerList = new EventListenerList();
    }

    @Override
    public void addChangeListener(ChangeListener listener) {
        listenerList.add(ChangeListener.class, listener);
    }

    @Override
    public void removeChangeListener(ChangeListener listener) {
        listenerList.remove(ChangeListener.class, listener);
    }

    /**
     * イベントを発行します。
     * @param e
     */
    protected void fireStateChanged(ChangeEvent e) {
        for (ChangeListener listener : listenerList.getListeners(ChangeListener.class)) {
            listener.stateChanged(e);
        }
    }
}
