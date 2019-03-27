package jp.desktopgame.mycomponent;

import java.awt.Point;
import javax.swing.event.ChangeListener;

/**
 * MVCのMとなるクラス。
 */
public interface MyModel {
    void addChangeListener(ChangeListener listener);

    void removeChangeListener(ChangeListener listener);

    Point getPathAt(int index);

    int getPathCount();
}
